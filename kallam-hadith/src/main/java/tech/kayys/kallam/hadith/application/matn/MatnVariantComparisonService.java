package tech.kayys.kallam.hadith.application.matn;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import tech.kayys.kallam.hadith.domain.HadithId;
import tech.kayys.kallam.hadith.domain.HadithOccurrence;
import tech.kayys.kallam.hadith.port.HadithCorpus;

/**
 * Produces a deterministic, token-level comparison of source-attested Matn occurrences.
 * Normalization is a comparison projection only; original source text is never rewritten.
 */
public final class MatnVariantComparisonService {
    private static final int MAX_TOKENS = 512;
    private static final Pattern ARABIC_MARKS = Pattern.compile("[\\u064B-\\u065F\\u0670\\u06D6-\\u06ED]");
    private static final Pattern NON_LETTER_OR_DIGIT = Pattern.compile("[^\\p{L}\\p{N}]+");
    private final HadithCorpus corpus;

    public MatnVariantComparisonService(HadithCorpus corpus) {
        this.corpus = Objects.requireNonNull(corpus, "corpus");
    }

    public MatnComparisonResult compare(HadithId hadithId) {
        Objects.requireNonNull(hadithId, "hadithId");
        var occurrences = corpus.findOccurrences(hadithId).stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(o -> o.id().value()))
                .toList();
        boolean truncated = occurrences.stream().anyMatch(o -> tokens(normalize(o.originalArabicMatn())).length > MAX_TOKENS);
        var selected = occurrences.stream().map(o -> new SourceTokens(o, limitedTokens(normalize(o.originalArabicMatn())))).toList();
        if (selected.isEmpty()) {
            return new MatnComparisonResult(hadithId, null, List.of(),
                    "NFKC + Arabic mark/tatweel removal + token LCS (max " + MAX_TOKENS + " tokens)", truncated);
        }

        var baseline = selected.get(0);
        var variants = new ArrayList<MatnVariant>();
        for (int i = 0; i < selected.size(); i++) {
            var current = selected.get(i);
            var differences = i == 0 ? List.<MatnDifference>of() : diff(baseline.tokens(), current.tokens());
            var occurrence = current.occurrence();
            variants.add(new MatnVariant(occurrence.id(), occurrence.collectionId(), occurrence.editionId(),
                    occurrence.sourceLocator(), occurrence.originalArabicMatn(),
                    String.join(" ", current.tokens()), differences));
        }
        return new MatnComparisonResult(hadithId, baseline.occurrence().id(), variants,
                "NFKC + Arabic mark/tatweel removal + token LCS (max " + MAX_TOKENS + " tokens)", truncated);
    }

    static String normalize(String text) {
        String value = Normalizer.normalize(text, Normalizer.Form.NFKC)
                .replace("\u0640", "");
        value = ARABIC_MARKS.matcher(value).replaceAll("");
        value = value.replace('أ', 'ا').replace('إ', 'ا').replace('آ', 'ا').replace('ٱ', 'ا')
                .replace('ى', 'ي');
        return NON_LETTER_OR_DIGIT.matcher(value).replaceAll(" ").trim().replaceAll("\\s+", " ");
    }

    private static String[] tokens(String normalized) {
        return normalized.isBlank() ? new String[0] : normalized.split(" ");
    }

    private static String[] limitedTokens(String normalized) {
        var all = tokens(normalized);
        return all.length <= MAX_TOKENS ? all : Arrays.copyOf(all, MAX_TOKENS);
    }

    private static List<MatnDifference> diff(String[] baseline, String[] variant) {
        int n = baseline.length, m = variant.length;
        int[][] lcs = new int[n + 1][m + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                lcs[i][j] = baseline[i].equals(variant[j])
                        ? 1 + lcs[i + 1][j + 1]
                        : Math.max(lcs[i + 1][j], lcs[i][j + 1]);
            }
        }
        var changes = new ArrayList<MatnDifference>();
        int i = 0, j = 0;
        while (i < n || j < m) {
            if (i < n && j < m && baseline[i].equals(variant[j])) {
                i++; j++; continue;
            }
            if (j < m && (i == n || lcs[i][j + 1] > lcs[i + 1][j])) {
                changes.add(new MatnDifference(MatnDifference.Operation.INSERTION, i, j, null, variant[j]));
                j++;
            } else if (i < n) {
                changes.add(new MatnDifference(MatnDifference.Operation.DELETION, i, j, baseline[i], null));
                i++;
            } else {
                changes.add(new MatnDifference(MatnDifference.Operation.INSERTION, i, j, null, variant[j]));
                j++;
            }
        }
        return List.copyOf(changes);
    }

    private record SourceTokens(HadithOccurrence occurrence, String[] tokens) {}
}

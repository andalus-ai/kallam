package tech.kayys.kallam.hadith.application.matn;

import java.util.List;
import tech.kayys.kallam.hadith.domain.HadithId;
import tech.kayys.kallam.hadith.domain.OccurrenceId;

/** Comparison output. The baseline is a deterministic reference, not an authoritative text. */
public record MatnComparisonResult(
        HadithId hadithId,
        OccurrenceId baselineOccurrenceId,
        List<MatnVariant> variants,
        String comparisonMethod,
        boolean truncated) {
    public MatnComparisonResult {
        if (hadithId == null) throw new IllegalArgumentException("hadithId must not be null");
        variants = List.copyOf(variants == null ? List.of() : variants);
        comparisonMethod = comparisonMethod == null ? "NFKC Arabic search projection + token LCS" : comparisonMethod;
    }
}

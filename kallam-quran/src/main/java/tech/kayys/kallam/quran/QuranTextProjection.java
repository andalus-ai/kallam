package tech.kayys.kallam.quran;
import java.util.List;
import java.util.Objects;
/** Derived display/search/analysis value with span alignment; source remains immutable. */
public record QuranTextProjection(String projectionId, QuranTextRepresentation source,
        QuranTextRepresentation derived, UnicodeNormalizationPolicy normalizationPolicy,
        List<TextAlignmentSegment> alignment) {
    public QuranTextProjection {
        if (projectionId == null || projectionId.isBlank()) throw new IllegalArgumentException("projectionId must not be blank");
        projectionId = projectionId.trim();
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(derived, "derived");
        Objects.requireNonNull(normalizationPolicy, "normalizationPolicy");
        alignment = List.copyOf(Objects.requireNonNull(alignment, "alignment"));
        if (source.kind() == TextRepresentationKind.DISPLAY || source.kind() == TextRepresentationKind.SEARCH_NORMALIZED
                || source.kind() == TextRepresentationKind.ANALYSIS)
            throw new IllegalArgumentException("projection source must be an attested source representation");
        if (derived.kind() != TextRepresentationKind.DISPLAY && derived.kind() != TextRepresentationKind.SEARCH_NORMALIZED
                && derived.kind() != TextRepresentationKind.ANALYSIS)
            throw new IllegalArgumentException("derived value must be display, search or analysis");
        if (!projectionId.equals(derived.transformationId()))
            throw new IllegalArgumentException("derived transformationId must equal projectionId");
        if (!source.representationId().equals(derived.sourceRepresentationId()))
            throw new IllegalArgumentException("derived representation must refer to the source representation identifier");
        if (alignment.isEmpty()) throw new IllegalArgumentException("alignment must not be empty");
        int previousSourceEnd = -1, previousDerivedEnd = 0;
        for (TextAlignmentSegment segment : alignment) {
            if (!segment.sourceRange().fits(source.text())) throw new IllegalArgumentException("source alignment range exceeds source text");
            if (!segment.derivedRange().fits(derived.text())) throw new IllegalArgumentException("derived alignment range exceeds derived text");
            if (segment.sourceRange().startInclusive() < previousSourceEnd
                    || segment.derivedRange().startInclusive() != previousDerivedEnd)
                throw new IllegalArgumentException("alignment must be source-monotonic and cover every derived code point");
            previousSourceEnd = segment.sourceRange().endExclusive();
            previousDerivedEnd = segment.derivedRange().endExclusive();
        }
        if (previousDerivedEnd != derived.text().codePointCount(0, derived.text().length()))
            throw new IllegalArgumentException("alignment must cover the complete derived text");
    }
}

package tech.kayys.kallam.quran;
import java.util.Objects;
/** Immutable text value with explicit identity and representation semantics. */
public record QuranTextRepresentation(String representationId, String text, TextRepresentationKind kind,
        String scriptProfile, String transformationId, String sourceRepresentationId) {
    public QuranTextRepresentation {
        representationId = required(representationId, "representationId");
        if (text == null || text.isEmpty()) throw new IllegalArgumentException("text must not be empty");
        Objects.requireNonNull(kind, "kind");
        scriptProfile = required(scriptProfile, "scriptProfile");
        transformationId = optionalTrim(transformationId);
        sourceRepresentationId = optionalTrim(sourceRepresentationId);
        if (kind == TextRepresentationKind.SOURCE_TRANSCRIPTION
                && (transformationId != null || sourceRepresentationId != null))
            throw new IllegalArgumentException("source transcription cannot derive from another representation");
        if (kind == TextRepresentationKind.UTHMANI_ORTHOGRAPHY
                && ((transformationId == null) != (sourceRepresentationId == null)))
            throw new IllegalArgumentException("derived Uthmani representation must identify both transformation and source");
        if (kind != TextRepresentationKind.SOURCE_TRANSCRIPTION
                && kind != TextRepresentationKind.UTHMANI_ORTHOGRAPHY
                && (transformationId == null || sourceRepresentationId == null))
            throw new IllegalArgumentException("derived representation must identify its transformation and source");
    }
    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
    private static String optionalTrim(String v) { return v == null ? null : v.trim(); }
}

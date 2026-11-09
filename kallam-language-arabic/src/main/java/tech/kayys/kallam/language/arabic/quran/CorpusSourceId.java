package tech.kayys.kallam.language.arabic.quran;
public record CorpusSourceId(String value) {
    public CorpusSourceId {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank");
        value = value.trim();
    }
}

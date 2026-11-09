package tech.kayys.kallam.language.arabic.quran;

/** Canonical surah/ayah coordinates; edition-specific locators belong to the source record. */
public record VerseReference(int surahNumber, int ayahNumber) {
    public VerseReference {
        if (surahNumber < 1 || surahNumber > 114) throw new IllegalArgumentException("surahNumber must be in [1,114]");
        if (ayahNumber < 1) throw new IllegalArgumentException("ayahNumber must be positive");
    }
}

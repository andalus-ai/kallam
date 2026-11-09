package tech.kayys.kallam.quran;

import java.util.Optional;

/** Read port for verified, edition-aware Qur'anic text. Implementations own storage and import policy. */
public interface QuranTextCorpus {
    Optional<QuranVerseText> findVerse(EditionId editionId, VerseReference reference);
}

package tech.kayys.kallam.language.arabic.quran;
/** Unicode policy for a derived projection. Source transcription is never normalized in place. */
public enum UnicodeNormalizationPolicy {
    PRESERVE_CODE_POINTS, NFC, NFD, SEARCH_PROFILE_DEFINED, NONE
}

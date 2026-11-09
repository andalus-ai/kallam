package tech.kayys.kallam.hadith.domain.isnad;
/** Ordered narrator mention; raw source wording is retained regardless of identity resolution. */
public record IsnadLink(int position, String originalNarratorText, NarratorId narratorId, ResolutionStatus resolutionStatus) {
 public enum ResolutionStatus { RESOLVED, UNRESOLVED, AMBIGUOUS, NOT_ASSESSED }
 public IsnadLink {
  if(position<1) throw new IllegalArgumentException("position must be >= 1");
  if(originalNarratorText==null||originalNarratorText.isBlank()) throw new IllegalArgumentException("originalNarratorText must not be blank");
  if(resolutionStatus==null) throw new IllegalArgumentException("resolutionStatus must not be null");
  originalNarratorText=originalNarratorText.trim();
  if(resolutionStatus==ResolutionStatus.RESOLVED && narratorId==null) throw new IllegalArgumentException("resolved link requires narratorId");
  if(resolutionStatus!=ResolutionStatus.RESOLVED && narratorId!=null) throw new IllegalArgumentException("only resolved links may carry narratorId");
 }
}
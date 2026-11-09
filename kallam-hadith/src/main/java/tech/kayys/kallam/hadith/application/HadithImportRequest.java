package tech.kayys.kallam.hadith.application;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import tech.kayys.kallam.hadith.domain.*;
/** One immutable source-edition import submission. */
public record HadithImportRequest(String batchId, String sourceName, String sourceVersion,
 String sourceChecksum, Instant acquiredAt, HadithCollection collection, HadithEdition edition,
 List<HadithOccurrence> occurrences) {
 public HadithImportRequest {
  batchId=required(batchId,"batchId"); sourceName=required(sourceName,"sourceName");
  sourceVersion=required(sourceVersion,"sourceVersion"); sourceChecksum=required(sourceChecksum,"sourceChecksum");
  Objects.requireNonNull(acquiredAt,"acquiredAt"); Objects.requireNonNull(collection,"collection");
  Objects.requireNonNull(edition,"edition"); occurrences=List.copyOf(Objects.requireNonNull(occurrences,"occurrences"));
  if(occurrences.isEmpty()) throw new IllegalArgumentException("occurrences must not be empty");
 }
 private static String required(String v,String f){if(v==null||v.isBlank())throw new IllegalArgumentException(f+" must not be blank");return v.trim();}
}

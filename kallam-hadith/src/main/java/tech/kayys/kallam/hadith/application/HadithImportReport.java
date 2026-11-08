package tech.kayys.kallam.hadith.application;
import java.util.List;
/** Outcome of validating and applying one import batch. */
public record HadithImportReport(String batchId, Status status, int received, int persisted, List<Issue> issues) {
 public HadithImportReport { issues=List.copyOf(issues==null?List.of():issues); }
 public enum Status { IMPORTED, ALREADY_IMPORTED, REJECTED }
 public record Issue(String recordId,String code,String message) {}
}

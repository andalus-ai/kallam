package tech.kayys.kallam.hadith.application;
import static org.junit.jupiter.api.Assertions.*;
import java.net.URI; import java.time.Instant; import java.util.List;
import org.junit.jupiter.api.Test;
import tech.kayys.kallam.hadith.domain.*;
import tech.kayys.kallam.hadith.port.HadithImportStore;
class HadithImportServiceTest {
 private final CollectionId collectionId=new CollectionId("bukhari");
 private final EditionId editionId=new EditionId("bukhari-ed-1");
 @Test void importsConsistentBatchAndPreservesOriginalText(){
  MemoryStore store=new MemoryStore(); HadithImportReport report=new HadithImportService(store).importBatch(request(List.of(occurrence("o-1",editionId,collectionId,"batch-1"))));
  assertEquals(HadithImportReport.Status.IMPORTED,report.status()); assertEquals(1,report.persisted());
  assertEquals("إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ",store.last.occurrences().getFirst().originalArabicMatn());
 }
 @Test void rejectsEditionMismatchBeforePersistence(){
  MemoryStore store=new MemoryStore(); HadithImportReport report=new HadithImportService(store).importBatch(request(List.of(occurrence("o-1",new EditionId("other"),collectionId,"batch-1"))));
  assertEquals(HadithImportReport.Status.REJECTED,report.status()); assertEquals(0,store.persistCalls);
  assertTrue(report.issues().stream().anyMatch(i->i.code().equals("OCCURRENCE_EDITION_MISMATCH")));
 }
 @Test void rejectsDuplicateOccurrenceIdentifiers(){
  HadithImportRequest request=request(List.of(occurrence("same",editionId,collectionId,"batch-1"),occurrence("same",editionId,collectionId,"batch-1")));
  HadithImportReport report=new HadithImportService(new MemoryStore()).importBatch(request);
  assertEquals(HadithImportReport.Status.REJECTED,report.status());
  assertTrue(report.issues().stream().anyMatch(i->i.code().equals("DUPLICATE_OCCURRENCE_ID")));
 }
 @Test void rejectsBatchIdReusedWithDifferentChecksum(){
  MemoryStore store=new MemoryStore(); store.next=HadithImportStore.WriteResult.CHECKSUM_CONFLICT;
  HadithImportReport report=new HadithImportService(store).importBatch(request(List.of(occurrence("o-1",editionId,collectionId,"batch-1"))));
  assertEquals(HadithImportReport.Status.REJECTED,report.status());
  assertTrue(report.issues().stream().anyMatch(i->i.code().equals("BATCH_CHECKSUM_CONFLICT")));
 }
 @Test void treatsSameBatchChecksumAsIdempotent(){
  MemoryStore store=new MemoryStore(); store.next=HadithImportStore.WriteResult.ALREADY_PRESENT;
  HadithImportReport report=new HadithImportService(store).importBatch(request(List.of(occurrence("o-1",editionId,collectionId,"batch-1"))));
  assertEquals(HadithImportReport.Status.ALREADY_IMPORTED,report.status()); assertEquals(0,store.persistCalls);
 }
 private HadithImportRequest request(List<HadithOccurrence> occurrences){
  HadithCollection c=new HadithCollection(collectionId,"Sahih al-Bukhari","Muhammad al-Bukhari","ar");
  HadithEdition e=new HadithEdition(editionId,collectionId,"Publisher","First edition","2001",URI.create("https://example.org/source"),"unknown","1");
  return new HadithImportRequest("batch-1","test-fixture","1","sha256:abc",Instant.parse("2026-01-01T00:00:00Z"),c,e,occurrences);
 }
 private HadithOccurrence occurrence(String id,EditionId e,CollectionId c,String batch){
  return new HadithOccurrence(new OccurrenceId(id),new HadithId("narration-1"),c,e,"Book 1","Chapter 1","1","1","1",
   "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ","حدثنا ...","ar","book=1/page=1",batch);
 }
 private static final class MemoryStore implements HadithImportStore {
  boolean imported; int persistCalls; HadithImportRequest last;
  public boolean hasImportedBatch(String id,String checksum){return imported;}
  public boolean persistAtomically(HadithImportRequest r){persistCalls++;last=r;imported=true;return true;}
 }
}

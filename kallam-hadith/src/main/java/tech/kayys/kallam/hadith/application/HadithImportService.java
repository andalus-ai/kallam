package tech.kayys.kallam.hadith.application;
import java.util.*;
import tech.kayys.kallam.hadith.domain.HadithOccurrence;
import tech.kayys.kallam.hadith.port.HadithImportStore;
/** Validates source-edition consistency before handing a batch to an atomic store. */
public final class HadithImportService {
 private final HadithImportStore store;
 public HadithImportService(HadithImportStore store){this.store=Objects.requireNonNull(store,"store");}
 public HadithImportReport importBatch(HadithImportRequest request){
  Objects.requireNonNull(request,"request");
  List<HadithImportReport.Issue> issues=validate(request);
  if(!issues.isEmpty())return new HadithImportReport(request.batchId(),HadithImportReport.Status.REJECTED,request.occurrences().size(),0,issues);
  if(store.hasImportedBatch(request.batchId(),request.sourceChecksum()))
   return new HadithImportReport(request.batchId(),HadithImportReport.Status.ALREADY_IMPORTED,request.occurrences().size(),0,List.of());
  boolean persisted=store.persistAtomically(request);
  return new HadithImportReport(request.batchId(),persisted?HadithImportReport.Status.IMPORTED:HadithImportReport.Status.ALREADY_IMPORTED,
   request.occurrences().size(),persisted?request.occurrences().size():0,List.of());
 }
 private List<HadithImportReport.Issue> validate(HadithImportRequest r){
  List<HadithImportReport.Issue> out=new ArrayList<>();
  if(!r.edition().collectionId().equals(r.collection().id()))
   out.add(new HadithImportReport.Issue(null,"EDITION_COLLECTION_MISMATCH","Edition must belong to the collection declared by this batch."));
  Set<String> ids=new HashSet<>();
  for(HadithOccurrence o:r.occurrences()){
   String id=o.id().value();
   if(!ids.add(id))out.add(new HadithImportReport.Issue(id,"DUPLICATE_OCCURRENCE_ID","Occurrence identifiers must be unique within a batch."));
   if(!o.collectionId().equals(r.collection().id()))out.add(new HadithImportReport.Issue(id,"OCCURRENCE_COLLECTION_MISMATCH","Occurrence collection does not match the batch collection."));
   if(!o.editionId().equals(r.edition().id()))out.add(new HadithImportReport.Issue(id,"OCCURRENCE_EDITION_MISMATCH","Occurrence edition does not match the batch edition."));
   if(!r.batchId().equals(o.importBatchId()))out.add(new HadithImportReport.Issue(id,"IMPORT_BATCH_MISMATCH","Occurrence importBatchId must match the submitted batch."));
   if(o.sourceLocator()==null||o.sourceLocator().isBlank())out.add(new HadithImportReport.Issue(id,"SOURCE_LOCATOR_REQUIRED","Every occurrence must identify its source location."));
  }
  return List.copyOf(out);
 }
}

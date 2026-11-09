package tech.kayys.kallam.hadith.application.isnad;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import tech.kayys.kallam.hadith.domain.OccurrenceId;
import tech.kayys.kallam.hadith.domain.isnad.*;
import tech.kayys.kallam.hadith.port.IsnadResearchRepository;
class IsnadResearchServiceTest {
 private static final NarratorId N1=new NarratorId("n1"), N2=new NarratorId("n2"), N3=new NarratorId("n3");
 @Test void reportsGapsAndUnresolvedStatesWithoutGradingChain(){
  var chain=chain("o1",link(1,"first",N1,IsnadLink.ResolutionStatus.RESOLVED),link(3,"uncertain",null,IsnadLink.ResolutionStatus.AMBIGUOUS),link(4,"not checked",null,IsnadLink.ResolutionStatus.NOT_ASSESSED));
  var report=service(Map.of("o1",chain),Map.of(N1,narrator(N1)),Map.of()).inspect(new OccurrenceId("o1"));
  assertEquals(List.of(2),report.missingPositions());assertEquals(List.of(3),report.ambiguousPositions());assertEquals(List.of(4),report.unassessedPositions());
  assertEquals(1,report.narratorResearch().size());assertEquals(2,report.unresolvedLinks().size());
 }
 @Test void retainsEvaluatorDisagreementAsSeparateRecords(){
  var chain=chain("o1",link(1,"first",N1,IsnadLink.ResolutionStatus.RESOLVED));
  var a=new NarratorEvaluation("a",N1,"Scholar A","Work A 1:10","ثقة","method A",null,null);
  var b=new NarratorEvaluation("b",N1,"Scholar B","Work B 2:20","فيه لين","method B",null,null);
  var report=service(Map.of("o1",chain),Map.of(N1,narrator(N1)),Map.of(N1,List.of(a,b))).inspect(new OccurrenceId("o1"));
  assertEquals(List.of("Scholar A","Scholar B"),report.narratorResearch().get(0).evaluations().stream().map(NarratorEvaluation::evaluator).toList());
 }
 @Test void findsSharedResolvedNarratorWithPositions(){
  var a=chain("o1",link(1,"one",N1,IsnadLink.ResolutionStatus.RESOLVED),link(2,"two",N2,IsnadLink.ResolutionStatus.RESOLVED));
  var b=chain("o2",link(1,"two",N2,IsnadLink.ResolutionStatus.RESOLVED),link(2,"three",N3,IsnadLink.ResolutionStatus.RESOLVED));
  var result=service(Map.of("o1",a,"o2",b),Map.of(),Map.of()).compare(new OccurrenceId("o1"),new OccurrenceId("o2"));
  assertEquals(1,result.sharedNarrators().size());assertEquals(N2,result.sharedNarrators().get(0).narratorId());
  assertEquals(2,result.sharedNarrators().get(0).firstChainPosition());assertEquals(1,result.sharedNarrators().get(0).secondChainPosition());
 }
 private static IsnadResearchService service(Map<String,IsnadChain> chains,Map<NarratorId,Narrator> narrators,Map<NarratorId,List<NarratorEvaluation>> evals){
  IsnadResearchRepository r=new IsnadResearchRepository(){
   public Optional<IsnadChain> findChain(OccurrenceId id){return Optional.ofNullable(chains.get(id.value()));}
   public Optional<Narrator> findNarrator(NarratorId id){return Optional.ofNullable(narrators.get(id));}
   public List<NarratorEvaluation> findEvaluations(NarratorId id){return evals.getOrDefault(id,List.of());}
  };return new IsnadResearchService(r);
 }
 private static Narrator narrator(NarratorId id){return new Narrator(id,"Narrator "+id.value(),List.of(),null,null,"bio:source");}
 private static IsnadChain chain(String id,IsnadLink... links){return new IsnadChain(new OccurrenceId(id),"original chain transcription","edition locator",List.of(links));}
 private static IsnadLink link(int p,String name,NarratorId id,IsnadLink.ResolutionStatus s){return new IsnadLink(p,name,id,s);}
}
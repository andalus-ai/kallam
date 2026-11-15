package tech.kayys.kallam.hadith.application.isnad;
import java.util.*;
import tech.kayys.kallam.hadith.domain.OccurrenceId;
import tech.kayys.kallam.hadith.domain.isnad.*;
import tech.kayys.kallam.hadith.domain.isnad.IsnadLink.ResolutionStatus;
import tech.kayys.kallam.hadith.port.IsnadResearchRepository;
/** Source-first Isnad inspection and identity-based chain intersection. */
public final class IsnadResearchService {
 private final IsnadResearchRepository repository;
 public IsnadResearchService(IsnadResearchRepository repository){this.repository=Objects.requireNonNull(repository,"repository");}
 public IsnadResearchReport inspect(OccurrenceId id){
  Objects.requireNonNull(id,"occurrenceId");
  var chain=repository.findChain(id).orElseThrow(()->new IllegalArgumentException("No Isnad chain for occurrence: "+id.value()));
  var research=new ArrayList<NarratorResearch>(); var unresolved=new ArrayList<IsnadLink>();
  var ambiguous=new ArrayList<Integer>(); var unassessed=new ArrayList<Integer>();
  for(var link:chain.links()) switch(link.resolutionStatus()){
   case RESOLVED -> repository.findNarrator(link.narratorId()).ifPresent(n->research.add(new NarratorResearch(link,n,repository.findEvaluations(link.narratorId()))));
   case UNRESOLVED -> unresolved.add(link);
   case AMBIGUOUS -> {unresolved.add(link);ambiguous.add(link.position());}
   case NOT_ASSESSED -> {unresolved.add(link);unassessed.add(link.position());}
  }
  var gaps=new ArrayList<Integer>();
  if(!chain.links().isEmpty()){int expected=1;for(var link:chain.links()){while(expected<link.position())gaps.add(expected++);expected=link.position()+1;}}
  return new IsnadResearchReport(chain,research,unresolved,gaps,ambiguous,unassessed);
 }
 public IsnadChainComparison compare(OccurrenceId firstId,OccurrenceId secondId){
  Objects.requireNonNull(firstId,"firstId");Objects.requireNonNull(secondId,"secondId");
  var first=repository.findChain(firstId).orElseThrow(()->new IllegalArgumentException("No Isnad chain for occurrence: "+firstId.value()));
  var second=repository.findChain(secondId).orElseThrow(()->new IllegalArgumentException("No Isnad chain for occurrence: "+secondId.value()));
  var positions=new HashMap<NarratorId,Integer>();
  first.links().stream().filter(l->l.resolutionStatus()==ResolutionStatus.RESOLVED).forEach(l->positions.putIfAbsent(l.narratorId(),l.position()));
  var shared=new ArrayList<SharedNarrator>();
  second.links().stream().filter(l->l.resolutionStatus()==ResolutionStatus.RESOLVED).forEach(l->{Integer p=positions.get(l.narratorId());if(p!=null)shared.add(new SharedNarrator(l.narratorId(),p,l.position()));});
  return new IsnadChainComparison(firstId,secondId,shared);
 }
}
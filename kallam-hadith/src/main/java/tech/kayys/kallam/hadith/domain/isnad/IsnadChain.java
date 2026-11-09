package tech.kayys.kallam.hadith.domain.isnad;
import java.util.Comparator;
import java.util.List;
import tech.kayys.kallam.hadith.domain.OccurrenceId;
/** Source-linked chain transcription. Gaps in link positions remain representable. */
public record IsnadChain(OccurrenceId occurrenceId, String originalIsnadText, String sourceLocator, List<IsnadLink> links) {
 public IsnadChain {
  if(occurrenceId==null) throw new IllegalArgumentException("occurrenceId must not be null");
  if(originalIsnadText==null||originalIsnadText.isBlank()) throw new IllegalArgumentException("originalIsnadText must not be blank");
  sourceLocator=sourceLocator==null?null:sourceLocator.trim();
  links=(links==null?List.<IsnadLink>of():links).stream().sorted(Comparator.comparingInt(IsnadLink::position)).toList();
  for(int i=1;i<links.size();i++) if(links.get(i-1).position()==links.get(i).position()) throw new IllegalArgumentException("duplicate Isnad link position: "+links.get(i).position());
 }
}
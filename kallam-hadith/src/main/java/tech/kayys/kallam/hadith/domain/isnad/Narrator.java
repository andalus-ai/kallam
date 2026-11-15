package tech.kayys.kallam.hadith.domain.isnad;
import java.util.List;
public record Narrator(NarratorId id, String displayName, List<String> nameVariants, String kunya, String nisba, String biographyReference) {
 public Narrator {
  if(id==null) throw new IllegalArgumentException("id must not be null");
  if(displayName==null || displayName.isBlank()) throw new IllegalArgumentException("displayName must not be blank");
  displayName=displayName.trim(); nameVariants=List.copyOf(nameVariants==null?List.of():nameVariants);
  kunya=clean(kunya); nisba=clean(nisba); biographyReference=clean(biographyReference);
 }
 private static String clean(String v){return v==null?null:v.trim();}
}
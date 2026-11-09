package tech.kayys.kallam.language.arabic.analysis;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import tech.kayys.kallam.language.arabic.text.*;
import tech.kayys.kallam.language.arabic.morphology.*;
class ArabicAnalysisResultTest {
 @Test void preservesCompetingMorphologicalCandidates(){var p=new AnalysisProvenance("test","1","rules","v1");var t=new ArabicToken("t","d",0,new SourceSpan(0,3),"كتب");var a=new MorphologicalAnalysis("a","t","كَتَبَ","ك ت ب","فَعَلَ","VERB",List.of("كتب"),List.of(),.8,p);var b=new MorphologicalAnalysis("b","t","كُتُب","ك ت ب","فُعُل","NOUN",List.of("كتب"),List.of(),.6,p);var r=new ArabicAnalysisResult("d",List.of(t),List.of(a,b),List.of(),List.of(),p);assertEquals(2,r.morphology().size());}
 @Test void rejectsUnknownTokenReferences(){var p=new AnalysisProvenance("test","1","rules","v1");var a=new MorphologicalAnalysis("a","missing","","","","UNRESOLVED",List.of(),List.of(),0,p);assertThrows(IllegalArgumentException.class,()->new ArabicAnalysisResult("d",List.of(),List.of(a),List.of(),List.of(),p));}
}
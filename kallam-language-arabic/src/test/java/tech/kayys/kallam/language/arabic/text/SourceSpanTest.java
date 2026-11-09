package tech.kayys.kallam.language.arabic.text;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class SourceSpanTest {
 @Test void usesCodePointsNotUtf16Offsets(){String s="😀كَتَبَ";assertEquals("كَتَبَ",new SourceSpan(1,7).extractFrom(s));}
 @Test void rejectsOutOfRange(){assertThrows(IllegalArgumentException.class,()->new SourceSpan(0,2).validateAgainst("ا"));}
 @Test void tokenSurfaceMustMatchExactSource(){var d=new ArabicDocument("d","كَتَبَ","Classical Arabic","src:1");var t=new ArabicToken("t","d",0,new SourceSpan(0,5),"كَتَبَ");assertDoesNotThrow(()->t.validateAgainst(d));var bad=new ArabicToken("b","d",0,new SourceSpan(0,5),"كتب");assertThrows(IllegalArgumentException.class,()->bad.validateAgainst(d));}
}
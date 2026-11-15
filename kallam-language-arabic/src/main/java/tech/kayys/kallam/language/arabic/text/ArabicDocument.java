package tech.kayys.kallam.language.arabic.text;
/** Source-preserving Arabic text; normalized projections never replace this value. */
public record ArabicDocument(String id,String sourceText,String languageVariety,String sourceReference) {
 public ArabicDocument { if(blank(id)||blank(sourceText)||blank(languageVariety)||blank(sourceReference)) throw new IllegalArgumentException("document fields are required"); }
 public int codePointLength(){return sourceText.codePointCount(0,sourceText.length());}
 private static boolean blank(String s){return s==null||s.isBlank();}
}
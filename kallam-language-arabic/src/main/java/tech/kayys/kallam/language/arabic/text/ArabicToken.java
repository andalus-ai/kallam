package tech.kayys.kallam.language.arabic.text;
/** Token annotation over source text, not a replacement for it. */
public record ArabicToken(String id,String documentId,int ordinal,SourceSpan span,String surface) {
 public ArabicToken {if(id==null||id.isBlank()||documentId==null||documentId.isBlank()||ordinal<0||span==null||surface==null||surface.isBlank())throw new IllegalArgumentException("invalid token");}
 public void validateAgainst(ArabicDocument d){if(!d.id().equals(documentId)||!surface.equals(span.extractFrom(d.sourceText())))throw new IllegalArgumentException("token does not match document source span");}
}
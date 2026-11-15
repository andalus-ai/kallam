package tech.kayys.kallam.language.arabic.text;
/** Half-open source range measured in Unicode code points, never UTF-16 indices. */
public record SourceSpan(int startCodePoint, int endCodePoint) {
 public SourceSpan { if(startCodePoint<0||endCodePoint<=startCodePoint) throw new IllegalArgumentException("invalid non-empty span"); }
 public void validateAgainst(String s){if(s==null||endCodePoint>s.codePointCount(0,s.length())) throw new IllegalArgumentException("span outside source");}
 public String extractFrom(String s){validateAgainst(s);return s.substring(s.offsetByCodePoints(0,startCodePoint),s.offsetByCodePoints(0,endCodePoint));}
}
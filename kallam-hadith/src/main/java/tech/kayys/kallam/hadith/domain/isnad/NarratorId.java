package tech.kayys.kallam.hadith.domain.isnad;
public record NarratorId(String value) {
 public NarratorId { if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank"); value=value.trim(); }
}
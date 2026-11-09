package tech.kayys.kallam.usul.ijma;

import java.util.List;
import java.util.Objects;

/** A sourced name form; a variant is not, by itself, proof of identity equivalence. */
public record CompanionNameVariant(
        String value,
        NameKind kind,
        String languageTag,
        List<String> attestationIds) {

    public enum NameKind { ISM, KUNYA, NASAB, NISBAH, LAQAB, OTHER }

    public CompanionNameVariant {
        value = required(value, "value");
        Objects.requireNonNull(kind, "kind");
        languageTag = languageTag == null || languageTag.isBlank() ? null : languageTag.trim();
        attestationIds = List.copyOf(Objects.requireNonNull(attestationIds, "attestationIds"));
        if (attestationIds.stream().anyMatch(id -> id == null || id.isBlank()))
            throw new IllegalArgumentException("attestationIds must not contain blank values");
        attestationIds = attestationIds.stream().map(String::trim).distinct().toList();
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
}

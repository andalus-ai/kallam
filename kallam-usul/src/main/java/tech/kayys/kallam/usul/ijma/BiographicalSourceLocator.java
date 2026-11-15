package tech.kayys.kallam.usul.ijma;

import java.util.Objects;

/** Edition-specific locator. At least one physical or digital locator is required. */
public record BiographicalSourceLocator(
        String sourceId,
        String volume,
        String pageStart,
        String pageEnd,
        String section,
        String reportNumber,
        String textAnchor) {

    public BiographicalSourceLocator {
        sourceId = required(sourceId, "sourceId");
        volume = optional(volume);
        pageStart = optional(pageStart);
        pageEnd = optional(pageEnd);
        section = optional(section);
        reportNumber = optional(reportNumber);
        textAnchor = optional(textAnchor);
        if (pageEnd != null && pageStart == null)
            throw new IllegalArgumentException("pageEnd requires pageStart");
        if (pageStart == null && volume == null && section == null && reportNumber == null && textAnchor == null)
            throw new IllegalArgumentException("at least one locator component is required");
    }

    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
    private static String optional(String v) { return v == null || v.isBlank() ? null : v.trim(); }
}

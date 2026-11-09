package tech.kayys.kallam.usul.ijma;

import java.util.Objects;

/**
 * A deliberately coarse historical date interval. Years are calendar years,
 * not Java dates; no conversion between Hijri and CE is implied.
 */
public record CompanionHistoricalDateRange(
        CalendarSystem calendar,
        Integer earliestYear,
        Integer latestYear,
        Precision precision) {

    public enum CalendarSystem { HIJRI, CE, UNKNOWN }
    public enum Precision { EXACT, APPROXIMATE, BEFORE, AFTER, UNKNOWN }

    public CompanionHistoricalDateRange {
        Objects.requireNonNull(calendar, "calendar");
        Objects.requireNonNull(precision, "precision");
        if (earliestYear != null && latestYear != null && earliestYear > latestYear)
            throw new IllegalArgumentException("earliestYear must not exceed latestYear");
        if (precision == Precision.EXACT && (earliestYear == null || latestYear == null
                || !earliestYear.equals(latestYear)))
            throw new IllegalArgumentException("EXACT requires one known year");
        if (precision == Precision.APPROXIMATE && earliestYear == null && latestYear == null)
            throw new IllegalArgumentException("APPROXIMATE requires at least one boundary");
        if (precision == Precision.BEFORE && latestYear == null)
            throw new IllegalArgumentException("BEFORE requires latestYear");
        if (precision == Precision.AFTER && earliestYear == null)
            throw new IllegalArgumentException("AFTER requires earliestYear");
        if (calendar == CalendarSystem.UNKNOWN && (earliestYear != null || latestYear != null))
            throw new IllegalArgumentException("UNKNOWN calendar cannot carry numeric year boundaries");
        if (precision == Precision.UNKNOWN && (earliestYear != null || latestYear != null))
            throw new IllegalArgumentException("UNKNOWN precision cannot carry numeric year boundaries");
    }
}

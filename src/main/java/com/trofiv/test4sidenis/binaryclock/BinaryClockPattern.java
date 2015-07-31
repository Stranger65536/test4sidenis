package com.trofiv.test4sidenis.binaryclock;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A {@code BinaryClockPattern} represents structure of binary clock as ordered list of {@code BinaryClockRowPattern}.
 * This representation provides any row count customization, with different time units at each row, and different
 * time coverage at each row. This features are very useful for creating binary clocks, including Berlin clock.
 */
public class BinaryClockPattern {
    private final List<BinaryClockRowPattern> clockRows;

    /**
     * Instantiates {@code BinaryClockPattern} object and validates all specified parameters.
     *
     * @param clockRows specified list of {@code BinaryClockRowPattern} clock rows
     */
    public BinaryClockPattern(final List<BinaryClockRowPattern> clockRows) {
        this.clockRows = validateClockPattern(clockRows);
    }

    /**
     * Calculates most precision row (row with least durable cell, including time unit) from specified
     * list of {@code BinaryClockRowPattern} objects.
     *
     * @param clockRows specified list of {@code BinaryClockRowPattern} objects
     * @return {@code BinaryClockRowPattern} object with minimal row cell time coverage
     */
    //method was maid public to simplify testing without reflection
    public static BinaryClockRowPattern getMostPrecisionRow(
            final Collection<BinaryClockRowPattern> clockRows) {
        return clockRows
                .stream()
                .min((o1, o2) -> Long.valueOf(o1.nanoSecondsCoveredByRow()).compareTo(o2.nanoSecondsCoveredByRow()))
                .get();
    }

    /**
     * Ensures that specified list of {@code BinaryClockRowPattern} clock rows is valid for binary clock.
     *
     * @param clockRows specified list of {@code BinaryClockRowPattern} clock rows
     * @return checked list of {@code BinaryClockRowPattern} rows
     */
    private static List<BinaryClockRowPattern> validateClockPattern(
            final List<BinaryClockRowPattern> clockRows) {
        Preconditions.checkNotNull(clockRows, "Clock rows must be specified!");
        Preconditions.checkArgument(!clockRows.isEmpty(), "At least one clock row must be specified!");
        final BinaryClockRowPattern mostPrecisionRow = getMostPrecisionRow(clockRows);
        checkAllDayCoveredByRows(clockRows, mostPrecisionRow);
        return clockRows;
    }

    /**
     * Ensures that total duration of all cells of clock is sufficient for 24-hours time representation.
     * Coverage with more that 24 hours is acceptable.
     * {@code mostPrecisionRow} parameter must be specified to correct validation in case of strictly 24-hours
     * clock rows total duration.
     *
     * @param clockRows        specified clock rows
     * @param mostPrecisionRow specified row from {@code clockRows} list with minimal time coverage
     */
    //method was maid public to simplify testing without reflection
    public static void checkAllDayCoveredByRows(
            final Collection<BinaryClockRowPattern> clockRows,
            final BinaryClockRowPattern mostPrecisionRow) {
        final long rowsCoverage = clockRows
                .stream()
                .mapToLong(BinaryClockRowPattern::nanoSecondsCoveredByRow)
                .sum();
        final long mostPrecisionCellCoverage = mostPrecisionRow.nanoSecondsCoveredByCell();
        //sum total row coverage need to correct validation of strictly 24-hours clock rows total duration
        //(23:59:59 with last row of seconds, for example, adding 1 second gives all 24 hours coverage)
        Preconditions.checkArgument(rowsCoverage + mostPrecisionCellCoverage >=
                TimeUnit.DAYS.toNanos(1), "Rows must cover entire day!");
    }

    /**
     * Simple getter for stored list of {@code BinaryClockRowPattern} clock rows.
     *
     * @return list of {@code BinaryClockRowPattern} represents rows of binary clock
     */
    public List<BinaryClockRowPattern> getClockRows() {
        return clockRows;
    }
}

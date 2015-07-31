package com.trofiv.test4sidenis.binaryclock;

import com.google.common.base.Preconditions;

import java.util.concurrent.TimeUnit;

/**
 * A {@code BinaryClockRowPattern} represents structure of single row of {@code BinaryClockPattern}.
 * This representation provide much customization of binary clocks, with any precision of timing up to
 * nanoseconds, inclusive, and with customized count of cells in each row. Row information contains count
 * of binary cells in a row, and time duration of each cell.
 */
public class BinaryClockRowPattern {
    private final int cellDuration;
    private final TimeUnit cellDurationUnit;
    private final int cellsInRow;

    /**
     * Instantiates {@code BinaryClockRowPattern} object and validates all specified parameters.
     *
     * @param cellDuration     duration (in time units) of each cell in a row
     * @param cellDurationUnit time unit (like hours, minutes, etc.) of each cell in a row
     * @param cellsInRow       total count of cells in a row
     */
    public BinaryClockRowPattern(
            final int cellDuration,
            final TimeUnit cellDurationUnit,
            final int cellsInRow) {
        this.cellDuration = validateDuration(cellDuration);
        this.cellDurationUnit = validateDurationUnit(cellDurationUnit);
        this.cellsInRow = validateCellCount(cellsInRow);
    }

    /**
     * Ensures that specified duration of cell is valid for binary clock.
     *
     * @param cellDuration specified cell duration
     * @return checked value of cell duration
     */
    private static int validateDuration(final int cellDuration) {
        Preconditions.checkArgument(cellDuration > 0, "Duration must be a positive value!");
        return cellDuration;
    }

    /**
     * Ensures that specified time unit for duration matches for one-day clock.
     *
     * @param cellDurationUnit specified time unit
     * @return checked value of cell time unit
     */
    private static TimeUnit validateDurationUnit(final TimeUnit cellDurationUnit) {
        Preconditions.checkNotNull(cellDurationUnit, "Cell durations unit must be specified!");
        Preconditions.checkArgument(cellDurationUnit.ordinal() < TimeUnit.DAYS.ordinal(),
                "Cell can't count entire day and more!");
        return cellDurationUnit;
    }

    /**
     * Ensures that specified count of cells in a row is valid for binary clock.
     *
     * @param cellsInRow specified cell count
     * @return checked value of cell count in a row
     */
    private static int validateCellCount(final int cellsInRow) {
        Preconditions.checkArgument(cellsInRow > 0, "Row must have at least one cell!");
        return cellsInRow;
    }

    /**
     * Calculates count of nanoseconds that covers single cell in a row.
     *
     * @return duration in nanoseconds that covers one cell
     */
    public long nanoSecondsCoveredByCell() {
        return cellDurationUnit.toNanos(cellDuration);
    }

    /**
     * Calculates count of nanoseconds that covers all cells a row.
     *
     * @return duration in nanoseconds that covers entire row
     */
    public long nanoSecondsCoveredByRow() {
        return nanoSecondsCoveredByCell() * cellsInRow;
    }

    /**
     * Simple getter for duration in time units of single cell.
     *
     * @return count of time units that covers a single cell
     */
    public int getCellDuration() {
        return cellDuration;
    }

    /**
     * Simple getter for time unit of single cell.
     *
     * @return {@code TimeUnit} object that represents time units of each cell in a row
     */
    public TimeUnit getCellDurationUnit() {
        return cellDurationUnit;
    }

    /**
     * Simple getter for count of cell in a row.
     *
     * @return count of cell in entire row
     */
    public int getCellsInRow() {
        return cellsInRow;
    }
}
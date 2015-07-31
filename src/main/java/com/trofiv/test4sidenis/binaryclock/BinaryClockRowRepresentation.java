package com.trofiv.test4sidenis.binaryclock;

import com.google.common.base.Preconditions;

/**
 * A {@code BinaryClockRowRepresentation} represents calculated
 * state of {@code BinaryClockRowPattern} after timing applied.
 */
public class BinaryClockRowRepresentation {
    private final BinaryClockRowPattern rowPattern;
    private final int lightCellsCount;

    /**
     * Instantiates {@code BinaryClockRowRepresentation} object and validates all specified parameters.
     *
     * @param rowPattern      specified {@code BinaryClockRowPattern} clock rows object
     * @param lightCellsCount specified count of binary cell that have to light
     */
    public BinaryClockRowRepresentation(
            final BinaryClockRowPattern rowPattern,
            final int lightCellsCount) {
        this.rowPattern = validateRowPattern(rowPattern);
        this.lightCellsCount = validateCellCount(lightCellsCount);
    }

    /**
     * Ensures that specified count of light cells is valid for binary clock row representation.
     *
     * @param lightCellsCount specified count of light cells
     * @return checked count of light cells
     */
    private static int validateCellCount(final int lightCellsCount) {
        Preconditions.checkArgument(lightCellsCount >= 0, "Count of cells must be positive!");
        return lightCellsCount;
    }

    /**
     * Ensures that specified {@code BinaryClockRowPattern} clock row
     * is valid for binary clock row representation.
     *
     * @param rowPattern specified {@code BinaryClockRowPattern} binary clock row
     * @return checked {@code BinaryClockRowPattern} object
     */
    private static BinaryClockRowPattern validateRowPattern(
            final BinaryClockRowPattern rowPattern) {
        Preconditions.checkNotNull(rowPattern, "Row pattern must be specified!");
        return rowPattern;
    }

    /**
     * Simple getter for row pattern.
     *
     * @return {@code BinaryClockRowPattern} object representing binary clock row
     */
    public BinaryClockRowPattern getRowPattern() {
        return rowPattern;
    }

    /**
     * Simple getter for count of lighted cells.
     *
     * @return count of binary cell that have to light
     */
    public int getLightCellsCount() {
        return lightCellsCount;
    }
}
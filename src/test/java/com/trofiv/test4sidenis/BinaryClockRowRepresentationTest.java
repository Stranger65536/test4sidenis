package com.trofiv.test4sidenis;

import com.trofiv.test4sidenis.binaryclock.BinaryClockRowPattern;
import com.trofiv.test4sidenis.binaryclock.BinaryClockRowRepresentation;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class BinaryClockRowRepresentationTest {
    private static final int FIVE_HOURS = 5;
    private static final int FOUR_CELLS = 4;
    private static final BinaryClockRowPattern FIVE_HOURS_FOUR_CELLS = new BinaryClockRowPattern(FIVE_HOURS, TimeUnit.HOURS, FOUR_CELLS);

    @SuppressWarnings({"unused", "UnusedAssignment"})
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCellCount() {
        final int lightCellCount = -1;
        final BinaryClockRowRepresentation representation = new BinaryClockRowRepresentation(
                FIVE_HOURS_FOUR_CELLS, lightCellCount);
    }

    @SuppressWarnings({"unused", "UnusedAssignment"})
    @Test(expected = NullPointerException.class)
    public void testInvalidClockRow() {
        final int lightCellCount = -1;
        final BinaryClockRowPattern pattern = null;
        final BinaryClockRowRepresentation representation = new BinaryClockRowRepresentation(
                pattern, lightCellCount);
    }
}
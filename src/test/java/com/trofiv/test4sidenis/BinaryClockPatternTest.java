package com.trofiv.test4sidenis;

import com.trofiv.test4sidenis.binaryclock.BinaryClockPattern;
import com.trofiv.test4sidenis.binaryclock.BinaryClockRowPattern;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BinaryClockPatternTest {
    private static final int FIVE_HOURS = 5;
    private static final int ONE_HOUR = 1;
    private static final int FOUR_CELLS = 4;
    private static final int FIVE_MINUTES = 5;
    private static final int ELEVEN_CELLS = 11;
    private static final int ONE_MINUTE = 1;
    private static final BinaryClockRowPattern FIVE_HOURS_FOUR_CELLS = new BinaryClockRowPattern(FIVE_HOURS, TimeUnit.HOURS, FOUR_CELLS);
    private static final BinaryClockRowPattern ONE_HOUR_FOUR_CELLS = new BinaryClockRowPattern(ONE_HOUR, TimeUnit.HOURS, FOUR_CELLS);
    private static final BinaryClockRowPattern FIVE_MINUTES_ELEVEN_CELLS = new BinaryClockRowPattern(FIVE_MINUTES, TimeUnit.MINUTES, ELEVEN_CELLS);
    private static final BinaryClockRowPattern ONE_MINUTE_FOUR_CELLS = new BinaryClockRowPattern(ONE_MINUTE, TimeUnit.MINUTES, FOUR_CELLS);
    private static final String ROW_LIST_CHANGED_WITHOUT_EXCEPTION = "Validation returns another clock row list without exception!";
    private static final String INVALID_MOST_PRECISION_ROW_SELECTED = "Invalid most precision row selected!";

    @Test
    public void testValidClock() {
        final List<BinaryClockRowPattern> clockRows = Arrays.asList(
                FIVE_HOURS_FOUR_CELLS,
                ONE_HOUR_FOUR_CELLS,
                FIVE_MINUTES_ELEVEN_CELLS,
                ONE_MINUTE_FOUR_CELLS
        );
        final BinaryClockPattern pattern = new BinaryClockPattern(clockRows);
        assertThat(ROW_LIST_CHANGED_WITHOUT_EXCEPTION, pattern.getClockRows(), is(clockRows));
    }

    @Test
    public void testMostPrecisionRow() {
        final List<BinaryClockRowPattern> clockRows = Arrays.asList(
                FIVE_HOURS_FOUR_CELLS,
                ONE_HOUR_FOUR_CELLS,
                FIVE_MINUTES_ELEVEN_CELLS,
                ONE_MINUTE_FOUR_CELLS
        );
        final BinaryClockRowPattern mostPrecisionRow = BinaryClockPattern.getMostPrecisionRow(clockRows);
        assertThat(INVALID_MOST_PRECISION_ROW_SELECTED, mostPrecisionRow, is(ONE_MINUTE_FOUR_CELLS));
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testValidDayCoverage() {
        final List<BinaryClockRowPattern> clockRows = Arrays.asList(
                FIVE_HOURS_FOUR_CELLS,
                ONE_HOUR_FOUR_CELLS,
                FIVE_MINUTES_ELEVEN_CELLS,
                ONE_MINUTE_FOUR_CELLS
        );
        final BinaryClockRowPattern mostPrecisionRow = ONE_MINUTE_FOUR_CELLS;
        BinaryClockPattern.checkAllDayCoveredByRows(clockRows, mostPrecisionRow);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDayCoverage() {
        final List<BinaryClockRowPattern> clockRows = Arrays.asList(
                FIVE_HOURS_FOUR_CELLS,
                FIVE_MINUTES_ELEVEN_CELLS,
                ONE_MINUTE_FOUR_CELLS
        );
        final BinaryClockRowPattern mostPrecisionRow = ONE_MINUTE_FOUR_CELLS;
        BinaryClockPattern.checkAllDayCoveredByRows(clockRows, mostPrecisionRow);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings({"unused", "UnusedAssignment"})
    public void testNullClockRows() {
        final BinaryClockPattern pattern = new BinaryClockPattern(null);
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings({"unused", "UnusedAssignment"})
    public void testEmptyClockRows() {
        final List<BinaryClockRowPattern> clockRows = new LinkedList<>();
        final BinaryClockPattern pattern = new BinaryClockPattern(clockRows);
    }
}
package com.trofiv.test4sidenis;

import com.trofiv.test4sidenis.binaryclock.BinaryClockRowPattern;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BinaryClockRowPatternTest {
    private static final long FOUR_HOURS_NANOSEC = 14400000000000L;
    private static final long SIXTEEN_HOURS_NANOSEC = 57600000000000L;
    private static final long FIVE_MINUTES_NANOSEC = 300000000000L;
    private static final long FIFTY_FIVE_MINUTES_NANOSEC = 3300000000000L;
    private static final long ONE_SECOND_NANOSEC = 1000000000L;
    private static final long THREE_MILLISECONDS_NANOSEC = 3000000L;
    private static final long TWELVE_MILLISECONDS_NANOSEC = 12000000L;
    private static final long FIVE_MICROSECONDS_NANOSEC = 5000L;
    private static final long TWENTY_FIVE_MICROSECONDS_NANOSEC = 25000L;
    private static final long TEN_NANOSEC = 10L;
    private static final long TWENTY_NANOSEC = 20L;
    private static final String INVALID_NANOSECONDS_CELL_COVERAGE = "Invalid nanoseconds coverage for one cell!";
    private static final String INVALID_NANOSECONDS_ROW_COVERAGE = "Invalid nanoseconds coverage for entire row!";

    @Test
    public void testValidHourClockRow() {
        final int cellDuration = 4;
        final TimeUnit cellDurationUnit = TimeUnit.HOURS;
        final int cellsInRow = 4;
        final BinaryClockRowPattern pattern = new BinaryClockRowPattern(cellDuration, cellDurationUnit, cellsInRow);
        assertThat(INVALID_NANOSECONDS_CELL_COVERAGE, pattern.nanoSecondsCoveredByCell(), is(FOUR_HOURS_NANOSEC));
        assertThat(INVALID_NANOSECONDS_ROW_COVERAGE, pattern.nanoSecondsCoveredByRow(), is(SIXTEEN_HOURS_NANOSEC));
    }

    @Test
    public void testValidMinuteClockRow() {
        final int cellDuration = 5;
        final TimeUnit cellDurationUnit = TimeUnit.MINUTES;
        final int cellsInRow = 11;
        final BinaryClockRowPattern pattern = new BinaryClockRowPattern(cellDuration, cellDurationUnit, cellsInRow);
        assertThat(INVALID_NANOSECONDS_CELL_COVERAGE, pattern.nanoSecondsCoveredByCell(), is(FIVE_MINUTES_NANOSEC));
        assertThat(INVALID_NANOSECONDS_ROW_COVERAGE, pattern.nanoSecondsCoveredByRow(), is(FIFTY_FIVE_MINUTES_NANOSEC));
    }

    @Test
    public void testValidSecondClockRow() {
        final int cellDuration = 1;
        final TimeUnit cellDurationUnit = TimeUnit.SECONDS;
        final int cellsInRow = 1;
        final BinaryClockRowPattern pattern = new BinaryClockRowPattern(cellDuration, cellDurationUnit, cellsInRow);
        assertThat(INVALID_NANOSECONDS_CELL_COVERAGE, pattern.nanoSecondsCoveredByCell(), is(ONE_SECOND_NANOSEC));
        assertThat(INVALID_NANOSECONDS_ROW_COVERAGE, pattern.nanoSecondsCoveredByRow(), is(ONE_SECOND_NANOSEC));
    }

    @Test
    public void testValidMillisecondClockRow() {
        final int cellDuration = 3;
        final TimeUnit cellDurationUnit = TimeUnit.MILLISECONDS;
        final int cellsInRow = 4;
        final BinaryClockRowPattern pattern = new BinaryClockRowPattern(cellDuration, cellDurationUnit, cellsInRow);
        assertThat(INVALID_NANOSECONDS_CELL_COVERAGE, pattern.nanoSecondsCoveredByCell(), is(THREE_MILLISECONDS_NANOSEC));
        assertThat(INVALID_NANOSECONDS_ROW_COVERAGE, pattern.nanoSecondsCoveredByRow(), is(TWELVE_MILLISECONDS_NANOSEC));
    }

    @Test
    public void testValidMicrosecondClockRow() {
        final int cellDuration = 5;
        final TimeUnit cellDurationUnit = TimeUnit.MICROSECONDS;
        final int cellsInRow = 5;
        final BinaryClockRowPattern pattern = new BinaryClockRowPattern(cellDuration, cellDurationUnit, cellsInRow);
        assertThat(INVALID_NANOSECONDS_CELL_COVERAGE, pattern.nanoSecondsCoveredByCell(), is(FIVE_MICROSECONDS_NANOSEC));
        assertThat(INVALID_NANOSECONDS_ROW_COVERAGE, pattern.nanoSecondsCoveredByRow(), is(TWENTY_FIVE_MICROSECONDS_NANOSEC));
    }

    @Test
    public void testValidNanosecondClockRow() {
        final int cellDuration = 10;
        final TimeUnit cellDurationUnit = TimeUnit.NANOSECONDS;
        final int cellsInRow = 2;
        final BinaryClockRowPattern pattern = new BinaryClockRowPattern(cellDuration, cellDurationUnit, cellsInRow);
        assertThat(INVALID_NANOSECONDS_CELL_COVERAGE, pattern.nanoSecondsCoveredByCell(), is(TEN_NANOSEC));
        assertThat(INVALID_NANOSECONDS_ROW_COVERAGE, pattern.nanoSecondsCoveredByRow(), is(TWENTY_NANOSEC));
    }

    @SuppressWarnings({"unused", "UnusedAssignment"})
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTimeUnitClockRow() {
        final int cellDuration = 10;
        final TimeUnit cellDurationUnit = TimeUnit.DAYS;
        final int cellsInRow = 2;
        final BinaryClockRowPattern pattern = new BinaryClockRowPattern(cellDuration, cellDurationUnit, cellsInRow);
    }

    @SuppressWarnings({"unused", "UnusedAssignment"})
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCellDurationClockRow() {
        final int cellDuration = 0;
        final TimeUnit cellDurationUnit = TimeUnit.NANOSECONDS;
        final int cellsInRow = 2;
        final BinaryClockRowPattern pattern = new BinaryClockRowPattern(cellDuration, cellDurationUnit, cellsInRow);
    }

    @SuppressWarnings({"unused", "UnusedAssignment"})
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCellCountClockRow() {
        final int cellDuration = 10;
        final TimeUnit cellDurationUnit = TimeUnit.NANOSECONDS;
        final int cellsInRow = -2;
        final BinaryClockRowPattern pattern = new BinaryClockRowPattern(cellDuration, cellDurationUnit, cellsInRow);
    }
}
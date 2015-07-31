package com.trofiv.test4sidenis;

import com.trofiv.test4sidenis.binaryclock.BerlinBinaryClock;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BerlinClockTest {
    private static final String INVALID_LIGHT_CELLS_COUNT = "Invalid light cells count!";

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static BerlinBinaryClock clock;

    @BeforeClass
    public static void setUp() {
        clock = new BerlinBinaryClock();
    }

    @SuppressWarnings({"MagicNumber", "OverlyComplexMethod", "OverlyLongMethod", "ConstantConditions"})
    private static List<Integer> getNaiveBerlinClock(final LocalTime time) {
        final int seconds = time.getSecond();
        final int minutes = time.getMinute();
        final int hours = time.getHour();
        int firstRow = 0;
        final int lightBlink = seconds % 2;
        firstRow = hours >= 5 ? firstRow + 1 : firstRow;
        firstRow = hours >= 10 ? firstRow + 1 : firstRow;
        firstRow = hours >= 15 ? firstRow + 1 : firstRow;
        firstRow = hours >= 20 ? firstRow + 1 : firstRow;
        int secondRow = 0;
        secondRow = hours % 5 >= 1 ? secondRow + 1 : secondRow;
        secondRow = hours % 5 >= 2 ? secondRow + 1 : secondRow;
        secondRow = hours % 5 >= 3 ? secondRow + 1 : secondRow;
        secondRow = hours % 5 >= 4 ? secondRow + 1 : secondRow;
        int thirdRow = 0;
        thirdRow = minutes >= 5 ? thirdRow + 1 : thirdRow;
        thirdRow = minutes >= 10 ? thirdRow + 1 : thirdRow;
        thirdRow = minutes >= 15 ? thirdRow + 1 : thirdRow;
        thirdRow = minutes >= 20 ? thirdRow + 1 : thirdRow;
        thirdRow = minutes >= 25 ? thirdRow + 1 : thirdRow;
        thirdRow = minutes >= 30 ? thirdRow + 1 : thirdRow;
        thirdRow = minutes >= 35 ? thirdRow + 1 : thirdRow;
        thirdRow = minutes >= 40 ? thirdRow + 1 : thirdRow;
        thirdRow = minutes >= 45 ? thirdRow + 1 : thirdRow;
        thirdRow = minutes >= 50 ? thirdRow + 1 : thirdRow;
        thirdRow = minutes >= 55 ? thirdRow + 1 : thirdRow;
        int fourthRow = 0;
        fourthRow = minutes % 5 >= 1 ? fourthRow + 1 : fourthRow;
        fourthRow = minutes % 5 >= 2 ? fourthRow + 1 : fourthRow;
        fourthRow = minutes % 5 >= 3 ? fourthRow + 1 : fourthRow;
        fourthRow = minutes % 5 >= 4 ? fourthRow + 1 : fourthRow;
        return Arrays.asList(lightBlink, firstRow, secondRow, thirdRow, fourthRow);
    }

    @Test
    public void testMidnightTime() {
        final LocalTime testTime = LocalTime.MIDNIGHT;
        clock.<String>of(testTime, result -> {
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(0).getLightCellsCount(), is(0));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(1).getLightCellsCount(), is(0));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(2).getLightCellsCount(), is(0));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(3).getLightCellsCount(), is(0));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(4).getLightCellsCount(), is(0));
            return "";
        });
    }

    @SuppressWarnings("MagicNumber")
    @Test
    public void testPreMidnightTime() {
        final LocalTime testTime = LocalTime.MAX;
        clock.<String>of(testTime, result -> {
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(0).getLightCellsCount(), is(4));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(1).getLightCellsCount(), is(3));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(2).getLightCellsCount(), is(11));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(3).getLightCellsCount(), is(4));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(4).getLightCellsCount(), is(59));
            return "";
        });
    }

    @Test
    public void testNoonTime() {
        final LocalTime testTime = LocalTime.NOON;
        clock.<String>of(testTime, result -> {
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(0).getLightCellsCount(), is(2));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(1).getLightCellsCount(), is(2));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(2).getLightCellsCount(), is(0));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(3).getLightCellsCount(), is(0));
            assertThat(INVALID_LIGHT_CELLS_COUNT, result.get(4).getLightCellsCount(), is(0));
            return "";
        });
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidSourceTime() {
        final LocalTime testTime = null;
        clock.<String>of(testTime, result -> "");
    }

    /**
     * Very complex naive test that looping all seconds-precision time from 00:00:00 to 23:59:59,
     * naive calculates berlin clock time and comparing result with result of programmed method
     */
    @SuppressWarnings("MagicNumber")
    @Test
    public void naiveBerlinClockTest() {
        LocalTime currentTime = LocalTime.MIN;
        while (!currentTime.equals(LocalTime.of(23, 59, 59))) {
            final List<Integer> naiveResult = getNaiveBerlinClock(currentTime);
            final List<Integer> testResult = clock.<List<Integer>>of(currentTime, result -> Arrays.asList(
                    result.get(4).getLightCellsCount() % 2, //5'th row represent seconds
                    result.get(0).getLightCellsCount(),
                    result.get(1).getLightCellsCount(),
                    result.get(2).getLightCellsCount(),
                    result.get(3).getLightCellsCount()
            ));
            //noinspection MagicCharacter
            assertEquals("Convert results of naive method and programmed differents at time "
                    + currentTime.format(DateTimeFormatter.ISO_LOCAL_TIME) + '!', naiveResult, testResult);
            currentTime = currentTime.plusSeconds(1);
        }
    }
}
package com.trofiv.test4sidenis.binaryclock;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * A {@code BerlinBinaryClock} represents Berlin binary clock and converts time from
 * standard {@code LocalTime} format to customized binary clock format, and represents it as
 * need using closure mechanism.
 */
public class BerlinBinaryClock extends GenericBinaryClock {
    private static final int FIVE_HOURS = 5;
    private static final int ONE_HOUR = 1;
    private static final int FOUR_CELLS = 4;
    private static final int FIVE_MINUTES = 5;
    private static final int ELEVEN_CELLS = 11;
    private static final int ONE_MINUTE = 1;
    private static final int ONE_SECOND = 1;
    private static final int FIFTY_NINE_SECONDS = 59;
    private static final BinaryClockRowPattern FIVE_HOURS_FOUR_CELLS = new BinaryClockRowPattern(FIVE_HOURS, TimeUnit.HOURS, FOUR_CELLS);
    private static final BinaryClockRowPattern ONE_HOUR_FOUR_CELLS = new BinaryClockRowPattern(ONE_HOUR, TimeUnit.HOURS, FOUR_CELLS);
    private static final BinaryClockRowPattern FIVE_MINUTES_ELEVEN_CELLS = new BinaryClockRowPattern(FIVE_MINUTES, TimeUnit.MINUTES, ELEVEN_CELLS);
    private static final BinaryClockRowPattern ONE_MINUTE_FOUR_CELLS = new BinaryClockRowPattern(ONE_MINUTE, TimeUnit.MINUTES, FOUR_CELLS);
    private static final BinaryClockRowPattern ONE_SECOND_FIFTY_NINE_CELLS = new BinaryClockRowPattern(ONE_SECOND, TimeUnit.SECONDS, FIFTY_NINE_SECONDS);

    /**
     * Binary clock pattern for Berlin binary clock.
     * Original Berlin clock has no seconds row, but
     * has light that blinks every second so seconds
     * row have to be added in pattern.
     */
    public static final BinaryClockPattern BERLIN_CLOCK_PATTERN = new BinaryClockPattern(Arrays.asList(
            FIVE_HOURS_FOUR_CELLS,
            ONE_HOUR_FOUR_CELLS,
            FIVE_MINUTES_ELEVEN_CELLS,
            ONE_MINUTE_FOUR_CELLS,
            ONE_SECOND_FIFTY_NINE_CELLS
    ));

    /**
     * Instantiates {@code BerlinBinaryClock} object using Berlin clock pattern.
     */
    public BerlinBinaryClock() {
        super(BERLIN_CLOCK_PATTERN);
    }
}
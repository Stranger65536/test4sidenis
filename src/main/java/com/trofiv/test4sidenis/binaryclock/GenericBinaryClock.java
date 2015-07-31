package com.trofiv.test4sidenis.binaryclock;

import com.google.common.base.Preconditions;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * A {@code GenericBinaryClock} represents general binary clock and convert time from
 * standard {@code LocalTime} format to customized binary clock format, and represent it as
 * need using closure mechanism.
 */
public class GenericBinaryClock {
    private final BinaryClockPattern clockPattern;

    /**
     * Instantiates {@code GenericBinaryClock} object using pattern representing
     * binary clock, and validates all specified parameters.
     *
     * @param clockPattern specified {@code BinaryClockPattern} pattern representing binary clock
     */
    public GenericBinaryClock(final BinaryClockPattern clockPattern) {
        this.clockPattern = validateClockPattern(clockPattern);
    }

    /**
     * Ensures that specified {@code BinaryClockPattern} pattern is valid for binary clock calculations.
     *
     * @param clockPattern specified pattern of binary clock
     * @return checked {@code BinaryClockPattern} object
     */
    private static BinaryClockPattern validateClockPattern(final BinaryClockPattern clockPattern) {
        Preconditions.checkNotNull(clockPattern, "Clock pattern must be specified!");
        return clockPattern;
    }

    /**
     * Calculates distribution of specified time to rows of binary clock.
     *
     * @param localTime specified time to be converted to binary time
     * @return list of {@code BinaryClockRowRepresentation} objects that representing
     * rows of timed binary clock with information about light cells.
     */
    //method was maid public to simplify testing without reflection
    private List<BinaryClockRowRepresentation> calculateRepresentation(final LocalTime localTime) {
        Preconditions.checkNotNull(clockPattern, "Time for conversion must be specified!");
        LocalTime remainTime = localTime;
        final List<BinaryClockRowRepresentation> representation = new LinkedList<>();
        //Algorithm: iterate over all rows of clock and calculate how much cell can be covered,
        //and sub covered time from remaining
        for (BinaryClockRowPattern rowPattern : clockPattern.getClockRows()) {
            //calculating how much row units can be covered by total amount of nanoseconds of remaining time
            final long remainTimeInRowUnits = rowPattern.getCellDurationUnit()
                    .convert(remainTime.toNanoOfDay(), TimeUnit.NANOSECONDS);
            final long countCellsThatRemainTimeCovers = remainTimeInRowUnits / rowPattern.getCellDuration();
            //this check need to avoid long-to-int cast loss precision
            //noinspection NumericCastThatLosesPrecision
            final int lightCellCount = countCellsThatRemainTimeCovers >= rowPattern.getCellsInRow()
                    ? rowPattern.getCellsInRow()
                    : (int) countCellsThatRemainTimeCovers;
            //TimeUnit has no explicit conversion to ChronoUnit, so subtraction of nanos works well
            final long nanosToSubtract = TimeUnit.NANOSECONDS
                    .convert((long) rowPattern.getCellDuration() * lightCellCount, rowPattern.getCellDurationUnit());
            remainTime = remainTime.minus(nanosToSubtract, ChronoUnit.NANOS);
            //noinspection ObjectAllocationInLoop
            representation.add(new BinaryClockRowRepresentation(rowPattern, lightCellCount));
        }
        return representation;
    }

    /**
     * Converts time from {@code LocalTime} to {@code BinaryClockRowRepresentation} binary clock format.
     * Provides customized representation mechanism of binary time using specified closure function.
     *
     * @param sourceTime        specified time in {@code LocalTime} format
     * @param representFunction specified closure function for result representation
     * @param <T>               specified type of object to which result will be represented
     * @return represented binary clock time
     */
    public <T> T of(final LocalTime sourceTime, final Function<List<BinaryClockRowRepresentation>, T> representFunction) {
        return representFunction.apply(calculateRepresentation(sourceTime));
    }
}
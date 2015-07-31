package com.trofiv.test4sidenis.binaryclock.format;

import com.google.common.base.Preconditions;
import com.trofiv.test4sidenis.binaryclock.BerlinBinaryClock;
import com.trofiv.test4sidenis.binaryclock.BinaryClockRowRepresentation;

import java.util.List;
import java.util.function.Function;

/**
 * A {@code ClockFormatter} class contains predefined closure functions to represents
 * binary clock time.
 */
public class ClockFormatter {
    private static final int AVERAGE_LINE_LENGTH = 30;

    /**
     * Text representation closure. Return rows with text like "2 of 4 (5 x HOURS) cells light".
     */
    public static final Function<List<BinaryClockRowRepresentation>, String> READABLE_FORMATTER =
            result -> {
                final StringBuilder stringBuilder = new StringBuilder(result.size() * AVERAGE_LINE_LENGTH);
                for (BinaryClockRowRepresentation row : result) {
                    //noinspection HardcodedLineSeparator
                    stringBuilder
                            .append(row.getLightCellsCount())
                            .append(" of ")
                            .append(row.getRowPattern().getCellsInRow())
                            .append(" (")
                            .append(row.getRowPattern().getCellDuration())
                            .append(" x ")
                            .append(row.getRowPattern().getCellDurationUnit())
                            .append(") cells light\n");
                }
                return stringBuilder.toString();
            };

    /**
     * Visualize Berlin Clock time as table of cells.
     * "[ ]" means cell does not light
     * "[X]" means cell lights
     * First row contains one cell for second-blinking light
     */
    public static final Function<List<BinaryClockRowRepresentation>, String> BERLIN_CLOCK_FORMATTER =
            result -> {
                Preconditions.checkArgument(result.size() == BerlinBinaryClock
                        .BERLIN_CLOCK_PATTERN.getClockRows().size(), "Clock pattern is not suitable for Berlin clock");
                final StringBuilder stringBuilder = new StringBuilder(result.size() * AVERAGE_LINE_LENGTH);
                final boolean lightBlink = result.get(4).getLightCellsCount() % 2 != 0;
                if (lightBlink) {
                    stringBuilder.append("[X]");
                } else {
                    stringBuilder.append("[ ]");
                }
                // noinspection HardcodedLineSeparator
                stringBuilder.append('\n');
                //iterate over all rows except last row with seconds
                for (int rowNumber = 0; rowNumber < 4; rowNumber++) {
                    for (int cellNumber = 0; cellNumber < result.get(rowNumber)
                            .getRowPattern().getCellsInRow(); cellNumber++) {
                        if (result.get(rowNumber).getLightCellsCount() > cellNumber) {
                            stringBuilder.append("[X]");
                        } else {
                            stringBuilder.append("[ ]");
                        }
                    }
                    // noinspection HardcodedLineSeparator
                    stringBuilder.append('\n');
                }
                return stringBuilder.toString();
            };
}
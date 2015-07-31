package com.trofiv.test4sidenis;

import com.trofiv.test4sidenis.binaryclock.BerlinBinaryClock;
import com.trofiv.test4sidenis.binaryclock.format.ClockFormatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(final String[] args) {
        final LocalTime time = LocalTime.now();
        final BerlinBinaryClock berlinBinaryClock = new BerlinBinaryClock();
        System.out.println("Converting " + time.format(DateTimeFormatter.ISO_LOCAL_TIME) + " to Berlin clock:");
        System.out.println(berlinBinaryClock.of(time, ClockFormatter.READABLE_FORMATTER));
        System.out.println(berlinBinaryClock.of(time, ClockFormatter.BERLIN_CLOCK_FORMATTER));
    }
}
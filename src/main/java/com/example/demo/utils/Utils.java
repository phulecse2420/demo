package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Utils {

    public static Date parseDate (String dateString, String format) {
        if ( dateString == null || dateString.isBlank() ) {
            throw new IllegalArgumentException("Date string cannot be null or empty");
        }
        if ( format == null || format.isBlank() ) {
            throw new IllegalArgumentException("Format string cannot be null or empty");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateString);
        }
        catch (ParseException e) {
            log.error("Invalid date format: ", e);
            throw new IllegalArgumentException("Unable to parse date with the provided format");
        }
    }
}

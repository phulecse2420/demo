package com.example.demo.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UtilsTest {

    @BeforeAll
    public static void beforeAll () {
        log.info("beforeAll");
    }

    @BeforeEach
    public void beforeEach () {
        log.info("beforeEach");
    }

    @AfterEach
    public void afterEach () {
        log.info("AfterEach");
    }

    @AfterAll
    public static void afterAll () {
        log.info("AfterAll");
    }

    @Test
    public void testParseDate_ValidDateString () {
        // Given a valid date string and format
        String dateString = "2025-04-05";
        String format = "yyyy-MM-dd";

        // When we parse the date
        Date parsedDate = Utils.parseDate(dateString, format);

        // Then the parsed date should not be null and match the expected value
        assertNotNull(parsedDate);
        assertEquals(new Date(1743786000000L), parsedDate);
    }

    @Test
    public void testParseDate_InvalidDateFormat_1 () {
        // Given an invalid date string for the format
        String dateString = "2025-04-05";
        String format = "MM/dd/yyyy"; // Incorrect format

        // When we try to parse the date, it should throw an IllegalArgumentException
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> Utils.parseDate(dateString, format)
        );

        // Then we assert that the exception message is as expected
        assertTrue(thrown.getMessage().contains("Unable to parse date"));
    }

    @Test
    public void testParseDate_InvalidDateFormat_2 () {
        // Given an invalid date string for the format
        String dateString = "2025-04-05";
        String format = "yyyy-MM-ddThh:mm:ss.SSSZ"; // Incorrect format

        // When we try to parse the date, it should throw an IllegalArgumentException
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> Utils.parseDate(dateString, format)
        );
        log.info("test", thrown);

        // Then we assert that the exception message is as expected
        assertTrue(thrown.getMessage().contains("Illegal pattern character 'T'"));
    }

    @Test
    public void testParseDate_NullDateString () {
        // Given a null date string and a valid format
        String format = "yyyy-MM-dd";

        // When we try to parse the date, it should throw an IllegalArgumentException
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> Utils.parseDate(null, format)
        );

        // Then we assert that the exception message is as expected
        assertTrue(thrown.getMessage().contains("Date string cannot be null or empty"));
    }

    @Test
    public void testParseDate_BlankDateString () {
        // Given a blank date string and a valid format
        String dateString = "   "; // Blank string
        String format = "yyyy-MM-dd";

        // When we try to parse the date, it should throw an IllegalArgumentException
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> Utils.parseDate(dateString, format)
        );

        // Then we assert that the exception message is as expected
        assertTrue(thrown.getMessage().contains("Date string cannot be null or empty"));
    }

    @Test
    public void testParseDate_NullFormat () {
        // Given a valid date string and a null format
        String dateString = "2025-04-05";

        // When we try to parse the date, it should throw an IllegalArgumentException
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> Utils.parseDate(dateString, null)
        );

        // Then we assert that the exception message is as expected
        assertTrue(thrown.getMessage().contains("Format string cannot be null or empty"));
    }

    @Test
    public void testParseDate_BlankFormat () {
        // Given a valid date string and a blank format
        String dateString = "2025-04-05";
        String format = "   "; // Blank format string

        // When we try to parse the date, it should throw an IllegalArgumentException
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> Utils.parseDate(dateString, format)
        );

        // Then we assert that the exception message is as expected
        assertTrue(thrown.getMessage().contains("Format string cannot be null or empty"));
    }

}

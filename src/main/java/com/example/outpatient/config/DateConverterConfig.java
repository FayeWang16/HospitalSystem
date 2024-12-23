package com.example.outpatient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
public class DateConverterConfig {

    /** Default date-time format */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** Default date format */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /** Default time format */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * LocalDate converter, used for converting RequestParam and PathVariable parameters
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
            }
        };
    }

    /**
     * LocalDateTime converter, used for converting RequestParam and PathVariable parameters
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
            }
        };
    }

    /**
     * LocalTime converter, used for converting RequestParam and PathVariable parameters
     */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return LocalTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
            }
        };
    }

    /**
     * Date converter, used for converting RequestParam and PathVariable parameters
     */
    @Bean
    public Converter<String, Date> dateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
                try {
                    Date parse = format.parse(source);
                    return parse;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
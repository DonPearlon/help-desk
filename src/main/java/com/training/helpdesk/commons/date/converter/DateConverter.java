package com.training.helpdesk.commons.date.converter;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Class provides conversion operations for instances of the {@link Date} and
 * {@link LocalDate} classes.
 *
 * @author Alexandr_Terehov
 */
@Component
public class DateConverter {

    /**
     * Provides conversion of the instance of the {@link LocalDate} class to the
     * instance of the {@link Date} class
     *
     * @param date
     *            instance of the {@link LocalDate} class.
     */
    public Date toDate(final LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }
    
    /**
     * Provides conversion of the instance of the {@link Date} class to the
     * instance of the {@link LocalDate} class
     *
     * @param date
     *            instance of the {@link Date} class.
     */
    public LocalDate toLocalDate(final Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
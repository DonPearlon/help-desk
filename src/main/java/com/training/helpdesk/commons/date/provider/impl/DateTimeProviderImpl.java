package com.training.helpdesk.commons.date.provider.impl;

import com.training.helpdesk.commons.date.provider.DateTimeProvider;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Implementation of the {@link DateTimeProvider} interface.
 *
 * @author Alexandr_Terehov
 */
@Service
public class DateTimeProviderImpl implements DateTimeProvider {
    
    /**
     * @return current date in UTC. {@link LocalDate}
     */
    public LocalDate getCurrentDateUtc() {
        return LocalDate.now(Clock.systemUTC());
    }
    
    /**
     * @return current time and date in UTC. {@link LocalDateTime}
     */
    public LocalDateTime getCurrentDateTimeUtc() {
        return LocalDateTime.now(Clock.systemUTC());
    }
}
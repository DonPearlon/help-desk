package com.training.helpdesk.commons.date.provider;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Interface used for representing a service which returns current date-time
 * values in the UTC format.
 *
 * @author Alexandr_Terehov
 */
public interface DateTimeProvider {
    
    /**
     * @return current date in UTC. {@link LocalDate}
     */
    LocalDate getCurrentDateUtc();
    
    /**
     * @return current time and date in UTC. {@link LocalDateTime}
     */
    LocalDateTime getCurrentDateTimeUtc();
}
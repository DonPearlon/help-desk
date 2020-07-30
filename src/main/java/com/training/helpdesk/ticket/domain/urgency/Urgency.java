package com.training.helpdesk.ticket.domain.urgency;

import com.training.helpdesk.commons.exceptions.NotFoundException;

/**
 * Enum represents possible urgency state of the ticket.
 *
 * @author Alexandr_Terehov
 */
public enum Urgency {
    CRITICAL(1),
    HIGH(2),
    AVERAGE(3),
    LOW(4);

    private final int index;

    Urgency(final int index) {
        this.index = index;
    }
    
    /**
     * @return index of the ticket's urgency.
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * 
     * @param index - index of the ticket's urgency.
     * @return urgency related to the index.
     */
    public static Urgency getByIndex(final int index) {
        switch (index) {
            case 1:
                return CRITICAL;
            case 2:
                return HIGH;
            case 3:
                return AVERAGE;
            case 4:
                return LOW;
            default:
                throw new NotFoundException("Ticket urgency state not found.");
        }
    }
    
    @Override
    public String toString() {
        switch (this) {
            case CRITICAL:
                return "Critical";
            case HIGH:
                return "High";
            case AVERAGE:
                return "Average";
            case LOW:
                return "Low";
            default:
                throw new NotFoundException("Ticket urgency state not found.");
        }
    }   
}
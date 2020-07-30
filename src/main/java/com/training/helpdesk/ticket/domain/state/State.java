package com.training.helpdesk.ticket.domain.state;

import com.training.helpdesk.commons.exceptions.NotFoundException;

/**
 * Enum represents possible state of the ticket.
 *
 * @author Alexandr_Terehov
 */
public enum State {
    DRAFT(1),
    NEW(2),
    APPROVED(3),
    DECLINED(4),
    IN_PROGRESS(5),
    DONE(6),
    CANCELED(7);

    private final int index;

    State(final int index) {
        this.index = index;
    }
    
    /**
     * @return index of the ticket's state.
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * 
     * @param index - index of the ticket's state.
     * @return state related to the index.
     */
    public static State getByIndex(final int index) {
        switch (index) {
            case 1:
                return DRAFT;
            case 2:
                return NEW;
            case 3:
                return APPROVED;
            case 4:
                return DECLINED;
            case 5:
                return IN_PROGRESS;
            case 6:
                return DONE;
            case 7:
                return CANCELED; 
            default:
                throw new NotFoundException("Ticket state not found.");
        }
    }
    
    @Override
    public String toString() {
        switch (this) {
            case DRAFT:
                return "Draft";
            case NEW:
                return "New";
            case APPROVED:
                return "Approved";
            case DECLINED:
                return "Declined";
            case IN_PROGRESS:
                return "In progress";
            case DONE:
                return "Done";
            case CANCELED:
                return "Canceled";     
            default:
                throw new NotFoundException("Ticket state not found.");
        }
    }   
}
package com.training.helpdesk.ticket.action.domain;

import com.training.helpdesk.commons.exceptions.NotFoundException;

/**
 * Enum representing ticket actions that can be used in the 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public enum Action {
    SUBMIT(1),
    CANCEL(2),
    APPROVE(3),
    DECLINE(4),
    ASSIGN_TO_ME(5),
    DONE(6),
    LEAVE_FEEDBACK(7),
    VIEW_FEEDBACK(8);

    private final int index;

    Action(final int index) {
        this.index = index;
    }
    
    /**
     * @return index of the action.
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * 
     * @param index - index of the action.
     * @return action related to the index.
     */
    public static Action getByIndex(final int index) {
        switch (index) {
            case 1:
                return SUBMIT;
            case 2:
                return CANCEL;
            case 3:
                return APPROVE;
            case 4:
                return DECLINE;
            case 5:
                return ASSIGN_TO_ME;
            case 6:
                return DONE;
            case 7:
                return LEAVE_FEEDBACK;
            case 8:
                return VIEW_FEEDBACK;    
            default:
                throw new NotFoundException("Action not found.");
        }
    }
    
    @Override
    public String toString() {
        switch (this) {
            case SUBMIT:
                return "Submit";
            case CANCEL:
                return "Cancel";
            case APPROVE:
                return "Approve";
            case DECLINE:
                return "Decline";
            case ASSIGN_TO_ME:
                return "Assign to me";
            case DONE:
                return "Done";
            case LEAVE_FEEDBACK:
                return "Leave feedback";
            case VIEW_FEEDBACK:
                return "View feedback";    
            default:
                throw new NotFoundException("Action not found.");
        }
    }   
}
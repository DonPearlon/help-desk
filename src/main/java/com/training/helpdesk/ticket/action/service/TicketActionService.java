package com.training.helpdesk.ticket.action.service;

import com.training.helpdesk.commons.exceptions.ActionForbiddenException;
import com.training.helpdesk.ticket.action.domain.Action;
import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.user.domain.User;

import java.util.List;

/**
 * Interface used for representing a service which provides 
 * operations ticket actions of the 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public interface TicketActionService {

    /**
     * Checks if action is allowed.
     * 
     * @param user
     *            user who is making the action.
     * @param ticket
     *            ticket to which the action applies.
     * @param action
     *            action to apply.                  
     * @return result of the checking.
     * @throws {@link ActionForbiddenException}
     *            if current action is forbidden.     
     */
    boolean checkAction(
            final User user, final Ticket ticket, final Action action);

    /**
     * Returns List of all actions available to the particular ticket.
     * 
     * @param user
     *            user who is making the action.
     * @param ticket
     *            ticket to which the action applies.
     * @return list of the {@link Action}. 
     */
    List<Action> getActions(final User user, final Ticket ticket);
}
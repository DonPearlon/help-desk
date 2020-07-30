package com.training.helpdesk.ticket.action.service.impl;


import com.training.helpdesk.commons.exceptions.ActionForbiddenException;
import com.training.helpdesk.ticket.action.domain.Action;
import com.training.helpdesk.ticket.action.service.TicketActionService;
import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.ticket.domain.state.State;
import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.domain.role.Role;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link TicketActionService} interface.
 *
 * @author Alexandr_Terehov
 */
@Service
public class TicketActionServiceImpl implements TicketActionService {
    
    private static final String ACTION_IS_FORBIDDEN
            = "Action: %s for user Id: %s to ticket Id: %s is forbidden.";

    /**
     * Returns List of all actions available to the particular ticket.
     * 
     * @param user
     *            user who is making the action.
     * @param ticket
     *            ticket to which the action applies.
     * @return list of the {@link Action}. 
     */
    public List<Action> getActions(final User user, final Ticket ticket) {
        List<Action> actions = new ArrayList<>();
        
        if (user.getRoleId().equals(Role.EMPLOYEE.getIndex())) {
            getEmployeeActions(user, ticket, actions);
        }
    
        if (user.getRoleId().equals(Role.MANAGER.getIndex())) {
            getManagerActions(user, ticket, actions);
        }
    
        if (user.getRoleId().equals(Role.ENGINEER.getIndex())) {
            getEngineerActions(user, ticket, actions);
        }
    
        if (ticket.getStateId().equals(State.DONE.getIndex()) 
                && checkOwner(ticket, user)) {
            if (ticket.getFeedback() != null) {
                actions.add(Action.VIEW_FEEDBACK);
            } else {
                actions.add(Action.LEAVE_FEEDBACK);
            }
        }
    
        return actions;
    }
    
    /**
     * Returns List of all actions available to the particular ticket for 
     * 'Employee' user role.
     * 
     * @param user
     *            user who is making the action.
     * @param ticket
     *            ticket to which the action applies.
     * @param actions
     *            list of the {@link Action}.
     * @return list of the {@link Action}.
     */
    private void getEmployeeActions(
            final User user, final Ticket ticket, List<Action> actions) {
        if (checkOwner(ticket, user)) {
            if (ticket.getStateId().equals(State.DRAFT.getIndex())
                    || (ticket.getStateId().equals(State.DECLINED.getIndex()))) {
                actions.add(Action.SUBMIT);
                actions.add(Action.CANCEL);
            }
        }
    }
    
    /**
     * Returns List of all actions available to the particular ticket for 
     * 'Manager' user role.
     * 
     * @param user
     *            user who is making the action.
     * @param ticket
     *            ticket to which the action applies.
     * @param actions
     *            list of the {@link Action}.
     * @return list of the {@link Action}.
     */
    private void getManagerActions(
            final User user, final Ticket ticket, List<Action> actions) {
        if (checkOwner(ticket, user)) {
            if (ticket.getStateId().equals(State.DRAFT.getIndex())
                    || (ticket.getStateId().equals(State.DECLINED.getIndex()))) {
                actions.add(Action.SUBMIT);
                actions.add(Action.CANCEL);
            }
        } else {
            if (ticket.getStateId().equals(State.NEW.getIndex())) {
                actions.add(Action.APPROVE);
                actions.add(Action.DECLINE);
                actions.add(Action.CANCEL);
            }
        }
    }
    
    /**
     * Returns List of all actions available to the particular ticket for 
     * 'Engineer' user role.
     * 
     * @param user
     *            user who is making the action.
     * @param ticket
     *            ticket to which the action applies.
     * @param actions
     *            list of the {@link Action}.
     * @return list of the {@link Action}.
     */
    private void getEngineerActions(
            final User user, final Ticket ticket, List<Action> actions) {
        if (ticket.getStateId().equals(State.APPROVED.getIndex())) {
            actions.add(Action.ASSIGN_TO_ME);
            actions.add(Action.CANCEL);
        }

        if (ticket.getStateId().equals(State.IN_PROGRESS.getIndex())
                && (ticket.getAssignee().getId().equals(user.getId()))) {
            actions.add(Action.DONE);
        }
    }
    
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
    public boolean checkAction(
            final User user, final Ticket ticket, final Action action) {
        if (!getActions(user, ticket).contains(action)) {
            throw new ActionForbiddenException(
                    String.format(ACTION_IS_FORBIDDEN, action, user.getId(), ticket.getId()));
        }
        return true;
    }
    
    /**
     * Checks if user is the owner of the ticket.
     * 
     * @param user
     *            user who is making the action.
     * @param ticket
     *            ticket to which the action applies.
     * @return result of the checking.
     */
    private boolean checkOwner(final Ticket ticket, final User user) {
        return ticket.getOwner().equals(user);
    }
}
package com.training.helpdesk.ticket.repository.h2;

import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.ticket.domain.state.State;
import com.training.helpdesk.ticket.repository.TicketRepository;
import com.training.helpdesk.user.domain.role.Role;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link TicketRepository} interface.
 * 
 * @author Alexandr_Terehov
 */
@Repository
public class TicketH2Repository implements TicketRepository {
    
    private final SessionFactory sessionFactory;

    /**
     * Constructor
     * 
     * @param sessionFactory
     *            object of the {@link SessionFactory} class to set.
     */
    public TicketH2Repository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Returns Ticket object by it's Id.
     *
     * @param id
     *            id of the ticket.
     * @return object of the {@link Ticket} class.
     */
    public Optional<Ticket> getTicketById(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(TicketQuery.SELECT_TICKET_BY_ID);
        query.setLong("id", id);
        Ticket ticket = (Ticket)query.uniqueResult();
        return Optional.ofNullable(ticket);
    }
    
    /**
     * Returns list of tickets related to the user whose role is 'Employee'.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link Ticket} class objects.
     */
    public List<Ticket> getEmployeeTickets(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(TicketQuery.SELECT_ALL_EMPLOYEE_TICKETS);
        query.setLong("id", id);
        return (List<Ticket>)query.list();
    }
    
    /**
     * Returns list of tickets related to the 'Manager' user role.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link Ticket} class objects.
     */
    public List<Ticket> getManagerTickets(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(TicketQuery.SELECT_ALL_MANAGER_TICKETS);
        query.setLong("id", id);
        query.setInteger(TicketQuery.APPROVED_STATE, State.APPROVED.getIndex());
        query.setInteger(TicketQuery.DECLINED_STATE, State.DECLINED.getIndex());
        query.setInteger(TicketQuery.IN_PROGRESS_STATE, State.IN_PROGRESS.getIndex());
        query.setInteger(TicketQuery.DONE_STATE, State.DONE.getIndex());
        query.setInteger(TicketQuery.CANCELED_STATE, State.CANCELED.getIndex());
        query.setInteger(TicketQuery.EMPLOYEE_ROLE, Role.EMPLOYEE.getIndex());
        query.setInteger(TicketQuery.NEW_STATE, State.NEW.getIndex());
        return (List<Ticket>)query.list();
    }
    
    /**
     * Returns list of tickets related to the 'Engineer' user role.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link Ticket} class objects.
     */
    public List<Ticket> getEngineerTickets(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(TicketQuery.SELECT_ALL_ENGINEER_TICKETS);
        query.setLong("id", id);
        query.setInteger(TicketQuery.EMPLOYEE_ROLE, Role.EMPLOYEE.getIndex());
        query.setInteger(TicketQuery.MANAGER_ROLE, Role.MANAGER.getIndex());
        query.setInteger(TicketQuery.APPROVED_STATE, State.APPROVED.getIndex());
        query.setInteger(TicketQuery.IN_PROGRESS_STATE, State.IN_PROGRESS.getIndex());
        query.setInteger(TicketQuery.DONE_STATE, State.DONE.getIndex());
        return (List<Ticket>)query.list();
    }
    
    /**
     * Returns list of tickets related to the user whose role is 'Manager'.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link Ticket} class objects.
     */
    public List<Ticket> getMyManagerTickets(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(TicketQuery.SELECT_MY_MANAGER_TICKETS);
        query.setLong("id", id);
        query.setInteger(TicketQuery.APPROVED_STATE, State.APPROVED.getIndex());
        return (List<Ticket>)query.list();
    }
    
    /**
     * Returns list of tickets related to the user whose role is 'Engineer'.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link Ticket} class objects.
     */
    public List<Ticket> getMyEngineerTickets(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(TicketQuery.SELECT_MY_ENGINEER_TICKETS);
        query.setLong("id", id);
        return (List<Ticket>)query.list();
    }
    
    /**
     * Inserts into database information about the new Ticket.
     *
     * @param ticket
     *            instance of the {@link Ticket} class.
     */
    public void insertTicket(final Ticket ticket) {
        sessionFactory.getCurrentSession().save(ticket);
    }
    
    /**
     * Updates information about the existing Ticket.
     *
     * @param ticket
     *            instance of the {@link Ticket} class.
     */
    public void updateTicket(final Ticket ticket) {
        sessionFactory.getCurrentSession().update(ticket);
    }
    
    /**
     * Nested class contains query strings used for execution of 'ticket' queries.
     */
    private static class TicketQuery {
    
        private static final String SELECT_TICKET_BY_ID = "FROM Ticket WHERE id = :id";
        private static final String SELECT_ALL_EMPLOYEE_TICKETS =
                "FROM Ticket WHERE owner.id = :id";
        private static final String SELECT_ALL_MANAGER_TICKETS =
                "FROM Ticket WHERE owner.id = :id OR (approver.id = :id AND "
                + "((stateId = :approvedState) OR (stateId = :declinedState)"
                + " OR (stateId = :inProgressState)"
                + " OR (stateId = :doneState) OR (stateId = :canceledState)))"
                + " OR (owner.roleId = :employeeRole AND stateId = :newState)";
        private static final String SELECT_ALL_ENGINEER_TICKETS =
                "FROM Ticket WHERE (((owner.roleId = :employeeRole) "
                    + "OR (owner.roleId = :managerRole)) AND stateId = :approvedState)"
                    + " OR (assignee.id = :id AND ((stateId = :inProgressState)"
                    + " OR (stateId = :doneState)))";
        private static final String SELECT_MY_MANAGER_TICKETS =
                "FROM Ticket WHERE (owner.id = :id) "
                + "OR (approver.id = :id AND stateId = :approvedState)";
        private static final String SELECT_MY_ENGINEER_TICKETS =
                "FROM Ticket WHERE assignee.id = :id";
        
        private static final String APPROVED_STATE = "approvedState";
        private static final String DECLINED_STATE = "declinedState";
        private static final String IN_PROGRESS_STATE = "inProgressState";
        private static final String DONE_STATE = "doneState";
        private static final String CANCELED_STATE = "canceledState";
        private static final String NEW_STATE = "newState";
        
        private static final String EMPLOYEE_ROLE = "employeeRole";
        private static final String MANAGER_ROLE = "managerRole";
    }
}
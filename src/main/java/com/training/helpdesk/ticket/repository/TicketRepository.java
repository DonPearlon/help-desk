package com.training.helpdesk.ticket.repository;

import com.training.helpdesk.ticket.domain.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface. Provides 'database' operations with
 * {@link Ticket} objects.
 * 
 * @author Alexandr_Terehov
 */
public interface TicketRepository {

    /**
     * Returns ticket by it's ID.
     *
     * @param id
     *            id of the ticket.
     * @return object of the {@link Ticket} class.
     */
    Optional<Ticket> getTicketById(final Long id);
    
    /**
     * Returns list of tickets related to the user whose role is 'Employee'.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link Ticket} class objects.
     */
    List<Ticket> getEmployeeTickets(final Long id);
    
    /**
     * Returns list of tickets related to the 'Manager' user role.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link Ticket} class objects.
     */
    List<Ticket> getManagerTickets(final Long id);
    
    /**
     * Returns list of tickets related to the 'Engineer' user role.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link Ticket} class objects.
     */
    List<Ticket> getEngineerTickets(final Long id);
    
    /**
     * Returns list of tickets related to the user whose role is 'Manager'.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link Ticket} class objects.
     */
    List<Ticket> getMyManagerTickets(final Long id);
    
    /**
     * Returns list of tickets related to the user whose role is 'Engineer'.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link Ticket} class objects.
     */
    List<Ticket> getMyEngineerTickets(final Long id);
    
    /**
     * Inserts into database information about the new Ticket.
     *
     * @param ticket
     *            instance of the {@link Ticket} class.
     */
    void insertTicket(final Ticket ticket);
    
    /**
     * Updates information about the existing Ticket.
     *
     * @param ticket
     *            instance of the {@link Ticket} class.
     */
    void updateTicket(final Ticket ticket);
}
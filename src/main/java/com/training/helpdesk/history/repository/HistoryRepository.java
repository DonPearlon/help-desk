package com.training.helpdesk.history.repository;

import com.training.helpdesk.history.domain.History;

import java.util.List;

/**
 * Data Access Object interface. Provides 'database' operations with
 * {@link History} objects.
 * 
 * @author Alexandr_Terehov
 */
public interface HistoryRepository {

    /**
     * Insert into database information about the history event.
     *
     * @param history
     *            instance of the {@link History} class.
     */
    void insertHistoryEvent(final History history);
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns a list of all history events contained in the database
     *         related to the ticket with specified id.
     */
    List<History> getHistoryByTicketId(final Long id);
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @param maxResults 
     *            the maximum number of rows.              
     * @return Returns a list of history events contained in the database
     *         related to the ticket with specified id.
     */
    List<History> getHistoryByTicketId(final Long id, final int maxResults);
}
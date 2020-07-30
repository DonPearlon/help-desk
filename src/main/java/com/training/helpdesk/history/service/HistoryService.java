package com.training.helpdesk.history.service;

import com.training.helpdesk.history.domain.History;
import com.training.helpdesk.history.dto.HistoryDto;

import java.util.List;

/**
 * Interface used for representing a service which provides 
 * operations with ticket history events of the 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public interface HistoryService {

    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns list of history events contained in the database
     *         related to the ticket with specified id.
     */
    List<HistoryDto> getHistoryByTicketId(final Long id);

    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns full list of history events contained in the database
     *         related to the ticket with specified id.
     */
    List<HistoryDto> getFullHistoryByTicketId(final Long id);

    /**
     * Saves information about the history event.
     *
     * @param history
     *            instance of the {@link History} class.
     */
    void saveHistoryEvent(final History history);
}
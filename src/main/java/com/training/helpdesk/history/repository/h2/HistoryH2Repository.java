package com.training.helpdesk.history.repository.h2;

import com.training.helpdesk.history.domain.History;
import com.training.helpdesk.history.repository.HistoryRepository;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the {@link HistoryRepository} interface. 
 *
 * @author Alexandr_Terehov
 */
@Repository
public class HistoryH2Repository implements HistoryRepository {
    private static final String SELECT_HISTORY_BY_TICKET 
            = "FROM History WHERE ticketId = :id order by date desc";

    private final SessionFactory sessionFactory;
    
    /**
     * Constructor
     * 
     * @param sessionFactory
     *            object implements {@link SessionFactory} interface to set.
     */
    public HistoryH2Repository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Insert into database information about the history event.
     *
     * @param history
     *            instance of the {@link History} class.
     */
    public void insertHistoryEvent(final History history) {
        sessionFactory.getCurrentSession().save(history);
    }
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns a list of all history events contained in the database
     *         related to the ticket with specified id.
     */
    public List<History> getHistoryByTicketId(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(SELECT_HISTORY_BY_TICKET);
        query.setLong("id", id);
        return (List<History>)query.list(); 
    }
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @param maxResults 
     *            the maximum number of rows.              
     * @return Returns a list of history events contained in the database
     *         related to the ticket with specified id.
     */
    public List<History> getHistoryByTicketId(final Long id, final int maxResults) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(SELECT_HISTORY_BY_TICKET);
        query.setLong("id", id);
        query.setMaxResults(maxResults);
        return (List<History>)query.list(); 
    }
}
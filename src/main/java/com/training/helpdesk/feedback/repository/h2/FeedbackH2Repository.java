package com.training.helpdesk.feedback.repository.h2;

import com.training.helpdesk.feedback.domain.Feedback;
import com.training.helpdesk.feedback.repository.FeedbackRepository;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementation of the {@link FeedbackRepository} interface. 
 *
 * @author Alexandr_Terehov
 */
@Repository
public class FeedbackH2Repository implements FeedbackRepository {
    private static final String SELECT_FEEDBACK_BY_TICKET
            = "FROM Feedback WHERE ticket.id = :id";

    private final SessionFactory sessionFactory;
    
    /**
     * Constructor
     * 
     * @param sessionFactory
     *            object implements {@link SessionFactory} interface to set.
     */
    public FeedbackH2Repository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Insert into database information about the feedback.
     *
     * @param feedback
     *            instance of the {@link Feedback} class.
     */
    public void insertFeedback(final Feedback feedback) {
        sessionFactory.getCurrentSession().save(feedback);
    }
    
    /**
     * 
     * @param ticketId
     *            id of the ticket.
     * @return feedback related to the ticket with specified id.
     */
    public Optional<Feedback> getFeedbackByTicketId(final Long ticketId) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(SELECT_FEEDBACK_BY_TICKET);
        query.setLong("id", ticketId);
        Feedback feedback = (Feedback) query.uniqueResult();
        return Optional.ofNullable(feedback);
    }
}
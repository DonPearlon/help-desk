package com.training.helpdesk.feedback.repository;

import com.training.helpdesk.feedback.domain.Feedback;

import java.util.Optional;

/**
 * Data Access Object interface. Provides 'database' operations with
 * {@link Feedback} objects.
 * 
 * @author Alexandr_Terehov
 */
public interface FeedbackRepository {

    /**
     * Insert into database information about the feedback.
     *
     * @param feedback
     *            instance of the {@link Feedback} class.
     */
    void insertFeedback(final Feedback feedback);

    /**
     * 
     * @param ticketId
     *            id of the ticket.
     * @return feedback related to the ticket with specified id.
     */
    Optional<Feedback> getFeedbackByTicketId(final Long ticketId);
}

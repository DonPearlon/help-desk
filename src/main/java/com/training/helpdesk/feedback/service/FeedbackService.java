package com.training.helpdesk.feedback.service;

import com.training.helpdesk.commons.exceptions.ActionForbiddenException;
import com.training.helpdesk.commons.exceptions.NotFoundException;
import com.training.helpdesk.feedback.dto.FeedbackDto;

/**
 * Interface used for representing a service which provides 
 * operations with ticket feedback of 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public interface FeedbackService {
    
    /**
     * Saves information about the feedback.
     *
     * @param userId
     *            id of the user who left feedback.
     * @param ticketId
     *            id of the ticket related to the feedback.
     * @param feedbackDto
     *            {@link FeedbackDto}. 
     * @throws {@link ActionForbiddenException}
     *            if current action is forbidden.                                    
     */
    void saveFeedback(final Long userId,
            final Long ticketId, final FeedbackDto feedbackDto);
    
    /**
     * Returns feedback related to the ticket with specified id.
     *
     * @param ticketId
     *            id of the ticket related to the feedback.
     * @return feedbackDto
     *            {@link FeedbackDto}.
     * @throws {@link NotFoundException}
     *            if feedback was not found.                                     
     */
    FeedbackDto getFeedbackByTicketId(final Long ticketId);
}
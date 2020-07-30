package com.training.helpdesk.feedback.service.impl;

import com.training.helpdesk.commons.date.provider.DateTimeProvider;
import com.training.helpdesk.commons.exceptions.ActionForbiddenException;
import com.training.helpdesk.commons.exceptions.NotFoundException;
import com.training.helpdesk.email.service.EmailService;
import com.training.helpdesk.feedback.converter.FeedbackConverter;
import com.training.helpdesk.feedback.domain.Feedback;
import com.training.helpdesk.feedback.dto.FeedbackDto;
import com.training.helpdesk.feedback.repository.FeedbackRepository;
import com.training.helpdesk.feedback.service.FeedbackService;
import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.ticket.domain.state.State;
import com.training.helpdesk.ticket.service.TicketService;
import com.training.helpdesk.user.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

import javax.mail.MessagingException;

/**
 * Implementation of the {@link FeedbackService} interface.
 *
 * @author Alexandr_Terehov
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    private static final Logger LOGGER 
            = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private static final String FEEDBACK_NOT_FOUND
            = "Feedback for ticket with Id:%s was not found.";
    private static final String FEEDBACK_IS_FORBIDDEN
            = "Feedback for user Id: %s to ticket Id: %s is forbidden.";
    private static final String EMAIL_ERROR = "Error with sending e-mail";

    private final FeedbackRepository repository;
    private final FeedbackConverter converter;
    private final TicketService ticketService; 
    private final DateTimeProvider dateTimeProvider;
    private final EmailService emailService;
    
    /**
     * Constructor.
     *
     * @param repository
     *            {@link FeedbackRepository}.
     * @param converter
     *            {@link FeedbackConverter}.
     * @param ticketService
     *            {@link TicketService}.
     * @param dateTimeProvider
     *            {@link DateTimeProvider}.
     * @param emailService
     *            {@link EmailService}.                        
     */
    public FeedbackServiceImpl(final FeedbackRepository repository,
            final FeedbackConverter converter, final TicketService ticketService,
            final DateTimeProvider dateTimeProvider, final EmailService emailService) {
        this.repository = repository;
        this.converter = converter;
        this.ticketService = ticketService;
        this.dateTimeProvider = dateTimeProvider;
        this.emailService = emailService;
    }
    
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
    @Transactional
    public void saveFeedback(
            final Long userId, final Long ticketId, final FeedbackDto feedbackDto) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        if (!ticket.getOwner().getId().equals(userId) 
                || (ticket.getStateId() != State.DONE.getIndex()) 
                || (ticket.getFeedback() != null)) {
            throw new ActionForbiddenException(
                    String.format(FEEDBACK_IS_FORBIDDEN, userId, ticketId));
        }
        Feedback feedback = converter.toEntity(feedbackDto);
        feedback.setDate(Date.valueOf(dateTimeProvider.getCurrentDateUtc()));
        feedback.setTicket(ticket);
        repository.insertFeedback(feedback);
        User engineer = ticket.getAssignee();
        try {
            emailService.sendTicketFeedbackEmail(
                    engineer.getEmail(), engineer.getFirstName(),
                    engineer.getLastName(), ticket.getId());
        } catch (MessagingException exc) {
            LOGGER.error(EMAIL_ERROR);
        }
    }
    
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
    @Transactional
    public FeedbackDto getFeedbackByTicketId(final Long ticketId) {
        Feedback feedback = repository.getFeedbackByTicketId(ticketId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(FEEDBACK_NOT_FOUND, ticketId)));
        return converter.toDto(feedback);
    }
}
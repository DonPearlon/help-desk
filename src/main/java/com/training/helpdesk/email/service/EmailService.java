package com.training.helpdesk.email.service;

import javax.mail.MessagingException;

/**
 * Interface used for representing a service which sends notification emails to
 * the users of 'Help-Desk' application.
 *
 * @author Alexandr_Terehov
 */
public interface EmailService {

    /**
     * Sends email about new ticket
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param ticketId
     *            id of the ticket.
     */
    void sendTicketNewEmail(final String recipientEmail, 
            final Long ticketId) throws MessagingException;

    /**
     * Sends email about approved ticket
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param ticketId
     *            id of the ticket.
     */
    void sendTicketApprovedEmail(final String recipientEmail, 
            final Long ticketId) throws MessagingException;

    /**
     * Sends email about declined ticket
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param recipientFirstName
     *            first name of the recipient.
     * @param recipientLastName
     *            last name of the recipient.                        
     * @param ticketId
     *            id of the ticket.
     */
    void sendTicketDeclinedEmail(final String recipientEmail, 
            final String recipientFirstName, final String recipientLastName,
            final Long ticketId) throws MessagingException;

    /**
     * Sends email about cancelled ticket
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param recipientFirstName
     *            first name of the recipient.
     * @param recipientLastName
     *            last name of the recipient.                        
     * @param ticketId
     *            id of the ticket.
     */
    void sendTicketCancelledManagerEmail(final String recipientEmail, 
            final String recipientFirstName, final String recipientLastName,
            final Long ticketId) throws MessagingException;

    /**
     * Sends email about cancelled ticket
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param ticketId
     *            id of the ticket.
     */
    void sendTicketCancelledEngineerEmail(final String recipientEmail, 
            final Long ticketId) throws MessagingException;

    /**
     * Sends email about 'done' ticket 
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param recipientFirstName
     *            first name of the recipient.
     * @param recipientLastName
     *            last name of the recipient.                        
     * @param ticketId
     *            id of the ticket.
     */
    void sendTicketDoneEmail(final String recipientEmail, 
            final String recipientFirstName, final String recipientLastName,
            final Long ticketId) throws MessagingException;

    /**
     * Sends email with info that ticket is available for feedback.
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param recipientFirstName
     *            first name of the recipient.
     * @param recipientLastName
     *            last name of the recipient.                        
     * @param ticketId
     *            id of the ticket.
     */
    void sendTicketFeedbackEmail(final String recipientEmail, 
            final String recipientFirstName, final String recipientLastName,
            final Long ticketId) throws MessagingException;
}
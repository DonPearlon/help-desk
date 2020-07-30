package com.training.helpdesk.email.service.impl;

import com.training.helpdesk.email.service.EmailService;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Implementation of the {@link EmailService} interface.
 *
 * @author Alexandr_Terehov
 */
@Service
public class EmailServiceImpl implements EmailService {
    
    private static final String HELP_DESK_EMAIL = "fostermailing20@gmail.com";

    private static final String TICKET_BASE_URL 
            = "http://localhost:3000/help-desk/ticket/";
    
    private static final String EMAIL_NEW_TEMPLATE = "html/ticket-new-email";
    private static final String EMAIL_APPROVED_TEMPLATE = "html/ticket-approved-email";
    private static final String EMAIL_DECLINED_TEMPLATE = "html/ticket-declined-email";
    private static final String EMAIL_CANCELLED_MANAGER_TEMPLATE
            = "html/ticket-cancelled-manager-email";
    private static final String EMAIL_CANCELLED_ENGINEER_TEMPLATE
            = "html/ticket-cancelled-engineer-email";
    private static final String EMAIL_DONE_TEMPLATE = "html/ticket-done-email";
    private static final String EMAIL_FEEDBACK_TEMPLATE = "html/ticket-feedback-email";

    private static final String TICKET_NEW = "New ticket for approval";
    private static final String TICKET_APPROVED = "Ticket was approved";
    private static final String TICKET_DECLINED = "Ticket was declined";
    private static final String TICKET_CANCELLED = "Ticket was cancelled";
    private static final String TICKET_DONE = "Ticket was done";
    private static final String TICKET_FEEDBACK = "Feedback was provided";

    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;

    /**
     * Constructor.
     *
     * @param mailSender
     *            {@link JavaMailSender}.
     * @param htmlTemplateEngine
     *            {@link TemplateEngine}.
     */
    public EmailServiceImpl(
            final JavaMailSender mailSender, final TemplateEngine htmlTemplateEngine) {
        this.mailSender = mailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
    }
    
    /**
     * Sends email about new ticket
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param ticketId
     *            id of the ticket.
     */
    public void sendTicketNewEmail(final String recipientEmail, 
            final Long ticketId) throws MessagingException {
        sendEmail(recipientEmail, getTicketEmailContext(ticketId),
                TICKET_NEW, EMAIL_NEW_TEMPLATE);
    }
    
    /**
     * Sends email about approved ticket
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param ticketId
     *            id of the ticket.
     */
    public void sendTicketApprovedEmail(final String recipientEmail, 
            final Long ticketId) throws MessagingException {
        sendEmail(recipientEmail, getTicketEmailContext(ticketId),
                TICKET_APPROVED, EMAIL_APPROVED_TEMPLATE);
    }
    
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
    public void sendTicketDeclinedEmail(final String recipientEmail, 
            final String recipientFirstName, final String recipientLastName,
            final Long ticketId) throws MessagingException {
        sendEmail(recipientEmail, 
                getTicketEmailContext(recipientFirstName, recipientLastName, ticketId),
                TICKET_DECLINED, EMAIL_DECLINED_TEMPLATE);
    }
    
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
    public void sendTicketCancelledManagerEmail(final String recipientEmail, 
            final String recipientFirstName, final String recipientLastName,
            final Long ticketId) throws MessagingException {
        sendEmail(recipientEmail, 
                getTicketEmailContext(recipientFirstName, recipientLastName, ticketId),
                TICKET_CANCELLED, EMAIL_CANCELLED_MANAGER_TEMPLATE);
    }
    
    /**
     * Sends email about cancelled ticket
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param ticketId
     *            id of the ticket.
     */
    public void sendTicketCancelledEngineerEmail(final String recipientEmail, 
            final Long ticketId) throws MessagingException {
        sendEmail(recipientEmail, getTicketEmailContext(ticketId),
                TICKET_CANCELLED, EMAIL_CANCELLED_ENGINEER_TEMPLATE);
    }
    
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
    public void sendTicketDoneEmail(final String recipientEmail, 
            final String recipientFirstName, final String recipientLastName,
            final Long ticketId) throws MessagingException {
        sendEmail(recipientEmail, 
                getTicketEmailContext(recipientFirstName, recipientLastName, ticketId),
                TICKET_DONE, EMAIL_DONE_TEMPLATE);
    }
    
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
    public void sendTicketFeedbackEmail(final String recipientEmail, 
            final String recipientFirstName, final String recipientLastName,
            final Long ticketId) throws MessagingException {
        sendEmail(recipientEmail,
                getTicketFeedbackEmailContext(recipientFirstName, recipientLastName, ticketId),
                TICKET_FEEDBACK, EMAIL_FEEDBACK_TEMPLATE);
    }
    
    /**
     * Returns context of the ticket notification email.
     *
     * @param ticketId
     *            id of the ticket.
     * @return {@link Context}
     *            ticket context.  
     */
    private Context getTicketEmailContext(final Long ticketId) {
        final Context context = new Context();
        context.setVariable("ticketId", ticketId);
        context.setVariable("linkTo", TICKET_BASE_URL + ticketId);
        return context;
    }
    
    /**
     * Returns context of the ticket notification email.
     *
     * @param recipientFirstName
     *            first name of the recipient.
     * @param recipientLastName
     *            last name of the recipient.  
     * @param ticketId
     *            id of the ticket.
     * @return {@link Context}
     *            ticket context.  
     */
    private Context getTicketEmailContext(final String recipientFirstName,
            final String recipientLastName,final Long ticketId) {
        final Context context = new Context();
        context.setVariable("firstName", recipientFirstName);
        context.setVariable("lastName", recipientLastName);
        context.setVariable("ticketId", ticketId);
        context.setVariable("linkTo", TICKET_BASE_URL + ticketId);
        return context;
    }
    
    /**
     * Returns context of the ticket feedback notification email.
     *
     * @param recipientFirstName
     *            first name of the recipient.
     * @param recipientLastName
     *            last name of the recipient.  
     * @param ticketId
     *            id of the ticket.
     * @return {@link Context}
     *            ticket context.  
     */
    private Context getTicketFeedbackEmailContext(final String recipientFirstName,
            final String recipientLastName,final Long ticketId) {
        final Context context = new Context();
        context.setVariable("firstName", recipientFirstName);
        context.setVariable("lastName", recipientLastName);
        context.setVariable("ticketId", ticketId);
        context.setVariable("linkTo", TICKET_BASE_URL + ticketId + "/feedback/view");
        return context;
    }
    
    /**
     * Sends email with info that ticket is available for feedback.
     *
     * @param recipientEmail
     *            email of the recipient.
     * @param context
     *            context of the email.
     * @param subject
     *            subject of the email.                        
     * @param emailTemplate
     *            template of the email.
     */
    private void sendEmail(final String recipientEmail, 
            final Context context, final String subject,
            final String emailTemplate) throws MessagingException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom(HELP_DESK_EMAIL);
        message.setTo(recipientEmail);

        final String htmlContent = this.htmlTemplateEngine.process(emailTemplate, context);
        message.setText(htmlContent, true);
        this.mailSender.send(mimeMessage);
    }
}
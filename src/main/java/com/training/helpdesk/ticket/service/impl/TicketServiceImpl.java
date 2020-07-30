package com.training.helpdesk.ticket.service.impl;

import com.training.helpdesk.attachment.domain.Attachment;
import com.training.helpdesk.attachment.service.AttachmentService;
import com.training.helpdesk.category.domain.Category;
import com.training.helpdesk.category.service.CategoryService;
import com.training.helpdesk.comment.domain.Comment;
import com.training.helpdesk.commons.date.provider.DateTimeProvider;
import com.training.helpdesk.commons.exceptions.ActionForbiddenException;
import com.training.helpdesk.commons.exceptions.NotFoundException;
import com.training.helpdesk.email.service.EmailService;
import com.training.helpdesk.history.domain.History;
import com.training.helpdesk.ticket.action.converter.ActionConverter;
import com.training.helpdesk.ticket.action.domain.Action;
import com.training.helpdesk.ticket.action.dto.ActionDto;
import com.training.helpdesk.ticket.action.service.TicketActionService;
import com.training.helpdesk.ticket.converter.TicketConverter;
import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.ticket.domain.state.State;
import com.training.helpdesk.ticket.dto.TicketCreationDto;
import com.training.helpdesk.ticket.dto.TicketDescriptionDto;
import com.training.helpdesk.ticket.dto.TicketDto;
import com.training.helpdesk.ticket.repository.TicketRepository;
import com.training.helpdesk.ticket.service.TicketService;
import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.dto.UserDto;
import com.training.helpdesk.user.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

/**
 * Implementation of the {@link TicketService} interface.
 *
 * @author Alexandr_Terehov
 */
@Service
public class TicketServiceImpl  implements TicketService {
    private static final Logger LOGGER 
            = LoggerFactory.getLogger(TicketServiceImpl.class);

    private static final int EMPLOYEE = 1;
    private static final int MANAGER = 2;
    private static final int ENGINEER = 3;
    
    private static final int SUBMIT = 1;
    private static final int CANCEL = 2;
    private static final int APPROVE = 3;
    private static final int DECLINE  = 4;
    private static final int ASSIGN_TO_ME = 5;
    private static final int DONE = 6;

    private static final String TICKET_NOT_FOUND
            = "Ticket with Id:%s was not found.";
    private static final String ROLE_NOT_FOUND
            = "Role with Id:%s was not found.";
    private static final String ACTION_NOT_FOUND
            = "Action with Id:%s was not found.";
    private static final String ATTACHMENT_NOT_FOUND
            = "Attachment for Ticket with Id:%s was not found.";
    
    private static final String HISTORY_TICKET_CREATED = "Ticket is created.";
    private static final String HISTORY_TICKET_EDITED = "Ticket is edited.";
    private static final String HISTORY_FILE_ATTACHED = "File is attached";
    private static final String HISTORY_FILE_REMOVED = "File is removed";
    private static final String HISTORY_TICKET_STATUS = "Ticket status is changed";
    
    private static final String EMAIL_ERROR = "Error with sending e-mail";
    
    private static final String ACTION_IS_FORBIDDEN
            = "Action: %s for user Id: %s to ticket Id: %s is forbidden.";
    private final TicketRepository repository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final AttachmentService attachmentService;
    private final TicketConverter ticketConverter;
    private final DateTimeProvider dateTimeProvider;
    private final ActionConverter actionConverter;
    private final TicketActionService ticketActionService; 
    private final EmailService emailService;
    
    /**
     * Constructor
     * 
     * @param repository
     *            object implements {@link TicketRepository} interface to set.
     * @param userService
     *            object implements {@link UserService} interface to set.            
     * @param ticketConverter
     *            instance of the {@link TicketConverter} class to set. 
     * @param categoryService
     *            object implements {@link CategoryService} interface to set.
     * @param attachmentService
     *            object implements {@link AttachmentService} interface to set. 
     * @param dateTimeProvider
     *            object implements {@link DateTimeProvider} interface to set. 
     * @param actionConverter
     *            instance of the {@link ActionConverter} class to set.   
     * @param ticketActionService
     *            object implements {@link TicketActionService} interface to set. 
     * @param emailService
     *            object implements {@link EmailService} interface to set.   
     *                         
     */
    public TicketServiceImpl(final TicketRepository repository,
            final UserService userService, final TicketConverter ticketConverter,
            final CategoryService categoryService, final AttachmentService attachmentService,
            final DateTimeProvider dateTimeProvider, final ActionConverter actionConverter,
            final TicketActionService ticketActionService, final EmailService emailService) {
        this.repository = repository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.attachmentService = attachmentService;
        this.ticketConverter = ticketConverter;
        this.dateTimeProvider = dateTimeProvider;
        this.actionConverter = actionConverter;
        this.ticketActionService = ticketActionService;
        this.emailService = emailService;
    }
    
    /**
     * Returns ticket with specified id.
     *
     * @param id
     *            id of the ticket.
     * @return object of the {@link TicketDescriptionDto} class.
     * @throws {@link NotFoundException}
     *            if ticket was not found.  
     */
    @Transactional 
    public TicketDescriptionDto getTicketDtoById(final Long id) {
        Ticket ticket = repository.getTicketById(id)
                .orElseThrow(() -> new NotFoundException(String.format(TICKET_NOT_FOUND, id)));
        return ticketConverter.toDtoWithDescription(ticket);
    }
    
    /**
     * Returns ticket with specified id.
     *
     * @param id
     *            id of the ticket.
     * @return object of the {@link Ticket} class.
     * @throws {@link NotFoundException}
     *            if ticket was not found.  
     */
    @Transactional 
    public Ticket getTicketById(final Long id) {
        return repository.getTicketById(id)
                .orElseThrow(() -> new NotFoundException(String.format(TICKET_NOT_FOUND, id)));
    }
    
    /**
     * Returns list of tickets related related to the user's role.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link TicketDto} class objects.
     */
    @Transactional
    public List<TicketDto>  getTicketsByUserId(final Long id) {
        User user = userService.getUserById(id);
        switch (user.getRoleId()) {
            case EMPLOYEE:
                return repository.getEmployeeTickets(id)
                        .stream().map(ticketConverter::toDto).sorted(Comparator.comparing(
                                ticket -> ticket.getUrgency().getIndex()))
                                        .collect(Collectors.toList());
            case MANAGER:
                return repository.getManagerTickets(id)
                        .stream().map(ticketConverter::toDto).sorted(Comparator.comparing(
                                ticket -> ticket.getUrgency().getIndex()))
                                        .collect(Collectors.toList());
            case ENGINEER:
                return repository.getEngineerTickets(id)
                        .stream().map(ticketConverter::toDto).sorted(Comparator.comparing(
                                ticket -> ticket.getUrgency().getIndex()))
                                        .collect(Collectors.toList());
            default:
                throw new NotFoundException(String.format(ROLE_NOT_FOUND, id));
        }
    }
    
    /**
     * Returns list of tickets related related to the particular user.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link TicketDto} class objects.
     */
    @Transactional
    public List<TicketDto> getMyTicketsByUserId(final Long id) {
        User user = userService.getUserById(id);
        switch (user.getRoleId()) {
            case EMPLOYEE:
                return repository.getEmployeeTickets(id)
                        .stream().map(ticketConverter::toDto).sorted(Comparator.comparing(
                                ticket -> ticket.getUrgency().getIndex()))
                                    .collect(Collectors.toList());
            case MANAGER:
                return repository.getMyManagerTickets(id)
                        .stream().map(ticketConverter::toDto).sorted(Comparator.comparing(
                                ticket -> ticket.getUrgency().getIndex()))
                                    .collect(Collectors.toList());
            case ENGINEER:
                return repository.getMyEngineerTickets(id)
                        .stream().map(ticketConverter::toDto).sorted(Comparator.comparing(
                                ticket -> ticket.getUrgency().getIndex()))
                                    .collect(Collectors.toList());
            default:
                throw new NotFoundException(String.format(ROLE_NOT_FOUND, id));
        }
    }
    
    /**
     * Saves information about the new Ticket.
     *
     * @param ownerId
     *            id of the ticket's owner.
     * @param ticketCreationDto
     *            instance of the {@link TicketCreationDto} class.
     * @param file
     *            {@link MultipartFile}.
     */
    @Transactional
    public void saveTicket(final Long ownerId,
            final TicketCreationDto ticketCreationDto, MultipartFile file) {
        User user = userService.getUserById(ownerId);
        Ticket ticket = ticketConverter.toEntity(ticketCreationDto);
        ticket.setOwner(user);
        ticket.setCreatedOn(Date.valueOf(dateTimeProvider.getCurrentDateUtc()));
        String commentText = ticketCreationDto.getComment();
        Category category = categoryService.getCategoryById(ticketCreationDto.getCategoryId());
        ticket.setCategory(category);
        if ((commentText != null) && !commentText.isEmpty()) {
            saveComment(ticket, user, commentText);
        }
        if ((file != null) && !file.isEmpty()) {
            saveAttachment(ticket, user, file);
        }
        saveHistoryEvent(ticket, user, HISTORY_TICKET_CREATED, HISTORY_TICKET_CREATED);
        repository.insertTicket(ticket);
    }
    
    /**
     * Updates information about the existing Ticket.
     *
     * @param ticketId
     *            id of the ticket.
     * @param ownerId
     *            id of the ticket's owner.
     * @param ticketCreationDto
     *            instance of the {@link TicketCreationDto} class.
     * @param file
     *            {@link MultipartFile}            
     * @throws {@link ActionForbiddenException}
     *            if current action is not allowed.            
     */
    @Transactional
    public void editTicket(final Long ticketId, final Long ownerId,
            final TicketCreationDto ticketCreationDto, MultipartFile file) {
        User user = userService.getUserById(ownerId);
        Ticket ticket = repository.getTicketById(ticketId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(TICKET_NOT_FOUND, ticketId)));
        if ( !checkOwner(ticket, user) 
                || !(ticket.getStateId().equals(State.DRAFT.getIndex()) 
                        || ticket.getStateId().equals(State.DECLINED.getIndex()))) {
            throw new ActionForbiddenException(
                    String.format(ACTION_IS_FORBIDDEN, "Edit", ownerId, ticketId));
        }
        ticket = ticketConverter.enrich(ticket, ticketCreationDto);
        ticket.setCreatedOn(Date.valueOf(dateTimeProvider.getCurrentDateUtc()));
        String commentText = ticketCreationDto.getComment();
        Category category = categoryService.getCategoryById(ticketCreationDto.getCategoryId());
        ticket.setCategory(category);
        if ((commentText != null) && !commentText.isEmpty()) {
            saveComment(ticket, user, commentText);
        }
        if ((file != null) && !file.isEmpty()) {
            saveAttachment(ticket, user, file);
        }
        saveHistoryEvent(ticket, user, HISTORY_TICKET_EDITED, HISTORY_TICKET_EDITED);
        repository.updateTicket(ticket);
    }
    
    /**
     * Deletes attachment related to the particular Ticket.
     *
     * @param ticketId
     *            id of the ticket.
     * @param userId
     *            id of the user performing the action.
     * @throws {@link NotFoundException}
     *            if requested resource is not found.             
     * @throws {@link ActionForbiddenException}
     *            if current action is not allowed.            
     */
    @Transactional
    public void deleteAttachment(final Long ticketId, final Long userId) {
        Ticket ticket = repository.getTicketById(ticketId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(TICKET_NOT_FOUND, ticketId)));
        User user = userService.getUserById(userId);
        if (!checkOwner(ticket, user)) {
            throw new ActionForbiddenException(String.format(
                ACTION_IS_FORBIDDEN, "Delete attachment", userId, ticketId));
        }
        if (ticket.getAttachment() == null) {
            throw new NotFoundException(String.format(ATTACHMENT_NOT_FOUND, ticketId));
        }
        Attachment attachment = ticket.getAttachment();
        ticket.setAttachment(null);
        attachmentService.deleteAttachmentById(attachment.getId());
        saveHistoryEvent(ticket, user, HISTORY_FILE_REMOVED, 
                HISTORY_FILE_REMOVED.concat(" : ").concat(attachment.getName()));
    }
    
    /**
     * Saves information about the comment related the particular Ticket.
     *
     * @param ticket
     *            ticket related to the comment.
     * @param user
     *            user who made the comment.
     * @param text
     *            text of the comment.            
     */
    @Transactional
    private void saveComment(final Ticket ticket, final User user, 
            final String text) {
        Comment comment = new Comment(user, text,
                Timestamp.valueOf(dateTimeProvider.getCurrentDateTimeUtc()));
        ticket.addComment(comment);
    }
    
    /**
     * Saves information about the attachment related the particular Ticket.
     *
     * @param ticket
     *            ticket related to the attachment.
     * @param user
     *            user who made the attachment.
     * @param file
     *            {@link MultipartFile}.            
     */
    @Transactional
    private void saveAttachment(final Ticket ticket, final User user,
            MultipartFile file) {
        Attachment attachment;
        try {
            attachment = new Attachment(file.getBytes(), file.getOriginalFilename());
            ticket.setAttachment(attachment);
            saveHistoryEvent(ticket, user,  HISTORY_FILE_ATTACHED,
                    HISTORY_FILE_ATTACHED.concat(" : ").concat(file.getOriginalFilename()));
        } catch (IOException exc) {
            LOGGER.error("Error with saving attatchemt to the ticket");
        }
    }
    
    /**
     * Saves information about history event related the particular Ticket.
     *
     * @param ticket
     *            ticket related to the history event.
     * @param user
     *            user who made the history event.
     * @param action
     *            action of the history event. 
     * @param description
     *            description of the history event.                        
     */
    @Transactional
    private void saveHistoryEvent(final Ticket ticket, final User user,
            final String action, final String description) {
        History historyEvent = new History(
                user, Timestamp.valueOf(dateTimeProvider.getCurrentDateTimeUtc()),
                action, description);
        ticket.addHistoryEvent(historyEvent);
    }
    
    /**
     * Returns List of all actions available to the particular ticket.
     * 
     * @param userId
     *            id of the user performing the action..
     * @param ticketId
     *            id of the ticket to which the action applies.
     * @return list of the {@link ActionDto}. 
     */
    @Transactional
    public List<ActionDto> getTicketActions(final Long userId, final Long ticketId) {
        User user = userService.getUserById(userId);
        Ticket ticket = repository.getTicketById(ticketId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(TICKET_NOT_FOUND, ticketId)));
        return ticketActionService.getActions(user, ticket).stream()
                .map(actionConverter::toDto).collect(Collectors.toList());
    }
    
    /**
     * Performs changing state action to the ticket with specified id.
     *
     * @param userId
     *            id of the user performing the action.
     * @param ticketId
     *            id of the ticket.
     * @param actionId
     *            id of the action to perform.            
     * @throws {@link NotFoundException}
     *            if requested resource is not found.             
     * @throws {@link ActionForbiddenException}
     *            if current action is not allowed.            
     */
    @Transactional
    public void performAction(final Long userId, final Long ticketId,
            final int actionId) {
        User user = userService.getUserById(userId);
        Ticket ticket = repository.getTicketById(ticketId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(TICKET_NOT_FOUND, ticketId)));
        ticketActionService.checkAction(user, ticket, Action.getByIndex(actionId));
        switch (actionId) {
            case SUBMIT:
                submitTicket(user, ticket);
                break;
            case CANCEL:
                cancelTicket(user, ticket);
                break;
            case APPROVE:
                approveTicket(user, ticket);
                break;
            case DECLINE:
                declineTicket(user, ticket);
                break;
            case ASSIGN_TO_ME:
                assignTicket(user, ticket);
                break;
            case DONE:
                doneTicket(user, ticket);
                break;
            default:
                throw new NotFoundException(String.format(ACTION_NOT_FOUND, actionId));    
        }
    }
    
    /**
     * Performs 'Submit' action to the ticket with specified id.
     *
     * @param user
     *            user performing the action.
     * @param ticket
     *             ticket to submit.
     */
    @Transactional
    private void submitTicket(final User user, final Ticket ticket) {
        ticket.setApprover(null);
        changeTicketState(ticket, user, State.NEW);
        repository.updateTicket(ticket);
        for (UserDto manager : userService.getManagers()) {
            try {
                emailService.sendTicketNewEmail(manager.getEmail(), ticket.getId());
            } catch (MessagingException exc) {
                LOGGER.error(EMAIL_ERROR);
            }
        }
    }
    
    /**
     * Performs 'Cancel' action to the ticket with specified id.
     *
     * @param user
     *            user performing the action.
     * @param ticket
     *             ticket to cancel.
     */
    @Transactional
    private void cancelTicket(final User user, final Ticket ticket) {
        State initialState = State.getByIndex(ticket.getStateId());
        changeTicketState(ticket, user, State.CANCELED);
        repository.updateTicket(ticket);
        User owner = ticket.getOwner();
        User approver = ticket.getApprover();
        try {
            if ((approver != null) && !(checkOwner(ticket, user))) {
                emailService.sendTicketCancelledEngineerEmail(
                        owner.getEmail(), ticket.getId()); 
                emailService.sendTicketCancelledEngineerEmail(
                        approver.getEmail(), ticket.getId()); 
            } else if (initialState != State.DRAFT) {
                emailService.sendTicketCancelledManagerEmail(
                        owner.getEmail(), owner.getFirstName(),
                            owner.getLastName(), ticket.getId());
            }
        } catch (MessagingException exc) {
            LOGGER.error(EMAIL_ERROR);
        }
    }
    
    /**
     * Performs 'Approve' action to the ticket with specified id.
     *
     * @param user
     *            user performing the action.
     * @param ticket
     *             ticket to approve.
     */
    @Transactional
    private void approveTicket(final User user, final Ticket ticket) {
        ticket.setApprover(user);
        changeTicketState(ticket, user, State.APPROVED);
        repository.updateTicket(ticket);
        try {
            emailService.sendTicketApprovedEmail(ticket.getOwner().getEmail(), ticket.getId());
            for (UserDto engineer : userService.getEngineers()) {
                emailService.sendTicketApprovedEmail(engineer.getEmail(), ticket.getId());
            }
        } catch (MessagingException exc) {
            LOGGER.error(EMAIL_ERROR);
        }
    }
    
    /**
     * Performs 'Decline' action to the ticket with specified id.
     *
     * @param user
     *            user performing the action.
     * @param ticket
     *             ticket to decline.
     */
    @Transactional
    private void declineTicket(final User user, final Ticket ticket) {
        changeTicketState(ticket, user, State.DECLINED);
        repository.updateTicket(ticket);
        User owner = ticket.getOwner();
        try {
            emailService.sendTicketDeclinedEmail(
                    owner.getEmail(), owner.getFirstName(), owner.getLastName(), ticket.getId());
        } catch (MessagingException exc) {
            LOGGER.error(EMAIL_ERROR);
        }
    }
    
    /**
     * Performs 'Assign' action to the ticket with specified id.
     *
     * @param user
     *            user performing the action.
     * @param ticket
     *             ticket to assign.
     */
    @Transactional
    private void assignTicket(final User user, final Ticket ticket) {
        ticket.setAssignee(user);
        changeTicketState(ticket, user, State.IN_PROGRESS);
        repository.updateTicket(ticket);
    }
    
    /**
     * Performs 'Done' action to the ticket with specified id.
     *
     * @param user
     *            user performing the action.
     * @param ticket
     *             ticket to be done.
     */
    @Transactional
    private void doneTicket(final User user, final Ticket ticket) {
        changeTicketState(ticket, user, State.DONE);
        User owner = ticket.getOwner();
        repository.updateTicket(ticket); 
        try {
            emailService.sendTicketDoneEmail(
                    owner.getEmail(), owner.getFirstName(), owner.getLastName(), ticket.getId());
        } catch (MessagingException exc) {
            LOGGER.error(EMAIL_ERROR);
        }
    }
    
    /**
     * Updates ticket state.
     *
     * @param ticket
     *            ticket to update.
     * @param user
     *            user who made update.
     * @param state
     *            new state of the ticket. 
     * @param description
     *            description of the history event.                        
     */
    @Transactional
    private void changeTicketState(final Ticket ticket, final User user,
            final State state) {
        String lastState = State.getByIndex(ticket.getStateId()).toString();
        ticket.setStateId(state.getIndex());
        String newState = State.getByIndex(ticket.getStateId()).toString();
        String ticketStateStr = HISTORY_TICKET_STATUS.concat(" from ")
                .concat(lastState).concat(" to ").concat(newState);
        saveHistoryEvent(ticket, user, HISTORY_TICKET_STATUS, ticketStateStr);
    }
    
    /**
     * Checks if user is the owner of the ticket.
     * 
     * @param user
     *            user who is making the action.
     * @param ticket
     *            ticket to which the action applies.
     * @return result of the checking.
     */
    private boolean checkOwner(final Ticket ticket, final User user) {
        return ticket.getOwner().equals(user);
    }
}
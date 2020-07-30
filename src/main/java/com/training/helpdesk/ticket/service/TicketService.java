package com.training.helpdesk.ticket.service;

import com.training.helpdesk.commons.exceptions.ActionForbiddenException;
import com.training.helpdesk.commons.exceptions.NotFoundException;
import com.training.helpdesk.ticket.action.dto.ActionDto;
import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.ticket.dto.TicketCreationDto;
import com.training.helpdesk.ticket.dto.TicketDescriptionDto;
import com.training.helpdesk.ticket.dto.TicketDto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface used for representing a ticket service which provides various
 * operations with tickets of 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public interface TicketService {

    /**
     * Returns ticket with specified id.
     *
     * @param id
     *            id of the ticket.
     * @return object of the {@link TicketDescriptionDto} class.
     * @throws {@link NotFoundException}
     *            if ticket was not found.  
     */
    TicketDescriptionDto getTicketDtoById(final Long id);
    
    /**
     * Returns ticket with specified id.
     *
     * @param id
     *            id of the ticket.
     * @return object of the {@link Ticket} class.
     * @throws {@link NotFoundException}
     *            if ticket was not found.  
     */
    Ticket getTicketById(final Long id);
    
    /**
     * Returns list of tickets related related to the user's role.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link TicketDto} class objects.
     */
    List<TicketDto> getTicketsByUserId(final Long id);
    
    /**
     * Returns list of tickets related related to the particular user.
     *
     * @param id
     *            id of the user.
     * @return list of the {@link TicketDto} class objects.
     */
    List<TicketDto> getMyTicketsByUserId(final Long id);
    
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
    void saveTicket(final Long ownerId, 
            final TicketCreationDto ticketCreationDto, final MultipartFile file);
    
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
    void editTicket(final Long ticketId, final Long ownerId,
            final TicketCreationDto ticketCreationDto, MultipartFile file);
    
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
    void deleteAttachment(final Long ticketId, final Long userId);
    
    /**
     * Returns List of all actions available to the particular ticket.
     * 
     * @param userId
     *            id of the user performing the action..
     * @param ticketId
     *            id of the ticket to which the action applies.
     * @return list of the {@link ActionDto}. 
     */
    List<ActionDto> getTicketActions(final Long userId, final Long ticketId);
    
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
    void performAction(final Long userId, final Long ticketId,
            final int actionId);
}
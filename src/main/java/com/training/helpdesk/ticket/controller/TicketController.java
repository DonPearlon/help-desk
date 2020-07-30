package com.training.helpdesk.ticket.controller;

import com.training.helpdesk.attachment.validator.extension.FileExtension;
import com.training.helpdesk.attachment.validator.size.FileSize;
import com.training.helpdesk.ticket.action.dto.ActionDto;
import com.training.helpdesk.ticket.dto.TicketCreationDto;
import com.training.helpdesk.ticket.dto.TicketDescriptionDto;
import com.training.helpdesk.ticket.dto.TicketDto;
import com.training.helpdesk.ticket.service.TicketService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/{id}/tickets")
@PreAuthorize("hasAnyAuthority('EMPLOYEE','MANAGER','ENGINEER')")
@Validated
@Api(tags = "2. Operations with tickets")
public class TicketController {
    private final TicketService ticketService;
    
    /**
     * The constructor of the class.
     *
     * @param ticketService - {@link TicketService}.
     */
    public TicketController(
            final TicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    /**
     * Returns the list of all tickets related to the user with specified id.
     *
     * @param id
     *            - id of the user.
     * @return {@link ResponseEntity}
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get tickets", notes = "${TicketController.getTickets.notes}")
    public ResponseEntity<List<TicketDto>> getTickets(
            @PathVariable(value = "id") final Long id) {
        return  ResponseEntity.ok(ticketService.getTicketsByUserId(id));
    }
    
    /**
     * Returns the list of tickets which are owned by the user with specified id.
     *
     * @param id
     *            - id of the user.
     * @return {@link ResponseEntity}
     */
    @GetMapping(value = "/my", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get my tickets", notes = "${TicketController.getMyTickets.notes}")
    public ResponseEntity<List<TicketDto>> getMyTickets(
            @PathVariable(value = "id") final Long id) {
        return  ResponseEntity.ok(ticketService.getMyTicketsByUserId(id));
    }
    
    /**
     * Saves new ticket.
     *
     * @param id
     *            - id of the user.
     * @param ticketCreationDto
     *            - {@link TicketCreationDto}.
     * @param file
     *            - {@link MultipartFile}.                        
     * @return {@link ResponseEntity}
     */
    @PostMapping(consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Save ticket", notes = "${TicketController.saveTicket.notes}")
    public ResponseEntity<Void> saveTicket(
            @PathVariable(value = "id") final Long id,
            @Valid @RequestPart("ticket") TicketCreationDto ticketCreationDto,
            @Valid @FileExtension @FileSize
            @RequestPart(name = "file", required = false) MultipartFile file) {
        ticketService.saveTicket(id, ticketCreationDto, file);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Returns ticket with specified id.
     *
     * @param ticketId
     *            - id of the ticket.
     * @return {@link ResponseEntity}
     */    
    @GetMapping(value = "/{ticketId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get ticket by id", notes = "${TicketController.getTicketById.notes}")
    public ResponseEntity<TicketDescriptionDto> getTicketById(
            @PathVariable(value = "ticketId") final Long ticketId) {
        return  ResponseEntity.ok(ticketService.getTicketDtoById(ticketId));
    }
    
    /**
     * Returns list of actions related to the ticket with specified id.
     *
     * @param userId
     *            - id of the user.
     * @param ticketId
     *            - id of the ticket.
     * @return {@link ResponseEntity}
     */    
    @GetMapping(value = "/{ticketId}/actions",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get ticket actions", 
            notes = "${TicketController.getTicketActions.notes}")
    public ResponseEntity<List<ActionDto>> getTicketActions(
            @PathVariable(value = "id") final Long userId,
            @PathVariable(value = "ticketId") final Long ticketId) {
        return  ResponseEntity.ok(ticketService.getTicketActions(userId, ticketId));
    }
    
    /**
     * Performs ticket action.
     *
     * @param userId
     *            - id of the user.
     * @param ticketId
     *            - id of the ticket.
     * @param actionId
     *            - id of the action.            
     * @return {@link ResponseEntity}
     */    
    @PutMapping(value = "/{ticketId}/actions/{actionId}")
    @ApiOperation(value = "Pefrorm action", notes = "${TicketController.performAction.notes}")
    public ResponseEntity<Void> performAction(
            @PathVariable(value = "id") final Long userId,
            @PathVariable(value = "ticketId") final Long ticketId,
            @PathVariable(value = "actionId") final int actionId) {
        ticketService.performAction(userId, ticketId, actionId);;
        return  ResponseEntity.noContent().build();
    }
    
    /**
     * Edits ticket with specified id.
     *
     * @param userId
     *            - id of the user.
     * @param ticketId
     *            - id of the ticket.
     * @param ticketCreationDto
     *            - {@link TicketCreationDto}.
     * @param file
     *            - {@link MultipartFile}.                        
     * @return {@link ResponseEntity}
     */
    @PostMapping(value = "/{ticketId}", 
            produces = MediaType.APPLICATION_JSON_VALUE, 
            consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Edit ticket", notes = "${TicketController.editTicket.notes}")
    public ResponseEntity<Void> editTicket(
            @PathVariable(value = "id") final Long userId,
            @PathVariable(value = "ticketId") final Long ticketId,
            @Valid @RequestPart("ticket") TicketCreationDto ticketCreationDto,
            @Valid @FileExtension @FileSize 
            @RequestPart(name = "file", required = false) MultipartFile file) {
        ticketService.editTicket(ticketId, userId, ticketCreationDto, file);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Deletes attachment related to the ticket.
     *
     * @param userId
     *            - id of the user.
     * @param ticketId
     *            - id of the ticket.
     * @return {@link ResponseEntity}
     */    
    @DeleteMapping(value = "/{ticketId}/attachments")
    @ApiOperation(value = "Delete ticket attachemnt",
            notes = "${TicketController.deleteAttachment.notes}")
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable(value = "ticketId") final Long ticketId,
            @PathVariable(value = "id") final Long userId) {
        ticketService.deleteAttachment(ticketId, userId);
        return ResponseEntity.noContent().build();
    }
}
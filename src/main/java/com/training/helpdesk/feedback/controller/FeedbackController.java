package com.training.helpdesk.feedback.controller;

import com.training.helpdesk.feedback.dto.FeedbackDto;
import com.training.helpdesk.feedback.service.FeedbackService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/{id}/tickets/{ticketId}/feedback")
@PreAuthorize("hasAnyAuthority('EMPLOYEE','MANAGER','ENGINEER')")
@Validated
@Api(tags = "7. Operations with feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
    
    /**
     * The constructor of the class.
     *
     * @param feedbackService
     *            {@link FeedbackService} - performs operations with ticket feedback.
     */
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }
    
    /**
     * Returns feedback left for the ticket with specified id.
     * 
     * @param ticketId
     *            - id of the ticket.
     * @return {@link ResponseEntity}
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get feedback", notes = "${FeedbackController.getFeedback.notes}")
    public ResponseEntity<FeedbackDto> getFeedback(
            @PathVariable(value = "ticketId") final Long ticketId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByTicketId(ticketId));
    }
    
    /**
     * Leaves feedback for the ticket with specified id.
     *
     * @param ticketId
     *            - id of the ticket.
     * @return {@link ResponseEntity}
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save feedback", notes = "${FeedbackController.saveFeedback.notes}")
    public ResponseEntity<Void> saveFeedback(
            @Valid @RequestBody final FeedbackDto feedbackDto,
            @PathVariable(value = "id") final Long userId,
            @PathVariable(value = "ticketId") final Long ticketId) {
        feedbackService.saveFeedback(userId, ticketId, feedbackDto);
        return ResponseEntity.noContent().build();
    }
}
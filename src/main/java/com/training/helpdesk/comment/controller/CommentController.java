package com.training.helpdesk.comment.controller;

import com.training.helpdesk.comment.dto.CommentCreationDto;
import com.training.helpdesk.comment.dto.CommentDto;
import com.training.helpdesk.comment.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/{id}/tickets/{ticketId}/comments")
@PreAuthorize("hasAnyAuthority('EMPLOYEE','MANAGER','ENGINEER')")
@Api(tags = "5. Operations with ticket comments")
public class CommentController {
    private final CommentService commentService;
    
    /**
     * The constructor of the class.
     *
     * @param commentService - {@link CommentService}.
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    /**
     * Returns the list of comments related to the ticket with specified id.
     *
     * @param id
     *            - id of the ticket.
     * @return {@link ResponseEntity}
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get comments", notes = "${CommentController.getComments.notes}")
    public ResponseEntity<List<CommentDto>> getComments(
                @PathVariable(value = "ticketId") final Long id) {
        return ResponseEntity.ok(commentService.getCommentsByTicketId(id));
    }
    
    /**
     * Returns the full list of comments related to the ticket with specified id.
     *
     * @param id
     *            - id of the ticket.
     * @return {@link ResponseEntity}
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all comments", notes = "${CommentController.getAllComments.notes}")
    public ResponseEntity<List<CommentDto>> getAllComments(
                @PathVariable(value = "ticketId") final Long id) {
        return ResponseEntity.ok(commentService.getAllCommentsByTicketId(id));
    }   
    
    /**
     * Creates a new comment for the ticket with specified id.
     *
     * @param commentCreationDto
     *            - {@link CommentCreationDto}.
     * @return {@link ResponseEntity}
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save comment", notes = "${CommentController.saveComment.notes}")
    public ResponseEntity<Void> saveComment(
            @Valid @RequestBody final CommentCreationDto commentCreationDto) {
        commentService.saveComment(commentCreationDto);
        return ResponseEntity.noContent().build();
    }
}
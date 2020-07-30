package com.training.helpdesk.comment.service;

import com.training.helpdesk.comment.domain.Comment;
import com.training.helpdesk.comment.dto.CommentCreationDto;
import com.training.helpdesk.comment.dto.CommentDto;

import java.util.List;

/**
 * Interface used for representing a service which provides 
 * operations with ticket comments of 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public interface CommentService {

    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns list of ticket comments contained in the database
     *         related to the ticket with specified id.
     */
    List<CommentDto> getCommentsByTicketId(final Long id);
    
    /**
     * Saves information about the comment.
     *
     * @param commentCreationDto
     *            instance of the {@link CommentCreationDto} class.
     */
    void saveComment(final CommentCreationDto commentCreationDto);
    
    /**
     * Insert into database information about the comment.
     *
     * @param comment
     *            instance of the {@link Comment} class.
     */
    void saveComment(final Comment comment);
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns full list of all ticket comments contained in the database
     *         related to the ticket with specified id.
     */
    List<CommentDto> getAllCommentsByTicketId(final Long id);
}
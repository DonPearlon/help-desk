package com.training.helpdesk.comment.repository;

import com.training.helpdesk.comment.domain.Comment;

import java.util.List;

/**
 * Data Access Object interface. Provides 'database' operations with
 * {@link Comment} objects.
 * 
 * @author Alexandr_Terehov
 */
public interface CommentRepository {

    /**
     * Insert into database information about the comment.
     *
     * @param comment
     *            instance of the {@link Comment} class.
     */
    void insertComment(final Comment comment);
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns a list of all comments contained in the database
     *         related to the ticket with specified id.
     */
    List<Comment> getCommentsByTicketId(final Long id);
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @param maxResults 
     *            the maximum number of rows.            
     * @return Returns a list of comments contained in the database
     *         related to the ticket with specified id.
     */
    List<Comment> getCommentsByTicketId(final Long id, final int maxResults);
}

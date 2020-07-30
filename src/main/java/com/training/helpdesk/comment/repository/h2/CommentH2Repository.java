package com.training.helpdesk.comment.repository.h2;

import com.training.helpdesk.comment.domain.Comment;
import com.training.helpdesk.comment.repository.CommentRepository;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the {@link CommentRepository} interface. 
 *
 * @author Alexandr_Terehov
 */
@Repository
public class CommentH2Repository implements CommentRepository {
    private static final String COMMENTS_BY_TICKET 
            = "FROM Comment WHERE ticketId = :id order by date desc";

    private final SessionFactory sessionFactory;
    
    /**
     * Constructor
     * 
     * @param sessionFactory
     *            object implements {@link SessionFactory} interface to set.
     */
    public CommentH2Repository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Insert into database information about the comment.
     *
     * @param comment
     *            instance of the {@link Comment} class.
     */
    public void insertComment(final Comment comment) {
        sessionFactory.getCurrentSession().save(comment);
    }
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns a list of all comments contained in the database
     *         related to the ticket with specified id.
     */
    public List<Comment> getCommentsByTicketId(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(COMMENTS_BY_TICKET);
        query.setLong("id", id);
        return (List<Comment>)query.list(); 
    }
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @param maxResults 
     *            the maximum number of rows.            
     * @return Returns a list of comments contained in the database
     *         related to the ticket with specified id.
     */
    public List<Comment> getCommentsByTicketId(final Long id, final int maxResults) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(COMMENTS_BY_TICKET);
        query.setLong("id", id);
        query.setMaxResults(maxResults);
        return (List<Comment>)query.list();
    }
}
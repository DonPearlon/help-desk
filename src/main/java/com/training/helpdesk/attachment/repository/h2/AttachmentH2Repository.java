package com.training.helpdesk.attachment.repository.h2;

import com.training.helpdesk.attachment.domain.Attachment;
import com.training.helpdesk.attachment.repository.AttachmentRepository;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementation of the {@link AttachmentRepository} interface. 
 *
 * @author Alexandr_Terehov
 */
@Repository
public class AttachmentH2Repository implements AttachmentRepository {
    private static final String SELECT_ATTACHMENT_BY_ID
            = "FROM Attachment WHERE id = :id"; 

    private final SessionFactory sessionFactory;
    
    /**
     * Constructor
     * 
     * @param sessionFactory
     *            object implements {@link SessionFactory} interface to set.
     */
    public AttachmentH2Repository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Get Attachment object by it's ID.
     *
     * @param id
     *            id of the attachment.
     * @return object of the {@link Attachment} class.
     */
    public Optional<Attachment> getAttachmentById(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(SELECT_ATTACHMENT_BY_ID);
        query.setLong("id", id);
        Attachment attachment = (Attachment)query.uniqueResult();
        return Optional.ofNullable(attachment);
    }
    
    /**
     * Deletes the attachment from the database.
     *
     * @param attachment
     *            instance of the {@link Attachment} class.
     */
    public void deleteAttachment(final Attachment attachment) {
        sessionFactory.getCurrentSession().delete(attachment);
    }
    
    /**
     * Insert into database information about the attachment.
     *
     * @param attachment
     *            instance of the {@link Attachment} class.
     */
    public void insertAttachment(final Attachment attachment) {
        sessionFactory.getCurrentSession().save(attachment);
    }
}
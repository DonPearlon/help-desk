package com.training.helpdesk.attachment.repository;

import com.training.helpdesk.attachment.domain.Attachment;

import java.util.Optional;

/**
 * Data Access Object interface. Provides 'database' operations with
 * {@link Attachment} objects.
 * 
 * @author Alexandr_Terehov
 */
public interface AttachmentRepository {
    
    /**
     * Insert into database information about the attachment.
     *
     * @param attachment
     *            instance of the {@link Attachment} class.
     */
    void insertAttachment(final Attachment attachment);
    
    /**
     * Get Attachment object by it's ID.
     *
     * @param id
     *            id of the attachment.
     * @return object of the {@link Attachment} class.
     */
    Optional<Attachment> getAttachmentById(final Long id);
    
    /**
     * Deletes the attachment from the database.
     *
     * @param attachment
     *            instance of the {@link Attachment} class.
     */
    void deleteAttachment(final Attachment attachment);
}
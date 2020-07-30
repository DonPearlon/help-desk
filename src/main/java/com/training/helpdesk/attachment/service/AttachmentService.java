package com.training.helpdesk.attachment.service;

import com.training.helpdesk.attachment.domain.Attachment;
import com.training.helpdesk.commons.exceptions.NotFoundException;

/**
 * Interface used for representing a service which provides various
 * operations with ticket attachments of the 'Help-Desk' application.
 *
 * @author Alexandr_Terehov
 */
public interface AttachmentService {

    /**
     * Get Attachment by it's ID.
     *
     * @param id
     *            id of the attachment.
     * @return byte array of the attachment.
     */
    byte[] getAttachmentById(final Long id);

    /**
     * Deletes the attachment from the database.
     *
     * @param id
     *            id of the attachment.
     * @throws {@link NotFoundException}
     *            if attachment was not found.              
     */
    void deleteAttachmentById(final Long id);

    /**
     * Saves information about the attachment.
     *
     * @param attachment
     *            instance of the {@link Attachment} class.
     * @throws {@link NotFoundException}
     *            if attachment was not found.              
     */
    void saveAttachment(Attachment attachment);
}
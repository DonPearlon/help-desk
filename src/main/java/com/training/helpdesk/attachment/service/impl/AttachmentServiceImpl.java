package com.training.helpdesk.attachment.service.impl;

import com.training.helpdesk.attachment.domain.Attachment;
import com.training.helpdesk.attachment.repository.AttachmentRepository;
import com.training.helpdesk.attachment.service.AttachmentService;
import com.training.helpdesk.commons.exceptions.NotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link AttachmentService} interface.
 *
 * @author Alexandr_Terehov
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private static final String ATTACHMENT_NOT_FOUND
            = "Attachment with Id:%s was not found.";

    private final AttachmentRepository repository;
    
    /**
     * Constructor
     * 
     * @param repository
     *            object implements {@link AttachmentRepository} interface to set.
     */
    public AttachmentServiceImpl(final AttachmentRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Get Attachment by it's ID.
     *
     * @param id
     *            id of the attachment.
     * @return byte array of the attachment.
     * @throws {@link NotFoundException}
     *            if attachment was not found.  
     */
    @Transactional
    public byte[] getAttachmentById(final Long id) {
        return repository.getAttachmentById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ATTACHMENT_NOT_FOUND, id)))
                    .getBlob();
    }
    
    /**
     * Deletes the attachment from the database.
     *
     * @param id
     *            id of the attachment.
     * @throws {@link NotFoundException}
     *            if attachment was not found.            
     */
    @Transactional
    public void deleteAttachmentById(final Long id) {
        Attachment attachment = repository.getAttachmentById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ATTACHMENT_NOT_FOUND, id)));
        repository.deleteAttachment(attachment);
    }
    
    /**
     * Saves information about the attachment.
     *
     * @param attachment
     *            instance of the {@link Attachment} class.
     */
    @Transactional
    public void saveAttachment(Attachment attachment) {
        repository.insertAttachment(attachment);
    }
}
package com.training.helpdesk.attachment.controller;

import com.training.helpdesk.attachment.service.AttachmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users/{userId}/tickets/{ticketId}/attachments")
@PreAuthorize("hasAnyAuthority('EMPLOYEE','MANAGER','ENGINEER')")
@Api(tags = "4. Operations with ticket attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;
    
    /**
     * The constructor of the class.
     *
     * @param attachmentService
     *            {@link AttachmentService} - performs operations with ticket
     *            attachments.
     */
    public AttachmentController(final AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }
    
    /**
     * 
     * @param id - id of the ticket.
     * @return attachment related to the ticket with specified id
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get attachment", notes = "${AttachmentController.getAttachment.notes}")
    public @ResponseBody byte[] getAttachment(
             @PathVariable(value = "id") final Long id) {
        return attachmentService.getAttachmentById(id);
    }
}
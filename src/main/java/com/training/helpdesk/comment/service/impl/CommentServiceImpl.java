package com.training.helpdesk.comment.service.impl;

import com.training.helpdesk.comment.converter.CommentConverter;
import com.training.helpdesk.comment.domain.Comment;
import com.training.helpdesk.comment.dto.CommentCreationDto;
import com.training.helpdesk.comment.dto.CommentDto;
import com.training.helpdesk.comment.repository.CommentRepository;
import com.training.helpdesk.comment.service.CommentService;
import com.training.helpdesk.commons.date.provider.DateTimeProvider;
import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link CommentService} interface.
 *
 * @author Alexandr_Terehov
 */
@Service
public class CommentServiceImpl implements CommentService {
    private static final int ROW_LIMIT = 5;
    
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CommentConverter converter;
    private final DateTimeProvider dateTimeProvider;
    
    /**
     * Constructor
     * 
     * @param commentRepository
     *            object implements {@link CommentRepository} interface to set.
     * @param userService
     *            object implements {@link UserService} interface to set. 
     * @param converter
     *            instance of the {@link CommentConverter} class to set. 
     * @param dateTimeProvider
     *            object implements {@link DateTimeProvider} interface to set.  
     */
    public CommentServiceImpl(final CommentRepository commentRepository,
            final UserService userService, final CommentConverter converter,
            final DateTimeProvider dateTimeProvider) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.converter = converter;
        this.dateTimeProvider = dateTimeProvider;
    }
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns list of ticket comments contained in the database
     *         related to the ticket with specified id.
     */
    @Transactional 
    public List<CommentDto> getCommentsByTicketId(final Long id) {
        return commentRepository.getCommentsByTicketId(id, ROW_LIMIT)
            .stream().map(converter::toDto).collect(Collectors.toList());
    }
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns full list of all ticket comments contained in the database
     *         related to the ticket with specified id.
     */
    @Transactional 
    public List<CommentDto> getAllCommentsByTicketId(final Long id) {
        return commentRepository.getCommentsByTicketId(id)
            .stream().map(converter::toDto).collect(Collectors.toList());
    }
    
    /**
     * Insert into database information about the comment.
     *
     * @param comment
     *            instance of the {@link Comment} class.
     */
    @Transactional
    public void saveComment(final Comment comment) {
        commentRepository.insertComment(comment);
    }
    
    /**
     * Insert into database information about the comment.
     *
     * @param commentCreationDto
     *            instance of the {@link CommentCreationDto} class.
     */
    @Transactional 
    public void saveComment(final CommentCreationDto commentCreationDto) {
        User user = userService.getUserById(commentCreationDto.getUserId());
        Comment comment = converter.toEntity(commentCreationDto);
        comment.setUser(user);
        comment.setDate(Timestamp.valueOf(dateTimeProvider.getCurrentDateTimeUtc()));
        commentRepository.insertComment(comment);
    }
}
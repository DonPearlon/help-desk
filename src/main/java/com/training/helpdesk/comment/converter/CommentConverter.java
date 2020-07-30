package com.training.helpdesk.comment.converter;

import com.training.helpdesk.comment.domain.Comment;
import com.training.helpdesk.comment.dto.CommentCreationDto;
import com.training.helpdesk.comment.dto.CommentDto;

import org.springframework.stereotype.Component;

/**
 * Class provides conversion operations for instances of the {@link Comment} and
 * {@link CommentDto} classes.
 *
 * @author Alexandr_Terehov
 */
@Component
public class CommentConverter {

    /**
     * Provides conversion of the instance of the {@link Comment} class to the
     * instance of the {@link CommentDto} class
     *
     * @param comment
     *            instance of the {@link Comment} class.
     */
    public CommentDto toDto(final Comment comment) {
        return new CommentDto(comment.getId(),
                comment.getUser().getFirstName().concat(" ")
                .concat(comment.getUser().getLastName()),
                comment.getText(), comment.getDate().toLocalDateTime());
    }
    
    /**
     * Provides conversion of the instance of the {@link CommentCreationDto} 
     * class to the instance of the {@link Comment} class
     *
     * @param commentCreationDto
     *            instance of the {@link CommentCreationDto} class.
     */
    public Comment toEntity(final CommentCreationDto commentCreationDto) {
        return new Comment(
            commentCreationDto.getText(), commentCreationDto.getTicketId());
    }
}
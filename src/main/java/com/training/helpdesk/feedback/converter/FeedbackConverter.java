package com.training.helpdesk.feedback.converter;

import com.training.helpdesk.commons.date.converter.DateConverter;
import com.training.helpdesk.feedback.domain.Feedback;
import com.training.helpdesk.feedback.dto.FeedbackDto;

import org.springframework.stereotype.Component;

/**
 * Class provides conversion operations for instances of the {@link Feedback} and
 * {@link FeedbackDto} classes.
 *
 * @author Alexandr_Terehov
 */
@Component
public class FeedbackConverter {
    
    private final DateConverter dateConverter;
    
    /**
     * The constructor of the class.
     *
     * @param dateConverter - {@link DateConverter}.
     */
    public FeedbackConverter(final DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    /**
     * Provides conversion of the instance of the {@link Feedback} class to the
     * instance of the {@link FeedbackDto} class
     *
     * @param feedback
     *            instance of the {@link Feedback} class.
     */
    public FeedbackDto toDto(final Feedback feedback) {
        return new FeedbackDto(feedback.getTicket().getName(), feedback.getRate(),
                feedback.getText(), dateConverter.toLocalDate(feedback.getDate())); 
    }
    
    /**
     * Provides conversion of the instance of the {@link FeedbackDto} class to the
     * instance of the {@link Feedback} class
     *
     * @param feedbackDto
     *            instance of the {@link FeedbackDto} class.
     */
    public Feedback toEntity(final FeedbackDto feedbackDto) {
        return new Feedback(feedbackDto.getUserId(),
                feedbackDto.getRate(), feedbackDto.getText());
    }
}
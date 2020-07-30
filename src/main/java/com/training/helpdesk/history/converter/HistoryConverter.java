package com.training.helpdesk.history.converter;

import com.training.helpdesk.history.domain.History;
import com.training.helpdesk.history.dto.HistoryDto;

import org.springframework.stereotype.Component;

/**
 * Class provides conversion operations for instances of the {@link History} and
 * {@link HistoryDto} classes.
 *
 * @author Alexandr_Terehov
 */
@Component
public class HistoryConverter {

    /**
     * Provides conversion of the instance of the {@link History} class to the
     * instance of the {@link HistoryDto} class
     *
     * @param history
     *            instance of the {@link History} class.
     */
    public HistoryDto toDto(final History history) {
        return new HistoryDto(history.getDate().toLocalDateTime(),
                history.getUser().getFirstName().concat(" ")
                .concat(history.getUser().getLastName()),
                history.getAction(), history.getDescription());
    }
}
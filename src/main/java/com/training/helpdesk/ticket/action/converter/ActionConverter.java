package com.training.helpdesk.ticket.action.converter;

import com.training.helpdesk.ticket.action.domain.Action;
import com.training.helpdesk.ticket.action.dto.ActionDto;

import org.springframework.stereotype.Component;

/**
 * Class provides conversion operations for instances of the {@link Action} and
 * {@link ActionDto} classes.
 *
 * @author Alexandr_Terehov
 */
@Component
public class ActionConverter {

    /**
     * Provides conversion of the instance of the {@link Action} class to the
     * instance of the {@link ActionDto} class
     *
     * @param action
     *            instance of the {@link Action} class.
     */
    public ActionDto toDto(final Action action) {
        return new ActionDto(action.getIndex(), action.toString());
    }
}

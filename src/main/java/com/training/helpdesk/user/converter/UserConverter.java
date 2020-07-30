package com.training.helpdesk.user.converter;

import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.domain.role.Role;
import com.training.helpdesk.user.dto.UserDto;

import org.springframework.stereotype.Component;

/**
 * Class provides conversion operations for instances of the {@link User} and
 * {@link UserDto} classes.
 *
 * @author Alexandr_Terehov
 */
@Component
public class UserConverter {

    /**
     * Provides conversion of the instance of the {@link User} class to the
     * instance of the {@link UserDto} class.
     *
     * @param user
     *            instance of the {@link User} class.
     */
    public UserDto toDto(final User user) {
        return new UserDto(user.getId(), user.getFirstName(),
            user.getLastName(), Role.getByIndex(user.getRoleId()), user.getEmail());
    }
}
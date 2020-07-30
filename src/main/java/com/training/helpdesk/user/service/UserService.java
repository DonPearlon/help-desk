package com.training.helpdesk.user.service;

import com.training.helpdesk.commons.exceptions.NotFoundException;
import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.dto.UserDto;

import java.util.List;

/**
 * Interface used for representing a user service which provides various
 * operations with users of 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public interface UserService {
    
    /**
     * @return List of all 'Managers' contained in the database.
     */
    List<UserDto> getManagers();
    
    /**
     * @return List of all 'Engineers' contained in the database.
     */
    List<UserDto> getEngineers();
    
    /**
     * Get UserDto object by ID of the user.
     *
     * @param id
     *            id of the user.
     * @return object of the {@link UserDto} class.
     * @throws {@link NotFoundException}
     *            when user was not found.  
     */
    UserDto getUserDtoById(final Long id);
    
    /**
     * Get User object by the Id.
     *
     * @param id
     *            id of the user.
     * @return object of the {@link User} class.
     */
    User getUserById(final Long id);
}
package com.training.helpdesk.user.repository;

import com.training.helpdesk.user.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface. Provides 'database' operations with
 * {@link User} objects.
 * 
 * @author Alexandr_Terehov
 */
public interface UserRepository {
    
    /**
     * @return List of all 'Managers' contained in the database.
     */
    List<User> getManagers();
    
    /**
     * @return List of all 'Engineers' contained in the database.
     */
    List<User> getEngineers();
    
    /**
     * Returns User object by it's ID.
     *
     * @param id
     *            id of the user.
     * @return object of the {@link User} class.
     */
    Optional<User> getUserById(final Long id);

    /**
     * Returns User object by it's email.
     *
     * @param email
     *            email of the user.
     * @return object of the {@link User} class.
     */
    Optional<User> getUserByEmail(final String email);
}
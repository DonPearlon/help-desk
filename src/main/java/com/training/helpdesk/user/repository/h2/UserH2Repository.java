package com.training.helpdesk.user.repository.h2;

import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.domain.role.Role;
import com.training.helpdesk.user.repository.UserRepository;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link UserRepository} interface.
 * 
 * @author Alexandr_Terehov
 */
@Repository
public class UserH2Repository implements UserRepository {
    private final SessionFactory sessionFactory;

    /**
     * Constructor
     * 
     * @param sessionFactory
     *            object implements {@link SessionFactory} interface to set.
     */
    public UserH2Repository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
   
    /**
     * @return List of all 'Managers' contained in the database.
     */
    public List<User> getManagers() {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(UserQuery.SELECT_USER_BY_ROLE).setCacheable(true);
        query.setLong("roleId", Role.MANAGER.getIndex());
        return (List<User>)query.list();
    }
    
    /**
     * @return List of all 'Engineers' contained in the database.
     */
    public List<User> getEngineers() {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(UserQuery.SELECT_USER_BY_ROLE).setCacheable(true);
        query.setLong("roleId", Role.ENGINEER.getIndex());
        return (List<User>)query.list();
    }
    
    /**
     * Returns User object by it's ID.
     *
     * @param id
     *            id of the user.
     * @return object of the {@link User} class.
     */
    public Optional<User> getUserById(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(UserQuery.SELECT_USER_BY_ID).setCacheable(true);
        query.setLong("id", id);
        User user = (User)query.uniqueResult();
        return Optional.ofNullable(user);
    }
    
    /**
     * Returns User object by it's email.
     *
     * @param email
     *            email of the user.
     * @return object of the {@link User} class.
     */
    public Optional<User> getUserByEmail(final String email) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(UserQuery.SELECT_USER_BY_EMAIL).setCacheable(true);
        query.setString("email", email);
        return Optional.ofNullable((User)query.uniqueResult());
    }
    
    /**
     * Nested class contains query strings used for execution of 'user' queries.
     */
    private static class UserQuery {
        private static final String SELECT_USER_BY_ID = "FROM User WHERE id = :id";
        private static final String SELECT_USER_BY_ROLE = "FROM User WHERE roleId = :roleId";
        private static final String SELECT_USER_BY_EMAIL = "from User where email = :email";
    }
}
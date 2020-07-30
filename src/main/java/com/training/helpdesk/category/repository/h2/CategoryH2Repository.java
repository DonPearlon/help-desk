package com.training.helpdesk.category.repository.h2;

import com.training.helpdesk.category.domain.Category;
import com.training.helpdesk.category.repository.CategoryRepository;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link CategoryRepository} interface. 
 *
 * @author Alexandr_Terehov
 */
@Repository
public class CategoryH2Repository implements CategoryRepository {
    private final SessionFactory sessionFactory;
    
    /**
     * Constructor
     * 
     * @param sessionFactory
     *            object implements {@link SessionFactory} interface to set.
     */
    public CategoryH2Repository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * @return List of all ticket categories contained in the database.
     */
    public List<Category> getCategories() {
        return (List<Category>)sessionFactory.getCurrentSession()
                .createQuery(CategoryQuery.SELECT_ALL_CATEGORIES).setCacheable(true).list();
    }
    
    /**
     * Get Category object by it's Id.
     *
     * @param id
     *            id of the category.
     * @return object of the {@link Category} class.
     */
    public Optional<Category> getCategoryById(final Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(CategoryQuery.SELECT_CATEGORY_BY_ID).setCacheable(true);
        query.setLong("id", id);
        Category category = (Category)query.uniqueResult();
        return Optional.ofNullable(category);
    }
    
    /**
     * Nested class contains query strings used for execution of 'category' queries.
     */
    private static class CategoryQuery {
        private static final String SELECT_CATEGORY_BY_ID 
                = "FROM Category WHERE id = :id";
        private static final String SELECT_ALL_CATEGORIES = "from Category";
    }
}
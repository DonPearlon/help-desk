package com.training.helpdesk.category.repository;

import com.training.helpdesk.category.domain.Category;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface. Provides 'database' operations with
 * {@link Category} objects.
 * 
 * @author Alexandr_Terehov
 */
public interface CategoryRepository {

    /**
     * @return Returns a list of all ticket categories contained in the database.
     */
    List<Category> getCategories();
    
    /**
     * Get ticket category by it's ID.
     *
     * @param id
     *            id of the ticket category.
     * @return object of the {@link Category} class.
     */
    Optional<Category> getCategoryById(final Long id);
}
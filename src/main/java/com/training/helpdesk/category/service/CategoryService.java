package com.training.helpdesk.category.service;

import com.training.helpdesk.category.domain.Category;
import com.training.helpdesk.category.dto.CategoryDto;
import com.training.helpdesk.commons.exceptions.NotFoundException;

import java.util.List;

/**
 * Interface used for representing a service which provides 
 * operations with ticket categories of 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public interface CategoryService {

    /**
     * @return List of all ticket categories of the application.
     */
    List<CategoryDto> getCategories();
    
    /**
     * Get ticket category by it's ID.
     *
     * @param id
     *            id of the ticket category.
     * @return object of the {@link Category} class.
     * @throws {@link NotFoundException}
     *            if category was not found.  
     */
    Category getCategoryById(final Long id);
}
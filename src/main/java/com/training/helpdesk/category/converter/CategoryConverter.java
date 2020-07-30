package com.training.helpdesk.category.converter;

import com.training.helpdesk.category.domain.Category;
import com.training.helpdesk.category.dto.CategoryDto;

import org.springframework.stereotype.Component;

/**
 * Class provides conversion operations for instances of the {@link Category} and
 * {@link CategoryDto} classes.
 *
 * @author Alexandr_Terehov
 */
@Component
public class CategoryConverter {

    /**
     * Provides conversion of the instance of the {@link Category} class to the
     * instance of the {@link CategoryDto} class
     *
     * @param category
     *            instance of the {@link Category} class.
     */
    public CategoryDto toDto(final Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}

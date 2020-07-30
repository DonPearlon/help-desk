package com.training.helpdesk.category.service.impl;

import com.training.helpdesk.category.converter.CategoryConverter;
import com.training.helpdesk.category.domain.Category;
import com.training.helpdesk.category.dto.CategoryDto;
import com.training.helpdesk.category.repository.CategoryRepository;
import com.training.helpdesk.category.service.CategoryService;
import com.training.helpdesk.commons.exceptions.NotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link CategoryService} interface.
 *
 * @author Alexandr_Terehov
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORY_NOT_FOUND
            = "Category with Id:%s was not found.";

    private final CategoryRepository repository;
    private final CategoryConverter converter;

    /**
     * Constructor.
     *
     * @param repository
     *            {@link CategoryRepository}.
     * @param converter
     *            {@link CategoryConverter}.
     */
    public CategoryServiceImpl(
            final CategoryRepository repository, final CategoryConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }
    
    /**
     * @return Returns a list of all ticket categories of the application.
     */
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories() {
        return repository.getCategories().stream()
                .map(converter::toDto).collect(Collectors.toList());
    }
    
    /**
     * Get ticket category by it's ID.
     *
     * @param id
     *            id of the ticket category.
     * @return object of the {@link Category} class.
     * @throws {@link NotFoundException}
     *            if category was not found.  
     */
    @Transactional(readOnly = true)
    public Category getCategoryById(final Long id) {
        return repository.getCategoryById(id)
                .orElseThrow(() ->  new NotFoundException(String.format(CATEGORY_NOT_FOUND, id)));
    }
}
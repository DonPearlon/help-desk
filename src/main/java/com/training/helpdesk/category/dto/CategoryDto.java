package com.training.helpdesk.category.dto;

import java.util.Objects;

/**
 * Class represents DTO category of the ticket of the 'Help-Desk' application.
 *
 * @author Alexandr_Terehov
 */

public class CategoryDto {
    private final Long id;
    private final String name;

    /**
     * Constructor.
     *
     * @param id
     *            id of the user.
     * @param name
     *            name of the ticket category.
     */
    public CategoryDto(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return id of the ticket category.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @return name of the ticket category.
     */
    public String getName() {
        return name;
    }

    /**
     * @return hashCode of the instance of the Category class.
     */
    @Override
    public int hashCode() {
        return (31 * ((id == null) ? 0 : id.hashCode())
                + ((name == null) ? 0 : name.hashCode()));
    }
    
    /**
     * Method used to compare this CategoryDto to the specified object.
     *
     * @param obj
     *            object to compare.
     * @return result of the comparison.            
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CategoryDto other = (CategoryDto) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(name, other.getName())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the CategoryDto class .
     */
    @Override
    public String toString() {
        return "{\r\n"
                + "  \"id\" : \"" +  id + "\",\r\n"
                + "  \"name\" : \"" +  name + "\",\r\n"
                + "}";
    }
}
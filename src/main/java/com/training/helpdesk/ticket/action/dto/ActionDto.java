package com.training.helpdesk.ticket.action.dto;

import java.util.Objects;

/**
 * Represents DTO of the ticket actions that can be used in the 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public class ActionDto {
    private Integer id;
    private String name;
    
    /**
     * Constructor.
     *
     * @param id
     *            id of the action.
     * @param name
     *            name of the action.
     */
    public ActionDto(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }
    
    /**
     * @return id of the action.
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     *            id of the action to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return name of the action.
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param name
     *            name of the action to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return hashCode of the instance of the ActionDto class.
     */
    @Override
    public int hashCode() {
        return (int) (31 * ((id == null) ? 0 : id.hashCode())
                + ((name == null) ? 0 : name.hashCode()));
    }
    
    /**
     * Method used to compare this ActionDto to the specified object.
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
        ActionDto other = (ActionDto) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(name, other.getName())) {
            return false;
        }
        if (this.hashCode() != other.hashCode()) {
            return false;
        }
        return true;
    }

    /**
     * @return string representation of the ActionDto.
     */
    @Override
    public String toString() {
        return "{\r\n"
                + "  \"id\" : \"" +  id + "\",\r\n"
                + "  \"name\" : \"" +  name + "\",\r\n"
                + "}";
    }
}
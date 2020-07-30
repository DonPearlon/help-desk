package com.training.helpdesk.category.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class represents category of the ticket of the 'Help-Desk' application.
 *
 * @author Alexandr_Terehov
 */
@Entity
@Table(name = "category")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    public Category() {

    }

    /**
     * @return id of the ticket category.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     *            id of the ticket category to set.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return name of the ticket category.
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param name
     *            name of the ticket category to set.
     */
    public void setName(String name) {
        this.name = name;
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
     * Method used to compare this Category to the specified object.
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
        Category other = (Category) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(name, other.getName())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the Category class .
     */
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
}
package com.training.helpdesk.user.domain.role;

import com.training.helpdesk.commons.exceptions.NotFoundException;

/**
 * Enum represents possible roles of the 'help-desk' app user.
 *
 * @author Alexandr_Terehov
 */
public enum Role {
    EMPLOYEE(1),
    MANAGER(2),
    ENGINEER(3);

    private final int index;
    
    /**
     * Constructor.
     *
     * @param index
     *            index of the role.
     */
    Role(final int index) {
        this.index = index;
    }
    
    /**
     * @return index of the role.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns Role by it's index  
     * 
     * @param index - index of the role.
     * @return role related to the index.
     */
    public static Role getByIndex(final int index) {
        switch (index) {
            case 1:
                return EMPLOYEE;
            case 2:
                return MANAGER;
            case 3:
                return ENGINEER;
            default:
                throw new NotFoundException("User role not found.");
        }
    }
}
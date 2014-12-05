/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;

public class RequirementListItem extends JButton {

    private static final long serialVersionUID = 4251094749415084075L;

    private Requirement requirement;
    private TaskPresenter presenter;

    /**
     * @param presenter 
     *              Taskpresenter
     * @param req       
     *              The requirement
     * @param assigned
     */
    public RequirementListItem(TaskPresenter presenter, Requirement req, boolean assigned) {
        this.setText(req.getName());
        this.requirement = req;
        this.presenter = presenter;

        if(assigned) {
            this.setAsRequirement();
        } else {
            this.unsetAsRequirement();
        }

    }

    /**
     * @param name
     *              Name of the requirement
     */
    public void setRequirementNameLabel(String name) {
        this.setText(name);
    }

    /**
     * 
     * @return  The Name of the Requirement
     */
    public String getRequirementNameLabel() {
        return this.getText();
    }

    /**
     * ALSO NEEDS ICON
     */
    public void setAsRequirement() {
        this.setIcon(Icons.REMOVE_USER);
        this.setHorizontalTextPosition(SwingConstants.LEFT);
        this.setAlignmentX(0.0f);
    }
    
    /**
     * NEEDS ICONS ! ! !! !!! !!!!! !!!!!!!! !!!!!!!!!!!!! !!!!!!!!!!!!!!!!!!!!!
     * !! !!! !!!!! !!!!!!! !!!!!!!!!!! !!!!!!!!!!!!! !!!!!!!!!!!!!!!! !!!!!!!!!!!!!!!!!!!
     */
    public void unsetAsRequirement() {
        this.setIcon(Icons.ADD_USER);
        this.setHorizontalTextPosition(SwingConstants.RIGHT);
    }
    
    /**
     * 
     * @param assigned
     */
    public void addChangeListButtonListener(boolean assigned) {
        if(assigned) {
            this.addActionListener(assignedListListener);
        } else {
            this.addActionListener(unassignedListListener);  
        }
    }

    
    /**
     * 
     */
    private ActionListener assignedListListener = (ActionEvent e) -> {
        presenter.removeRequirement(requirement);
        presenter.addRequirementsToView();
    };
    
    /**
     * 
     */
    private ActionListener unassignedListListener = (ActionEvent e) -> {
        presenter.addRequirement(requirement);
        presenter.addRequirementsToView();
    };

}

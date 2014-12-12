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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;

public class UserListsView extends JPanel {
    
    private static final long serialVersionUID = 2597998987471055654L;
    private JPanel assignedUsersPanel = new JPanel();
    private JPanel unassignedUsersPanel = new JPanel();
    private JLabel assignedUsersLabel = new JLabel("Assigned Users");
    private JLabel unassignedUsersLabel = new JLabel("Unassigned Users");
    private JScrollPane assignedScrollPane = new JScrollPane();
    private JScrollPane unassignedScrollPane = new JScrollPane();
    private TaskPresenter presenter;

    /**
     * Create the panel.
     * 
     * @param presenter taks presenter
     */
    public UserListsView(TaskPresenter presenter) {
        this.setLayout(new MigLayout("", "[grow][grow]", "[][grow]"));
        this.presenter = presenter;
        
        this.add(assignedUsersLabel, "cell 0 0,alignx center");        
        this.add(unassignedUsersLabel, "cell 1 0,alignx center");
        
        this.assignedScrollPane.setViewportBorder(null);
        this.add(assignedScrollPane, "cell 0 1,grow");
        this.assignedScrollPane.setViewportView(assignedUsersPanel);
        this.assignedUsersPanel.setLayout(new MigLayout("fill"));
        
        this.unassignedScrollPane.setViewportBorder(null);
        this.add(unassignedScrollPane, "cell 1 1,grow");
        this.unassignedScrollPane.setViewportView(unassignedUsersPanel);
        this.unassignedUsersPanel.setLayout(new MigLayout("fill"));
    }
    
    /**
     * Adds a UserListItem to either the assigned or unassigned list.
     * @param user User to add to the list
     * @param assigned Either true if the user is assigned to the task, or false
     * if unassigned
     */
    public void addUserToList(User user, boolean assigned) {
        UserListItem userItem = new UserListItem(presenter, user, assigned);
        userItem.addChangeListButtonListener(assigned);
        if(assigned) {
            userItem.setAsAssignedUser();
            this.assignedUsersPanel.add(userItem, "dock north");
            this.unassignedUsersPanel.remove(userItem);
        } else {
            userItem.setAsUnassignedUser();
            this.unassignedUsersPanel.add(userItem, "dock north");
            this.assignedUsersPanel.remove(userItem);
        }
        
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Remove all users from the panel, so we can redraw with the update of users
     */
    public void removeAllUsers() {
        this.assignedUsersPanel.removeAll();
        this.unassignedUsersPanel.removeAll();
    }

}

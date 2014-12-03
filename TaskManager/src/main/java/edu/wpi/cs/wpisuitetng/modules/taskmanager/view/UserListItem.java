package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;

public class UserListItem extends JButton {

    private static final long serialVersionUID = 4251094749415084075L;

    private User user;
    private TaskPresenter presenter;

    /**
     * @param userName The users name
     * @param assigned True if assigned to task, false if not
     */
    public UserListItem(TaskPresenter presenter, User user, boolean assigned) {
        this.setText(user.getName());
        this.user = user;
        this.presenter = presenter;
        
        if(assigned) {
            this.setAsAssignedUser();
        } else {
            this.setAsUnassignedUser();
        }
    }

    /**
     * @param name Name of the User
     */
    public void setUserNameLabel(String name) {
        this.setText(name);
    }

    /**
     * @return The Name of the User
     */
    public String getUserNameLabel() {
        return this.getText();
    }

    /**
     * Sets the user to be assigned type, so that button 
     * shows an "add" icon
     */
    public void setAsAssignedUser() {
        this.setIcon(Icons.REMOVE_USER);
        this.setHorizontalTextPosition(SwingConstants.LEFT);
        this.setAlignmentX(0.0f);
    }
    
    /**
     * Sets the user to be unassigned type, so that button 
     * shows a "remove" icon
     */
    public void setAsUnassignedUser() {
        this.setIcon(Icons.ADD_USER);
        this.setHorizontalTextPosition(SwingConstants.RIGHT);
    }
    
    /**
     * Adds a listener based on which list the user is in.
     */
    public void addChangeListButtonListener(boolean assigned) {
        if(assigned) {
            this.addActionListener(assignedListListener);
        } else {
            this.addActionListener(unassignedListListener);  
        }
    }
    
    /**
     * Listener for assigned users button.
     * Remove from assignedTo list in model.
     * Add users to view again.
     */
    private ActionListener assignedListListener = (ActionEvent e) -> {
        presenter.removeUserFromAssignedTo(user);
        presenter.addUsersToView();
    };
    
    /**
     * Listener for unassigned users button
     * Add to assignedTo list in model.
     * Add users to view again.
     */
    private ActionListener unassignedListListener = (ActionEvent e) -> {
        presenter.addUserToAssignedTo(user);
        presenter.addUsersToView();
    };

}

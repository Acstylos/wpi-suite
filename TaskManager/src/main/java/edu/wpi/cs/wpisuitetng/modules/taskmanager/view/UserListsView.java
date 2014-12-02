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
        UserListItemPanel userItem = new UserListItemPanel(presenter, user, assigned);
        userItem.addChangeListButtonListener(assigned);
        if(assigned) {
            userItem.setAsAssignedUser();
            this.assignedUsersPanel.add(userItem, "dock north");
        } else {
            userItem.setAsUnassignedUser();
            this.unassignedUsersPanel.add(userItem, "dock north");
        }
    }
    
    /**
     * Remove all users from the panel, so we can redraw with the update of users
     */
    public void removeAllUsers() {
        this.assignedUsersPanel.removeAll();
        this.unassignedUsersPanel.removeAll();
    }

}
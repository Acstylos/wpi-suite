package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

public class UserListsView extends JPanel {
    
    private static final long serialVersionUID = 2597998987471055654L;
    private JPanel assignedUsersPanel = new JPanel();
    private JPanel unassignedUsersPanel = new JPanel();
    private JLabel assignedUsersLabel = new JLabel("Assigned Users");
    private JLabel unassignedUsersLabel = new JLabel("Unassigned Users");
    private JScrollPane assignedScrollPane = new JScrollPane();
    private JScrollPane unassignedScrollPane = new JScrollPane();

    /**
     * Create the panel.
     */
    public UserListsView() {
        this.setLayout(new MigLayout("", "[grow][grow]", "[][grow]"));
        
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
     * @param name Name of the user to add to the list
     * @param assigned Either true if the user is assigned to the task, or false
     * if unassigned
     */
    public void addUserToList(String name, boolean assigned) {
        if(assigned) {
            this.assignedUsersPanel.add(new UserListItemPanel(name, assigned), "dock north");
        } else {
            this.unassignedUsersPanel.add(new UserListItemPanel(name, !assigned), "dock north");
        }
    }
    
    /**
     * Removes a UserListItem from the proper list.
     * @param userId The ID of the UserListItem component to remove
     * @param assigned Either true if the user is assigned to the task, or false
     * if unassigned
     */
    public void removeUserFromList(JPanel userId, boolean assigned) {
        // TODO: make a proper method for this maybe.
    }
    
    /**
     * On button press removes user from assigned users if they
     * exist there. Then adds them to the unassigned panel.
     * @param user User to be added to the assigned list
     */
    public void moveUserToUnassignedList(UserListItemPanel user) {
        // TODO: make a proper method for this.
    }
    
    /**
     * On button press removes user from unassigned users if they
     * exist there. Then adds them to the assigned panel.
     * @param user User to be added to the unassigned list
     */
    public void moveUserToAssignedList(UserListItemPanel user) {
        // TODO: make a proper method for this
    }

}

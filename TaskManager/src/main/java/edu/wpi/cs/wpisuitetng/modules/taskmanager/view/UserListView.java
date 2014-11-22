package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class UserListView extends JPanel {
    
    private static final long serialVersionUID = 2597998987471055654L;
    
    private JPanel userListPanel = new JPanel();
    private JPanel userActionsPanel = new JPanel();
    private JLabel addNewUserLabel = new JLabel("Add New User:");
    private JComboBox userListComboBox = new JComboBox();
    private UserListItemPanel user1 = new UserListItemPanel("Alex Stylos");

    /**
     * Create the panel.
     */
    public UserListView() {
        this.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
        this.add(userListPanel, "cell 0 0,grow");
        this.add(userActionsPanel, "cell 0 1,grow");
        
        this.userListPanel.setLayout(new MigLayout("fill"));
        this.userListPanel.add(user1, "dock north");
        
        this.userActionsPanel.setLayout(new MigLayout("", "[][grow]", "[]"));
        this.userActionsPanel.add(addNewUserLabel, "cell 0 0,alignx trailing");
        this.userActionsPanel.add(userListComboBox, "cell 1 0,growx");
    }

}

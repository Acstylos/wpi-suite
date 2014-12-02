package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import net.miginfocom.swing.MigLayout;

public class UserListItemPanel extends JPanel {

    private static final long serialVersionUID = 4251094749415084075L;

    private JLabel userNameLabel = new JLabel();
    private User user;
    private JButton listChangeButton = new JButton("X");
    private TaskPresenter presenter;

    /**
     * @param userName The users name
     * @param assigned True if assigned to task, false if not
     */
    public UserListItemPanel(TaskPresenter presenter, User user, boolean assigned) {
        setBackground(Color.LIGHT_GRAY);
        setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(240, 240, 240)));
        this.setLayout(new MigLayout("fill"));
        this.userNameLabel.setText(user.getName());
        this.user = user;
        this.presenter = presenter;
        this.listChangeButton.setBorder(null);
        this.listChangeButton.setFocusPainted(false);
        this.add(listChangeButton, "dock east");
        
        if(assigned) {
            this.listChangeButton.setText("X");
        } else {
            this.listChangeButton.setText("+");
        }
        
        this.add(userNameLabel, "dock west");
    }

    /**
     * @param name Name of the User
     */
    public void setUserNameLabel(String name) {
        this.userNameLabel.setText(name);
    }

    /**
     * @return The Name of the User
     */
    public String getUserNameLabel() {
        return this.userNameLabel.getText();
    }

    /**
     * Sets the panel to be assigned type, so that button 
     * shows "+" instead of "x".
     */
    public void setAsAssignedUser() {
        this.listChangeButton.setText("X");
    }
    
    /**
     * Sets the panel to be unassigned type, so that button 
     * shows "x" instead of "+".
     */
    public void setAsUnassignedUser() {
        this.listChangeButton.setText("+");
    }
    
    /**
     * Adds a listener based on which list the panel is in.
     */
    public void addChangeListButtonListener(boolean assigned) {
        if(assigned) {
            this.listChangeButton.addActionListener(assignedListListener);
        } else {
            this.listChangeButton.addActionListener(unassignedListListener);  
        }
    }
    
    /**
     * Listener for assigned users button.
     * Remove from assignedTo list in model.
     * Add users to view again.
     */
    private ActionListener assignedListListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            presenter.removeAssignedTo(user);
            presenter.addUsersToView();
        }
    };
    
    /**
     * Listener for unassigned users button
     * Add to assignedTo list in model.
     * Add users to view again.
     */
    private ActionListener unassignedListListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            presenter.addUserToAssignedTo(user);
            presenter.addUsersToView();
        }
    };

}

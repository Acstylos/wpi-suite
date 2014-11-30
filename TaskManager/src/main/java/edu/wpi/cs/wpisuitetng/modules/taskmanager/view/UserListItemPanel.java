package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;

public class UserListItemPanel extends JPanel {

    private static final long serialVersionUID = 4251094749415084075L;

    private JLabel userNameLabel = new JLabel();
    private JButton listChangeButton = new JButton("X");

    /**
     * Create the panel.
     */
    public UserListItemPanel(String userName, boolean assigned) {
        setBackground(Color.LIGHT_GRAY);
        setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(240, 240, 240)));
        this.setLayout(new MigLayout("fill"));
        this.userNameLabel.setText(userName);
        this.listChangeButton.setBorder(null);
        this.listChangeButton.setFocusPainted(false);
        this.add(listChangeButton, "dock east");
        
        if(assigned) {
            this.listChangeButton.setText("x");
        } else {
            this.listChangeButton.setText("+");
        }
        
        this.add(userNameLabel, "dock west");
    }

    public void setUserNameLabel(String name) {
        this.userNameLabel.setText(name);
    }

    public String getUserNameLabel() {
        return this.userNameLabel.getText();
    }

    /**
     * Sets the panel to be assigned type, so that button 
     * shows "+" instead of "x".
     */
    public void setAsAssignedUser() {
        this.listChangeButton.setText("+");
    }
    
    /**
     * Sets the panel to be unassigned type, so that button 
     * shows "x" instead of "+".
     */
    public void setAsUnassignedUser() {
        this.listChangeButton.setText("x");
    }

}

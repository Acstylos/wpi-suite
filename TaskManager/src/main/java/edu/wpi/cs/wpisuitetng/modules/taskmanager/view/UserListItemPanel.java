package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Point;

import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class UserListItemPanel extends JPanel {

    private static final long serialVersionUID = 4251094749415084075L;
    
    private JLabel userNameLabel = new JLabel();
    private JButton deleteButton = new JButton("Click me");
    

    /**
     * Create the panel.
     */
    public UserListItemPanel(String userName) {
        setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        this.setLayout(new MigLayout("fill"));
        this.userNameLabel.setText(userName);
        this.add(deleteButton, "dock east");
        this.deleteButton.setVisible(false);
        
        
        add(userNameLabel, "dock west");
        setCallbacks();
    }
    
    public void setUserNameLabel(String name){
        this.userNameLabel.setText(name);
    }
    
    public String getUserNameLabel(){
        return this.userNameLabel.getText();
    }
    
    public void setCallbacks(){
        this.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            public void mouseEntered(MouseEvent e) {
                deleteButton.setVisible(true);
                revalidate();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	System.out.println("Exit Triggered");
                if(getMousePosition() == null){
                	deleteButton.setVisible(false);
                	revalidate();
                	repaint();
                }
            }
            
        });
    }

}

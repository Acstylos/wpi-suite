package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
/*constructs message view for comments and history of tasks
 * message: message within comment box
 * 
 */
public class ActivityView extends JPanel {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7743312885538872898L;
    /**
     * Create the panel.
     */
    private String message = "";
    private JPanel pastMessagePanel;

    /**
     * Constructor sets up panel and colors
     */
    public ActivityView() {
        setLayout(new MigLayout("", "[grow]", "[]"));

        pastMessagePanel = new JPanel();
        pastMessagePanel.setForeground(Color.WHITE);
        pastMessagePanel.setBackground(Color.LIGHT_GRAY);
        add(pastMessagePanel, "cell 0 0,grow");
        pastMessagePanel.setLayout(new MigLayout("", "[grow][]", "[]"));
        this.pastMessagePanel = pastMessagePanel;
        

    }

    /**
     * sets the message within a MessageView and sets it up
     * @param message: message within MessageView
     */
    public void setMessage(String message){
        this.message = message;
        JLabel pastActivityLabel = new JLabel(message);
        this.pastMessagePanel.add(pastActivityLabel, "cell 0 0");
        pastActivityLabel.setForeground(Color.WHITE);
        pastActivityLabel.setBackground(Color.LIGHT_GRAY);

    }
    
    /**
     * Makes X button appear when mouse is over message (not yet working)
     * @param e: MouseEvent
     */
    public void mouseEntered(MouseEvent e) {
        JButton deleteActivityButton = new JButton("X");
        pastMessagePanel.add(deleteActivityButton, "cell 1 0");
        deleteActivityButton.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
     }
}
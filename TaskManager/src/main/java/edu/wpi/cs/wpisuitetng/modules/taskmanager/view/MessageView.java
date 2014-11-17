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
public class MessageView extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 7743312885538872898L;
    /**
     * Create the panel.
     */
    private String message = "";
    private JPanel panel;
    private JPanel panel_1;
    private JPanel pastMessagePanel;

    public MessageView() {
        setLayout(new MigLayout("", "[grow]", "[]"));

        pastMessagePanel = new JPanel();
        pastMessagePanel.setForeground(Color.WHITE);
        pastMessagePanel.setBackground(Color.LIGHT_GRAY);
        add(pastMessagePanel, "cell 0 0,grow");
        pastMessagePanel.setLayout(new MigLayout("", "[grow][]", "[]"));
        this.pastMessagePanel = pastMessagePanel;
        
        


    }

    /*setMessage: sets the message within a MessageView and sets it up
     * message: message within MessageView
     */
    public void setMessage(String message){
        this.message = message;
        JLabel lblNewLabel = new JLabel(message);
        this.pastMessagePanel.add(lblNewLabel, "cell 0 0");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setBackground(Color.LIGHT_GRAY);

    }
    
    /*Makes X button appear when mouse is over message
     * (not yet working)
     */
    public void mouseEntered(MouseEvent e) {
        JButton btnNewButton = new JButton("X");
        pastMessagePanel.add(btnNewButton, "cell 1 0");
        btnNewButton.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
     }
}


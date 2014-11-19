package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTextArea;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
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
    private JPanel pastMessagePanel = new JPanel();
    private JTextArea pastActivityText = new JTextArea();

    /**
     * Constructor sets up panel and colors
     */
    public ActivityView() {
        this.setLayout(new MigLayout("", "[grow]", "[grow]"));
        
        pastActivityText.setEditable(false);
        pastMessagePanel.setForeground(Color.WHITE);
        pastMessagePanel.setBackground(Color.LIGHT_GRAY);
        add(pastMessagePanel, "cell 0 0,grow");
        pastMessagePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.pastMessagePanel.add(pastActivityText, "cell 0 0, grow");
        pastActivityText.setForeground(Color.BLACK);
        pastActivityText.setBackground(Color.LIGHT_GRAY);
        this.pastActivityText.setWrapStyleWord(true);
        this.pastActivityText.setLineWrap(true);
        this.pastActivityText.setMinimumSize(this.getMinimumSize());
    }

    /**
     * sets the message within a MessageView and sets it up
     * @param message: message within MessageView
     */
    public void setMessage(String message){
        this.pastActivityText.setText(message);
    }
    
    public String getMessage(){
        return this.pastActivityText.getText();
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
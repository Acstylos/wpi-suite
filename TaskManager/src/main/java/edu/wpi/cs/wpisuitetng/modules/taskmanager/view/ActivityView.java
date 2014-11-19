package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;
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
    private JTextArea pastActivityText = new JTextArea();
    //private JTextArea pastActivityText = new JTextArea();

    /**
     * Constructor sets up panel and colors
     */
    public ActivityView() {
        setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.add(pastActivityText, "cell 0 0, grow");
        pastActivityText.setEditable(false);
        pastActivityText.setForeground(Color.BLACK);
        pastActivityText.setBackground(Color.LIGHT_GRAY);
        pastActivityText.setWrapStyleWord(true);
        pastActivityText.setLineWrap(true);
    }
    
    /**
     * Constructor sets up panel and colors
     */
    public ActivityView(String text) {
        setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.add(pastActivityText, "cell 0 0, grow");
        pastActivityText.setEditable(false);
        pastActivityText.setForeground(Color.BLACK);
        pastActivityText.setBackground(Color.LIGHT_GRAY);
        pastActivityText.setWrapStyleWord(true);
        pastActivityText.setLineWrap(true);
        pastActivityText.setText(text);
    }

    /**
     * sets the message within a MessageView and sets it up
     * @param message: message within MessageView
     */
    public void setMessage(String message){
        pastActivityText.setText(message);
    }
    
    public String getMessage(){
        return pastActivityText.getText();
    }
}
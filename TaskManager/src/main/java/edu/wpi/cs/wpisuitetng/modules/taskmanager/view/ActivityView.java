package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;

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

    /**
     * Constructor sets up panel and colors
     */
    public ActivityView() {
        this.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.add(pastActivityText, "cell 0 0, grow");
        this.pastActivityText.setEditable(false);
        this.pastActivityText.setForeground(Color.BLACK);
        this.pastActivityText.setBackground(Color.LIGHT_GRAY);
        this.pastActivityText.setWrapStyleWord(true);
        this.pastActivityText.setLineWrap(true);
    }
    
    /**
     * Constructor sets up panel and colors
     */
    public ActivityView(String text) {
        this.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.add(pastActivityText, "cell 0 0, grow");
        this.pastActivityText.setEditable(false);
        this.pastActivityText.setForeground(Color.BLACK);
        this.pastActivityText.setBackground(Color.LIGHT_GRAY);
        this.pastActivityText.setWrapStyleWord(true);
        this.pastActivityText.setLineWrap(true);
        this.pastActivityText.setText(text);
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
}
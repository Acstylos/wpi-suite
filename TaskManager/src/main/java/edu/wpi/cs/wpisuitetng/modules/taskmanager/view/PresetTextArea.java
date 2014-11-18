package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

/* JWriteinText extends JTextArea as a box that provides a constant light gray
 * text when cleared or reset.
 * When clicked, the text is cleared and the user can type freely,
 * and the text will be black.
 */
public class PresetTextArea extends JTextArea{

    private static final long serialVersionUID = 1940773640674478555L;
    /*Parameters:
	startText: text in box after it is cleared
	commentTyped: if false, box should be reset when clicked
     */    
    private String startText;
    private boolean commentTyped = false;
    
    /**
     * Empty constructor
     */
    public PresetTextArea(){
        setFont(new Font("Tahoma", Font.PLAIN, 14));
        
    }
    
    /**
     * Constructor for starting with a specific field.
     * @param startText Text that the field starts with.
     */
    public PresetTextArea(String startText){
        this.startText = startText;
    }

    /**
     * Sets text back to the original start text and resets boolean
     */
    public void resetText(){
        this.setForeground(Color.LIGHT_GRAY);
        this.setToolTipText("");
        this.setText(this.startText);
        this.commentTyped = false;
    }

    /**
     * Clears the box if it is clicked and currently has the
     * start text inside
     */
    public void clicked() {
        if (!commentTyped){
            this.setForeground(Color.BLACK);
            this.setText("");
            commentTyped=true;
        }
    }

    /**
     * sets the box to the given gray text after being cleared
     * @param startText String that box will always start with
     */
    public void setStartText(String startText){
        this.commentTyped = false;
        this.startText = startText;
        this.setForeground(Color.LIGHT_GRAY);
        this.setText(startText);
    }	

}

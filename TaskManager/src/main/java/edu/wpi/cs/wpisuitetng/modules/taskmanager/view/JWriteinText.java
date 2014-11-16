package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

/*JWriteinText extends JTextArea as a box that provides a constant light gray
 * text when cleared or reset.
 * When clicked, the text is cleared and the user can type freely,
 * and the text will be black.
 */
public class JWriteinText extends JTextArea{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1940773640674478555L;
	/*Parameters:
	startText: text in box after it is cleared
	commentTyped: if false, box should be reset when clicked
	*/
	private String startText = "";
	private boolean commentTyped = false;
	
	//ResetText: Sets text back to the original start text and resets boolean
	public void ResetText(){
	this.setForeground(Color.LIGHT_GRAY);
	this.setFont(new Font("Times New Roman", Font.PLAIN, 14));
	this.setToolTipText("");
	this.setText(this.startText);
	this.commentTyped = false;
	}
	
	/*
	 * Clicked: Clears the box if it is clicked and currently has the
	 * start text inside
	 */
	public void Clicked() {
			if (!commentTyped){
				this.setForeground(Color.BLACK);

				this.setText("");
				commentTyped=true;
			}
		}
	
	/*
	 * SetStartText: sets the gray text after being cleared to the given string
	 * startText: String that box will always start with
	 */
	public void SetStartText(String startText){
		this.commentTyped = false;
		this.startText = startText;
		this.setForeground(Color.LIGHT_GRAY);
		this.setText(startText);
	}	
	
}

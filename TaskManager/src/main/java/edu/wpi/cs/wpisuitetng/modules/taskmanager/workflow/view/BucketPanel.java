package edu.wpi.cs.wpisuitetng.modules.taskmanager.workflow.view;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;

public class BucketPanel extends JPanel
{

	private static final long serialVersionUID = -5937582878085666950L;
	private String title_;
	
	public BucketPanel(String title){
		setPreferredSize(new Dimension(250, 500));
		// Tasks will be structured from top to bottom
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(250, 500));
		setTitle(title);
		setBorder(new TitledBorder(null, title_, TitledBorder.CENTER, TitledBorder.TOP, null, null));
	
	}

	public String getTitle() {
		return title_;
	}

	public void setTitle(String title) {
		this.title_ = title;
	}

}

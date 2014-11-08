package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import java.awt.Dimension;


import edu.wpi.cs.wpisuitetng.modules.taskmanager.workflow.view.BucketView;
import javax.swing.ScrollPaneConstants;
import javax.swing.JPanel;

public class MainView extends JScrollPane 
{
	
	private static final long serialVersionUID = -346061317795260862L;
	
	public MainView(){
		BucketView workflowView = new BucketView();
		setViewportView(workflowView);
	}
}
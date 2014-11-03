package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;

/**
 * This is the main class for the WPI Suite TM module for Janeway.
 * 
 * WPI Suite TM is a task manager consisting of one tab that provides an
 * interface for keeping track of flow-based tasks.
 */
public class TaskManager implements IJanewayModule {
	
	/** A list containing the one tab */
	private List<JanewayTabModel> tabs;
	
	public TaskManager() {
		JPanel toolbar = new JPanel();
		JPanel mainPanel = new JPanel();
		
		
		/* The main panel of the tab contains a label and a progress bar for
			now. In the future, this is where we will have our UI elements. */
		JLabel progressLabel = new JLabel("Progress on WPI Suite Task Manager:");
		mainPanel.add(progressLabel);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(80);
		mainPanel.add(progressBar);
		
		/* Create the tab model for the task manager */
		tabs = new ArrayList<JanewayTabModel>();
		tabs.add(new JanewayTabModel("Task Manager", new ImageIcon(), toolbar, mainPanel));
	}

	/**
	 * @return The name of the module ("Task Manager")
	 */
	@Override
	public String getName() {
		return "Task Manager";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}

}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;

// Init commit comment.

/**
 * This is the main class for the WPI Suite TM module for Janeway.
 *
 * WPI Suite TM is a task manager consisting of one tab that provides an
 * interface for keeping track of flow-based tasks.
 */
public class TaskManager implements IJanewayModule
{
    /** A list containing the one tab */
    private List<JanewayTabModel> tabs_;

    public TaskManager() {
	JPanel toolbarPanel = new JPanel();
	JPanel mainPanel = new JPanel();

	/*
	 * The main panel of the tab contains a scroll pane, 
	 * which holds a BucketView that acts as the workflow.
	 */

	mainPanel.add(new MainView());
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


	/* Create the tab model for the task manager */
	tabs_ = new ArrayList<JanewayTabModel>();
	tabs_.add(new JanewayTabModel("Task Manager", new ImageIcon(),
		toolbarPanel, mainPanel));

    }

    /**
     * @return The name of the module ("Task Manager")
     */
    @Override
    public String getName()
    {
	return "Task Manager";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JanewayTabModel> getTabs()
    {
	return tabs_;
    }

}

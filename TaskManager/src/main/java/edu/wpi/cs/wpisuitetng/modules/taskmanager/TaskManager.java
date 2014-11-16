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

import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.WorkflowPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ToolbarView;


/**
 * This is the main class for the WPI Suite TM module for Janeway.
 *
 * WPI Suite TM is a task manager consisting of one tab that provides an
 * interface for keeping track of flow-based tasks.
 */
public class TaskManager implements IJanewayModule
{
    /** A list containing the one tab */
    private List<JanewayTabModel> tabs;

    MainView mainView;

    public TaskManager() {
        ToolbarView toolbarPanel = new ToolbarView();

        mainView = MainView.getInstance();//   new MainView(workflowPresenter);
        /* Create the tab model for the task manager */
        tabs = new ArrayList<JanewayTabModel>();
        tabs.add(new JanewayTabModel("Task Manager", new ImageIcon(),
                toolbarPanel, mainView));
        //      mainPanel.add(new CommentView());

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
        return tabs;
    }
}

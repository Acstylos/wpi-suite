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

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.chrono.JapaneseDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.WorkflowPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;


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
        JPanel toolbarPanel = new JPanel();
        
        WorkflowPresenter workflowPresenter = new WorkflowPresenter(0);

        mainView = new MainView(workflowPresenter);
        
        /* Create the tab model for the task manager */
        tabs = new ArrayList<JanewayTabModel>();
        tabs.add(new JanewayTabModel("Task Manager", new ImageIcon(),
                toolbarPanel, mainView));

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

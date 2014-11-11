/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;

/**
 * This is the main class for the WPI Suite TM module for Janeway.
 *
 * WPI Suite TM is a task manager consisting of one tab that provides an
 * interface for keeping track of flow-based tasks.
 */
public class TaskManager implements IJanewayModule
{
  /** Current main view. */
  private TaskView view;
  
  /** A list containing the one tab */
  private List<JanewayTabModel> tabs;

  public TaskManager() {
    JPanel toolbarPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JButton button = new JButton("Load your workflow.");
    
    button.addActionListener((ActionEvent e) -> {
        if (TaskManager.this.view == null) {
            TaskPresenter taskPresenter = new TaskPresenter(0);
            view = taskPresenter.getView();
            mainPanel.remove(button);
            mainPanel.add(view);
        }});

    mainPanel.add(button);
    /* Create the tab model for the task manager */
    this.tabs = new ArrayList<JanewayTabModel>();
    this.tabs.add(new JanewayTabModel("Task Manager", new ImageIcon(),
        toolbarPanel, mainPanel));

    view = null;
  }

  /**
   * @return The name of the module ("Task Manager")
   */
  public String getName()
  {
    return "Task Manager";
  }

  /**
   * {@inheritDoc}
   */
  public List<JanewayTabModel> getTabs()
  {
    return this.tabs;
  }
}

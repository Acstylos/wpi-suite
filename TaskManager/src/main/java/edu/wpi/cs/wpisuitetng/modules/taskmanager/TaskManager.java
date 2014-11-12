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
import java.util.Date;

import javax.swing.ImageIcon;
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
  /** A list containing the one tab */
  private List<JanewayTabModel> tabs;

  public TaskManager() {
    JPanel toolbarPanel = new JPanel();
    JPanel mainPanel = new JPanel();

    /* Create the tab model for the task manager */
    this.tabs = new ArrayList<JanewayTabModel>();
    this.tabs.add(new JanewayTabModel("Task Manager", new ImageIcon(),
        toolbarPanel, mainPanel));

    String title = "Duck";
    int effort = 100;
    String description = "Add a duck";
    Date dueDate = new Date(114, 11, 18);

    final TaskModel taskModel = new TaskModel(1, title, description, effort, dueDate);

    final TaskPresenter taskPresenter = new TaskPresenter(taskModel);

    final TaskView taskView = taskPresenter.getView();
    mainPanel.add(taskView);
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

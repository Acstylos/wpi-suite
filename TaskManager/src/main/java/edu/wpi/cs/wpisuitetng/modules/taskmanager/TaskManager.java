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
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;

// Remove this comment Blah

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
    
    final TaskView taskView = new TaskView(title, effort, description, dueDate);
    mainPanel.add(taskView);
    
    taskView.addOnSaveListener((ActionEvent action) -> {
        System.out.println("Save");
        System.out.println(taskView.getTitleText());
        System.out.println(taskView.getEstimatedEffort());
        System.out.println(taskView.getDescriptionText());
    });
    
    taskView.addOnReloadListener((ActionEvent action) -> {
        System.out.println("Reload");
        taskView.setTitleText(title);
        taskView.setEstimatedEffort(effort);
        taskView.setDescriptionText(description);
        taskView.setDueDate(dueDate);
    });
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
    return this.tabs;
  }

}

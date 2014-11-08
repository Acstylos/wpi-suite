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
  private List<JanewayTabModel> tabs_;

  public TaskManager() {
    JPanel toolbarPanel = new JPanel();
    JPanel mainPanel = new JPanel();

    /* Create the tab model for the task manager */
    tabs_ = new ArrayList<JanewayTabModel>();
    tabs_.add(new JanewayTabModel("Task Manager", new ImageIcon(),
        toolbarPanel, mainPanel));
    
    TaskView taskView = new TaskView("Add a Duck", 100, "Add this duck to "
        + "WPI Suite TM asap, this is a <b>mission critical</b> task and "
        + "must be at <i>least</i> 80% done. <br/>"
        + "<img src='http://i.imgur.com/R6cYlWl.png'/>",
        new Date(114, 11, 18));
    mainPanel.add(taskView);
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

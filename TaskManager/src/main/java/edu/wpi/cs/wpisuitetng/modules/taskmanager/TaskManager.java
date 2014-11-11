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
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
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
  private TaskPresenter mainPresenter;

  public TaskManager() {
    JPanel toolbarPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JButton button = new JButton("Load your workflow.");

    button.addActionListener((ActionEvent e) -> {
      if (TaskManager.this.mainPresenter == null) {
        mainPresenter = new TaskPresenter(0);
        mainPanel.remove(button);
        mainPanel.add(new JScrollPane(mainPresenter.getView()));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
      }});

    mainPanel.add(button);
    /* Create the tab model for the task manager */
    this.tabs = new ArrayList<JanewayTabModel>();
    this.tabs.add(new JanewayTabModel("Task Manager", new ImageIcon(),
        toolbarPanel, mainPanel));
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

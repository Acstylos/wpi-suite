/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

/**
 * A {@link javax.swing.JComponent} that renders the fields of a single task
 * and provides the minimum amount of logic for basic user interaction.
 *
 * @see #setTitleText(String)
 * @see #setDescriptionText(String)
 * @see #setDueDate(Date)
 */
public class TaskView extends JPanel
{
  private static final long serialVersionUID = -997563229078386090L;

  private TitledBorder title_;
  private JTextPane description_;
  private JLabel dueDate_;

  /**
   * Create a new TaskView with the specified default values.
   *
   * @param title The initial task title that will be displayed
   * @param description The initial in-depth description that will be displayed
   * @param dueDate The initial due date that will be displayed
   */
  public TaskView(String title, String description, Date dueDate) {
    /* Set a TitledBorder for this panel containing the initial title of the task */
    title_ = BorderFactory.createTitledBorder(title);
    setBorder(title_);

    /* Add a non-editable text pane with the initial description text */
    description_ = new JTextPane();
    description_.setContentType("text/html");
    description_.setEditable(false);
    description_.setText(description);

    /* Wrap the description text in a scroll pane to allow scrolling */
    add(new JScrollPane(description_));

    /* Add a label with the initial due date */
    dueDate_ = new JLabel("Due by " + DateFormat.getInstance().format(dueDate));
    add(dueDate_);

    /* Set the layout for this JFrame to a standard Swing box layout */
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
  }



  /**
   * @param titleText The new title of the task
   */
  public void setTitleText(String titleText) {
    title_.setTitle(titleText);
  }



  /**
   * @param descriptionText The new task description, which may contain HTML
   */
  public void setDescriptionText(String descriptionText) {
    description_.setText(descriptionText);
  }



  /**
   * @param dueDate The new due date of the task
   */
  public void setDueDate(Date dueDate) {
    dueDate_.setText("Due: " + DateFormat.getInstance().format(dueDate));
  }



  /**
   * @return The default size for task views, 250 by 200 pixels
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(250, 200);
  }
}

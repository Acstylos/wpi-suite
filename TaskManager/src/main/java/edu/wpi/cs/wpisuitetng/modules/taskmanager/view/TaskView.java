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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * A {@link javax.swing.JComponent} that renders the fields of a single task and
 * provides the minimum amount of logic for basic user interaction.
 */
public class TaskView extends JPanel {
    private static final long serialVersionUID = -997563229078386090L;

    private JTextField title;
    private JFormattedTextField estimatedEffort;
    private JTextPane description;
    private JFormattedTextField dueDate;
    private JButton saveButton;
    private JButton reloadButton;

    /**
     * Create a new TaskView with the specified default values.
     *
     * @param title
     *            The initial task title that will be displayed
     * @param estimatedEffort
     *            The initial estimated effort that will be displayed
     * @param description
     *            The initial in-depth description that will be displayed
     * @param dueDate
     *            The initial due date that will be displayed
     * @see #setTitleText(String)
     * @see #setEstimatedEffort(int)
     * @see #setDescriptionText(String)
     * @see #setDueDate(Date)
     */
    public TaskView(String title, int estimatedEffort, String description, Date dueDate) {
        /* Set a TitledBorder for this panel that just says "Task". */
        setBorder(BorderFactory.createTitledBorder("Task"));
        
        /* Add a text field with the title */
        this.title = new JTextField(title);
        this.title.setFont(new Font("Dialog", Font.BOLD, 12));
        add(this.title);

        /* Add a text field with the estimated effort */
        this.estimatedEffort = new JFormattedTextField(new Integer(estimatedEffort));
        add(this.estimatedEffort);

        /* Add a text field with the initial due date */
        this.dueDate = new JFormattedTextField(dueDate);
        add(this.dueDate);

        /* Add a text pane with the initial description text */
        this.description = new JTextPane();
        this.description.setText(description);

        /* Wrap the description text in a scroll pane to allow scrolling */
        add(new JScrollPane(this.description));
        
        /* Add a button to save the fields */
        this.saveButton = new JButton("Save");
        add(this.saveButton);
        
        /* Add a button to reload the fields from the database */
        this.reloadButton = new JButton("Reload");
        add(this.reloadButton);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(reloadButton);
        add(buttonPanel);

        /* Set the layout for this JFrame to a standard Swing box layout, and
         * set some basic layout properties */
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 200));
        this.description.setPreferredSize(new Dimension(250, 100));
    }
    
    /**
     * Add an {@link ActionListener} that will be called when the task is saved by the user
     * @param listener
     */
    public void addOnSaveListener(ActionListener listener) {
        this.saveButton.addActionListener(listener);
    }
    
    /**
     * Add an {@link ActionListener} that will be called when the task is reloaded by the user
     * @param listener
     */
    public void addOnReloadListener(ActionListener listener) {
        this.reloadButton.addActionListener(listener);
    }

    /**
     * @param titleText
     *            The new title of the task
     */
    public void setTitleText(String titleText) {
        this.title.setText(titleText);
    }
    
    /**
     * @return The title of the task
     */
    public String getTitleText() {
        return this.title.getText();
    }

    /**
     * @param estimatedEffort
     *            The new estimated effort of the task, in arbitrary work units.
     */
    public void setEstimatedEffort(int estimatedEffort) {
        this.estimatedEffort.setValue(estimatedEffort);
    }
    
    /**
     * @return The estimated effort of the task, in arbitrary work units.
     */
    public int getEstimatedEffort() {
        return (Integer)estimatedEffort.getValue();
    }

    /**
     * @param descriptionText The new task description
     */
    public void setDescriptionText(String descriptionText) {
        this.description.setText(descriptionText);
    }
    
    /**
     * @return The task description
     */
    public String getDescriptionText() {
        return this.description.getText();
    }

    /**
     * @param dueDate
     *            The new due date of the task
     */
    public void setDueDate(Date dueDate) {
        this.dueDate.setValue(dueDate);
    }

    /**
     * @return The due date.
     */
    public Date getDueDate() {
        return (Date) dueDate.getValue();
    }
}

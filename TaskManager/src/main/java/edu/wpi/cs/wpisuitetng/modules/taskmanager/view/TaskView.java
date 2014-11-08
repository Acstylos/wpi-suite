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
 * A {@link javax.swing.JComponent} that renders the fields of a single task and
 * provides the minimum amount of logic for basic user interaction.
 */
public class TaskView extends JPanel {
    private static final long serialVersionUID = -997563229078386090L;

    private TitledBorder title;
    private JLabel estimatedEffort;
    private JTextPane description;
    private JLabel dueDate;

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
    public TaskView(String title, int estimatedEffort, String description,
            Date dueDate) {
        /*
         * Set a TitledBorder for this panel containing the initial title of the
         * task
         */
        this.title = BorderFactory.createTitledBorder(title);
        setBorder(this.title);

        /* Add a label with the estimated effort */
        this.estimatedEffort = new JLabel("Estimated Effort: "
                + estimatedEffort);
        add(this.estimatedEffort);

        /* Add a non-editable text pane with the initial description text */
        this.description = new JTextPane();
        this.description.setContentType("text/html");
        this.description.setEditable(false);
        this.description.setText(description);

        /* Wrap the description text in a scroll pane to allow scrolling */
        add(new JScrollPane(this.description));

        /* Add a label with the initial due date */
        this.dueDate = new JLabel("Due by "
                + DateFormat.getInstance().format(dueDate));
        add(this.dueDate);

        /* Set the layout for this JFrame to a standard Swing box layout */
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * @param titleText
     *            The new title of the task
     */
    public void setTitleText(String titleText) {
        this.title.setTitle(titleText);
    }

    /**
     * @param estimatedEffort
     *            The new estimated effort of the task, in arbitrary work units.
     */
    public void setEstimatedEffort(int estimatedEffort) {
        this.estimatedEffort.setText("Estimated Effort: " + estimatedEffort);
    }

    /**
     * @param descriptionText
     *            The new task description, which may contain HTML
     */
    public void setDescriptionText(String descriptionText) {
        this.description.setText(descriptionText);
    }

    /**
     * @param dueDate
     *            The new due date of the task
     */
    public void setDueDate(Date dueDate) {
        this.dueDate.setText("Due by "
                + DateFormat.getInstance().format(dueDate));
    }

    /**
     * @return The default size for task views, 250 by 200 pixels
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 200);
    }
}

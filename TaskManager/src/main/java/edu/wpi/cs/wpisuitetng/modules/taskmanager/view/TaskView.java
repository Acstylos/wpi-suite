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

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

/**
 * A {@link javax.swing.JComponent} that renders the fields of a single task and
 * provides the minimum amount of logic for basic user interaction.
 */
public class TaskView extends JPanel {
    private static final long serialVersionUID = -997563229078386090L;
    
    private JComboBox<BucketView> statusComboBox = new JComboBox<BucketView>();
    private JLabel taskNameLabel = new JLabel("Task Name:");
    private JLabel dateLabel = new JLabel("Due Date:");
    private JLabel statusLabel = new JLabel("Status:");
    private JLabel actualEffortLabel = new JLabel("Actual Effort:");
    private JLabel estEffortLabel = new JLabel("Estimated Effort:");
    private JPanel buttonPanel = new TaskButtonsPanel();
    private JPanel commentPanel = new JPanel();
    private JPanel descriptionPanel = new JPanel();
    private JPanel detailsPanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JPanel splitPanel = new JPanel();
    private JPanel usersPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane();
    private JSpinner actualEffortSpinner = new JSpinner();
    private JSpinner estEffortSpinner = new JSpinner();
    private JSplitPane splitPane = new JSplitPane();
    private JWriteinText descriptionMessage = new JWriteinText();
    private JTextField taskNameField = new JTextField();
    private JXDatePicker datePicker = new JXDatePicker();
    
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
     * @see #setTaskNameField(String)
     * @see #setEstimatedEffort(int)
     * @see #setDescriptionText(String)
     * @see #setDueDate(Date)
     */
    public TaskView(String title, int estimatedEffort, String description, Date dueDate) {
        this.setBorder(null);
        // Set layouts for all panels
        this.setLayout(new MigLayout("", "[grow]", "[grow][min]"));
        
        this.buttonPanel.setLayout(new MigLayout("", "[][][]", "[]"));
        this.commentPanel.setLayout(new MigLayout("", "[]", "[]"));
        this.descriptionPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.detailsPanel.setLayout(new MigLayout("", "[grow]", "[][grow][grow]"));
        this.infoPanel.setLayout(new MigLayout("", "[][][grow]", "[][][][][]"));
        this.splitPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.usersPanel.setLayout(new MigLayout("", "[]", "[]"));
        
        this.add(buttonPanel, "cell 0 1,grow");
        this.add(splitPanel, "cell 0 0,grow");
        this.splitPanel.add(splitPane, "cell 0 0,grow");
        

        this.splitPane.setResizeWeight(0.5);
        this.splitPane.setRightComponent(commentPanel);
        this.splitPane.setLeftComponent(detailsPanel);

        // Format the detailsPanel layout with components
        this.detailsPanel.add(infoPanel, "cell 0 0, grow");
        this.detailsPanel.add(descriptionPanel, "cell 0 1,grow");
        this.detailsPanel.add(usersPanel, "cell 0 2,grow");

        // Format the infoPanel layout with components
        this.infoPanel.add(taskNameLabel, "cell 0 0");
        this.infoPanel.add(taskNameField, "cell 1 0 2 1, grow");
        this.taskNameField.setText(title);
        this.infoPanel.add(dateLabel, "cell 0 1");
        this.infoPanel.add(datePicker, "cell 1 1, grow");
        this.datePicker.setDate(dueDate);
        this.infoPanel.add(statusLabel, "cell 0 2");
        this.infoPanel.add(statusComboBox, "cell 1 2");
        // TODO: Integrate this ComboBox with changing tasks between BucketViews
        this.statusComboBox.setModel(new DefaultComboBoxModel(new String[] {"New", "Selected", "In Progress", "Completed"}));
        this.infoPanel.add(actualEffortLabel, "cell 0 3");
        this.infoPanel.add(actualEffortSpinner, "cell 1 3");
        this.actualEffortSpinner.setModel(new SpinnerNumberModel(0, 0, 99999, 1));
        this.infoPanel.add(estEffortLabel, "cell 0 4");
        this.infoPanel.add(estEffortSpinner, "cell 1 4");
        this.estEffortSpinner.setModel(new SpinnerNumberModel(0, 0, 99999, 1));
        this.estEffortSpinner.setValue(estimatedEffort);

        // Format the descriptionPanel layout with components
        this.descriptionPanel.add(scrollPane, "cell 0 0,grow");
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setViewportView(descriptionMessage);
        
        this.descriptionMessage.setWrapStyleWord(true);
        this.descriptionMessage.setLineWrap(true);
        this.descriptionMessage.setStartText("Description Here...");
        this.descriptionMessage.setText(description);
        this.descriptionMessage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                descriptionMessage.clicked();
            }
        });
    }

    /**
     * Add an {@link ActionListener} that will be called when the task is saved by the user
     * @param listener
     */
    public void addOnSaveListener(ActionListener listener) {
        //this.saveButton.addActionListener(listener);
    }

    /**
     * Add an {@link ActionListener} that will be called when the task is reloaded by the user
     * @param listener
     */
    public void addOnReloadListener(ActionListener listener) {
        //this.reloadButton.addActionListener(listener);
    }

    /**
     * @param titleText The new title of the task
     */
    public void setTaskNameField(String titleText) {
        this.taskNameField.setText(titleText);
    }

    /**
     * @return The title of the task
     */
    public String getTaskNameField() {
        return this.taskNameField.getText();
    }
    
    /**
     * @param actualEffort The new actual effort of the task, in arbitrary work units.
     */
    public void setActualEffort(int actualEffort) {
        this.actualEffortSpinner.setValue(actualEffort);
    }

    /**
     * @return The estimated effort of the task, in arbitrary work units.
     */
    public int getActualEffort() {
        return (int) this.actualEffortSpinner.getValue();
    }

    /**
     * @param estimatedEffort The new estimated effort of the task, in arbitrary work units.
     */
    public void setEstimatedEffort(int estimatedEffort) {
        this.estEffortSpinner.setValue(estimatedEffort);
    }

    /**
     * @return The estimated effort of the task, in arbitrary work units.
     */
    public int getEstimatedEffort() {
        return (int) this.estEffortSpinner.getValue();
    }

    /**
     * @param descriptionText The new task description
     */
    public void setDescriptionText(String descriptionText) {
        this.descriptionMessage.setText(descriptionText);
    }

    /**
     * @return The task description
     */
    public String getDescriptionText() {
        return this.descriptionMessage.getText();
    }

    /**
     * @param dueDate
     *            The new due date of the task
     */
    public void setDueDate(Date dueDate) {
        //this.datePicker.setDate(dueDate);
    }

    /**
     * @return The due date.
     */
    public Date getDueDate() {
        return this.datePicker.getDate();
    }
}

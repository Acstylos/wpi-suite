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

import java.awt.Image;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;
import net.miginfocom.swt.SwtComponentWrapper;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

/**
 * A {@link javax.swing.JComponent} that renders the fields of a single task and
 * provides the minimum amount of logic for basic user interaction.
 */
public class TaskView extends JPanel {
    private static final long serialVersionUID = -997563229078386090L;
    
    private int index;
    private ViewMode viewMode;

    private JComboBox<BucketView> statusComboBox = new JComboBox<BucketView>();
    private JLabel taskNameLabel = new JLabel("Task Name:");
    private JLabel dateLabel = new JLabel("Due Date:");
    private JLabel statusLabel = new JLabel("Status:");
    private JLabel actualEffortLabel = new JLabel("Actual Effort:");
    private JLabel estEffortLabel = new JLabel("Estimated Effort:");
    private TaskButtonsPanel buttonPanel;
    private JTabbedPane commentPanel = new CommentView();
    private JPanel descriptionPanel = new JPanel();
    private JPanel detailsPanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JPanel splitPanel = new JPanel();
    private JPanel usersPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane();
    private JSpinner actualEffortSpinner = new JSpinner();
    private JSpinner estEffortSpinner = new JSpinner();
    private JSplitPane splitPane = new JSplitPane();
    private JTextArea descriptionMessage = new JTextArea();
    private JTextField taskNameField = new JTextField();
    private JXDatePicker datePicker = new JXDatePicker();
    private LineBorder validBorder = new LineBorder(Color.GRAY, 1);
    private LineBorder invalidBorder = new LineBorder(Color.RED, 1);

    static {
        /* Change the default icons for JXDatePicker. */
        UIManager.put("JXDatePicker.arrowIcon", Icons.CALENDAR);
        UIManager.put("JXMonthView.monthDownFileName", Icons.LEFT_ARROW);
        UIManager.put("JXMonthView.monthUpFileName", Icons.RIGHT_ARROW);
    }

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
    public TaskView(String title, int estimatedEffort, String description,
            Date dueDate, ViewMode viewMode) {
        this.setBorder(null);
        // Set layouts for all panels
        this.setLayout(new MigLayout("", "[grow]", "[grow][min]"));

        this.descriptionPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.detailsPanel.setLayout(new MigLayout("", "[grow]",
                "[][grow][grow]"));
        this.infoPanel.setLayout(new MigLayout("", "[][][grow]", "[][][][][]"));
        this.splitPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.usersPanel.setLayout(new MigLayout("", "[]", "[]"));

        this.buttonPanel = new TaskButtonsPanel(viewMode);
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
        this.statusComboBox.setModel(new DefaultComboBoxModel(new String[] {
                "New", "Selected", "In Progress", "Completed" }));
        this.infoPanel.add(actualEffortLabel, "cell 0 3");
        this.infoPanel.add(actualEffortSpinner, "cell 1 3");
        this.actualEffortSpinner
                .setModel(new SpinnerNumberModel(0, 0, 99999, 1));
        this.infoPanel.add(estEffortLabel, "cell 0 4");
        this.infoPanel.add(estEffortSpinner, "cell 1 4");
        this.estEffortSpinner.setModel(new SpinnerNumberModel(0, 0, 99999, 1));
        this.estEffortSpinner.setValue(estimatedEffort);

        // Format the descriptionPanel layout with components
        this.descriptionPanel.add(scrollPane, "cell 0 0,grow");
        this.scrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setViewportView(descriptionMessage);

        this.descriptionMessage.setWrapStyleWord(true);
        this.descriptionMessage.setLineWrap(true);
        this.descriptionMessage.setText(description);
        this.viewMode = viewMode;
        
        
        DocumentListener validateListener = new DocumentListener() {
            /** {@inheritDoc} */
            @Override
            public void removeUpdate(DocumentEvent e) {
                validateFields();
            }

            /** {@inheritDoc} */
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateFields();
            }

            /** {@inheritDoc} */
            @Override
            public void changedUpdate(DocumentEvent e) {
                validateFields();
            }
        };
        
        /* Re-validate all of the input fields every time the task title 
         * or description is changed by the user.
         */
        this.taskNameField.getDocument().addDocumentListener(validateListener);
        this.descriptionMessage.getDocument().addDocumentListener(validateListener);
        this.datePicker.getEditor().getDocument().addDocumentListener(validateListener);
        
        validateFields();
    }

    /**
     * This should call something to save task to the model
     */
    public void addOkOnClickListener(ActionListener listener) {
        this.buttonPanel.addOkOnClickListener(listener);
    }

    /**
     * This should call something to refresh the view with the model
     */
    public void addCancelOnClickListener(ActionListener listener) {
        this.buttonPanel.addCancelOnClickListener(listener);
    }

    /**
     * This calls something to refresh, and closes the tab this view is open in
     */
    public void addClearOnClickListener(ActionListener listener) {
        this.buttonPanel.addClearOnClickListener(listener);
    }

    /**
     * This calls something to move the task to the archive
     */
    public void addDeleteOnClickListener(ActionListener listener) {
        this.buttonPanel.addDeleteOnClickListener(listener);
    }
    
    /**
     * @param titleText
     *            The new title of the task
     */
    public void setTaskNameField(String titleText) {
        this.taskNameField.setText(titleText);
        validateFields();
    }

    /**
     * @return The title of the task
     */
    public String getTaskNameField() {
        return this.taskNameField.getText();
    }

    /**
     * @param actualEffort
     *            The new actual effort of the task, in arbitrary work units.
     */
    public void setActualEffort(int actualEffort) {
        this.actualEffortSpinner.setValue(actualEffort);
        validateFields();
    }

    /**
     * @return The estimated effort of the task, in arbitrary work units.
     */
    public int getActualEffort() {
        return (int) this.actualEffortSpinner.getValue();
    }

    /**
     * @param estimatedEffort
     *            The new estimated effort of the task, in arbitrary work units.
     */
    public void setEstimatedEffort(int estimatedEffort) {
        this.estEffortSpinner.setValue(estimatedEffort);
        validateFields();
    }

    /**
     * @return The estimated effort of the task, in arbitrary work units.
     */
    public int getEstimatedEffort() {
        return (int) this.estEffortSpinner.getValue();
    }

    /**
     * @param descriptionText
     *            The new task description
     */
    public void setDescriptionText(String descriptionText) {
        this.descriptionMessage.setText(descriptionText);
        validateFields();
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
        this.datePicker.setDate(dueDate);
        validateFields();
    }

    /**
     * @return The due date.
     */
    public Date getDueDate() {
        return this.datePicker.getDate();
    }
    
    /**
     * @param index The index of the tab this view is in
     */
    public void setIndex(int index){
        this.index = index;
    }
    
    /**
     * @return The index of this tab
     */
    public int getIndex(){
        return this.index;
    }
    
    /**
     * @param viewMode Either Creating or editing based on what the user is doing
     */
    public void setViewMode(ViewMode viewMode){
        this.viewMode = viewMode;
        buttonPanel.validateButtons(viewMode);
    }
    
    public ViewMode getViewMode(){
        return this.viewMode;
    }
    
    /**
     * Check that all fields are valid and update the user interface to
     * provide feedback on what isn't valid.
     */
    public void validateFields() {
        JFormattedTextField dateEditor = this.datePicker.getEditor();
        
        /* The title and description both have to contain something besides
         * leading and trailing whitespace, and the due date must be a valid
         * date.
         */
        boolean isTitleInvalid = this.taskNameField.getText().trim().isEmpty();
        boolean isDescriptionInvalid = this.descriptionMessage.getText().trim().isEmpty();
        boolean isDateInvalid = dateEditor.getText().trim().isEmpty();
        
        
        /* Try to parse the text in the datefield.  If unsucessful, mark the
         * date field as invalid.
         */
        try {
            dateEditor.getFormatter().stringToValue(dateEditor.getText());
        } catch (ParseException e) {
            isDateInvalid = true;
        }
        
        final boolean isValid = !isTitleInvalid && !isDescriptionInvalid && !isDateInvalid;
        if (isValid) {
            /* If everything's valid, get rid of any error message and
             * enabled the OK button.
             */
            this.buttonPanel.clearError();
            this.buttonPanel.setOkEnabledStatus(true);
        } else {
            /* Otherwise, disable saving the task and set an error message.
             */
            this.buttonPanel.setOkEnabledStatus(false);
            this.buttonPanel.setError("The highlighted fields are required");
        }
        
        /* Set an red border on input fields that aren't valid */
        if (isTitleInvalid) {
            this.taskNameField.setBorder(invalidBorder);
        } else {
            this.taskNameField.setBorder(validBorder);
        }
        
        if (isDescriptionInvalid) {
            this.descriptionMessage.setBorder(invalidBorder);
        } else {
            this.descriptionMessage.setBorder(validBorder);
        }
        
        if (isDateInvalid) {
            this.datePicker.setBorder(invalidBorder);
        } else {
            this.datePicker.setBorder(validBorder);
        } 
    }
}

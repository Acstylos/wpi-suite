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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;

/**
 * A {@link javax.swing.JComponent} that renders the fields of a single task and
 * provides the minimum amount of logic for basic user interaction.
 */
public class TaskView extends JPanel {
    private static final long serialVersionUID = -997563229078386090L;

    
    private int index;
    private ViewMode viewMode;

    private JComboBox<Requirement> requirementComboBox = new JComboBox<Requirement>();
    private JComboBox<BucketView> statusComboBox = new JComboBox<BucketView>();
    private Color colorsOptions[] = { Color.WHITE, Color.YELLOW, Color.RED, Color.GREEN, Color.MAGENTA, Color.GRAY};
    private JComboBox<Color> colorComboBox = new JComboBox <Color>(colorsOptions);
    private JLabel taskNameLabel = new JLabel("Task Name:");
    private JLabel dateLabel = new JLabel("Due Date:");
    private JLabel actualEffortLabel = new JLabel("Actual Effort:");
    private JLabel estEffortLabel = new JLabel("Estimated Effort:");
    private JLabel requirementLabel = new JLabel("Related Requirement:");
    private final JButton requirementButton = new JButton("View Requirement");
    private JLabel changeColorLabel = new JLabel ("Category:"); 
    private TaskButtonsPanel buttonPanel;
    private JTabbedPane commentPanel = new CommentView(this.viewMode);
    private JPanel descriptionPanel = new JPanel();
    private JPanel detailsPanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JPanel splitPanel = new JPanel();
    private UserListsView usersPanel;
    private JScrollPane scrollPane = new JScrollPane();
    private JSpinner actualEffortSpinner = new JSpinner();
    private JSpinner estEffortSpinner = new JSpinner();
    private JSplitPane splitPane = new JSplitPane();
    private JXTextArea descriptionMessage = new JXTextArea("Write a description...", Color.GRAY);
    private JXTextField taskNameField = new JXTextField("Write a title...", Color.GRAY);
    private JXDatePicker datePicker = new JXDatePicker();
    private TaskPresenter presenter;
    private TaskModel model = new TaskModel();

    private final static LineBorder validBorder = new LineBorder(Color.GRAY, 1);
    private final static LineBorder invalidBorder = new LineBorder(Color.RED, 1);
    private final static Color modifiedColor = Color.BLACK;
    private final static Color unmodifiedColor = Color.GRAY;

    
    static {
        /* Change the default icons for JXDatePicker. */
        UIManager.put("JXDatePicker.arrowIcon", Icons.CALENDAR);
        UIManager.put("JXMonthView.monthDownFileName", Icons.LEFT_ARROW);
        UIManager.put("JXMonthView.monthUpFileName", Icons.RIGHT_ARROW);
    }

    /**
     * Create a new TaskView with the specified default values.
     *
     * @param model
     *            The initial data that will be displayed
     * @param viewMode
     *            The ViewMode of the task that says how the view will be displayed
     * @param presenter
     *            The TaskPresenter that is responsible for this view
     */
    public TaskView(TaskModel model, ViewMode viewMode, TaskPresenter presenter) {
        this.presenter = presenter;
        this.usersPanel = new UserListsView(presenter);
        // Set layouts for all panels
        this.setLayout(new MigLayout("", "[grow]", "[grow][min]"));

        /* Remove the default borders on the date picker and the description
         * box, so the red error outline looks consistant for all of the
         * fields.
         */
        this.datePicker.getEditor().setBorder(new EmptyBorder(1, 1, 1, 1));
        this.scrollPane.setBorder(new EmptyBorder(1, 1, 1, 1));

        this.descriptionPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.detailsPanel.setLayout(new MigLayout("", "[grow]",
                "[][20%,grow][30%]"));
        this.infoPanel.setLayout(new MigLayout("", "[][][grow]", "[][][][][][]"));  
        this.splitPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));

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
        this.infoPanel.add(dateLabel, "cell 0 1");
        this.infoPanel.add(datePicker, "cell 1 1, grow");
        requirementLabel.setForeground(unmodifiedColor);
        this.infoPanel.add(requirementLabel, "cell 0 2");
        this.infoPanel.add(requirementComboBox, "cell 1 2");
        this.requirementComboBox.setModel(new DefaultComboBoxModel(new String[] {
        "None" }));
        this.infoPanel.add(requirementButton, "cell 2 2");
        this.infoPanel.add(actualEffortLabel, "cell 0 3");
        this.infoPanel.add(actualEffortSpinner, "cell 1 3");
        this.actualEffortSpinner.setModel(new SpinnerNumberModel(0, 0, 99999, 1));

        this.infoPanel.add(estEffortLabel, "cell 0 4");
        this.infoPanel.add(estEffortSpinner, "cell 1 4");
        this.estEffortSpinner.setModel(new SpinnerNumberModel(0, 0, 99999, 1));
        this.colorComboBox.setRenderer(new ColorRenderer());
        this.colorComboBox.setSelectedIndex(0);
        this.colorComboBox.setSize(statusComboBox.getSize());
        this.infoPanel.add(changeColorLabel, "cell 0 6");
        this.infoPanel.add(colorComboBox, "cell 1 6");  
        // Format the descriptionPanel layout with components
        this.descriptionPanel.add(scrollPane, "cell 0 0,grow");
        this.scrollPane
        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setViewportView(descriptionMessage);

        this.descriptionMessage.setWrapStyleWord(true);
        this.descriptionMessage.setLineWrap(true);
        this.viewMode = viewMode;
        //if you are in create mode, then comments are disabled.
        ((CommentView) this.commentPanel).toggleTextField(this.viewMode) ;
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

        ChangeListener changeListener = (ChangeEvent) -> {
            validateFields();
        };

        ItemListener itemListener = (ItemListener) -> {
            validateFields();
        };

        /*
         * Re-validate all of the input fields every time any field is changed
         * by the user.
         */
        this.taskNameField.getDocument().addDocumentListener(validateListener);
        this.actualEffortSpinner.addChangeListener(changeListener);
        this.estEffortSpinner.addChangeListener(changeListener);
        this.datePicker.getEditor().getDocument()
        .addDocumentListener(validateListener);
        this.descriptionMessage.getDocument().addDocumentListener(
                validateListener);
        this.requirementComboBox.addItemListener(itemListener);
        this.colorComboBox.addItemListener(itemListener);   
        setModel(model);
    }

    /**
     * makes a call to save task to the model
     * @param listener listen to click
     */
    public void addOkOnClickListener(ActionListener listener) {
        this.buttonPanel.addOkOnClickListener(listener);
    }

    /**
     * makes a call to refresh the view with the model
     * @param listener listen to click
     */
    public void addCancelOnClickListener(ActionListener listener) {
        this.buttonPanel.addCancelOnClickListener(listener);
    }

    /**
     * makes a call to refresh, and closes the tab this view is open in
     * @param listener listen to click
     */
    public void addClearOnClickListener(ActionListener listener) {
        this.buttonPanel.addClearOnClickListener(listener);
    }

    /**
     * makes a call to move the task to the archive
     * @param listener listen to click
     */
    public void addDeleteOnClickListener(ActionListener listener) {
        this.buttonPanel.addDeleteOnClickListener(listener);
    }

    /**
     * makes a call to add requirement to the related requirement field
     * @param listener  The listener to be added to the ComboBox
     */
    public void addChangeRequirementListener(ActionListener listener) {
        this.requirementComboBox.addActionListener(listener);
    }

    /**
     * makes a call to open the requirement tab
     * @param listener  The listener to open the requirement tab
     */
    public void addRequirementButtonListener(ActionListener listener) {
        this.requirementButton.addActionListener(listener);
    }

    /**
     * set the requirement button to enable or disenable
     * @param enable
     *              the boolean value to set the button either enable or disenable
     */
    public void setRequirementButtonEnable(boolean enable) {
        this.requirementButton.setEnabled(enable);
    }
    
    /*
     * adds an action listener to the colorComboBox
     * @param listener The listener to be added to the ComboBox
     */
    public void addChangeColorListener(ActionListener listener) {
        this.colorComboBox.addActionListener(listener);
    }
    
    /**
     * Set all of the fields in the view from the data in the model
     * 
     * @param model
     *            the {@link TaskModel} to copy into the view
     */
    public void setModel(TaskModel model) {
        this.model = model;
        this.taskNameField.setText(model.getTitle());
        this.actualEffortSpinner.setValue(model.getActualEffort());
        this.estEffortSpinner.setValue(model.getEstimatedEffort());
        this.descriptionMessage.setText(model.getDescription());
        this.datePicker.setDate(model.getDueDate());
        if(model.getLabelColor()==null);
        else
               this.colorComboBox.setSelectedItem(model.getLabelColor());
        validateFields();
    }

    /**
     * @return The title of the task
     */
    public String getTaskNameField() {
        return this.taskNameField.getText();
    }

    /**
     * @return The estimated effort of the task, in arbitrary work units.
     */
    public int getActualEffort() {
        return (int) this.actualEffortSpinner.getValue();
    }

    /**
     * @return The estimated effort of the task, in arbitrary work units.
     */
    public int getEstimatedEffort() {
        return (int) this.estEffortSpinner.getValue();
    }

    /**
     * @return The task description
     */
    public String getDescriptionText() {
        return this.descriptionMessage.getText();
    }

    /**
     * @return The due date.
     */
    public Date getDueDate() {
        return this.datePicker.getDate();
    }

    /**
     * @param index
     *            The index of the tab this view is in
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return The index of this tab
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * @param viewMode
     *            Either Creating or editing based on what the user is doing
     */
    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
        buttonPanel.validateButtons(viewMode);
    }

    /**
     * @return The view mode of the task
     */
    public ViewMode getViewMode(){
        return this.viewMode;
    }

    /**
     * @return  Index of requirements in the requirementComboBox
     */
    public int getRequirementIndex() {
        return requirementComboBox.getSelectedIndex();
    }

    /**
     * @param requirementIndex   the Index of the requirement in the requirementComboBox
     */
    public void setRequirement(int requirementIndex) {
        requirementComboBox.setSelectedIndex(requirementIndex);
    }

    /**
     * add a requirement to the reuqirementComboBox
     * @param   req
     *              the requirement item
     */
    public void addRequirementsToComboBox(Requirement[] reqs){
        this.requirementComboBox.setModel(new DefaultComboBoxModel(new String[] {
        "None" }));
        for (int i = 0; i < reqs.length; i++) {
            requirementComboBox.addItem(reqs[i]);
        }
    }

    /**
     * Check that all fields are valid and update the user interface to provide
     * feedback on what isn't valid.
     */
    public void validateFields() {
        JFormattedTextField dateEditor = this.datePicker.getEditor();

        /*
         * The title and description both have to contain something besides
         * leading and trailing whitespace, and the due date must be a valid
         * date.
         */
        boolean isTitleInvalid = this.taskNameField.getText().trim().isEmpty();
        boolean isDescriptionInvalid = this.descriptionMessage.getText().trim()
                .isEmpty();
        boolean isDateInvalid = dateEditor.getText().trim().isEmpty();

        /*
         * Try to parse the text in the datefield. If unsucessful, mark the date
         * field as invalid.
         */
        try {
            dateEditor.getFormatter().stringToValue(dateEditor.getText());
        } catch (ParseException e) {
            isDateInvalid = true;
        }

        final boolean isValid = !isTitleInvalid && !isDescriptionInvalid
                && !isDateInvalid;

        /*
         * Set an error message if at least one field doesn't have a valid
         * value.
         */
        if (isValid) {
            this.buttonPanel.clearError();
        } else {
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

        boolean isModified = false;

        /*
         * Set the color of each label based on weather or not its value was
         * changed by the user.
         */
        if (this.getTaskNameField().equals(this.model.getTitle())) {
            this.taskNameLabel.setForeground(unmodifiedColor);
        } else {
            this.taskNameLabel.setForeground(modifiedColor);
            isModified = true;
        }

        if (this.getActualEffort() == this.model.getActualEffort()) {
            this.actualEffortLabel.setForeground(unmodifiedColor);
        } else {
            this.actualEffortLabel.setForeground(modifiedColor);
            isModified = true;
        }

        if (this.getEstimatedEffort() == this.model.getEstimatedEffort()) {
            this.estEffortLabel.setForeground(unmodifiedColor);
        } else {
            this.estEffortLabel.setForeground(modifiedColor);
            isModified = true;
        }

        if (this.getDescriptionText().equals(this.model.getDescription())) {
        } else {
            isModified = true;
        }

        if (this.presenter.getAssignedUserList().equals(this.model.getAssignedTo())) {
        } else {
            isModified = true;
        }

        if (this.getRequirementIndex() == this.model.getRequirement()) {
            this.requirementLabel.setForeground(unmodifiedColor);
        } else {
            this.requirementLabel.setForeground(modifiedColor);
            isModified = true;
        }

        if(this.model.getLabelColor()==null){
            if(this.colorComboBox.getSelectedItem().equals(new Color(255,255,255))){
                this.changeColorLabel.setForeground(unmodifiedColor);
            }
            else
                this.changeColorLabel.setForeground(modifiedColor);              
        }
        else if (this.model.getLabelColor().equals(this.colorComboBox.getSelectedItem())) {
            this.changeColorLabel.setForeground(unmodifiedColor);
        } else {
            this.changeColorLabel.setForeground(modifiedColor);
            isModified = true;
        }
        /* The date value might be null */
        boolean datesAreEqual;
        if (this.getDueDate() == null && this.model.getDueDate() == null) {
            datesAreEqual = true;
        } else if (this.getDueDate() == null || this.model.getDueDate() == null) {
            datesAreEqual = false;
        } else {
            datesAreEqual = this.getDueDate().equals(this.model.getDueDate());
        }

        if (datesAreEqual) {
            this.dateLabel.setForeground(unmodifiedColor);
        } else {
            this.dateLabel.setForeground(modifiedColor);
            isModified = true;
        }

        /*
         * Allow the user to save the task if something is modified and
         * everything is still valid.
         */
        this.buttonPanel.setOkEnabledStatus(isValid && isModified);
        /* Allow the user to reset the fields if something is modified. */
        this.buttonPanel.setClearEnabledStatus(isModified);
        /* Don't show cancel dialog if something hasn't been modified. */
        this.presenter.setAllowCancelDialogEnabled(isModified);
    }

    /**
     * @return the commentPanel of this Task View. Contains the Comment User
     *         used wants to post Casted to a commentView because cannot
     *         .getText() of a JPanel
     */
    public CommentView getCommentView() {
        return (CommentView) this.commentPanel;
    }

    /**
     * @return returns the panel that users are on
     */
    public UserListsView getUserListPanel() {
        this.validateFields();
        return this.usersPanel;
    }

    /**
     * @return the color of the current label.
     */
    public Color getLabelColor() {
        return (Color) colorComboBox.getSelectedItem();
    }

    /**
     * disable editing of task fields within taskView
     */
    public void disableEdits() {
        buttonPanel.setOkEnabledStatus(true);
        this.taskNameField.setEditable(false);
        this.descriptionMessage.setEditable(false);
        this.actualEffortSpinner.setEnabled(false);
        this.estEffortSpinner.setEnabled(false);
        this.datePicker.setEditable(false);
        this.getCommentView().getCommentText().setEditable(false);
        this.taskNameField.setEditable(false);
       
        
        for (Component button : this.getUserListPanel().getAssignedUserListPanel().getComponents()){
            System.out.println("assingedButton" + button);
           button.setEnabled(false);   
        }
        for (Component button : this.getUserListPanel().getUnassignedUserListPanel().getComponents()){
            System.out.println("unassignedButton" + button);
            button.setEnabled(false);   
         }
        
    }
    
    /**
     * re-enable editing of task fields within a taskView
     */
    public void enableEdits() {
        this.taskNameField.setEditable(true);
        this.descriptionMessage.setEditable(true);
        this.actualEffortSpinner.setEnabled(true);
        this.estEffortSpinner.setEnabled(true);
        this.datePicker.setEditable(true);
        this.getCommentView().getCommentText().setEditable(true);
        this.taskNameField.setEditable(true);
        
        for (Component button : this.getUserListPanel().getAssignedUserListPanel().getComponents()){
            button.setEnabled(true);   
         }
         for (Component button : this.getUserListPanel().getUnassignedUserListPanel().getComponents()){
             button.setEnabled(true);   
          }
    }
}

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Color;

/**
 * This Panel is placed at the bottom of the TaskView. The buttons become active
 * based on TaskView's requirements and the buttons change titles based on if
 * you are creating the task or editing the task.
 */
public class TaskButtonsPanel extends JPanel {

    private static final long serialVersionUID = -3971494855765228847L;
    private final JButton okButton = new JButton();
    private final JButton cancelButton = new JButton("Close");
    private final JButton clearButton = new JButton();
    private final JButton deleteButton = new JButton("Delete");
    private String okString;
    private String clearString;
    private String deleteString;// going to use this later for actual cancel,
                                // instead of close
    private String cancelString; // going to use this later for actual cancel,
                                 // instead of close
    private final JLabel errorLabel = new JLabel();

    /**
     * Create the panel.
     * 
     * @param viewMode
     *            the currently enabled view
     */
    public TaskButtonsPanel(ViewMode viewMode) {
        this.setLayout(new MigLayout("",
                "[0%,min][0%,min][0%,min][0%,min][0%,grow]", "[]"));
        this.add(okButton, "cell 0 0");
        this.add(clearButton, "cell 1 0");
        this.add(cancelButton, "cell 2 0");
        this.add(deleteButton, "cell 3 0");
        this.add(errorLabel, "cell 4 0");
        validateButtons(viewMode);
        errorLabel.setIcon(new ImageIcon(TaskButtonsPanel.class
                .getResource("error.png")));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        okButton.setIcon(Icons.OK);
        clearButton.setIcon(Icons.CLEAR);
        cancelButton.setIcon(Icons.CANCEL);
        deleteButton.setIcon(Icons.TRASH);
    }

    /**
     * Assign a viewMode and ensure that all buttons are labeled correctly
     * 
     * @param viewMode
     *            the currently enabled viewMode
     */
    public void validateButtons(ViewMode viewMode) {
        if (viewMode == ViewMode.CREATING) {
            okString = "Create";
            clearString = "Clear";
            cancelString = "Cancel";
            deleteString = "";
            okButton.setVisible(true);
            clearButton.setVisible(true);
            cancelButton.setVisible(true);
            deleteButton.setVisible(false);
        } else if (viewMode == ViewMode.EDITING) {
            okString = "Update";
            clearString = "Undo Changes";
            cancelString = "Close";
            deleteString = "Archive";
            okButton.setVisible(true);
            clearButton.setVisible(true);
            cancelButton.setVisible(true);
            deleteButton.setVisible(false);
        } else if (viewMode == ViewMode.ARCHIVING) {
            okString = "Update";
            clearString = "Undo Changes";
            cancelString = "Close";
            deleteString = "Delete";
            okButton.setVisible(true);
            clearButton.setVisible(true);
            cancelButton.setVisible(true);
            deleteButton.setVisible(true);
        }
        okButton.setText(okString);
        clearButton.setText(clearString);
        cancelButton.setText(cancelString);
        deleteButton.setText(deleteString);
    }

    /**
     * Add the listener for the ok Button
     * 
     * @param listener
     *            The action that calls the listener
     */
    public void addOkOnClickListener(ActionListener listener) {
        okButton.addActionListener(listener);
    }

    /**
     * Add the listener for the cancel Button
     * 
     * @param listener
     *            The action that calls the listener
     */
    public void addCancelOnClickListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    /**
     * Add the listener for the clear button
     * 
     * @param listener
     *            The action that calls the listener
     */
    public void addClearOnClickListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    /**
     * Add the listener for the delete button
     * 
     * @param listener
     *            The action that calls the listener
     */
    public void addDeleteOnClickListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    /**
     * Set an error to display next to the buttons, to provide feedback about
     * why one or more of them is disabled.
     * 
     * @param error
     *            String representing error
     */
    public void setError(String error) {
        errorLabel.setText(error);
        errorLabel.setVisible(true);
        errorLabel.repaint();
    }

    /**
     * Hide the error label.
     * 
     * @see #setError(String)
     */
    public void clearError() {
        errorLabel.setVisible(false);
        errorLabel.repaint();
    }

    /**
     * sets the okButton to be enabled or disabled based on status.
     * 
     * @param status
     *            True if the button is active, False otherwise
     */
    public void setOkEnabledStatus(boolean status) {
        okButton.setEnabled(status);
    }

    /**
     * sets the clearButton to be enabled or disabled based on status.
     * 
     * @param status
     *            True if the button is active, False otherwise
     */
    public void setClearEnabledStatus(boolean status) {
        clearButton.setEnabled(status);
    }

    /**
     * sets the cancelButton to be enabled or disabled based on status.
     * 
     * @param status
     *            True if the button is active, False otherwise
     */
    public void setCancelEnabledStatus(boolean status) {
        cancelButton.setEnabled(status);
    }

    /**
     * sets the deleteButton to be enabled or disabled based on status.
     * 
     * @param status
     *            True if the button is active, False otherwise
     */
    public void setDeleteEnabledStatus(boolean status) {
        deleteButton.setEnabled(status);
    }

}

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ViewMode;
import net.miginfocom.swing.MigLayout;

/**
 * This Panel is placed at the bottom of the TaskView.
 * The buttons become active based on TaskView's requirements
 * and the buttons change titles based on if you are 
 * creating the task or editing the task.
 */
public class TaskButtonsPanel extends JPanel {

    private static final long serialVersionUID = -3971494855765228847L;
    private final JButton okButton = new JButton();
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton clearButton = new JButton();
    private final JButton deleteButton = new JButton("Delete");
    private final String okString;
    private final String clearString;

    private TaskView parentPanel;

    /**
     * Create the panel.
     */
    public TaskButtonsPanel(TaskView parentPanel, ViewMode viewMode) {
        this.setLayout(new MigLayout("", "[][][]", "[]"));
        this.parentPanel = parentPanel;
        this.add(okButton);
        this.add(clearButton);
        if (viewMode == ViewMode.CREATING) {
            okString = "Create";
            clearString = "Clear";
        } else {
            okString = "Update";
            clearString = "Undo Changes";
            this.add(deleteButton);
        }
        this.add(cancelButton);
        this.okButton.setText(okString);
        this.clearButton.setText(clearString);
        okButton.setEnabled(false);
        clearButton.setEnabled(false);
        this.setupListeners();
    }

    /**
     * Sets up the listeners on this buttons in this panel 
     */
    private void setupListeners() {
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentPanel.saveTask();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.clearTask();
            }

        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentPanel.cancelTask();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentPanel.deleteTask();
            }
        });
    }

    /**
     * sets the okButton to be enabled or disabled based on status.
     * @param status True if the button is active, False otherwise
     */
    public void setOkEnabledStatus(boolean status) {
        this.okButton.setEnabled(status);
    }

    /**
     * sets the clearButton to be enabled or disabled based on status.
     * @param status True if the button is active, False otherwise
     */
    public void setClearEnabledStatus(boolean status) {
        this.clearButton.setEnabled(status);
    }

    /**
     * sets the cancelButton to be enabled or disabled based on status.
     * @param status True if the button is active, False otherwise
     */
    public void setCancelEnabledStatus(boolean status) {
        this.cancelButton.setEnabled(status);
    }

    /**
     * sets the deleteButton to be enabled or disabled based on status.
     * @param status True if the button is active, False otherwise
     */
    public void setDeleteEnabledStatus(boolean status) {
        this.deleteButton.setEnabled(status);
    }

}

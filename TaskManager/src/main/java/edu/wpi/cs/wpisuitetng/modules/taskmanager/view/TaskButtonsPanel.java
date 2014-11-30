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

import javax.swing.JButton;
import javax.swing.JPanel;

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
    private final JButton cancelButton = new JButton("Close");
    private final JButton clearButton = new JButton();
    private final JButton deleteButton = new JButton("Archive");
    private String okString;
    private String clearString;
    private String cancelString; 
    private String deleteString;// going to use this later for actual cancel, instead of close


    /**
     * Create the panel.
     */
    public TaskButtonsPanel(ViewMode viewMode) {
        validateButtons(viewMode);
    }
    
    public void validateButtons(ViewMode viewMode){
        this.setLayout(new MigLayout("", "[][][][]", "[]"));
        this.add(okButton);
        if(viewMode != ViewMode.ARCHIVING)
            this.add(clearButton);
        if(viewMode != ViewMode.ARCHIVING)
            this.add(cancelButton);
        if(viewMode != ViewMode.CREATING)
            this.add(deleteButton);
        if (viewMode == ViewMode.CREATING) {
            okString = "Create";
            clearString = "Clear";
        } else if (viewMode == ViewMode.EDITING){
            okString = "Update";
            clearString = "Undo Changes";
            deleteString = "Archive";
        } else if (viewMode == ViewMode.ARCHIVING){
            okString = "Restore";
            deleteString = "Delete";
        }
        this.okButton.setText(okString);
        this.clearButton.setText(clearString);
        this.deleteButton.setText(deleteString);
    }
    
    public void addOkOnClickListener(ActionListener listener){
        this.okButton.addActionListener(listener);
    }
    
    public void addCancelOnClickListener(ActionListener listener){
        this.cancelButton.addActionListener(listener);
    }
    
    public void addClearOnClickListener(ActionListener listener){
        this.clearButton.addActionListener(listener);
    }
    
    public void addDeleteOnClickListener(ActionListener listener){
        this.deleteButton.addActionListener(listener);
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

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
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;

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
    private String cancelString; // going to use this later for actual cancel, instead of close
    private final JLabel errorLabel = new JLabel();


    /**
     * Create the panel.
     */
    public TaskButtonsPanel(ViewMode viewMode) {
        this.setLayout(new MigLayout("", "[][][][]", "[]"));
        this.add(okButton, "cell 0 0");
        this.add(clearButton, "cell 1 0");
        this.add(cancelButton, "cell 2 0");
        errorLabel.setIcon(new ImageIcon(TaskButtonsPanel.class.getResource("error.png")));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        
        add(errorLabel, "cell 3 0");
        validateButtons(viewMode);
    }
    
    public void validateButtons(ViewMode viewMode){
        if (viewMode == ViewMode.CREATING) {
            okString = "Create";
            clearString = "Clear";
            //this.remove(deleteButton);
        } else {
            okString = "Update";
            clearString = "Undo Changes";
            //this.add(deleteButton);
        }
        this.okButton.setText(okString);
        this.clearButton.setText(clearString);
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
     * Set an error to display next to the buttons, to provide feedback about
     * why one or more of them is disabled.
     */
    public void setError(String error) {
        this.errorLabel.setText(error);
        this.errorLabel.setVisible(true);
        this.errorLabel.repaint();
    }
    
    /**
     * Hide the error label.
     * @see #setError(String)
     */
    public void clearError() {
        this.errorLabel.setVisible(false);
        this.errorLabel.repaint();
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

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
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
    private String cancelString; // going to use this later for actual cancel, instead of close


    /**
     * Create the panel.
     */
    public TaskButtonsPanel(ViewMode viewMode) {
        this.setLayout(new MigLayout("", "[][][]", "[]"));
        this.add(okButton);
        this.add(clearButton);
        this.add(cancelButton);
        validateButtons(viewMode);
        
        try {
            this.okButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("okay.png"))));
            this.clearButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("clear.png"))));
            this.cancelButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("cancel.png"))));
            this.deleteButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("trash.png"))));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
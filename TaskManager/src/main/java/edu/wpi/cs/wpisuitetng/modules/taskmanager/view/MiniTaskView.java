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

import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.border.EmptyBorder;

/**
 * This is the TaskView shown inside of buckets to reduce the amount of clutter on screen
 */
public class MiniTaskView extends JPanel {
    
    private Date dueDate;
    private String taskName;
    private String fullName;
    JLabel taskNameLabel = new JLabel();

    /**
     * @return Label of taskName
     */
    public JLabel getTaskNameLabel() {
        return taskNameLabel;
    }

    /**
     * Create the panel.
     */
    public MiniTaskView(String taskName, Date dueDate, String fullName) {
        setLayout(new MigLayout("fill"));
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.fullName = fullName;
        taskNameLabel.setToolTipText(this.fullName);
        taskNameLabel.setBorder(new EmptyBorder(8, 8, 8, 8));
        this.add(taskNameLabel, "dock west");
        this.taskNameLabel.setText(fullName);
        this.taskNameLabel.setIcon(Icons.TASK);
    }
    
    /**
     * Add the listener for changing tabs
     * @param listener  the event that will trigger the action
     */     
    public void addOnClickOpenTabView(MouseListener listener){
        this.addMouseListener(listener);
        this.taskNameLabel.addMouseListener(listener);
    }
    
    /**
     * @return the dueDate of the task
     */
    public Date getDueDate() {
        return this.dueDate;
    }

    /**
     * @return the task name of the task
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * @param dueDate the dueDate of the task to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    /**
     * @param taskName the title to set
     */
    public void setTaskName(String taskName, String fullName) {
        this.taskName = taskName;
        this.taskNameLabel.setText(fullName);
        this.taskNameLabel.setToolTipText(fullName);
    }
    
    /**
     * @return fullName of task
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the task name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}

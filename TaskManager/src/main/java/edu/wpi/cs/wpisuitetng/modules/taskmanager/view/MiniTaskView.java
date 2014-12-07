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

import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import net.miginfocom.swing.MigLayout;

import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;

/**
 * This is the TaskView shown inside of buckets to reduce the amount of clutter on screen
 */
public class MiniTaskView extends JPanel {
    
    private TaskModel model;
    
    JLabel taskNameLabel = new JLabel();

    /**
     * @return Label of taskName
     */
    public JLabel getTaskNameLabel() {
        return taskNameLabel;
    }

    /**
     * Create the panel.
     * @param model The model to render in this view
     */
    public MiniTaskView(TaskModel model) {
        setLayout(new MigLayout("fill"));
        taskNameLabel.setBorder(new EmptyBorder(8, 8, 8, 8));
        this.add(taskNameLabel, "dock west");
        this.taskNameLabel.setIcon(Icons.TASK);
        
        this.setModel(model);

        /* Initialize a drag when the user clicks on the MiniTaskView */
        MouseAdapter dragAdapter = new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                TransferHandler handler = getTransferHandler();
                handler.exportAsDrag(MiniTaskView.this, e, TransferHandler.MOVE);
            }
        };
        
        this.addMouseMotionListener(dragAdapter);
        this.taskNameLabel.addMouseMotionListener(dragAdapter);
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
     * @param model The model to render in this view
     */
    public void setModel(TaskModel model) {
        this.model = model;

        this.taskNameLabel.setText(this.model.getTitle());
        this.taskNameLabel.setToolTipText(this.model.getTitle());
    }

    /**
     * @return The model that this view renders
     */
    public TaskModel getModel() {
        return this.model;
    }
}

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
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import net.miginfocom.swing.MigLayout;

import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;

/**
 * This is the TaskView shown inside of buckets to reduce the amount of clutter
 * on screen
 */
public class MiniTaskView extends JPanel {

    private TaskModel model;
    private JPanel colorLabel;

    JLabel taskNameLabel = new JLabel();

    /**
     * @return Label of taskName
     */
    public JLabel getTaskNameLabel() {
        return taskNameLabel;
    }

    /**
     * Create the panel.
     * 
     * @param model
     *            The model to render in this view
     */
    public MiniTaskView(TaskModel model) {
        setLayout(new MigLayout("", "[grow][30px]", "[grow]"));
        taskNameLabel.setBorder(new EmptyBorder(8, 8, 8, 8));
        this.add(taskNameLabel, "cell 0 0");
        this.taskNameLabel.setIcon(Icons.TASK);

        this.setModel(model);

        this.setTransferHandler(new TransferHandler("model"));

        JPanel holderPanel = new JPanel();
        holderPanel.setBorder(null);
        add(holderPanel, "cell 1 0,grow");
        holderPanel.setLayout(new MigLayout("fill"));

        colorLabel = new JPanel();
        holderPanel.add(colorLabel, "dock north");
        colorLabel.setMaximumSize(new Dimension(30, 10));

    }

    /**
     * Add the listener for changing tabs
     * 
     * @param listener
     *            the event that will trigger the action
     */
    public void addOnClickOpenTabView(MouseListener listener) {
        this.addMouseListener(listener);
        this.taskNameLabel.addMouseListener(listener);
    }

    public void updateLabel() {
        if (model.getLabelColor() != null) {
            if (!model.getLabelColor().equals(new Color(255, 255, 255)))
                colorLabel.setBackground(model.getLabelColor());
            else
                colorLabel.setBackground(null);
        }
    }

    /**
     * @param model
     *            The model to render in this view
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
    /**
     * @return the panel to be filled with color
     */
    public JPanel getColorLabel() {
        return colorLabel;
    }
}

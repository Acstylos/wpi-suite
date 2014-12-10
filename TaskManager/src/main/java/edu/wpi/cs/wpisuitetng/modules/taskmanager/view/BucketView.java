/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import net.miginfocom.swing.MigLayout;

/**
 * BucketView is the view that displays a list of tasks. These tasks are
 * sorted by the title that is on the bucket. The default titles for the
 * 4 default buckets "New", "Scheduled", "In Progress", and "Completed".
 * @author Thefloorisjava
 *
 */
public class BucketView extends JPanel
{

    private static final long serialVersionUID = -5937582878085666950L;
    private List<MiniTaskView> taskViews = new ArrayList<MiniTaskView>();
    private BucketModel model;
    private JLabel titleLabel = new JLabel();
    private JPanel titlePanel = new JPanel();
    private JPanel taskViewHolderPanel = new JPanel();
    private JScrollPane taskScrollPane = new JScrollPane();

    /**
     * Constructor for BucketViews.
     * @param title Temporary constructor that will title the buckets
     */
    public BucketView(BucketModel model) {
        // Ensure the layout and properties of this panel is correct
        this.setMaximumSize(new Dimension(300, 32767));
        this.setPreferredSize(new Dimension(300, 200));
        this.setMinimumSize(new Dimension(300, 200));
        this.setBackground(Color.LIGHT_GRAY);
        this.setBorder(new EmptyBorder(0, 5, 5, 5));
        this.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        // Start by adding the changeable title to the top of the view
        this.add(titlePanel, "dock north");
        this.titlePanel.setBackground(Color.LIGHT_GRAY);
        this.titlePanel.setBorder(null);
        this.titlePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.titlePanel.add(titleLabel, "cell 0 0, alignx center, aligny center");
        taskScrollPane.setBorder(null);


        // Need a scroll pane to allow us to scroll through all tasks in the bucketView. 
        this.add(taskScrollPane, "dock north");
        taskViewHolderPanel.setBorder(null);
        this.taskViewHolderPanel.setBackground(Color.LIGHT_GRAY);
        this.taskViewHolderPanel.setLayout(new MigLayout("fill"));
        this.taskScrollPane.setViewportView(taskViewHolderPanel);   
        
        this.setModel(model);
    }

    /**
     * @return Returns a list of TaskViews
     */
    public List<MiniTaskView> getTaskViews() {
        return this.taskViews;
    }

    /**
     * @param model The model of the bucket to render in this view
     */
    public void setModel(BucketModel model) {
        this.model = model;
        
        this.titleLabel.setText(this.model.getTitle());
    }

    /**
     * Adds a single MiniTaskView to the bucket, with spacers
     * @param task The MiniTaskView to be added to the bucket
     */
    public void addTaskToView(MiniTaskView task){
        this.taskViews.add(task);
        this.taskViewHolderPanel.add(task, "dock north");
        Component spacerStrut = Box.createVerticalStrut(5);
        this.taskViewHolderPanel.add(spacerStrut, "north");
        
        Dimension maxView = new Dimension((int) this.getPreferredSize().getWidth()-32, (int) this.getMaximumSize().getHeight());
        task.setMaximumSize(maxView);//prevent horizontal scroll
        task.getTaskNameLabel().setMaximumSize(maxView);
    }
}

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
import java.awt.Component;
import java.awt.Point;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;

/**
 * This is the TaskView shown inside of buckets to reduce the amount of clutter
 * on screen
 */
public class MiniTaskView extends JPanel {

    private JPanel colorPanel = new JPanel();
    private static final long serialVersionUID = -5428820718299212324L;
    private JLabel taskNameLabel = new JLabel();
    private JButton editButton = new JButton("Edit");
    private JButton archiveButton = new JButton("");
    private JPanel userPanel = new JPanel();
    private JLabel dueDateLabel = new JLabel();
    private JPanel holderPanel = new JPanel();
    private boolean expanded = false;
    private final JScrollPane userScrollPane = new JScrollPane();
    private TaskModel model;

    /**
     * Create the panel. Initially in collapsed view.
     * @param model The model to render in this view
     */
    public MiniTaskView(TaskModel model) {
        setLayout(new MigLayout("", "[grow][30px]", "[grow]"));
        this.holderPanel.setLayout(new MigLayout("fill"));
        this.holderPanel.setMinimumSize(new Dimension(50,10));
        this.colorPanel.setMinimumSize(new Dimension(10,15));
        this.holderPanel.add(colorPanel, "dock north");
        taskNameLabel.setBorder(new EmptyBorder(8, 8, 8, 8));
        this.taskNameLabel.setIcon(Icons.TASKNEW);
        this.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1), new EmptyBorder(0, 8, 0, 8)));
        this.setCollapsedView();
        this.setModel(model);

        /* Initialize a drag when the user clicks on the MiniTaskView */
        MouseAdapter dragAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (MiniTaskView.this.model.getIsArchived()) {
                    /* Archived tasks cannot be dragged */
                    return;
                }
                
                TransferHandler handler = getTransferHandler();
                handler.exportAsDrag(MiniTaskView.this, e, TransferHandler.MOVE);
                
                /* Set a ghost version of this view to show under the cursor as
                 * it gets dragged.
                 */
                GhostGlassPane glassPane = (GhostGlassPane) getRootPane().getGlassPane();
                glassPane.setGhostComponent(MiniTaskView.this, e.getPoint());
                glassPane.setVisible(true);
                
                /* Highlight the MiniTaskView to show which task is being dragged */
                setColorHighlighted(true);
            }
        };
        
        DragSource.getDefaultDragSource().addDragSourceMotionListener(new DragSourceAdapter() {
            @Override
            public void dragMouseMoved(DragSourceDragEvent dsde) {
                /* Move the ghost image when the mouse is moved during a drag */
                GhostGlassPane glassPane = MainView.getInstance().getGlassPane();
                Point point = dsde.getLocation();
                SwingUtilities.convertPointFromScreen(point, glassPane);
                glassPane.setPoint(point);
                glassPane.repaint();
            }
        });
        this.addMouseMotionListener(dragAdapter);
        this.taskNameLabel.addMouseMotionListener(dragAdapter);

        this.userPanel.setLayout(new MigLayout("fill"));
        this.userScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.userScrollPane.setBorder(new TitledBorder(null, "Assigned Users", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        this.userScrollPane.setViewportView(userPanel);
        this.setColorHighlighted(false);
    }

    /**
     * @param If <code>true</code>, this view will be rendered with a different
     * color scheme to suggest that it's selected.  This is used to indicate
     * that a task is being dragged and dropped.
     */
    public void setColorHighlighted(boolean highlighted) {
        Color foreground, background;
        
        if (highlighted) {
            background = UIManager.getColor("textHighlight");
        } else {
            background = UIManager.getColor("menu");
        }
        
        this.setBackground(background);
        this.holderPanel.setBackground(background);
        this.userPanel.setBackground(background);     
        this.userScrollPane.setBackground(background);
    }

    /**
     * @param archived If <code>true</code>, set the colors of the view to
     * reflect that the task is archived
     */
    public void setColorArchived(boolean archived) {
        Color foreground, background;
        
        if (archived) {
            background = new Color(210, 210, 210);
        } else {
            background = UIManager.getColor("menu");
        }
        
        this.setBackground(background);
        this.holderPanel.setBackground(background);
        this.userPanel.setBackground(background);     
        this.userScrollPane.setBackground(background);
    }

    /**
     * Remove all of the components in the view, then add them back in
     * with the proper layout for a collapsed view.
     */
    public void setCollapsedView(){
        this.removeAll();
        this.setLayout(new MigLayout("", "0[grow][]", "-1[grow]"));
        this.add(taskNameLabel, "cell 0 0,grow");
        this.add(holderPanel, "cell 1 0,grow");
        this.holderPanel.setBorder(null);
        this.expanded = false;
        this.revalidate();
        this.repaint();
    }


    /**
     * Remove all of the components in the view, then add them back in
     * with the proper layout for an expanded view.
     */
    public void setExpandedView(){
        this.removeAll();

        this.setLayout(new MigLayout("", "0[grow][grow][]", "-1[][][][]"));
        this.add(taskNameLabel, "cell 0 0 2 1, grow");
        this.add(dueDateLabel, "cell 0 1");
        this.add(userScrollPane, "cell 0 2 3 1,grow");
        this.add(holderPanel, "cell 2 0,grow");
        this.holderPanel.setBorder(null);
        this.expanded = true;
        this.add(editButton, "flowx,cell 0 3,alignx left,aligny bottom");
        if (this.model.getIsArchived()){
            archiveButton.setText("Restore");
        }
        else archiveButton.setText("Archive");
        this.add(archiveButton, "cell 0 3,alignx left,aligny bottom");
        this.revalidate();
        this.repaint();
    }

    /**
     * Adds all users in the UserList to the panel as text. 
     * @param userList List of user names to add to the panel.
     */
    public void addUsersToUserPanel(List<String> userList){
        this.userPanel.removeAll();
        for(String name: userList){
            JLabel userLabel = new JLabel(name);
            this.userPanel.add(userLabel, "dock north");
        }
    }

    /**
     * Add the listener for changing tabs
     * @param listener  the event that will trigger the action
     */     
    public void addOnClickOpenExpandedView(MouseListener listener){
        this.addMouseListener(listener);
        this.taskNameLabel.addMouseListener(listener);
        this.userPanel.addMouseListener(listener);
        this.userScrollPane.addMouseListener(listener);
    }

    /**
     * Adds the listener for opening the editing tab of the task.
     * @param listener The listener that reacts on button click.
     */
    public void addOnClickEditButton(ActionListener listener){
        this.editButton.addActionListener(listener);
    }

    /**
     * Adds the listener for archiving a task 
     * @param listener the listener that reacts on button click
     */
    public void addOnClickArchiveButton(ActionListener listener){
        this.archiveButton.addActionListener(listener);
    }
    /**
     * updates this miniTaskView's color label with the color from this MiniTaskView's Model.
     * paints null if the user selected no label.
     */
    public void updateLabel() {
        if (model.getLabelColor() != null) {
            if (!model.getLabelColor().equals(new Color(255, 255, 255)))
                colorPanel.setBackground(model.getLabelColor());
            else
                colorPanel.setBackground(null);
        }
    }

    /**
     * @param model
     *            The model to render in this view
     */
    public void setModel(TaskModel model) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");
        this.model = model;

        if (this.model.getIsArchived()) {
            this.taskNameLabel.setText(this.model.getTitle() + " (archived)");
        } else {
            this.taskNameLabel.setText(this.model.getTitle());
        }
        this.taskNameLabel.setToolTipText(this.model.getTitle());
        
        if(this.model.getDueDate() != null){
            this.dueDateLabel.setText("Due : " + dateFormat.format(this.model.getDueDate()));
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * @return If this task is expanded or not.
     */
    public boolean isExpanded(){
        return expanded;
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
        return colorPanel;
    }

    /**
     * Set Icon for this miniTaskView
     * @param icon
     */
    public void setTaskNameLabelIcon(Icon icon) {
    	this.taskNameLabel.setIcon(icon);
    }

    /*
     * @return the label containing the task name within this view
     */
    public JLabel getTaskNameLabel() {
        return this.taskNameLabel;
    }
}

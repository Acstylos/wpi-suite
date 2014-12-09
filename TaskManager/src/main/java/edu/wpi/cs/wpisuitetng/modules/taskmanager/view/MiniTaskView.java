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
import java.awt.Point;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import javax.swing.border.EmptyBorder;

import org.jfree.chart.block.LineBorder;

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
        this.setBorder(new javax.swing.border.LineBorder(Color.GRAY, 1));
        
        this.setModel(model);

        /* Initialize a drag when the user clicks on the MiniTaskView */
        MouseAdapter dragAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                TransferHandler handler = getTransferHandler();
                handler.exportAsDrag(MiniTaskView.this, e, TransferHandler.MOVE);
                
                /* Set a ghost version of this view to show under the cursor as
                 * it gets dragged.
                 */
                GhostGlassPane glassPane = (GhostGlassPane) getRootPane().getGlassPane();
                glassPane.setGhostComponent(MiniTaskView.this, e.getPoint());
                glassPane.setVisible(true);
                
                /* Highlight the MiniTaskView to show which task is being dragged */
                setHighlighted(true);
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

        this.setHighlighted(false);
    }

    /**
     * @param If <code>true</code>, this view will be rendered with a different
     * color scheme to suggest that it's selected.  This is used to indicate
     * that a task is being dragged and dropped.
     */
    public void setHighlighted(boolean highlighted) {
        if (highlighted) {
            this.setBackground(UIManager.getColor("control").darker());
        } else {
            this.setBackground(UIManager.getColor("control"));
        }
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

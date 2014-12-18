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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTextField;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;

/**
 * BucketView is the view that displays a list of tasks. These tasks are sorted
 * by the title that is on the bucket. The default titles for the 4 default
 * buckets "New", "Scheduled", "In Progress", and "Completed".
 * 
 * @author Thefloorisjava
 *
 */
public class BucketView extends JPanel {

    private static final long serialVersionUID = -5937582878085666950L;
    private List<MiniTaskView> taskViews = new ArrayList<MiniTaskView>();
    private BucketModel model;
    private JLabel titleLabel = new JLabel();
    private JPanel titlePanel = new JPanel();
    private JPanel taskViewHolderPanel = new JPanel();
    private JScrollPane taskScrollPane = new JScrollPane();
    private final JXTextField changeTitleField = new JXTextField("", Color.GRAY);
    private final JButton okButton = new JButton(Icons.OK);
    private final static LineBorder validBorder = new LineBorder(Color.GRAY, 1);
    private final static LineBorder invalidBorder = new LineBorder(Color.RED, 1);
    private final JButton cancelButton = new JButton(Icons.CANCEL);

    /**
     * Constructor for BucketViews.
     * 
     * @param title
     *            Temporary constructor that will title the buckets
     */
    public BucketView(BucketModel model) {
        // Ensure the layout and properties of this panel is correct
        this.setMaximumSize(new Dimension(300, 32767));
        this.setPreferredSize(new Dimension(300, 200));
        this.setMinimumSize(new Dimension(300, 200));
        this.setBackground(Color.LIGHT_GRAY);
        this.setBorder(new EmptyBorder(0, 5, 5, 5));
        this.setLayout(new MigLayout("fill"));
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        // Start by adding the changeable title to the top of the view
        this.add(titlePanel, "dock north");
        titlePanel.setBackground(Color.LIGHT_GRAY);
        titlePanel.setBorder(null);
        titlePanel.setMinimumSize(new Dimension(10, 40));
        this.setStaticTitlePanel();
        taskScrollPane.setBorder(null);

        // Need a scroll pane to allow us to scroll through all tasks in the
        // bucketView.
        this.add(taskScrollPane, "dock north");
        taskViewHolderPanel.setBorder(null);
        taskViewHolderPanel.setBackground(Color.LIGHT_GRAY);
        taskViewHolderPanel.setLayout(new MigLayout("fill"));
        taskScrollPane.setViewportView(taskViewHolderPanel);

        this.setModel(model);

        MouseAdapter dragAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                TransferHandler handler = getTransferHandler();
                handler.exportAsDrag(BucketView.this, e, TransferHandler.MOVE);
            }
        };

        this.addMouseMotionListener(dragAdapter);
    }

    /**
     * @return Returns a list of TaskViews
     */
    public List<MiniTaskView> getTaskViews() {
        return taskViews;
    }

    /**
     * @param model
     *            The model of the bucket to render in this view
     */
    public void setModel(BucketModel model) {
        this.model = model;
        titleLabel.setText(this.model.getTitle());
    }

    /**
     * Adds a single MiniTaskView to the bucket, with spacers
     * 
     * @param task
     *            The MiniTaskView to be added to the bucket
     */
    public void addTaskToView(MiniTaskView task) {
        taskViews.add(task);
        taskViewHolderPanel.add(task, "dock north");
        Dimension maxView = new Dimension((int) this.getPreferredSize()
                .getWidth() - 32, (int) this.getMaximumSize().getHeight());
        task.setMaximumSize(maxView);// prevent horizontal scroll
        task.getTaskNameLabel().setMaximumSize(maxView);
    }

    /**
     * Finds the index of where the task should be dropped in the bucket
     * 
     * @param point
     *            the location of the dragged task
     * @param flag
     *            true or false if the task was dragged from within the bucket
     *            or different bucket
     * @return the index relating to its position in the bucket
     */
    public int getInsertionIndex(Point point, boolean flag) {
        for (MiniTaskView miniTaskView : taskViews) {
            Point center = new Point(0, miniTaskView.getY()
                    + miniTaskView.getHeight() / 2);
            center = SwingUtilities.convertPoint(taskViewHolderPanel, center,
                    this);

            if (point.y < center.y) {
                return model.getTaskIds().indexOf(
                        miniTaskView.getModel().getId());
            }
        }

        if (taskViews.size() == 0) {
            return taskViews.size();
        } else if (flag) {
            return taskViews.size() - 1;
        } else {
            return taskViews.size();
        }
    }

    /**
     * resets the task list by removing all from view
     */
    public void resetTaskList() {
        taskViewHolderPanel.removeAll();
        taskViews.clear();
    }

    /**
     * Removes miniTaskView from BucketView
     * 
     * @param miniTaskView
     *            miniTaskView to be removed
     */
    public void removeTaskView(MiniTaskView miniTaskView) {
        taskViews.remove(miniTaskView);
    }

    /**
     * Adds a listener to change the text on the Buckets title field.
     * 
     * @param listener
     *            The mouse listener that provides the effect for the label.
     */
    public void addChangeBucketNameListener(MouseListener listener) {
        titleLabel.addMouseListener(listener);
    }

    /**
     * Adds a listener to the OK button. Should set the view back to static
     * view.
     * 
     * @param listener
     *            ActionListener that will determine how the button acts.
     */
    public void addOkButtonListener(ActionListener listener) {
        okButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the Cancel button. Should set the view back to static
     * view, without changing the title.
     * 
     * @param listener
     *            ActionListener that will determine how the button acts.
     */
    public void addCancelButtonListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    /**
     * Sets the layout of the title panel to allow for editing the label name.
     */
    public void setChangeTitlePanel() {
        titlePanel.removeAll();

        titlePanel.setLayout(new MigLayout("", "[grow][min]", "[grow]"));
        titlePanel.add(changeTitleField, "cell 0 0,grow");
        titlePanel.add(okButton, "flowx,cell 1 0,alignx center,growy");
        changeTitleField.setDocument(new PlainDocument());
        changeTitleField.setPrompt(titleLabel.getText());

        titlePanel.add(cancelButton, "cell 2 0,alignx center,growy");

        changeTitleField.getDocument().addDocumentListener(
                changedTaskNameListener);
    }

    /**
     * Sets the layout of the title panel to be unchangable.
     */
    public void setStaticTitlePanel() {
        titlePanel.removeAll();

        titlePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        titlePanel.add(titleLabel, "cell 0 0, alignx center, aligny center");
    }

    /**
     * @return The task name label, so that it can be edited
     */
    public JLabel getBucketNameLabel() {
        return titleLabel;
    }

    /**
     * @return The changed text field, so we can see what was edited.
     */
    public JXTextField getChangeTextField() {
        return changeTitleField;
    }

    /**
     * Enable or disable the post and reset buttons depending of if there's
     * something entered in the comment box.
     */
    private void validateField() {
        if (changeTitleField.getText().trim().isEmpty()) {
            okButton.setEnabled(false);
            changeTitleField.setBorder(invalidBorder);
        } else {
            okButton.setEnabled(true);
            changeTitleField.setBorder(validBorder);
        }
    }

    private final DocumentListener changedTaskNameListener = new DocumentListener() {

        @Override
        public void removeUpdate(DocumentEvent e) {
            validateField();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            validateField();
        }

        @Override
        public void changedUpdate(DocumentEvent arg0) {
            validateField();
        }
    };

}

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

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;

public class TestBucketView {

    BucketModel bModel = new BucketModel(1, "test");

    @Test
    public void testCreateNonNullBucketView() {
        assertNotNull(new BucketView(bModel));
    }

    @Test
    public void testGetTaskViews() {
        assertEquals(new ArrayList<MiniTaskView>(),
                new BucketView(bModel).getTaskViews());
    }

    @Test
    public void testAddTaskToView() {
        MiniTaskView task = new MiniTaskView(new TaskModel());
        ArrayList<MiniTaskView> mList = new ArrayList<MiniTaskView>();
        mList.add(task);
        BucketView bView = new BucketView(bModel);
        bView.addTaskToView(task);
        Dimension maxView = new Dimension((int) bView.getPreferredSize()
                .getWidth() - 32, (int) bView.getMaximumSize().getHeight());
        task.setMaximumSize(maxView);// prevent horizontal scroll
        task.getTaskNameLabel().setMaximumSize(maxView);
        assertArrayEquals(mList.toArray(), bView.getTaskViews().toArray());
    }

    @Test
    public void testRemoveTaskView() {
        MiniTaskView task = new MiniTaskView(new TaskModel());
        BucketView bView = new BucketView(bModel);
        bView.addTaskToView(task);
        assertNotNull(bView.getTaskViews());
        bView.removeTaskView(task);
        assertTrue(bView.getTaskViews().isEmpty());
    }

    @Test
    public void testGetInsertionIndex() {
        BucketView bView = new BucketView(bModel);
        TaskModel tModel = new TaskModel(1, 1, "title", "ti", 0, new Date(), 0);
        MiniTaskView task = new MiniTaskView(tModel);
        assertEquals(0, bView.getInsertionIndex(new Point(0, 0), true));
        bView.getTaskViews().add(task);
        assertEquals(1, bView.getInsertionIndex(new Point(0, 0), false));
        assertEquals(0, bView.getInsertionIndex(new Point(0, 0), true));
    }
    
    @Test
    public void testGetBucketNameLabel() {
        BucketView bView = new BucketView(bModel);
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        titleLabel.setText("test");
        assertEquals(titleLabel.getText(), bView.getBucketNameLabel().getText());
    }

}

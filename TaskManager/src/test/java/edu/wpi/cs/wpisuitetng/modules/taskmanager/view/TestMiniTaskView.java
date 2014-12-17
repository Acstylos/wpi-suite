/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import static org.junit.Assert.*;

import java.awt.Color;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;

public class TestMiniTaskView {
    @SuppressWarnings("deprecation")
    TaskModel tModel = new TaskModel(1, "test", "test discription", 10, new Date(2014, 12, 16), 1);
    
    @Test
    public void testCreateNonNullMiniTaskView() {
        assertNotNull(new MiniTaskView(tModel));
    }

    @Test
    public void testSetColorHighlighted() {
        MiniTaskView mtView = new MiniTaskView(tModel);
        mtView.setColorHighlighted(true);
        assertEquals(UIManager.getColor("textHighlight"), mtView.getBackground());
        mtView.setColorHighlighted(false);
        assertEquals(UIManager.getColor("menu"), mtView.getBackground());
    }

    @Test
    public void testSetColorArchived() {
        MiniTaskView mtView = new MiniTaskView(tModel);
        mtView.setColorArchived(false);
        assertEquals(UIManager.getColor("menu"), mtView.getBackground());
        mtView.setColorArchived(true);
        assertEquals(new Color(210, 210, 210), mtView.getBackground());
    }

    @Test
    public void testSetExpandedView() {
        tModel.setIsArchived(true);
        MiniTaskView mtView = new MiniTaskView(tModel);
        mtView.setExpandedView();
        tModel.setIsArchived(false);
        mtView.setExpandedView();
    }

    @Test
    public void testAddUsersToUserPanel() {
        MiniTaskView mtView = new MiniTaskView(tModel);
        List<String> userlist = new ArrayList<String>();
        JPanel userPanel = new JPanel();
        JLabel aLabel = new JLabel("a");
        JLabel bLabel = new JLabel("b");
        userPanel.add(aLabel, "dock north");
        userPanel.add(bLabel, "dock north");
        userlist.add("a");
        userlist.add("b");
        mtView.addUsersToUserPanel(userlist);
        assertEquals(userPanel.getComponent(1).getName(), mtView.getUserPanel().getComponent(1).getName());
    }

    @Test
    public void testUpdateLabel() {
        tModel.setLabelColor(new Color(255, 255, 255));
        MiniTaskView mtView = new MiniTaskView(tModel);
        mtView.updateLabel();
        assertEquals(new Color(255, 255, 255), mtView.getColorLabel().getBackground());
        tModel.setLabelColor(new Color(100, 100, 100));
        MiniTaskView mtView2 = new MiniTaskView(tModel);
        mtView2.updateLabel();
        assertEquals(new Color(100, 100, 100), mtView2.getColorLabel().getBackground());
    }
}

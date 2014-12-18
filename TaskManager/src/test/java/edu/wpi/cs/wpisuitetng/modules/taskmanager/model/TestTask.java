/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;

/**
 * Tests methods in iteration.java and tests the creation of variants of Bucket
 *
 * @author theFloorIsJava
 */

public class TestTask {

    @Test
    public void createNonVoidTask() {
        assertNotNull(new TaskModel());
    }

    @Test
    public void testCompareToMethod() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = new Date(1, 1, 2000);
        Date date2 = new Date(2, 2, 2000);

        TaskModel before = new TaskModel(1, "Title1", "description2", 1, date1,
                1);
        before.setActualEffort(1);
        TaskModel after = new TaskModel(1, "Title2", "description2", 1, date1,
                1);
        TaskPresenter test = new TaskPresenter(before);
        after.setActualEffort(1);
        String titleChange = "Title was changed from Title1 to Title2";
        assertEquals(titleChange, test.compareTasks(before, after));
        after.setTitle("Title1");
        after.setDescription("description1");
        String descriptionChange = "Description was changed.";
        assertEquals(descriptionChange, test.compareTasks(before, after));
        String dateChange = "Due Date was changed from "
                + dateFormat.format(date1) + " to " + dateFormat.format(date2);
        after.setDescription("description2");
        after.setDueDate(date2);
        assertEquals(dateChange, test.compareTasks(before, after));
        String effortChange = "Actual Effort was changed from 1 to 2\nEstimated Effort was changed from 1 to 2";
        after.setDueDate(date1);
        after.setActualEffort(2);
        after.setEstimatedEffort(2);
        assertEquals(effortChange, test.compareTasks(before, after));
    }
}

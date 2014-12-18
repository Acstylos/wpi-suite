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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.MockData;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.ActivityEntityManager;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.ActivityModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.WorkflowPresenter;

public class TestTaskView {

    @Test
    public void testCreateNonNullTaskView() {
        TaskModel tModel = new TaskModel(1, "", "", 0, new Date(), 0);
        List<Integer> uList = new ArrayList<Integer>();
        uList.add(1);
        tModel.setAssignedTo(uList);
        TaskPresenter tPresenter = new TaskPresenter(tModel);
        tPresenter.setAssignedUserList();
        tPresenter.getAssignedUserList().add(1);
        assertNotNull(new TaskView(tModel, ViewMode.EDITING, tPresenter));
    }

    @Test
    public void testSettersGetters() {
        TaskModel tModel = new TaskModel(1, "", "", 0, new Date(), 0);
        List<Integer> uList = new ArrayList<Integer>();
        uList.add(1);
        tModel.setAssignedTo(uList);
        TaskPresenter tPresenter = new TaskPresenter(tModel);
        tPresenter.setAssignedUserList();
        tPresenter.getAssignedUserList().add(1);
        TaskView view = new TaskView(tModel, ViewMode.EDITING, tPresenter);
        view.setIndex(3);
        assertEquals(3, view.getIndex());
        view.setViewMode(ViewMode.ARCHIVING);
        assertEquals(ViewMode.ARCHIVING, view.getViewMode());
        view.setRequirement(0);
        assertEquals(0, view.getRequirementIndex());
        assertNotNull(view.getUserListPanel());
        assertNotNull(view.getCommentView());
        assertNotNull(view.getLabelColor());

    }

}

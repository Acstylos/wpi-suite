/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ViewMode;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;



import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;

public class TestTaskPresenter {


    @Test
    public final void testUpdateBeforeModel() {
        TaskPresenter test = new TaskPresenter(new TaskModel());
        test.setModelNoView(new TaskModel(2, "beforeModel", "Descrip", 23, new Date(2014, 12, 10), 1));
        test.updateBeforeModel();
        assertTrue(test.getModel().equals(test.getBeforeModel()));
    }

    @Test
    public final void testIdToUsername() {
        TaskPresenter test = new TaskPresenter(new TaskModel());
        User [] currentUsers = {new User("bob", "dbob" , "password", 1) , 
                new User ("john", "jmoney", "whatsapassword", 2)};
      
        test.addUsersToAllUserList(currentUsers);
        assertEquals(test.idToUsername(1) ,"dbob");
        assertEquals(test.idToUsername(2) ,"jmoney");
        assertEquals(test.idToUsername(3) ,"");
    }

}

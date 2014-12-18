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

import java.util.Date;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.MockData;

public class TestTaskEntityManager {
    MockData db;
    Session defaultSession;
    Project testProject;
    User testUser;
    String mockSsid;
    TaskModel tskm;
    TaskEntityManager manager;

    @Before
    public void setUp() {
        db = new MockData(new HashSet<Object>());
        testUser = new User("joe", "joe", "1234", 2);
        testUser.setRole(Role.ADMIN);
        testProject = new Project("test", "1");
        mockSsid = "abc123";
        defaultSession = new Session(testUser, testProject, mockSsid);
        tskm = new TaskModel();
        manager = new TaskEntityManager(db);
        db.save(testUser);
    }

    /**
     * Test manager
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testMakeTaskEntity() throws WPISuiteException {
        TaskModel test1 = new TaskModel(1, "title1", "desc1", 2, new Date(), 1);
        test1.setProject(testProject);

        assertNotNull(manager.makeEntity(defaultSession, test1.toJson()));
    }

    /**
     * Test saving
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testSave() throws WPISuiteException {
        manager.save(defaultSession, new TaskModel());
    }

    /**
     * Test count of buckets
     * 
     * @throws WPISuiteException
     *             if not valid request
     */
    @Test
    public void testCount() throws WPISuiteException {
        assertEquals(0, manager.Count());
        manager.save(defaultSession, new TaskModel());
        assertEquals(1, manager.Count());
        manager.save(defaultSession, new TaskModel());
        assertEquals(2, manager.Count());
        manager.save(defaultSession, new TaskModel());
        assertEquals(3, manager.Count());
    }

    /**
     * Test deleting all
     * 
     * @throws WPISuiteException
     *             if not valid request
     */
    @Test
    public void testDeleteAllAndEnsureRole() throws WPISuiteException {
        TaskModel test1 = new TaskModel(1, "title1", "desc1", 2, new Date(), 1);
        TaskModel test2 = new TaskModel(2, "title2", "desc2", 2, new Date(), 1);
        TaskModel test3 = new TaskModel(3, "title3", "desc3", 3, new Date(), 1);
        test1.setProject(testProject);
        test2.setProject(testProject);
        test3.setProject(testProject);
        manager.save(defaultSession, test1);
        manager.save(defaultSession, test2);
        manager.save(defaultSession, test3);
        assertEquals(3, manager.Count());
        manager.deleteAll(defaultSession);
        assertEquals(0, manager.Count());
    }

    /**
     * Test getting all Buckets
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testGetAll() throws WPISuiteException {
        TaskModel test1 = new TaskModel(1, "title1", "desc1", 2, new Date(), 1);
        TaskModel test2 = new TaskModel(2, "title2", "desc2", 2, new Date(), 1);
        TaskModel test3 = new TaskModel(3, "title3", "desc3", 3, new Date(), 1);
        test1.setProject(testProject);
        test2.setProject(testProject);
        test3.setProject(testProject);
        manager.save(defaultSession, test1);
        manager.save(defaultSession, test2);
        manager.save(defaultSession, test3);
        TaskModel tskmList[] = manager.getAll(defaultSession);
        assertEquals(3, tskmList.length);
    }

    /**
     * Test getting an entity
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testGetEntity() throws WPISuiteException {
        TaskModel test1 = new TaskModel(1, "title1", "desc1", 2, new Date(), 1);
        TaskModel test2 = new TaskModel(2, "title2", "desc2", 2, new Date(), 1);
        TaskModel test3 = new TaskModel(3, "title3", "desc3", 3, new Date(), 1);
        test1.setProject(testProject);
        test2.setProject(testProject);
        test3.setProject(testProject);
        manager.save(defaultSession, test1);
        manager.save(defaultSession, test2);
        manager.save(defaultSession, test3);
        TaskModel tskmList[] = manager.getEntity(defaultSession, "2");

        assertEquals(1, tskmList.length);
        assertEquals(2, tskmList[0].getId());
        assertEquals("title2", tskmList[0].getTitle());
    }

    /**
     * Test getting an invalid entity
     * 
     * @throws WPISuiteException
     *             expects to not find the entity
     */
    @Test(expected = WPISuiteException.class)
    public void testGetBadEntity() throws WPISuiteException {
        manager.getEntity(defaultSession, "0");
    }

    /**
     * Test getting an entity for an invalid iteration
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testGetEntityForBucketNotFound() throws WPISuiteException {
        boolean exceptionThrown = false;
        TaskModel test1 = new TaskModel(1, "title1", "desc1", 2, new Date(), 1);
        TaskModel test2 = new TaskModel(2, "title2", "desc2", 2, new Date(), 1);
        TaskModel test3 = new TaskModel(3, "title3", "desc3", 3, new Date(), 1);
        test1.setProject(testProject);
        test2.setProject(testProject);
        test3.setProject(testProject);
        manager.save(defaultSession, test1);
        manager.save(defaultSession, test2);
        try {
            manager.getEntity(defaultSession, "6");
        } catch (NotFoundException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     * Test to delete an entity
     * 
     * @throws WPISuiteException
     *             if not valid request
     */
    @Test
    public void testDeleteEntity() throws WPISuiteException {
        TaskModel test1 = new TaskModel(1, "title1", "desc1", 2, new Date(), 1);
        TaskModel test2 = new TaskModel(2, "title2", "desc2", 2, new Date(), 1);
        TaskModel test3 = new TaskModel(3, "title3", "desc3", 3, new Date(), 1);
        test1.setProject(testProject);
        test2.setProject(testProject);
        test3.setProject(testProject);
        manager.save(defaultSession, test1);
        manager.save(defaultSession, test2);
        manager.save(defaultSession, test3);
        assertEquals(3, manager.Count());
        assertTrue(manager.deleteEntity(defaultSession, "3"));
        assertEquals(2, manager.Count());
        boolean exceptionThrown = false;
        try {
            manager.deleteEntity(defaultSession, "3");
        } catch (NotFoundException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     * Test updating an iteration
     * 
     * @throws WPISuiteException
     *             if not valid request
     */
    @Test
    public void testUpdatingAnInteration() throws WPISuiteException {
        TaskModel test1 = new TaskModel(1, "title1", "desc1", 2, new Date(), 1);
        test1.setProject(testProject);
        manager.save(defaultSession, test1);
        assertEquals(1, manager.Count());
        assertEquals(1, manager.getEntity(defaultSession, "1")[0].getId());
        assertEquals("title1",
                manager.getEntity(defaultSession, "1")[0].getTitle());
        manager.update(defaultSession, new TaskModel(1, "changed", "desc", 23,
                new Date(), 2).toJson());
        assertEquals(1, manager.Count());
        assertEquals("changed",
                manager.getEntity(defaultSession, "1")[0].getTitle());

        boolean exceptionThrown = false;
        try {
            // this bucketModel is not saved yet , so will cause error
            manager.update(defaultSession, new TaskModel(4, "change Id 4",
                    "desc", 23, new Date(), 2).toJson());
        } catch (WPISuiteException e) {
            exceptionThrown = true;
        }
        // an exception will be thrown because the model was never saved, so
        // cant update.
        assertTrue(exceptionThrown);
    }
}

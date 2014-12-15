/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team The Floor is Java
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
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.MockData;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketEntityManager;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;

public class TestBucketEntityManager {
    MockData db;
    Session defaultSession;
    Project testProject;
    User testUser;
    String mockSsid;
    BucketModel bkm;
    BucketEntityManager manager;

    @Before
    public void setUp() {
        db = new MockData(new HashSet<Object>());
        testUser = new User("joe", "joe", "1234", 2);
        testUser.setRole(Role.ADMIN);
        testProject = new Project("test", "1");
        mockSsid = "abc123";
        defaultSession = new Session(testUser, testProject, mockSsid);
        bkm = new BucketModel();
        manager = new BucketEntityManager(db);
        db.save(testUser);
    }

    /**
     * Test manager
     * 
     * @throws WPISuiteException
     * @throws ConflictException
     * @throws BadRequestException
     */
    @Test
    public void testMakeBucketEntity() throws BadRequestException,
            ConflictException, WPISuiteException {
        BucketModel test1 = new BucketModel(1, "dummyTitle");
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
        manager.save(defaultSession, new BucketModel());
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
        manager.save(defaultSession, new BucketModel());
        assertEquals(1, manager.Count());
        manager.save(defaultSession, new BucketModel());
        assertEquals(2, manager.Count());
        manager.save(defaultSession, new BucketModel());
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
        manager.save(defaultSession, new BucketModel());
        manager.save(defaultSession, new BucketModel());
        manager.save(defaultSession, new BucketModel());
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
        manager.save(defaultSession, new BucketModel());
        manager.save(defaultSession, new BucketModel());
        manager.save(defaultSession, new BucketModel());
        BucketModel bkmList[] = manager.getAll(defaultSession);
        assertEquals(3, bkmList.length);
    }

    /**
     * Test getting an entity
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testGetEntity() throws WPISuiteException {
        manager.save(defaultSession, new BucketModel(3, "test 3"));
        manager.save(defaultSession, new BucketModel(4, "test 4"));
        manager.save(defaultSession, new BucketModel(5, "test 5"));
        BucketModel bkmList[] = manager.getEntity(defaultSession, "4");

        assertEquals(1, bkmList.length);
        assertEquals(4, bkmList[0].getId());
        assertEquals("test 4", bkmList[0].getTitle());
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
        manager.save(defaultSession, new BucketModel(3, "test 3"));
        manager.save(defaultSession, new BucketModel(4, "test 4"));
        manager.save(defaultSession, new BucketModel(5, "test 5"));
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
        manager.save(defaultSession, new BucketModel(3, "test 3"));
        assertEquals(1, manager.Count());
        assertTrue(manager.deleteEntity(defaultSession, "3"));
        assertEquals(0, manager.Count());
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
        manager.save(defaultSession, new BucketModel(3, "test 3"));
        assertEquals(1, manager.Count());
        assertEquals(3, manager.getEntity(defaultSession, "3")[0].getId());

        assertEquals("test 3",
                manager.getEntity(defaultSession, "3")[0].getTitle());

        manager.update(defaultSession, new BucketModel(3, "changed").toJson());
        assertEquals(1, manager.Count());
        assertEquals("changed",
                manager.getEntity(defaultSession, "3")[0].getTitle());

        boolean exceptionThrown = false;
        try {
            // this bucketModel is not saved yet , so will cause error
            manager.update(defaultSession,
                    new BucketModel(4, "change Id 4").toJson());
        } catch (WPISuiteException e) {
            exceptionThrown = true;
        }
        // an exception will be thrown because the model was never saved, so
        // cant update.
        assertTrue(exceptionThrown);
    }
}

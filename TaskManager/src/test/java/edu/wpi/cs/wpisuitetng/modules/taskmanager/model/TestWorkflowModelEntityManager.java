package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockData;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.Iteration;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowEntityManager;


public class TestWorkflowModelEntityManager {
    MockData db;
    Session defaultSession;
    Project testProject;
    User testUser;
    String mockSsid;
    WorkflowModel wfm;
    WorkflowEntityManager manager;

    @Before
    public void setUp() {
	db = new MockData(new HashSet<Object>());
	testUser = new User("joe", "joe", "1234", 2);
	testUser.setRole(Role.ADMIN);
	testProject = new Project("test", "1");
	mockSsid = "abc123";
	defaultSession = new Session(testUser, testProject, mockSsid);
	wfm = new WorkflowModel();
	manager = new WorkflowEntityManager(db);
	db.save(testUser);
    }

    /**
     * Test manager
     */
    @Test
    public void testMakeBusketEntity() {
	assertNotNull(manager);
    }

    /**
     * Test saving
     * @throws WPISuiteException 
     */
    @Test
    public void testSave() throws WPISuiteException {
	manager.save(defaultSession, new WorkflowModel());
    }

    /**
     * Test count of buckets
     * 
     * @throws WPISuiteException if not valid request
     */
    @Test
    public void testCount() throws WPISuiteException {
	assertEquals(0, manager.Count());
	manager.save(defaultSession, new WorkflowModel());
	assertEquals(1, manager.Count());
	manager.save(defaultSession, new WorkflowModel());
	assertEquals(2, manager.Count());
	manager.save(defaultSession, new WorkflowModel());
	assertEquals(3, manager.Count());
    }

    /**
     * Test deleting all
     * 
     * @throws WPISuiteException if not valid request
     */
    @Test
    public void testDeleteAllAndEnsureRole() throws WPISuiteException {
	manager.save(defaultSession, new WorkflowModel());
	manager.save(defaultSession, new WorkflowModel());
	manager.save(defaultSession, new WorkflowModel());
	assertEquals(3, manager.Count());
	manager.deleteAll(defaultSession);
	assertEquals(0, manager.Count());
    }

    /**
     * Test getting all Buckets
     * @throws WPISuiteException 
     */
    @Test
    public void testGetAll() throws WPISuiteException {
	manager.save(defaultSession, new WorkflowModel());
	manager.save(defaultSession, new WorkflowModel());
	manager.save(defaultSession, new WorkflowModel());
	WorkflowModel wfmList[] = manager.getAll(defaultSession);
	assertEquals(3, wfmList.length);
    }
    /**
     * Test getting an entity
     * @throws WPISuiteException 
     */
    @Test
    public void testGetEntity() throws WPISuiteException {
	manager.save(defaultSession, new WorkflowModel(3, "test 3"));
	manager.save(defaultSession, new WorkflowModel(4, "test 4"));
	manager.save(defaultSession, new WorkflowModel(5, "test 5"));
	WorkflowModel wfmList[] = manager.getEntity(defaultSession, "4");

	assertEquals(1, wfmList.length);
	assertEquals(4, wfmList[0].getId());
	assertEquals("test 4", wfmList[0].getTitle());
    }

    /**
     * Test getting an invalid entity
     * @throws WPISuiteException expects to not find the entity
     */
    @Test(expected = WPISuiteException.class)
    public void testGetBadEntity() throws WPISuiteException {
	manager.getEntity(defaultSession, "0");
    }

    /**
     * Test getting an entity for an invalid iteration
     * @throws WPISuiteException 
     */
    @Test
    public void testGetEntityForBucketNotFound() throws WPISuiteException {
	boolean exceptionThrown = false;
	manager.save(defaultSession, new WorkflowModel(3, "test 3"));
	manager.save(defaultSession, new WorkflowModel(4, "test 4"));
	manager.save(defaultSession, new WorkflowModel(5, "test 5"));
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
     * @throws WPISuiteException if not valid request
     */
    @Test
    public void testDeleteEntity() throws WPISuiteException {
	manager.save(defaultSession, new WorkflowModel(3, "test 3"));
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
     * @throws WPISuiteException if not valid request
     */
    @Test
    public void testUpdatingAnInteration() throws WPISuiteException {
	manager.save(defaultSession, new WorkflowModel(3, "test 3"));
	assertEquals(1, manager.Count());
	assertEquals(3, manager.getEntity(defaultSession, "3")[0].getId());
	assertEquals("test 3", manager.getEntity(defaultSession, "3")[0].getTitle());

	manager.update(defaultSession, new WorkflowModel(3, "changed").toJson());
	assertEquals(1, manager.Count());
	assertEquals("changed", manager.getEntity(defaultSession, "3")[0].getTitle());

	boolean exceptionThrown = false;
	try {
	    manager.update(defaultSession, new WorkflowModel(4, "change Id 4").toJson());
	} catch (BadRequestException e) {
	    exceptionThrown = true;
	}
	assertTrue(exceptionThrown);
    }
}


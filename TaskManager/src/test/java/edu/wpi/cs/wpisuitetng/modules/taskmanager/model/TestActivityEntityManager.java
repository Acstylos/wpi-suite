package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockData;

public class TestActivityEntityManager {
	
		MockData db;
		Session defaultSession;
		Project testProject;
		User testUser;
	    String mockSsid;
		ActivityModel actm;
		ActivityEntityManager manager;
		
		@Before
	    public void setUp() {
			db = new MockData(new HashSet<Object>());
			testUser = new User("joe", "joe", "1234", 2);
	        testUser.setRole(Role.ADMIN);
	        testProject = new Project("test", "1");
	        mockSsid = "abc123";
	        defaultSession = new Session(testUser, testProject, mockSsid);
	        actm = new ActivityModel();
	        manager = new ActivityEntityManager(db);
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
	        manager.save(defaultSession, new ActivityModel());
	    }
	    
	    /**
	     * Test count of buckets
	     * 
	     * @throws WPISuiteException if not valid request
	     */
	    @Test
	    public void testCount() throws WPISuiteException {
	        assertEquals(0, manager.Count());
	        manager.save(defaultSession, new ActivityModel());
	        assertEquals(1, manager.Count());
	        manager.save(defaultSession, new ActivityModel());
	        assertEquals(2, manager.Count());
	        manager.save(defaultSession, new ActivityModel());
	        assertEquals(3, manager.Count());
	    }
	    
	    /**
	     * Test deleting all
	     * 
	     * @throws WPISuiteException if not valid request
	     */
	    @Test
	    public void testDeleteAllAndEnsureRole() throws WPISuiteException {
	        manager.save(defaultSession, new ActivityModel());
	        manager.save(defaultSession, new ActivityModel());
	        manager.save(defaultSession, new ActivityModel());
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
	        manager.save(defaultSession, new ActivityModel());
	        manager.save(defaultSession, new ActivityModel());
	        manager.save(defaultSession, new ActivityModel());
	        ActivityModel actvList[] = manager.getAll(defaultSession);
	        assertEquals(3, actvList.length);
	    }

	    @Test
	    public void testGetEntity() throws WPISuiteException {
	    	testUser = new User("joe", "joe", "1234", 2);
	        testUser.setRole(Role.ADMIN);
	        manager.save(defaultSession, new ActivityModel(2, testUser,"comment1"));
	        manager.save(defaultSession, new ActivityModel(3, testUser,"comment2"));
	        manager.save(defaultSession, new ActivityModel(4, testUser,"comment3"));
	        ActivityModel actvList[] = manager.getEntity(defaultSession, "4");
	        
	        assertEquals(1, actvList.length);
	        assertEquals(4, actvList[0].getId());
	        assertEquals("comment3", actvList[0].getActivity());
	    }

	    /**
	     * Test getting an invalid entity
	     * @throws WPISuiteException expects to not find the entity
	     */
	    @Test(expected = WPISuiteException.class)
	    public void testGetBadEntity() throws WPISuiteException {
	        manager.getEntity(defaultSession, "0");
	    }
	    


}

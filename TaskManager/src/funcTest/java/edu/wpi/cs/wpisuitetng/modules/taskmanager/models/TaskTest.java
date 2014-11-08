/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.models.TaskStatus;

 public class TaskTest {
	User Keenan;
	User Yihao;
	List<User> testList;
	Task t1;
	Task t2;
	Task t3;
	
	@Before
	public void setUpTask(){
		Keenan = new User("Krgray","keenan","",-1);
		Yihao = new User ("Yih", "yihao", "", -1);
		testList = new ArrayList();
		testList.add(Keenan);
		testList.add(Yihao);
		t1 = new Task();
		t2 = new Task(1928, "TaskTwo","This is task two", 400);
		t3 = new Task();
		t3.setId(2000);
		t3.setTitle("TaskThree");
		t3.setDescription("This is task three");	
		t3.setAssignedTo(Keenan);
		t3.setAssignedTo(Yihao);
		t3.setEstimatedEffort(150);
		
	}
	@Test
	public void testCreateDefaultTask(){
		assertEquals(-1, t1.getID());
		assertEquals("", t1.getTitle());
		assertEquals("", t1.getDescription());
		assertEquals(TaskStatus.NEW, t1.getStatus());
		assertEquals(-1, t1.getEstimatedEffort());
		assertEquals(-1, t1.getActualEffort());
	    
	}

	@Test
	public void testCreateTask(){
		assertEquals(1928, t2.getID());
		assertEquals("TaskTwo", t2.getTitle());
		assertEquals("This is task two", t2.getDescription());
		assertEquals(TaskStatus.NEW, t2.getStatus());
		assertEquals(400, t2.getEstimatedEffort());
		assertEquals(-1, t2.getActualEffort());
	}
 
 	@Test
 	public void testModifyTask(){
 		assertEquals(2000, t3.getID());
 		assertEquals("TaskThree", t3.getTitle());
 		assertEquals("This is task three", t3.getDescription());
 		assertEquals(testList, t3.getAssignedTo());
 		assertEquals(150, t3.getEstimatedEffort());
 		
 		
 	}
}
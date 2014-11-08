/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.models.TaskStatus;

/**
 * The model end of the Task object
 */
public class Task extends AbstractModel {
	private int ID;
	private String Title, Description;
	private TaskStatus Status;
	private List<User> AssignedTo;  
	private int EstimatedEffort;
	private int ActualEffort;
	private Date dueDate;
	//private List<events> Activities; 
	//private List<Task> dependencies;
	
	/**
	 * Constructor for a default Task object
	 * 
	 */
	public Task(){
		ID = -1;
		Title ="";
		Description = "";	
		Status = TaskStatus.NEW;
	    AssignedTo = new ArrayList<User>();
	    EstimatedEffort = -1;
	    ActualEffort = -1;
	}
	
	/**
	 * Constructor for a task with specific properties.
	 * Other properties are the same as the default constructor
	 * 
	 * @param ID the unique id of the Task
	 * @param Title the title of the Task
	 * @param Description the description of the Task
	
	 */
	public Task(int id, String title, String description, int estimatedEffort) {
		this();
		this.ID = id;
		this.Title = title;
		this.Description = description;
		this.EstimatedEffort = estimatedEffort;
	}

	/**
	 * @return the ID of the task.  Returns -1 by default
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param id, The task ID 
	 */
	public void setId(int id) {
		this.ID = id;
	}

	/**
	 * @return the Title of this task
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * @param title, The task Title
	 */
	public void setTitle(String title) {
		this.Title = title;
	}
	
	/**
	 * @return the description of this Task
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description, the Description of this Task
	 */
	public void setDescription(String description) {
		this.Description = description;
	}
	
	/**
	 * @return the Status of this Task
	 */
	public TaskStatus getStatus() {
		return Status;
	}
	
	/**
	 * @param status the Status of this Task
	 */
	public void setStatus(TaskStatus status) {
		this.Status = status;
	}
	
	/**
	 * @return the list of users assigned to this Task
	 */
	public List<User> getAssignedTo() {
		return AssignedTo;
	}
	
	/**
	 * @param user adds a user to the list of assigned users
	 */
	public void setAssignedTo(User user){
		this.AssignedTo.add(user);
		
	}
		
	/**
	 * Converts this Task to a JSON string
	 * @return a string in JSON representing this Task
	 */
	public String toJson() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Task.class);
		return json;
	}
	
	/**
	 * Converts the given list of Task to a JSON string
	 * @param tlist a list of Task
	 * @return a string in JSON representing the list of Tasks
	 */
	public static String toJSON(Task[] tlist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(tlist, Task.class);
		return json;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
}
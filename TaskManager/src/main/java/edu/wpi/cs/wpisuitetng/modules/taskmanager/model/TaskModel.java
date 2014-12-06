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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * The model end of the Task object
 */
public class TaskModel extends AbstractModel {
    private int id;
    private String title;
    private String shortTitle;
    private String description;
    private List<Integer> assignedTo;
    private List<Integer> activityIds;
    private int estimatedEffort;
    private int actualEffort;
    private Date dueDate;
    private int status;
    private boolean isArchived;

    /**
     * Constructor for a default Task object
     * 
     */
    public TaskModel() {
        id = -1;
        title = "New Task";
        shortTitle = this.shortenString(this.title);
        description = "";
        assignedTo = new ArrayList<Integer>();
        activityIds = new ArrayList<Integer>();
        estimatedEffort = 0;
        actualEffort = 0;
        status = 1;
        isArchived = false;
    }

    /**
     * Constructor for a task with specific properties. Other properties are the
     * same as the default constructor
     * 
     * @param id
     *            The unique id of the task
     * @param title
     *            The title of the task
     * @param description
     *            The description of the task
     * @param estimatedEffort
     *            The estimated effort of a task
     * @param dueDate
     *            The due date for the task
     * @param status
     *             the bucket the task belongs in
     */
    public TaskModel(int id, String title, String description,
            int estimatedEffort, Date dueDate, int status) {
        this();
        this.id = id;
        this.title = title;
        this.shortTitle = this.shortenString(this.title);
        this.description = description;
        this.estimatedEffort = estimatedEffort;
        this.dueDate = dueDate;
        this.status = status;
        this.isArchived = false;
    }

    /**
     * Compares the task models before and after it is updated and makes a
     * string that contains all the user changes
     * 
     * @param that
     *            the task model after it was updated.
     * @return summary the message which shows what has changed.
     */
    public String compareTo(TaskModel that) {
        boolean flag = false;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String summary = "";
        if (this.title.compareTo(that.title) != 0) {
            summary = "Title was changed from " + this.title + " to "
                    + that.title;
            flag = true;
        }
        if (this.actualEffort != that.actualEffort) {
            if (flag)
                summary += "\n";
            summary += "Actual Effort was changed from " + this.actualEffort
                    + " to " + that.actualEffort;
            flag = true;
        } else if (!flag)
            flag = false;
        if (this.estimatedEffort != that.estimatedEffort) {
            if (flag)
                summary += "\n";
            summary += "Estimated Effort was changed from "
                    + this.estimatedEffort + " to " + that.estimatedEffort;
            flag = true;
        } else if (!flag)
            flag = false;
        if (this.dueDate.compareTo(that.dueDate) != 0) {
            if (flag)
                summary += "\n";
            summary += "Due Date was changed from "
                    + dateFormat.format(this.dueDate) + " to "
                    + dateFormat.format(that.dueDate);
            flag = true;
        } else if (!flag)
            flag = false;
        if (this.description.compareTo(that.description) != 0) {
            if (flag)
                summary += "\n";
            summary += "Description was changed.";
        }
        return summary;

        // will implement "assigned to" changes and "status" from int to enum
    }
    
    /**
     * @return The ID of the task. Returns -1 by default
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            The task ID to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The title of this task
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            The task title to be set
     */
    public void setTitle(String title) {
        this.title = title;
        this.shortTitle=this.shortenString(title);
    }

    /**
     * @return The description of this Task
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            The description of this task to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @param userList The list of users assigned to this task
     */
    public void setAssignedTo(List<Integer> userList) {
        this.assignedTo = new ArrayList<Integer>(userList);
    }

    /**
     * @return The list of users assigned to this task
     */
    public List<Integer> getAssignedTo() {
        return this.assignedTo;
    }

    /**
     * @param user
     *            Adds a user to the list of assigned users
     */
    public void addUserToAssignedTo(User user) {
        this.assignedTo.add(user.getIdNum());
    }

    /**
     * @param user
     *            The user to be removed from the list of assigned users
     */
    public void removeUserFromAssignedTo(User user) {
        this.assignedTo.remove((Object)user.getIdNum());
    }

    /**
     * @return The due date of this task
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate
     *            The due date of the task to be set.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Copy all of the fields from another TaskModel
     * 
     * @param other
     *            The TaskModel to be copied
     */
    public void copyFrom(TaskModel other) {
        this.title = other.getTitle();
        this.description = other.getDescription();
        // Make sure we shallow-copy the array list, instead of pass references to it.
        this.assignedTo = new ArrayList<Integer>(other.getAssignedTo());
        this.estimatedEffort = other.getEstimatedEffort();
        this.dueDate = other.getDueDate();
        this.actualEffort = other.getActualEffort();
        this.status = other.getStatus();
        this.activityIds = other.getActivityIds();
        this.isArchived = other.getIsArchived();
    }

    /**
     * Converts this task object to a JSON string
     * 
     * @return A string in JSON representing this Task
     */
    public String toJson() {
        String json;
        Gson gson = new Gson();
        json = gson.toJson(this, TaskModel.class);
        return json;
    }

    /**
     * Converts the given list of tasks to a JSON string
     * 
     * @param tlist
     *            A list of Tasks
     * @return A string in JSON representing the list of tasks
     */
    public static String toJson(TaskModel[] tlist) {
        String json;
        Gson gson = new Gson();
        json = gson.toJson(tlist, TaskModel.class);
        return json;
    }

    /**
     * Convert the given JSON string to a TaskModel instance
     * 
     * @param json
     * 			String to be converted
     * 
     * @return The JSON string representing the object
     */
    public static TaskModel fromJson(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, TaskModel.class);
    }

    /**
     * Convert the given JSON string with a JSON array of tasks into an array of
     * tasks
     * 
     * @param json
     * 			String to be converted
     * 
     * @return TaskModel array
     */
    public static TaskModel[] fromJsonArray(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, TaskModel[].class);
    }

    /**
     * Checks if a given object is a TaskModel object
     * 
     * @param o Object to check
     * @return true if TaskModel, otherwise false
     * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
     */
    public Boolean identify(Object o) {
        Boolean returnValue = false;
        if (o instanceof TaskModel && id == ((TaskModel) o).getId()) {
            returnValue = true;
        }
        if (o instanceof String && Integer.toString(id).equals(o)) {

        }
        return returnValue;
    }

    /**
     * Takes the title of a task and reduces the number of characters
     * @param title: string to be modified
     * @return String representing shortened title
     */
    private String shortenString(String title){
        int maxLength=12;
        if (title.length() <= maxLength){
            return title;
        }
        else{
            title=title.substring(0, maxLength);
            title=title.concat("...");
            return title;
        }
    }
    
    /**
     * Will implement later
     */
    public void save() {
        // TODO Auto-generated method stub
    }

    /**
     * Will implement later
     */
    public void delete() {
        // TODO Auto-generated method stub
    }

    /**
     * @return The estimated effort of this task
     */
    public int getEstimatedEffort() {
        return estimatedEffort;
    }

    /**
     * @param estimatedEffort
     *            The estimated effort of this task to be set
     */
    public void setEstimatedEffort(int estimatedEffort) {
        this.estimatedEffort = estimatedEffort;
    }

    /**
     * @return The actual effort of this task
     */
    public int getActualEffort() {
        return actualEffort;
    }

    /**
     * @param actualEffort
     *            The actual effort of this task to be set
     */
    public void setActualEffort(int actualEffort) {
        this.actualEffort = actualEffort;
    }

    /**
     * Get the status of the task
     * 
     * @return The status of the task
     */
    public int getStatus() {
        return status;
    }

    /**
     * Set the status of the task
     * 
     * @param status
     *            The status of the task
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * return the list of activity ids
     * 
     * @return activityIds linked list of activity ids
     */
    public List<Integer> getActivityIds() {
        return activityIds;
    }
    
    /**
     * @return shortTitle shortened title for tabs and MiniTaskView
     */
    public String getShortTitle(){
        return this.shortenString(this.title);
    }

    /**
     * set the activity list to the given list
     * 
     * @param activityIds
     *            the list to be copied from
     */
    public void setActivityIds(List<Integer> activityIds) {
        this.activityIds = activityIds;
    }

    /**
     * Adds an activity id to the list to keep track of activities
     * 
     * @param id
     *            the activity id
     */
    public void addActivityID(int id) {
        activityIds.add(id);
    }

    public boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

}

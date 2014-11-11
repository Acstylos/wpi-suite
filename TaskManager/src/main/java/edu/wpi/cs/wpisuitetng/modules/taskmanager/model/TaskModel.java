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
    private String description;
    private List<User> assignedTo;
    private int estimatedEffort;
    private int actualEffort;
    private Date dueDate;

    /**
     * Constructor for a default Task object
     * 
     */
    public TaskModel() {
        id = 0;
        title = "Title";
        description = "Description";
        assignedTo = new ArrayList<User>();
        estimatedEffort = 0;
        actualEffort = 0;
    }

    /**
     * Constructor for a task with specific properties. Other properties are the
     * same as the default constructor
     * 
     * @param id
     *            the unique id of the Task
     * @param title
     *            the title of the Task
     * @param description
     *            the description of the Task
     */
    public TaskModel(int id, String title, String description,
            int estimatedEffort, Date dueDate) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
        this.estimatedEffort = estimatedEffort;
        this.dueDate = dueDate;
    }

    /**
     * @return the ID of the task. Returns -1 by default
     */
    public int getID() {
        return id;
    }

    /**
     * @param id
     *            , The task ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the Title of this task
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            , The task Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description of this Task
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            , the Description of this Task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the list of users assigned to this Task
     */
    public List<User> getAssignedTo() {
        return assignedTo;
    }

    /**
     * @param user
     *            adds a user to the list of assigned users
     */
    public void setAssignedTo(User user) {
        this.assignedTo.add(user);
    }

    /**
     * @return the due date of this Task
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param Sets
     *            the due date to the given date.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Copy all of the fields from another TaskModel
     * 
     * @param other
     */
    public void copyFrom(TaskModel other) {
        this.title = other.getTitle();
        this.description = other.getDescription();
        this.assignedTo = other.getAssignedTo();
        this.estimatedEffort = other.getEstimatedEffort();
        this.actualEffort = other.getActualEffort();
    }

    /**
     * Converts this Task to a JSON string
     * 
     * @return a string in JSON representing this Task
     */
    public String toJSON() {
        String json;
        Gson gson = new Gson();
        json = gson.toJson(this, TaskModel.class);
        return json;
    }

    /**
     * Converts the given list of Task to a JSON string
     * 
     * @param tlist
     *            a list of Task
     * @return a string in JSON representing the list of Tasks
     */
    public static String toJSON(TaskModel[] tlist) {
        String json;
        Gson gson = new Gson();
        json = gson.toJson(tlist, TaskModel.class);
        return json;
    }

    /**
     * Convert the given JSON string to a TaskModel instance
     */
    public static TaskModel fromJSON(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, TaskModel.class);
    }

    public Boolean identify(Object o) {
        Boolean returnValue = false;
        if (o instanceof TaskModel && id == ((TaskModel) o).getID()) {
            returnValue = true;
        }
        if (o instanceof String && Integer.toString(id).equals(o)) {

        }
        return returnValue;
    }

    public void save() {
        // TODO Auto-generated method stub
    }

    public void delete() {
        // TODO Auto-generated method stub
    }

    /**
     * @return EstimatedEffort of Task
     */
    public int getEstimatedEffort() {
        return estimatedEffort;
    }

    /**
     * @param EstimatedEffort
     *            of Task
     */
    public void setEstimatedEffort(int estimatedEffort) {
        this.estimatedEffort = estimatedEffort;
    }

    public int getActualEffort() {
        return actualEffort;
    }

    public void setActualEffort(int actualEffort) {
        this.actualEffort = actualEffort;
    }

    public String toJson() {
        return toJSON();
    }
}

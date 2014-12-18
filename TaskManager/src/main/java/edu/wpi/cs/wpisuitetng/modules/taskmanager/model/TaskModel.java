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

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private int requirement;
    private int estimatedEffort;
    private int actualEffort;
    private Date dueDate;
    private Date dateCreated;
    private int status;
    private Color labelColor;
    private boolean isArchived;

    /**
     * Constructor for a default Task object
     * 
     */
    public TaskModel() {
        id = -1;
        title = "New Task";
        shortTitle = this.shortenString(title);
        description = "";
        assignedTo = new ArrayList<Integer>();
        activityIds = new ArrayList<Integer>();
        requirement = 0;
        estimatedEffort = 0;
        actualEffort = 0;
        status = 1;
        labelColor = null;
        isArchived = false;
        dueDate = null;
        final Calendar cal = Calendar.getInstance();
        dateCreated = cal.getTime();
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
     *            the bucket the task belongs in
     */
    public TaskModel(int id, String title, String description,
            int estimatedEffort, Date dueDate, int status) {
        this();
        this.id = id;
        this.title = title;
        shortTitle = this.shortenString(this.title);
        this.description = description;
        this.estimatedEffort = estimatedEffort;
        this.dueDate = dueDate;
        this.status = status;
        labelColor = null;
        isArchived = false;
    }

    /**
     * determines if this taskModel is equal to that taskModel
     * 
     * @param that
     *            the other TaskModel
     * @return boolean, true if equal , false otherwise
     */
    @Override
    public boolean equals(Object other) {
        try {
            final TaskModel that = (TaskModel) other;
            if (title == that.title && shortTitle == that.shortTitle
                    && description == that.description
                    && estimatedEffort == that.estimatedEffort
                    && dueDate == that.dueDate && status == that.status
                    && labelColor == that.labelColor
                    && isArchived == that.isArchived) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get a formatted HTML string containing the list of changes between this
     * task and another. This is used for generating email reports.
     * 
     * @param other
     *            Another task
     * @return An HTML string containing a difference between this task and the
     *         other task
     */
    public String compareToHtml(TaskModel other) {
        String str = "";
        str += "<h2>Changes</h2>";
        str += "<ul>";

        if (!this.getTitle().equals(other.getTitle())) {
            str += "<li>The <b>title</b> was changed from \"" + this.getTitle()
                    + "\" to \"" + other.getTitle() + "\".</li>";
        }

        if (!this.getDescription().equals(other.getDescription())) {
            str += "<li>The <b>description</b> was changed from \""
                    + this.getDescription() + "\" to \""
                    + other.getDescription() + "\".</li>";
        }

        if (!this.getAssignedTo().containsAll(other.getAssignedTo())
                || !other.getAssignedTo().containsAll(this.getAssignedTo())) {
            str += "<li>The list of <b>assigned users</b> was changed.</li>";
            System.out.println("Old: " + this.getAssignedTo());
            System.out.println("New: " + other.getAssignedTo());
        }

        if (this.getActivityIds().size() != other.getActivityIds().size()) {
            str += "<li>A comment was added</li>";
        }

        if (this.getEstimatedEffort() != other.getEstimatedEffort()) {
            str += "<li>The <b>estimated effort</b> was changed from "
                    + this.getEstimatedEffort() + " to "
                    + other.getEstimatedEffort() + ".</li>";
        }

        if (this.getActualEffort() != other.getActualEffort()) {
            str += "<li>The <b>actual effort</b> was changed from "
                    + this.getActualEffort() + " to " + other.getActualEffort()
                    + ".</li>";
        }

        final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        if (!this.getDueDate().equals(other.getDueDate())) {
            str += "<li>The <b>due date</b> was changed from "
                    + dateFormat.format(this.getDueDate()) + " to "
                    + dateFormat.format(other.getDueDate()) + ".</li>";
        }

        if (this.getIsArchived() && !other.getIsArchived()) {
            str += "<li>It was archived</li>";
        } else if (other.getIsArchived() && !this.getIsArchived()) {
            str += "<li>It was un-archived</li>";
        }

        return str;
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
        shortTitle = this.shortenString(title);
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
     * @param userList
     *            The list of users assigned to this task
     */
    public void setAssignedTo(List<Integer> userList) {
        assignedTo = new ArrayList<Integer>(userList);
    }

    /**
     * @return The list of users assigned to this task
     */
    public List<Integer> getAssignedTo() {
        return assignedTo;
    }

    /**
     * @param user
     *            Adds a user to the list of assigned users
     */
    public void addUserToAssignedTo(User user) {
        assignedTo.add(user.getIdNum());
    }

    /**
     * @param user
     *            The user to be removed from the list of assigned users
     */
    public void removeUserFromAssignedTo(User user) {
        assignedTo.remove((Object) user.getIdNum());
    }

    /**
     * add requirement Id to the list of requirement Ids
     * 
     * @param req
     *            the requirement that needs to be added
     */
    public void setRequirement(int req) {
        requirement = req;
    }

    /**
     * 
     * @return
     */
    public int getRequirement() {
        return requirement;
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
        title = other.getTitle();
        description = other.getDescription();
        // Make sure we shallow-copy the array list, instead of pass references
        // to it.
        assignedTo = new ArrayList<Integer>(other.getAssignedTo());
        estimatedEffort = other.getEstimatedEffort();
        dueDate = other.getDueDate();
        actualEffort = other.getActualEffort();
        status = other.getStatus();
        activityIds = other.getActivityIds();
        requirement = other.getRequirement();
        labelColor = other.getLabelColor();
        isArchived = other.getIsArchived();
    }

    /**
     * Converts this task object to a JSON string
     * 
     * @return A string in JSON representing this Task
     */
    public String toJson() {
        final String json;
        final Gson gson = new Gson();
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
        final String json;
        final Gson gson = new Gson();
        json = gson.toJson(tlist, TaskModel.class);
        return json;
    }

    /**
     * Convert the given JSON string to a TaskModel instance
     * 
     * @param json
     *            string to be converted
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
     * @return TaskModel array
     */
    public static TaskModel[] fromJsonArray(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, TaskModel[].class);
    }

    /**
     * Checks if a given object is a TaskModel object
     * 
     * @param o
     *            Object to check
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
     * 
     * @param title
     *            : string to be modified
     * @return String representing shortened title
     */
    private String shortenString(String title) {
        final int maxLength = 12;
        if (title.length() <= maxLength) {
            return title;
        } else {
            title = title.substring(0, maxLength);
            title = title.concat("...");
            return title;
        }
    }

    /**
     * Gets a csv entry for various calendar formats.
     *
     * @return CSV entry, with a newline.
     */
    public String getCsv() {
        final SimpleDateFormat dfdate = new SimpleDateFormat("MM/dd/yy");
        final SimpleDateFormat dftime = new SimpleDateFormat("hh:mm:ss a");
        final StringBuilder tmp = new StringBuilder();

        tmp.append(title).append(',');
        tmp.append(dfdate.format(dueDate)).append(',');
        tmp.append(description);
        tmp.append('\n');

        return tmp.toString();
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
     * 
     * @return labelColor color of label
     */
    public Color getLabelColor() {
        return labelColor;
    }

    /**
     * 
     * @param labelColor
     *            color for the label to be
     */
    public void setLabelColor(Color labelColor) {
        this.labelColor = labelColor;
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
        return new ArrayList<Integer>(activityIds);
    }

    /**
     * @return shortTitle shortened title for tabs and MiniTaskView
     */
    public String getShortTitle() {
        return this.shortenString(title);
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

    /**
     * @return the date task was created
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @return if task is archived
     */
    public boolean getIsArchived() {
        return isArchived;
    }

    /**
     * @param isArchived
     *            boolean indicating if task is archived
     */
    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

}

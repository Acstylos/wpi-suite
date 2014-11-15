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

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * The model end of the Activity object, the history is an auto generated activity
 * while the comments are manually added activities
 */
public class ActivityModel {

    private int activityId;
    private User user;
    private String activity;

    /**
     * Default constructor for a default ActivityModel
     */
    public ActivityModel() {
	this.activityId = -1;
	this.user = new User("", "", "", -1);
	this.activity = "";
    }

    /**
     * Constructor for a activity with specific properties. Other properties are
     * the same as the default constructor
     * @param activityId the id of the activity
     * @param user the user that added the activity
     * @param activity either auto-generated or manually added comments
     */
    public ActivityModel(int activityId, User user, String activity) {
	this.activityId = activityId;
	this.user = user;
	this.activity = activity;
    }

    /**
     * @return the activity ID
     */
    public int getactivityID() {
	return activityId;
    }

    /**
     * @param activityID the activity ID to be set
     */
    public void setactivityID(int activityID) {
	this.activityId = activityID;
    }

    /**
     * @return the user
     */
    public User getUser() {
	return user;
    }

    /**
     * @param user the user to be set
     */
    public void setUser(User user) {
	this.user = user;
    }

    /**
     * @return the activity
     */
    public String getactivity() {
	return activity;
    }

    /**
     * @param activity the activity to be set
     */
    public void setactivity(String activity) {
	this.activity = activity;
    }
}

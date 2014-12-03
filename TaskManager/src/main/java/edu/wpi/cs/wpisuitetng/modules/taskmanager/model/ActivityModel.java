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

import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * The model end of the Activity object, the history is an auto generated
 * activity while the comments are manually added activities
 */
public class ActivityModel extends AbstractModel {

    private int id;
    private User user;
    private Date date;
    private String activity;
    private boolean isAutogen;

    /**
     * Default constructor for a default ActivityModel
     */
    public ActivityModel() {
        this.id = -1;
        this.user = new User("", "", "", -1);
        this.activity = "";
        this.date = new Date();
        this.isAutogen = false;

    }

    /**
     * Constructor for a activity with specific properties. Other properties are
     * the same as the default constructor
     * 
     * @param id
     *            the id of the activity
     * @param user
     *            the user that added the activity
     * @param activity
     *            either auto-generated or manually added comments
     * @param isAutogen
     *            either auto-generated or manually added
     */
    public ActivityModel(int id, User user, String activity, Date date,
            boolean isAutogen) {
        this.id = id;
        this.user = user;
        this.activity = activity;
        this.date = date;
        this.isAutogen = isAutogen;
    }

    /**
     * @return true or false if this is an auto-generated comment
     */
    public boolean getIsAutogen() {
        return isAutogen;
    }

    /**
     * @param isAutogen true or false if auto-generated comment
     */
    public void setIsAutogen(boolean isAutogen) {
        this.isAutogen = isAutogen;
    }
    
    /**
     * @return the date of the activity
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date
     *            the date of the activity
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the activity ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the activity ID to be set
     */
    public void setId(int id) {
        this.id = id;

    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to be set
     */
    public void setUser(User user) {
        this.user = user;

    }

    /**
     * @return the activity
     */
    public String getActivity() {
        return activity;
    }

    /**
     * @param activity
     *            the activity to be set
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * Copy all of the fields from another ActivityModel
     * 
     * @param other
     *            the ActivtyModel to be copied
     */
    public void copyFrom(ActivityModel other) {
        this.activity = other.getActivity();
        this.id = other.getId();
        this.user = other.getUser();
        this.isAutogen = other.getIsAutogen(); 
    }

    /**
     * Converts this activity object to a JSON string
     * 
     * @return A string in JSON representing this activity
     */
    public String toJson() {
        String json;
        Gson gson = new Gson();
        json = gson.toJson(this, ActivityModel.class);
        return json;
    }

    /**
     * Converts the given list of activities to a JSON string
     * 
     * @param alist
     *            A list of activities
     * 
     * @return A string in JSON representing the list of activities
     */
    public static String toJson(ActivityModel[] alist) {
        String json;
        Gson gson = new Gson();
        json = gson.toJson(alist, ActivityModel.class);
        return json;
    }

    /**
     * Convert the given JSON string to a ActivityModel instance
     * 
     * @return The JSON string representing the object
     */
    public static ActivityModel fromJson(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, ActivityModel.class);
    }

    /**
     * Convert the given JSON string with a JSON array of activities into an
     * array of activities
     * 
     * @return ActivityModel array
     */
    public static ActivityModel[] fromJsonArray(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, ActivityModel[].class);
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean identify(Object o) {
        // TODO Auto-generated method stub
        return null;
    }

}

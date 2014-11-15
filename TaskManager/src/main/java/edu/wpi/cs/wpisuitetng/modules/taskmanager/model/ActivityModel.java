package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class ActivityModel {

    private int activityID;
    private User user;
    private String activity;
    
    /**
     * Default constructor for a default ActivityModel
     */
    public ActivityModel() {
	this.activityID = -1;
	this.user = new User("","","",-1);
	this.activity = "";
    }
    
    /**
     * Constructor for a activity with specific properties. Other properties are
     * the same as the default constructor 
     * @param user the user that added the activity
     * @param activity the activity of the user
     */
    public ActivityModel(User user, String activity){
	this.activityID+=1;
	this.user = user;
	this.activity = activity;
    }

    /**
     * @return the activity ID
     */
    public int getactivityID() {
        return activityID;
    }

    /**
     * @param activityID the activity ID to be set
     */
    public void setactivityID(int activityID) {
        this.activityID = activityID;
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

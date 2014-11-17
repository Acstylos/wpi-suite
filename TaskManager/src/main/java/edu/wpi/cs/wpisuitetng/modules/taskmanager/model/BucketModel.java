package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Bucket is a list of serialized ID's of tasks
 * @author TheFloorIsJava
 *
 */
public class BucketModel extends AbstractModel {

    private int id;
    private String title;
    private ArrayList<Integer> taskIds;

    /**
     * Default constructor
     */
    public BucketModel(){
        this(-1, "Title");
    }

    /**
     * Constructor for the bucket model
     * @param id
     * @param title
     */
    public BucketModel(int id, String title){
        this.id = id;
        this.title=title;
        this.taskIds = new ArrayList <Integer>();
    }

    /**
     * Will Implement Later
     */
    @Override
    public void save() {
        // TODO Auto-generated method stub

    }

    /**
     * Will Implement Later
     */
    @Override
    public void delete() {
        // TODO Auto-generated method stub

    }

    /**
     * @return The JSON string for the object
     */
    @Override
    public String toJson() {
        return new Gson().toJson(this, BucketModel.class);
    }

    /**
     * Parses a JSON string to an object
     * @param json The JSON string for the BucketModel
     * @return The BucketModel parsed from the JSON string
     */
    public static BucketModel fromJson(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, BucketModel.class);
    }

    /**
     * Parses a JSON string to an array of objects
     * @param json The JSON string for the array of BucketModels
     * @return An array of BucketModels parsed from a Json array
     */
    public static BucketModel[] fromJsonArray(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, BucketModel[].class);
    }

    /**
     * Will implement later
     * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
     */
    @Override
    public Boolean identify(Object o) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return The ID of the bucket
     */
    public int getId(){
        return id;
    }

    /**
     * @param ID The ID of the bucket to be set
     */
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * add an task id to the tasks
     * @param id
     */
    public void addId(int id){
	taskIds.add(id);
    }
    
    /**
     * remove the task with id from the tasks
     * @param id
     */
    public void removeId(int id){
	taskIds.remove(id);
    }

    /**
     * @return The title of the bucket
     */
    public String getTitle(){
        return title;
    }

    /**
     * @param title The title of the bucket to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @param newTaskIds The list of task IDs to be set
     */
    public void setTaskIds(ArrayList <Integer> newTaskIds){
        this.taskIds = newTaskIds;
    }

    /**
     * @return The list of task IDs
     */
    public ArrayList<Integer> getTaskIds(){
        return this.taskIds;
    }

    /**
     * Copies all of the values from the given BucketModel to this Bucket
     * excluding the Id.
     * @param toCopyFrom the BucketModel to copy from.
     */
    public void copyFrom(BucketModel toCopyFrom) { 
        this.title = toCopyFrom.title;
        this.taskIds = toCopyFrom.taskIds;
    }
}

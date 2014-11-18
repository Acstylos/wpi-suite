package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Workflow is the container for the buckets
 * @author TheFloorIsJava
 *
 */
public class WorkflowModel extends AbstractModel{

    private int id;
    private String title;
    private ArrayList<Integer> bucketIds;
    private ArrayList<Integer> archive;
    
    /**
     * add task with the id to archive
     * @param id
     * 		id of the task that adds to the archive
     */
    public void addToArchive(int id){
	archive.add(id);
    }
    
    /**
     * get all the list of task ids in the archive
     * @return	a list of tasks ids in the archive
     */
    public ArrayList<Integer> getArchive(){
	return archive;
    }
    
    /**
     * remove a specific task id from archive
     * @param id
     * 		id of the task that removes from the archive
     */
    public void removeFromArchive(int id){
	archive.remove(id);
    }
    
    /**
     * Default constructor
     */
    public WorkflowModel(){
    	this(-1, "");
    }

    /**
     * Constructor for the workflow model
     * @param title
     * @param ID
     */
    public WorkflowModel(int ID, String title){
    	this.id = ID;
    	this.title=title;
    	this.bucketIds=new ArrayList<>();
    }
    
    /**
     * Will implement later
     */
    @Override
    public void save() {
	// TODO Auto-generated method stub

    }

    /**
     * Will implement later
     */
    @Override
    public void delete() {
	// TODO Auto-generated method stub

    }
    
    /**
     * @return The Json string for the object
     */
    @Override
    public String toJson(){
    	Gson gson = new Gson();
    	return gson.toJson(this, WorkflowModel.class);
    }
    
    /**
     * Parses a Json string to an object
     * @param json the json-encoded WorkflowModel to deserialize
     * @return the WorkflowModel contained in the given JSON
     */
    public static WorkflowModel fromJson(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, WorkflowModel.class);
    }
    
    /**
     * Parses a Json string to an array of objects
     * @param json The Json string for the array of BucketModels
     * @return An array of BucketModels parsed from the Json array
     */
    public static WorkflowModel[] fromJSONArray(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, WorkflowModel[].class);
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
     * gets the list of bucket IDs
     * @return the List of bucket IDs
     */
    public ArrayList<Integer> getBucketIds(){
    	return this.bucketIds;
    }
    
    /**
     * Sets the list of bucket IDs
     * @param bucketIDs The list of bucketIDs
     */
    public void setBucketIds(ArrayList<Integer> bucketIds) {
    	this.bucketIds = bucketIds;
    }

    /**
     * @return The title of the workflow
     */
    public String getTitle(){
    	return this.title;
    }

    /**
     * @param title The title of the workflow to be set
     */
    public void setTitle(String title){
    	this.title = title;
    }
    
    /**
     * @return The ID of the workflow
     */
    public int getId(){
    	return this.id;
    }

    /**
     * @param ID The ID of the workflow to be set
     */
    public void setId(int id){
    	this.id = id;
    }
    
    /**
	 * Copies all of the values from the given WorkflowModel to this WorkflowModel
	 * excluding the Id.
	 * @param toCopyFrom the BucketModel to copy from.
	 */
    public void copyFrom(WorkflowModel toCopyFrom) { 
    	this.id = toCopyFrom.id;
    	this.title = toCopyFrom.title;
    	this.bucketIds = toCopyFrom.bucketIds;
    }
}

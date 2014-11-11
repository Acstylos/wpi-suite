package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
/**
 * Workflow Model is the full screen (mainly the list of buckets)
 * @author TheFloorIsJava
 *
 */
public class WorkflowModel extends AbstractModel{

    private int ID;
    private String title;
    private ArrayList<Integer> bucketIDs;
	
    public WorkflowModel(int ID, String title, ArrayList<Integer> bucketIDs){
	this.ID = ID;
	this.title = title;
	this.bucketIDs = bucketIDs;
    }
	
    public WorkflowModel(){
	this.ID = 0;
	this.title = "title";
	this.bucketIDs = new ArrayList<Integer>();
    }

    public void save() {}
    public void delete() {}

    /**
     * Passes JSON Server an instance of this Class.
     */
    @Override
    public String toJson() {
	return new Gson().toJson(this, WorkflowModel.class);
    }

    /**
     * Deserializes the class.
     */
    public static WorkflowModel fromJSON(String json) {
	final Gson parser = new Gson();
	return parser.fromJson(json, WorkflowModel.class);
    }

    @Override
    public Boolean identify(Object o) {
	// TODO Auto-generated method stub
	return null;
    }
	
    /**
     * sets the ID of the workflow
     * @param ID serialized integer value of workflow
     */
    public void setID(int ID){
	this.ID = ID;
    }
	
    /**
     * retrieves the ID of workflow
     * @return ID serialized integer value of workflow
     */
    public int getID(){
	return ID;
    }
	
    /**
     * gets the list of Buckets
     * @return the List of Buckets
     */
    public ArrayList<Integer> getBucketIDs(){
	return this.bucketIDs;
    }
	
    /**
     * get Title of WorkflowModel
     * @return workflowTitle
     */
    public String getTitle(){
	return this.title;
    }
	
    /**
     * set title of WorkflowModel
     * @param title
     */
    public void setTitle(String title){
	this.title=title;
    }
}

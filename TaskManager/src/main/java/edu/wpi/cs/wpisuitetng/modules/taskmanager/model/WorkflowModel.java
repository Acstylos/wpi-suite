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
    private String workflowTitle;
    private ArrayList <BucketModel> listOfBucket;
    
    public WorkflowModel(){
	this("", -1);
    }
    
    public WorkflowModel(WorkflowModel another){
	this.ID = another.ID;
	this.workflowTitle = another.workflowTitle;
	this.listOfBucket = another.listOfBucket;
    }

    public WorkflowModel(String title, int ID){
	this.ID = ID;
	this.workflowTitle=title;
	this.listOfBucket=new ArrayList<>();

    }
    @Override
    public void save() {
	// TODO Auto-generated method stub

    }

    @Override
    public void delete() {
	// TODO Auto-generated method stub

    }

    /**
     * Returns an instance of WorkflowModel constructed using the given
     * WorkflowModel encoded as a JSON string.
     * 
     * @param json the json-encoded WorkflowModel to deserialize
     * @return the WorkflowModel contained in the given JSON
     */
    public static WorkflowModel fromJson(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, WorkflowModel.class);
    }

    @Override
    public String toJson() {
	return new Gson().toJson(this, WorkflowModel.class);

    }

    @Override
    public Boolean identify(Object o) {
	// TODO Auto-generated method stub
	return null;
    }
    /**
     * gets the list of Buckets
     * @return the List of Buckets
     */
    public ArrayList<BucketModel> getWorkflow(){
	return this.listOfBucket;
    }

    /**
     * get Title of WorkflowModel
     * @return workflowTitle
     */
    public String getTitle(){
	return this.workflowTitle;
    }

    /**
     * set title of WorkflowModel
     * @param title
     */
    public void setTitle(String title){
	this.workflowTitle=title;
    }
    
    /**
     * get the ID of WorkflowModel
     * @return ID
     */
    public int getID(){
	return this.ID;
    }
}

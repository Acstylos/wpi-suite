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

	private static int increment=1;
	private int ID;
	private String workflowTitle;
	private ArrayList <BucketModel> listOfBucket;
	
	WorkflowModel(String title){
		
		this.ID=increment; 
		this.workflowTitle=title;
		this.listOfBucket=new ArrayList<BucketModel>();
		WorkflowModel.increment++;
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
	 * Passes JSON Server an instance of this Class.
	 */
	@Override
	public String toJson() {
		return new Gson().toJson(this, WorkflowModel.class);

	}
	/**
	 * Will Implement Later
	 */
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
	 * returns the ID of this workflow Model
	 * @return ID
	 */
	public int getID() {
		return ID;
	}

}

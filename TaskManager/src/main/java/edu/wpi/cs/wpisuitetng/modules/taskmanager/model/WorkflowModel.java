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

<<<<<<< HEAD
	private static int increment=1;
	private int ID;
=======
	private static int ID=0;
>>>>>>> 3503417... BucketEntityManager.java and its tests class
	private String workflowTitle;
	private ArrayList <BucketModel> listOfBucket;
	
	WorkflowModel(String title){
<<<<<<< HEAD
		
		this.ID=increment; 
		this.workflowTitle=title;
		this.listOfBucket=new ArrayList<BucketModel>();
		WorkflowModel.increment++;
=======
		this.ID++;
		this.workflowTitle=title;
		this.listOfBucket=new ArrayList<BucketModel>();
		
>>>>>>> 3503417... BucketEntityManager.java and its tests class
	}
	/**
	 * Will Implement Later
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
<<<<<<< HEAD

=======
>>>>>>> 3503417... BucketEntityManager.java and its tests class
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
<<<<<<< HEAD
	/**
	 * returns the ID of this workflow Model
	 * @return ID
	 */
	public int getID() {
		return ID;
	}

}
=======
}

>>>>>>> 3503417... BucketEntityManager.java and its tests class

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
	
	private static int increment=1;
	private int ID;//Bucket ID
	private String title; //title of Bucket
	private ArrayList <Integer> bucket; //list of serialized IDs of tasks 
	
	BucketModel(String title){
		this.ID=increment;
		this.title=title;
		this.bucket=new ArrayList <Integer>();
		BucketModel.increment++;
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
	 * Gives JSon Server instance of this class
	 */
	@Override
	public String toJson() {
		return new Gson().toJson(this, BucketModel.class);

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
	 * retrieves the ID of Bucket.
	 * @return ID serialized integer value of bucket
	 */
	public int getID(){
		return ID;
	}
	
	/**
	 * gets private field title
	 * @return Title of Bucket
	 */
	public String getTitle(){
		return title;
	}
	/**
	 * Sets private field Bucket
	 * @param newBucket the new list of Integers to be set
	 */
	public void setBucket(ArrayList <Integer> newBucket){
		this.bucket=newBucket;
		
	}
	/**
	 * returns the private field bucket 
	 * @return bucket (list of integers)
	 */
	public ArrayList<Integer> getBucket(){
		return this.bucket;
	}
	

}

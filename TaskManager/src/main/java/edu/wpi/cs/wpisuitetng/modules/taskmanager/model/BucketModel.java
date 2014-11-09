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
	
	private static int ID=0;//Bucket ID
	private String title; //title of Bucket
	private ArrayList <Integer> bucket; //list of serialized IDs of tasks 
	
	BucketModel(String title){
		this.title=title;
		this.bucket=new ArrayList <Integer>();
		ID++;
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
	public String toJson() {
		return new Gson().toJson(this, BucketModel.class);

	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getID(){
		return ID;
	}
	
	public String getTitle(){
		return title;
	}

	public void setBucket(ArrayList <Integer> newBucket){
		this.bucket=newBucket;
		
	}
	
	public ArrayList<Integer> getBucket(){
		return this.bucket;
	}
	

}

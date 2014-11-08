package edu.wpi.cs.wpisuitetng.modules.taskmanager.workflow.model.bucket;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Basic Bucket class that contains the data to be stored for a Bucket
 * 
 * @version
 * @author Thefloorislava
 */
public class Bucket extends AbstractModel  {

	/** an ID for a Bucket  */
    private int id; // 
    
    /** the name of the bucket */
    private String name;
    
    /** 
     * list of integer because we need class of tasks done by other team 
     * will be changed later
     **/
    private List<Integer> bucketOfTasks = new ArrayList <Integer>();
   
    /**
     *  Getter for name
     *  
     *  @return name
     */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for the name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
     *  Getter for id
     *  
     *  @return id
     */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter for the id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
     * Constructs a bucket with default characteristics
     */
    public Bucket() {
    	super();
    	id = 1;
    	name = "";
    }
    
    /**
     * Returns an instance of workflow constructed using the given
     * workflow encoded as a JSON string.
     * 
     * @param json
     *            JSON-encoded Requirement to deserialize @return the
     *            workflow contained in the given JSON
     */
    public static Bucket fromJson(String json) {
        return new Gson().fromJson(json, Bucket.class);
    }
	
	
    /**
     * Method save.
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
     */
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * Method delete.
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
     */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	 /**
     * Method toJSON. @return String * @see
     * edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see
     * edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
     */
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
     * Method identify.
     * 
     * @param o Object @return Boolean * @see
     *            edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * @see
     *            edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
     */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}

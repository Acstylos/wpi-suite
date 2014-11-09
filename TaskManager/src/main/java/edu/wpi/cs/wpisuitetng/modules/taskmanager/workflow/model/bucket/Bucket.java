package edu.wpi.cs.wpisuitetng.modules.taskmanager.workflow.model.bucket;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Basic Bucket class that contains the data to be stored for a Bucket
 * 
 * @version
 * @author
 */
public class Bucket extends AbstractModel  {

	/** an ID for a Bucket  */
    private int id; // 
    
    /** the name of the bucket */
    private String name;
    
    private List<Integer> bucketOfTasks = new ArrayList <Integer>();
   

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Bucket is a list of serialized ID's of tasks
 * 
 * @author TheFloorIsJava
 *
 */
public class BucketModel extends AbstractModel {

    private int ID;// Bucket ID
    private String title; // title of Bucket
    private ArrayList<Integer> bucket; // list of serialized IDs of tasks

    public BucketModel(int ID, String title, ArrayList<Integer> bucket) {
        this.ID = ID;
        this.title = title;
        this.bucket = bucket;
    }

    public BucketModel() {
        this.ID = 0;
        this.title = "title";
        this.bucket = new ArrayList<Integer>();
    }

    public void save() {
    }

    public void delete() {
    }

    /**
     * Gives JSon Server instance of this class
     */
    @Override
    public String toJson() {
        return new Gson().toJson(this, BucketModel.class);
    }

    public static BucketModel fromJSON(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, BucketModel.class);
    }

    public Boolean identify(Object o) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Copy all of the fields from another BucketModel.
     * 
     * @param other
     */
    public void copyFrom(BucketModel other) {
        this.ID = other.getID();
        this.title = other.getTitle();
        this.bucket = other.getBucket();
    }

    /**
     * sets the ID of the bucket
     * 
     * @param ID
     *            serialized integer value of bucket
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * retrieves the ID of Bucket.
     * 
     * @return ID serialized integer value of bucket
     */
    public int getID() {
        return ID;
    }

    /**
     * gets private field title
     * 
     * @return Title of Bucket
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets private field Bucket
     * 
     * @param newBucket
     *            the new list of Integers to be set
     */
    public void setBucket(ArrayList<Integer> newBucket) {
        this.bucket = newBucket;

    }

    /**
     * returns the private field bucket
     * 
     * @return bucket (list of integers)
     */
    public ArrayList<Integer> getBucket() {
        return this.bucket;
    }
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Workflow is the container for the buckets
 * 
 * @author TheFloorIsJava
 *
 */
public class WorkflowModel extends AbstractModel {

    private int id;
    private String title;
    private ArrayList<Integer> bucketIds;

    /**
     * Default constructor
     */
    public WorkflowModel() {
        this(-1, "");
    }

    /**
     * Constructor for the workflow model
     * 
     * @param title
     * @param ID
     */
    public WorkflowModel(int ID, String title) {
        this.id = ID;
        this.title = title;
        this.bucketIds = new ArrayList<>();
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
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this, WorkflowModel.class);
    }

    /**
     * Parses a Json string to an object
     * 
     * @param json
     *            the json-encoded WorkflowModel to deserialize
     * @return the WorkflowModel contained in the given JSON
     */
    public static WorkflowModel fromJson(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, WorkflowModel.class);
    }

    /**
     * Parses a Json string to an array of objects
     * 
     * @param json
     *            The Json string for the array of BucketModels
     * @return An array of BucketModels parsed from the Json array
     */
    public static WorkflowModel[] fromJSONArray(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, WorkflowModel[].class);
    }

    /**
     * determines if the other object is of class WorkFlowModel
     * @param o other object
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
     */
    @Override
    public Boolean identify(Object o) {
        try{
            if(this.getClass().equals(((WorkflowModel) o).getClass()))
                    return true;
        }
        catch (Exception e){
            return false;
        }
       return null;
    }

    /**
     * gets the list of bucket IDs
     * 
     * @return the List of bucket IDs
     */
    public ArrayList<Integer> getBucketIds() {
        return this.bucketIds;
    }

    /**
     * Sets the list of bucket IDs
     * 
     * @param bucketIDs
     *            The list of bucketIDs
     */
    public void setBucketIds(ArrayList<Integer> bucketIds) {
        this.bucketIds = bucketIds;
    }
    
    /**
     * Adds a single ID to the bucketId list.
     * @param id ID of the bucket to add to the list.
     */
    public void addBucketId(int id){
        if(!this.bucketIds.contains(id)){
            this.bucketIds.add(id);
        }
    }

    /**
     * @return The title of the workflow
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title
     *            The title of the workflow to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Determines if this WorkflowModel is equal to that WorkflowModel
     * @param that the other workflowModel
     * @return boolean, true if the id, title, and bucketIds are equal. otherwise false
     */
    public boolean equals(WorkflowModel that) {
        return (this.id == that.id && this.title.equals(that.title) && this.bucketIds
                .equals(that.bucketIds));
    }

    /**
     * @return The ID of the workflow
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param ID
     *            The ID of the workflow to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Copies all of the values from the given WorkflowModel to this
     * WorkflowModel excluding the Id.
     * 
     * @param toCopyFrom
     *            the BucketModel to copy from.
     */
    public void copyFrom(WorkflowModel toCopyFrom) {
        this.id = toCopyFrom.id;
        this.title = toCopyFrom.title;
        this.bucketIds = toCopyFrom.bucketIds;
    }
}

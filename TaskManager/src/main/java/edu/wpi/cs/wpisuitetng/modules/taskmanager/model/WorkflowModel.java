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
    private int defaultBucketIndex;
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
        id = ID;
        this.title = title;
        bucketIds = new ArrayList<>();
        defaultBucketIndex = 0;
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
        final Gson gson = new Gson();
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
     * 
     * @param o
     *            other object
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
     */
    @Override
    public Boolean identify(Object o) {
        try {
            if (this.getClass().equals(((WorkflowModel) o).getClass())) {
                return true;
            }
        } catch (Exception e) {
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
        return bucketIds;
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
     * 
     * @param id
     *            ID of the bucket to add to the list.
     */
    public void addBucketId(int id) {
        if (!bucketIds.contains(id)) {
            bucketIds.add(id);
        }
    }

    /**
     * Deletes a single bucket from the list of buckets.
     * 
     * @param id
     *            Id of the bucket to remove.
     */
    public void deleteBucketId(int id) {
        if (bucketIds.contains(id)) {
            bucketIds.remove((Object) id);
        }
    }

    /**
     * Swaps two bucektId's in the BucketId's list.
     * 
     * @param id1
     *            The first bucketId
     * @param id2
     *            The second bucketId
     */
    public void swapBucketIds(int id1, int id2) {
        final int index1 = bucketIds.indexOf(id1);
        final int index2 = bucketIds.indexOf(id2);
        final int temp = bucketIds.get(index1);
        bucketIds.set(index1, bucketIds.get(index2));
        bucketIds.set(index2, temp);
    }

    /**
     * @return The title of the workflow
     */
    public String getTitle() {
        return title;
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
     * 
     * @param that
     *            the other workflowModel
     * @return boolean, true if the id, title, and bucketIds are equal.
     *         otherwise false
     */
    public boolean equals(WorkflowModel that) {
        return (id == that.id && title.equals(that.title) && bucketIds
                .equals(that.bucketIds));
    }

    /**
     * @return The ID of the workflow
     */
    public int getId() {
        return id;
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
        id = toCopyFrom.id;
        title = toCopyFrom.title;
        bucketIds = toCopyFrom.bucketIds;
        defaultBucketIndex = toCopyFrom.defaultBucketIndex;
    }

    /**
     * @return Gets the bucket which is designated for new tasks.
     */
    public int getDefaultBucketIndex() {
        return defaultBucketIndex;
    }

    /**
     * @param defaultBucketIndex
     *            The bucket that wil be designated for new tasks.
     */
    public void setDefaultBucketIndex(int defaultBucketIndex) {
        this.defaultBucketIndex = defaultBucketIndex;
    }
}

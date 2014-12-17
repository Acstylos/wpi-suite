/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.BucketView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ManageBucketsPanel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.WorkflowView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Creates 4 static buckets for the workflow and passes it to the view
 * 
 * @author TheFloorIsJava
 * 
 */

public class WorkflowPresenter {

    private WorkflowView view;
    private ManageBucketsPanel manageView;
    private WorkflowModel model;
    private Map<Integer, BucketPresenter> bucketPresenters = new HashMap<>();

    /**
     * Constructor does nothing for now
     *
     * @param workflowId
     *            the id of the workflow 
     */
    public WorkflowPresenter(int workflowId) {
        this.model = new WorkflowModel();
        model.setId(workflowId);
        this.view = new WorkflowView("Loading...");
        this.manageView = new ManageBucketsPanel(this.model);
        registerCallbacks();
    }

    /**
     * Request the server for a new workflow or the workflow corresponding to the
     * current ID
     */
    public void load() {
        // Sends a request for the TaskViews associated with the BucketView
        final Request request = Network.getInstance().makeRequest(
                "taskmanager/workflow", HttpMethod.GET);
        request.addObserver(new WorkflowObserver(this, HttpMethod.GET));
        request.send();
    }

    /**
     * Initializes the workflow and stores it to the DB.
     * @param presenter Presenter to associate the workflow with.
     */
    public void initWorkflow(WorkflowPresenter presenter){
        saveBaseWorkflow(presenter);
        BucketPresenter.saveBaseBuckets();
        writeModelToView();
    }

    /**
     * Adds in a default workflow with id=1 to the DB
     * @param presenter Presenter to associate the workflow with.
     */
    private void saveBaseWorkflow(WorkflowPresenter presenter){
        WorkflowModel workflow = new WorkflowModel(1, "Workflow");
        ArrayList<Integer> baseBucketList = new ArrayList<Integer>();
        baseBucketList.add(1);
        baseBucketList.add(2);
        baseBucketList.add(3);
        baseBucketList.add(4);
        workflow.setBucketIds(baseBucketList);
        presenter.setModel(workflow);
        final Request request = Network.getInstance().makeRequest("taskmanager/workflow", HttpMethod.PUT);
        request.setBody(workflow.toJson());
        request.addObserver(new WorkflowObserver(presenter, HttpMethod.PUT));
        request.send();
    }

    /**
     * Set the view of the model
     */
    public void writeModelToView() {
        view.removeAll();
        view.addSpacers();
        for (int i : model.getBucketIds()) {
            if (!bucketPresenters.containsKey(i)) {
                bucketPresenters.put(i, new BucketPresenter(i, this));
            }
            bucketPresenters.get(i).load();
            BucketView bucketView = bucketPresenters.get(i).getView();
            view.addBucketToView(bucketView);
        }
        updateManageWorkflowView();
        view.revalidate();
        view.repaint();
    }

    /**
     * Write the model to the network/database. Must be created already.
     */
    private void saveModel() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/workflow", HttpMethod.POST); // Update.
        request.setBody(model.toJson());
        request.addObserver(new WorkflowObserver(this, HttpMethod.POST));
        request.send();
    }

    /**
     * Handles the result of a GET request
     * 
     * @param models
     *            The models sent from the network
     */
    public void responseGet(WorkflowModel model) {
        this.model = model;
        writeModelToView();
    }

    /**
     * Handles the result of a POST request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responsePost(WorkflowModel model) {

    }

    /**
     * Handles the result of a PUT request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responsePut(WorkflowModel model) {
    }

    /**
     * Handles the result of a DELETE request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responseDelete(WorkflowModel model) {

    }

    /**
     * @return The view corresponding to the model
     */
    public WorkflowView getView() {
        return view;
    }

    /**
     * @param model The model this workflow presenter represents.
     */
    public void setModel(WorkflowModel model){
        this.model = model;
    }

    /**
     * @return The model corresponding to the presenter
     */
    public WorkflowModel getModel() {
        return model;
    }

    /**
     * @return The list of BucketPresenters inside of the workflow.
     */
    public Map<Integer, BucketPresenter> getBucketPresenters() {
        return bucketPresenters;
    }

    /**
     * @param id ID of bucket to return
     * @return a bucket with the corresponding ID
     */
    public BucketPresenter getBucketPresenterById(int id){
        return bucketPresenters.get(id);
    }

    /**
     * move tasks from one bucket to another
     * 
     * @param taskId
     *            The id of tasks needed to be moved
     * @param toId
     *            The id of the bucket task moved to
     * @param fromId
     *            The id of the bucket task moved from
     */
    public void moveTask(int taskId, int toId, int fromId) {
        bucketPresenters.get(toId).addTask(taskId, bucketPresenters.get(fromId).getTask(taskId));
        bucketPresenters.get(fromId).removeTask(taskId);
    }

    /**
     * get the bucket id from the hashmap
     * @param id the id of the bucket to be retrieved
     * @return the bucket model from the hashmap
     */
    public BucketPresenter getBucket(int id) {
        return bucketPresenters.get(id);
    }

    /**
     * Gets a csv representation for various calendar formats.
     *
     * @return CSV entry, with a newline.
     */
    public String getCsv() {
        String t = "Subject,Start Date, Description\n";
        for (BucketPresenter i : bucketPresenters.values()) {
            t = t + i.getCsv();
        }
       return t;
    }
    
    /*
     * Adds a bucket ID to the list of bucketIDs in the workflow model. Sends an async
     * update to the database.
     * 
     * @param id ID of the existing bucket.
     * @param bucket bucketPresenter associated with the task
     */
    public void addBucket(int id, BucketPresenter bucket){
        model.addBucketId(id);
        if (!bucketPresenters.containsKey(id)) {
            bucketPresenters.put(id, bucket);
        }        
        saveModel();
    }
    
    /**
     * Removes a bucket from the presenters list, model list, view, and the database.
     * @param id Id of the bucket to remove.
     */
    public void removeBucket(int id){
        bucketPresenters.remove(id);
        model.deleteBucketId(id);
        saveModel();
        writeModelToView();
        // TODO: remove from the database.
    }

    /**
     * @return The ManageWorkflowPanel for this workflow.
     */
    public ManageBucketsPanel getManageWorkflowView(){
        return this.manageView;
    }

    /**
     * Updates the manageWorkflowView to reflect the buckets in the workflow.
     */
    public void updateManageWorkflowView(){
        List<String> bucketNamesList = new ArrayList<String>();
        for(int id : model.getBucketIds()){
            bucketNamesList.add(bucketPresenters.get(id).getModel().getTitle());
        }
        String[] bucketNamesArray = new String[bucketNamesList.size()];
        bucketNamesArray = bucketNamesList.toArray(bucketNamesArray);
        manageView.addBucketNameArrayToList(bucketNamesArray);
    }
    
    /**
     * Adds all buckets in the bucketPresenters map to the view.
     * Takes into account the order mapping from mapPositionToId
     */
    public void addAllBucketsToView(){
        this.view.removeAll();
        this.view.addSpacers();
        for(int id : model.getBucketIds()){
            BucketView bucketView = bucketPresenters.get(id).getView();
            view.addBucketToView(bucketView);
        }
    }

    /**
     * Registers all listeners for all views related to workflow.
     */
    public void registerCallbacks(){
        /*
         * Adds a close button to the managerView, so that you can close the tab.
         */
        this.manageView.addCloseButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = MainView.getInstance().indexOfComponent(manageView);
                MainView.getInstance().remove(index);
                MainView.getInstance().setSelectedIndex(0);
            }

        });

        /*
         * Creates a new bucket in the DB, and adds it to the workflowView. 
         */
        this.manageView.addAddBucketButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                BucketPresenter bucketPresenter = new BucketPresenter(0, MainView.getInstance().getWorkflowPresenter());
                bucketPresenter.getModel().setTitle(manageView.getNewBucketTitle());
                bucketPresenter.createInDatabase();
                view.addBucketToView(bucketPresenter.getView());
                try {
                    Thread.sleep(500); // needed to make sure the ManageWorkflowPanel reflects changes in time.
                    updateManageWorkflowView();
                } catch (InterruptedException e1) {
                    System.err.println("addBucketButton: sleeping for 500 ms failed: " + e1.getStackTrace());
                }
            }

        });

        /*
         * Deletes a bucket in the DB, and resets the workflow view.
         * ONLY DELETES IF THERE ARE NO TASKS AND IT IS NOT THE LAST BUCKET IN ON THE SCREEN
         */
        this.manageView.addDeleteBucketButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = model.getBucketIds().get(manageView.getBucketListIndex()); //getBucketIdFromList(mapPositionToId);
                if(bucketPresenters.size() > 1){
                    if(bucketPresenters.get(id).getModel().getTaskIds().isEmpty()){
                        removeBucket(id);
                        updateManageWorkflowView();
                    }
                }
            }

        });
        
        /*
         * Moves buckets left in the workflow view. Up == left in the workflow view.
         * Moves buckets up in the ManageWorkflow List.
         */
        this.manageView.addMoveBucketUpButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentIndex = manageView.getBucketListIndex();
                int currentId = model.getBucketIds().get(currentIndex);
                if(currentIndex > 0){ // if current index is 0, we can't move it more left.
                    int  leftId = model.getBucketIds().get(currentIndex-1);
                    model.swapBucketIds(currentId, leftId);
                }
                updateManageWorkflowView();
                addAllBucketsToView();
                saveModel();
            }
        });

        /*
         * Moves buckets right in the workflow view. Down == right in the workflow view.
         * Moves buckets down in the ManageWorkflow List.
         */
        this.manageView.addMoveBucketDownButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentIndex = manageView.getBucketListIndex();
                int currentId = model.getBucketIds().get(currentIndex);
                if(currentIndex < model.getBucketIds().size()){ // if current index is > total indexes, we can't move it more right.
                    int  leftId = model.getBucketIds().get(currentIndex+1);
                    model.swapBucketIds(currentId, leftId);
                }
                updateManageWorkflowView();
                addAllBucketsToView();
                saveModel();
            }
        });
    }
}

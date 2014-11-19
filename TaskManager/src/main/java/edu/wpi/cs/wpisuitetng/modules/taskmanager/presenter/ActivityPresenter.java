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

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.ActivityModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ActivityView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class ActivityPresenter {

    private ActivityView view;
    private ActivityModel model;
    private TaskPresenter parentTask;
    private User user;

    /**
     * Constructor for an activity presenter
     * 
     * @param model
     *            the model associated with the presenter
     * 
     * @param task
     *            the task associated with the activity
     */
    public ActivityPresenter(ActivityModel model, TaskPresenter task) {
        this.model = model;
        this.view = new ActivityView();
        this.parentTask = task;
        registerCallbacks();
        load();
    }

    /**
     * Constructor for an activity presenter
     * 
     * @param id
     *            the id of the specific activity made
     * @param task
     *            the task associated with the activity
     */
    public ActivityPresenter(int id, TaskPresenter task) {
        this.parentTask = task;
        this.model = new ActivityModel();
        this.model.setId(id);
        this.view = new ActivityView();
        registerCallbacks();
        load();
    }

    /**
     * Updates the view with information from the model
     */
    public void writeModelToView() {
        view.setActivity(model.getActivity());
        view.setUser(user);
        view.setDate(model.getDate());
    }

    /**
     * Updates the model with the information from the view
     */
    public void writeViewToModel() {
        model.setActivity(view.getActivity());
        model.setDate(view.getDate());
        model.setId(view.getUser().getIdNum());
        saveModel();
        loadUser();
    }

    /**
     * Requests the server for a new bucket or the bucket corresponding to the
     * current ID
     */
    public void load() {
        HttpMethod method;
        String id = "/" + model.getId();
        if (model.getId() == 0) { // Put = create a new model
            method = HttpMethod.PUT;
            id = "";
        } else {// Retrieve a model
            method = HttpMethod.GET;
        }

        // Sends a request for the ActivityViews associated with the
        // ActivityView
        final Request request = Network.getInstance().makeRequest(
                "taskmanager/activity" + id, method);
        if (method == HttpMethod.PUT) {
            request.setBody(model.toJson());
        }
        request.addObserver(new ActivityObserver(this, method)); // add an
        // observer to
        // the response
        request.send();
        
        loadUser();
    }
    
    /**
     * Loads the user from the database with the userid associated wtih the activity
     */
    public void loadUser(){
    	// Sends a request to the network for the user corresponding to the userId of the activity
        final Request request = Network.getInstance().makeRequest(
        		"coreuser" + model.getId(), HttpMethod.GET);
        request.addObserver(new ActivityObserver(this, HttpMethod.GET));
        //send the response to the server
        request.send();
    }

    /**
     * Register callbacks with the local view.
     */
    private void registerCallbacks() {
        view.addOnAddSaveListener((ActionEvent event) -> {
            ActivityPresenter.this.saveModel();
        });
    }

    /**
     * 
     * @param activityId
     */
    public void saveActivity(int activityId) {
        this.model.setId(activityId);
        saveModel();
    }

    /**
     * Write the model to the network/database. Must be created already.
     */
    private void saveModel() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/activity", HttpMethod.POST); // Update.
        request.setBody(model.toJson());
        request.addObserver(new ActivityObserver(this, HttpMethod.POST));
        request.send();
    }

    /**
     * Handles the result of a GET request
     * 
     * @param models
     *            The models sent from the network
     */
    public void responseGet(ActivityModel[] models) {
        if (models[0].getId() == 0)
            return;
        this.model = models[0];
    }
    
    /**
     * Handles the result of a GET request
     * 
     * @param models
     *            The models sent from the network
     */
    public void responseGet(User[] models) {
        if (models[0].getIdNum() == 0)
            return;
        this.user = models[0];
    }

    /**
     * Handles the result of a POST request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responsePost(ActivityModel model) {

    }

    /**
     * Handles the result of a PUT request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responsePut(ActivityModel model) {
        this.model = model;
    }

    /**
     * Handles the result of a DELETE request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responseDelete(ActivityModel model) {

    }

    /**
     * @return The view corresponding to the model
     */
    public ActivityView getView() {
        return view;
    }

    /**
     * @return The model corresponding to the presenter
     */
    public ActivityModel getModel() {
        return model;
    }

    /**
     * @return The task presenter corresponding to the presenter
     */
    public TaskPresenter getTask() {
        return parentTask;
    }

    /**
     * @param model
     *            THe model of the presenter to be set
     */
    public void setModel(ActivityModel model) {
        this.model = model;
    }
}
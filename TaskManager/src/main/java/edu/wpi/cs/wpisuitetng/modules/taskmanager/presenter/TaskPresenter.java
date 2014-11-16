/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.BucketView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

/**
 * This class creates a TaskView and updates the task with new information from
 * the database. This lets you modify the task, and will let the user modify it
 * as well.
 */
public class TaskPresenter {

    /** View for the task. */
    private TaskView view;
    /** Model for the task. */
    private TaskModel model;
    
    private BucketPresenter bucket;

    
    
    /**
     * Constructs a TaskPresenter for the given model. Constructs the view
     * offscreen, available if you call getView().
     * 
     * @param model
     *            model to copy
     */
    public TaskPresenter(TaskModel model, BucketPresenter bucket) {
        this.model = model;
        this.bucket = bucket;
        view = new TaskView(model.getTitle(), model.getEstimatedEffort(),
                model.getDescription(),  model.getDueDate());
        registerCallbacks();
        
        Request request = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.PUT);
        request.setBody(this.model.toJson());
        request.addObserver(new TaskObserver(this));
        request.send();
    }

    
    /**
     * 
     * @param id
     */
    public TaskPresenter(int id, BucketPresenter bucket){
    	this.bucket = bucket;
        this.model = new TaskModel();
        this.model.setId(id);
        this.view = new TaskView("Loading...", 0, "", new Date(0, 0, 1));
        
    	reloadView();
    }
    
    /**
     * Register callbacks with the local view.
     */
    private void registerCallbacks() {
        view.addOnSaveListener((ActionEvent event) -> {
            TaskPresenter.this.saveView();
        });
        view.addOnReloadListener((ActionEvent event) -> {
            TaskPresenter.this.reloadView();
        });
    }

    /**
     * Save the fields entered in the view.
     */
    private void saveView() {
        updateModel();
        
        Request request = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.POST);
        request.setBody(this.model.toJson());
        request.addObserver(new TaskObserver(this));
        request.send();
    }

    /**
     * Have the presenter reload the view from the model.
     */
    private void reloadView() {
        Request request = Network.getInstance().makeRequest("taskmanager/task/" + this.model.getId(), HttpMethod.GET);
        request.addObserver(new TaskObserver(this));
        request.send();
    }
    
    /**
     * Update the model with data from the view
     */
    public void updateModel() {
        model.setTitle(view.getTaskNameField());
        model.setEstimatedEffort(view.getEstimatedEffort());
        model.setDescription(view.getDescriptionText());
        //model.setDueDate(view.getDueDate());
    }
    
    /**
     * Update the view with data from the model
     */
    public void updateView() {
        view.setTaskNameField(model.getTitle());
        view.setEstimatedEffort(model.getEstimatedEffort());
        view.setDescriptionText(model.getDescription());
        view.setDueDate(model.getDueDate());
    }
    
    /**
     * Get the view for this Task.
     */
    public TaskView getView() {
        return view;
    }

    /**
     * Get the model for this class.
     * 
     * @return This provider's model.
     */
    public TaskModel getModel() {
        return model;
    }
    
    /**
     * Set the model for this class.
     * 
     * @param model This provider's model.
     */
    public void setModel(TaskModel model) {
        this.model = model;
    }
    
    public BucketPresenter getBucket(){
    	return bucket;
    }
}

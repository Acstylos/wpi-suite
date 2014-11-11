/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenterObserver;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

import java.awt.event.ActionEvent;
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

    /**
     * Constructs a TaskPresenter for the given model. Constructs the view
     * offscreen, available if you call getView().
     * 
     * @param model
     *            model to copy
     */
    public TaskPresenter(int TaskID) {
        this.model = new TaskModel();
        view = new TaskView("Loading...", 0, "Loading...", new Date());
        registerCallbacks();
	loadModel();
    }

    /**
     * Register callbacks with the local view.
     */
    private void registerCallbacks() {
        view.addOnSaveListener((ActionEvent event) -> {
		TaskPresenter.this.writeViewToModel();
		TaskPresenter.this.saveModel();
	    });
        view.addOnReloadListener((ActionEvent event) -> {
		TaskPresenter.this.loadModel();
		TaskPresenter.this.writeModelToView();
	    });
    }

    /**
     * Save the fields entered in the view.
     */
    private void writeViewToModel() {
        model.setTitle(view.getTitleText());
        model.setEstimatedEffort(view.getEstimatedEffort());
        model.setDescription(view.getDescriptionText());
        model.setDueDate(view.getDueDate());
    }

    /**
     * Have the presenter reload the view from the model.
     */
    private void writeModelToView() {
        view.setTitleText(model.getTitle());
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
     * Load the model from the network/database. Creates if TaskID is zero.
     */
    private void loadModel() {
	HttpMethod method;
	if (model.getID() == 0) { // Create
	    method = HttpMethod.PUT;
	} else { // Get
	    method = HttpMethod.GET;
	}

	Request request = Network.getInstance().makeRequest("taskmanager/taskmodel/" + model.getID(), method);
	request.addObserver(new TaskPresenterObserver(this, method));
	request.send();
    }

    /**
     * Write the model to the network/database. Must be created already.
     */
    private void saveModel() {
	Request request = Network.getInstance().makeRequest("taskmanager/taskmodel/" + model.getID(), HttpMethod.POST); // Update.
	request.setBody(model.toJson());
	request.addObserver(new TaskPresenterObserver(this, HttpMethod.POST));
	request.send();
    }

    /**
     * Respond to a get.
     */
    public void responseGet(TaskModel model) {
	if (model.getID() == 0)
	    return;
	this.model = model;
	writeModelToView();
    }

    /**
     * Respond to an update.
     */
    public void responseUpdate(TaskModel model) {

    }

    /**
     * Respond to a get.
     */
    public void responseCreate(TaskModel model) {

    }

    /**
     * Respond to a Delete.
     */
    public void responseDelete(TaskModel model) {

    }

    /**
     * Get the model for this class.
     *
     * @return This provider's model.
     */
    public TaskModel getModel() {
        return model;
    }
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.ActivityModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ActivityView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.CommentView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class creates an ActivityView and manages the comments/history.
 */
public class ActivityPresenter {

    private CommentView commentView;
    private ActivityView view;
    private ActivityModel model;
    private TaskPresenter parentTask;

    /**
     * Default constructor
     */
    public ActivityPresenter() {
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
        this(task, "", false);
        this.model.setId(id);
    }

    /**
     * Constructs an Activity Presenter with a task, string and boolean
     * 
     * @param task
     *            the task presenter associated with this activityPresenter
     * @param activity
     *            the comment string being used
     * @param isAutogen
     *            true or false to determine if this is auto or manually
     *            generated
     */
    public ActivityPresenter(TaskPresenter task, String activity,
            boolean isAutogen) {
        this.parentTask = task;
        this.model = new ActivityModel();
        this.commentView = task.getView().getCommentView();
        this.model.setActivity(activity);
        this.model.setIsAutogen(isAutogen);
        this.view = new ActivityView(this.model.getActivity());
    }

    /**
     * Create an Activity Presenter and a model with the given Text
     * 
     * @param text
     *            the typed comment
     */
    public ActivityPresenter(String text) {
        this.model = new ActivityModel();
        this.view = new ActivityView();
        this.model.setActivity(text);

    }

    /**
     * Create a new activity in the database. Initializes an async network
     * request with an observer.
     */
    public void createInDatabase() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/activity", HttpMethod.PUT);
        request.setBody(this.model.toJson());
        request.addObserver(new ActivityObserver(this));
        request.send();
    }

    /**
     * Updates the view with information from the model
     */
    public void updateView() {
        view.setActivity(model.getActivity());
        if (model.getIsAutogen()) {
            commentView.postHistory(view);
        }
        else
            commentView.postActivity(view);
        commentView.revalidate();
        commentView.repaint();

        // view.setUser(model.getUser());
        // view.setDate(model.getDate());

    }

    /**
     * Updates the model with the information from the view
     */
    public void updateModel() {
        model.setActivity(view.getActivity());
        // model.setDate(view.getDate());
        // model.setUser(view.getUser());
    }

    /**
     * loads the array of Activity Models from the Database
     */
    public void load() {
        final Request request = Network.getInstance().makeRequest(
                "taskmanager/activity/" + model.getId(), HttpMethod.GET);
        request.addObserver(new ActivityObserver(this));

        request.send();
    }

    /**
     * Write the model to the network/database. Must be created already.
     */
    private void updateToDataBase() {
        updateModel();

        Request request = Network.getInstance().makeRequest(
                "taskmanager/activity", HttpMethod.POST); // Update.
        request.setBody(model.toJson());
        request.addObserver(new ActivityObserver(this));
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
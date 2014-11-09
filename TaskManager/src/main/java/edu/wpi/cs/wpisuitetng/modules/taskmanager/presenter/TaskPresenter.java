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
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;

import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * This class creates a TaskView and updates the task with new information from
 * the database. This lets you modify the task, and will let the user modify it
 * as well.
 */
public class TaskPresenter {

    /** View for the task. */
    private final TaskView view;
    /** Model for the task. */
    private final TaskModel model;

    /**
     * Constructs a TaskPresenter for the given model. Constructs the view
     * offscreen, available if you call getView().
     * 
     * @param model
     *            model to copy
     */
    public TaskPresenter(TaskModel model) {
        this.model = model;
        view = new TaskView(model.getTitle(), model.getEstimatedEffort(),
                model.getDescription(),  model.getDueDate());
        registerCallbacks();
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
        model.setTitle(view.getTitleText());
        model.setEstimatedEffort(view.getEstimatedEffort());
        model.setDescription(view.getDescriptionText());
        model.setDueDate(view.getDueDate());
    }
    /**
     * Get the view for this Task.
     */
    public TaskView getView() {
        return view;
    }

    /**
     * Have the presenter reload the view from the model.
     */
    public void reloadView() {
        view.setTitleText(model.getTitle());
        view.setEstimatedEffort(model.getEstimatedEffort());
        view.setDescriptionText(model.getDescription());
        view.setDueDate(model.getDueDate());
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

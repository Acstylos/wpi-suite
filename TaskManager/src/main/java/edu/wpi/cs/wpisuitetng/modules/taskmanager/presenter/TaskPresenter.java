/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;

import java.util.Date;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class creates a TaskView and updates the task with
 * new information from the database. This lets you modify the task,
 * and will let the user modify it as well.
 */
public class TaskPresenter { // implements ActionListener {

    	/** View for the task. */
	private final TaskView view;
	/** Model for the task. */
	private final TaskModel model;

	/**
	 * Constructs a TaskPresenter for the given model. Constructs the
	 * view offscreen, available if you call getView().
	 * @param model = model to copy
	 */
	public TaskPresenter(TaskModel model) {
		this.model = model;
		view = new TaskView(model.getTitle(), model.getEstimatedEffort(),
		                    model.getDescription(), new Date(114, 11, 18));
	}

	/**
	 * Get the view for this Task.
	 */
	public TaskView getView() {
		return view;
	}

	/**
	 * Get the model for this class.
	 */
	public TaskModel getModel() {
	    return model;
	}
}

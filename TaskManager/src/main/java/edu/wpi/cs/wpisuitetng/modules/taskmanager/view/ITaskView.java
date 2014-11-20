/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskObserver;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;

import java.awt.event.ActionListener;

/**
 * Interface for any view in model-view-presenter that displays a task.
 * Concrete classes should implement this interface to hide implementation
 * details from {@link TaskPresenter} and {@link TaskObserver}.
 * 
 * @see TaskModel
 * @see TaskPresenter
 */
public interface ITaskView {
    /**
     * @param model The model that the view should display
     */
    public void setModel(TaskModel model);

    /**
     * @return A model containing the data from the view.  If the view is
     * editable, this should contain the modified data.
     */
    public TaskModel getModel();

    /**
     * Add a listener to be called when user input from the view indicates
     * that the task should be saved.
     *
     * @param listener If this view exposes a user interface to save a task,
     * this will be added as a listener to a UI element.
     */
    public void addOnSaveListener(ActionListener listener);

    /**
     * Add a listener to be called when a user cancels the interaction with
     * the task through the user interface.
     *
     * @param listener If this view has a cancel feature, this will be added
     * as a listener to a UI element.
     */
    public void addOnCancelListener(ActionListener listener);

    /**
     * Add a listener to be called when a user clears the fields of a task.
     *
     * @param listener If this view has a clear feature, this will be added
     * as a listener to a UI element.
     */
    public void addOnClearListener(ActionListener listener);

    /**
     * Add a listener to be called when user input indicates that this task
     * should be deleted.
     *
     * @param listener If this view has a delete feature, this will be added
     * as a listener to a UI element.
     */
    public void addOnDeleteListener(ActionListener listener);
}

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

import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.ActivityModel;

/**
 * Interface for any view in model-view-presenter that displays an activity.
 * Concrete classes should implement this interface to hide implementation
 * details from {@link ActivityPresenter} and {@link ActivityObserver}.
 * 
 * @see ActivityView
 */
public interface IActivityView {
    /**
     * @param model The model that the view should display
     */
    public void setModel(ActivityModel model);

    /**
     * @return A model containing the data from the view.  If the view is
     * editable, this should contain the modified data.
     */
    public ActivityModel getModel();

    /**
     * Add a listener to be called when user input from the view indicates
     * that the activity should be saved.
     *
     * @param listener If we have a view that allows editing comments and
     * activities, it will attach this listener to some "Save Comment" user
     * interface element .
     */
    public void addOnSaveListener(ActionListener listener);

    /**
     * Add a listener to be called when user input from the view indicates
     * that the activity should be deleted.
     *
     * @param listener This will be attached to whatever UI element is used
     * to let the user delete an activity.
     */
    public void addOnDeleteListener(ActionListener listener);
}

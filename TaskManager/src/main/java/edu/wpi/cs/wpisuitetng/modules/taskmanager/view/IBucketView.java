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

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketObserver;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;

import java.awt.event.ActionListener;

/**
 * Interface for any view in model-view-presenter that displays a bucket.
 * Concrete classes should implement this interface to hide implementation
 * details from {@link BucketPresenter} and {@link BucketObserver}.
 * 
 * @see BucketModel
 * @see BucektPresenter
 */
public interface IBucketView {
    /**
    * @param model The model that the view should display
    */
   public void setModel(BucketModel model);

   /**
    * @return A model containing the data from the view.  If the view is
    * editable, this should contain the modified data.
    */
   public BucketModel getModel();

   /**
    * Add a listener to be called when user input from the view indicates
    * that the bucket should be saved.
    *
    * @param listener If this view exposes a user interface to save a bucekt,
    * this will be added as a listener to a UI element.
    */
   public void addOnSaveListener(ActionListener listener);

   /**
    * Add a listener to be called when user input indicates that this bucket
    * should be deleted.
    *
    * @param listener If this view has a delete feature, this will be added
    * as a listener to a UI element.
    */
   public void addOnDeleteListener(ActionListener listener);
    
}

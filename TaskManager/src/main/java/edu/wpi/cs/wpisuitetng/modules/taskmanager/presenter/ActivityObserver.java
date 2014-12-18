/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.ActivityModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Observes the result of network requests for activities
 *
 * @author TheFloorIsJava
 */
public class ActivityObserver implements RequestObserver {

    ActivityPresenter presenter;

    /**
     * Constructor for ActivityObserver.
     * 
     * @param presenter
     *            ActivityPresenter
     */
    public ActivityObserver(ActivityPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Parse the ActivityViews from the response received by the network
     *
     * @param iReq
     *            IRequest Request to the server
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(IRequest)
     */
    public void responseSuccess(IRequest iReq) {
        /**
         * Take the appropriate action based on what the method of the request
         * was.
         */

        final String json = iReq.getResponse().getBody();
        ActivityModel model = new ActivityModel();
        switch (iReq.getHttpMethod()) {
        case GET:
            model = ActivityModel.fromJsonArray(json)[0];
            presenter.setModel(model);
            presenter.updateView();
            presenter.getTask().updateCommentView();
            break;
        case PUT:
            model = ActivityModel.fromJson(json);
            /*
             * Set the new model and update the view to reflect the new data.
             * GET and PUT requests both respond with a modified task - GET
             * returns the task stored in the database and PUT returns the same
             * task but with a new ID assigned.
             */
            presenter.setModel(model);
            presenter.updateView();
            presenter.getTask().saveActivityId(model.getId());

            break;
        case POST:
            break;

        case DELETE:
            break;
        }
    }

    /**
     * Takes an action if the response results in an error. Specifically,
     * outputs that the request failed.
     * 
     * @param iReq
     *            Request to the server
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    public void responseError(IRequest iReq) {
        System.err.println(iReq.getResponse());
    }

    /**
     * Takes an action if the response fails. Specifically, outputs that the
     * request failed.
     *
     * @param iReq
     * @param exception
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(IRequest,
     *      Exception)
     */
    public void fail(IRequest iReq, Exception exception) {
        System.err.println("Activity request failed");
        exception.printStackTrace();
    }
}
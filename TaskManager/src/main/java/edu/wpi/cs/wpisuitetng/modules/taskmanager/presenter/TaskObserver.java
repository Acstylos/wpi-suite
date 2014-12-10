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

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Observes the result of network requests for tasks
 * 
 * @author TheFloorIsJava
 */
public class TaskObserver implements RequestObserver {

    TaskPresenter presenter;

    /**
     * Construct the observer
     * 
     * @param presenter
     *            The presenter that make the request
     */
    public TaskObserver(TaskPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Parse the TaskViews from the response received by the network
     *
     * @param iReq
     *            IRequest Request to the server
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(IRequest)
     */
    public void responseSuccess(IRequest iReq) {
        System.out
        .println("Received response: " + iReq.getResponse().getBody());

        /*
         * Take the appropriate action based on what the method of the request
         * was.
         */

        String json = iReq.getResponse().getBody();
        TaskModel model = new TaskModel();
        switch (iReq.getHttpMethod()) {
        case GET:
            model = TaskModel.fromJsonArray(json)[0];
            this.presenter.setModel(model);
            break;

        case PUT:
            System.out.println("Successfully saved new tasks!");
            model = TaskModel.fromJson(json);
            /*
             * Set the new model and update the view to reflect the new data.
             * GET and PUT requests both respond with a modified task - GET
             * returns the task stored in the database and PUT returns the same
             * task but with a new ID assigned.
             */
            this.presenter.addHistory("Create");

            this.presenter.setModel(model);

            /*
             * Update the list of tasks in the bucket now that we know that it
             * is in the database and we have the ID.
             */
            this.presenter.getBucket().addTask(model.getId(), this.presenter);

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
     * @param iReq Request to the server
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
        System.err.println("Task request failed");
        exception.printStackTrace();
    }
}

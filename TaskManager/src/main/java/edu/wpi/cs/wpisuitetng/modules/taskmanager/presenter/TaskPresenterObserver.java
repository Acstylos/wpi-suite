/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This observer deals with responses from the server relating to TaskPresenter
 * queries.
 */
public class TaskPresenterObserver implements RequestObserver {

    private final TaskPresenter presenter;
    private final HttpMethod method;

    public TaskPresenterObserver(TaskPresenter presenter, HttpMethod method) {
        this.presenter = presenter;
        this.method = method;
    }

    /**
     * Parse the response message and hand them to the presenter.
     */
    public void responseSuccess(IRequest iReq) {
        // Get the request.
        final ResponseModel response = iReq.getResponse();

        // Parse the message.
        final TaskModel model = TaskModel.fromJSON(response.getBody());

        switch (method) {
        case GET:
            presenter.responseGet(model);
            break;
        case POST:
            presenter.responseUpdate(model);
            break;
        case PUT:
            presenter.responseCreate(model);
            break;
        case DELETE:
            presenter.responseDelete(model);
            break;
        }
    }

    /**
     * Handle a failure.
     */
    public void responseError(IRequest iReq) {
        System.err.println("The request to " + httpMethodToString(method)
                + " a task failed.");
    }

    /**
     * Handle a failure.
     */
    public void fail(IRequest iReq, Exception exception) {
        System.err.println("The request to " + httpMethodToString(method)
                + " a task failed.");
    }

    /**
     * Map a HttpMethod to a string. (CRUD edition.)
     */
    private static String httpMethodToString(HttpMethod method_) {
        String methodString = "";
        switch (method_) {
        case GET:
            methodString = "fetch";
            break;
        case POST:
            methodString = "update";
            break;
        case PUT:
            methodString = "create";
            break;
        case DELETE:
            methodString = "delete";
            break;
        }
        return methodString;
    }

    public TaskPresenter getPresenter() {
        return presenter;
    }
}

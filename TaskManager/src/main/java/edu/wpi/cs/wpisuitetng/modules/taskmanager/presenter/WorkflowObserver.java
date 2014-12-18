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

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * 
 * 
 * @author TheFloorIsJava
 *
 */
public class WorkflowObserver implements RequestObserver {

    private WorkflowPresenter presenter;
    private HttpMethod method;

    /**
     * Construct the observer
     * 
     * @param presenter
     *            The presenter that make requests
     * @param method
     *            method to talk to network
     */
    public WorkflowObserver(WorkflowPresenter presenter, HttpMethod method) {
        this.presenter = presenter;
        this.method = method;
    }

    /**
     * Parse the something from the response received by the network
     * 
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        // Store the response
        final ResponseModel response = iReq.getResponse();

        final WorkflowModel model;

        switch (iReq.getHttpMethod()) {
        case GET:
            // Parse the message
            WorkflowModel workflows[] = WorkflowModel.fromJSONArray(response
                    .getBody());
            ArrayList<WorkflowModel> workflowArrayList = new ArrayList<WorkflowModel>(
                    Arrays.asList(workflows));
            if (workflowArrayList.size() == 0) {
                presenter.initWorkflow(presenter);
            } else {
                System.out.println("GET Response Body: "
                        + response.getBody().toString());
                model = WorkflowModel.fromJSONArray(response.getBody())[0];
                presenter.responseGet(model);
            }
            break;
        case POST:
            // Parse the message
            model = WorkflowModel.fromJson(response.getBody());
            presenter.responsePost(model);
            break;
        case PUT:
            // Parse the message
            model = WorkflowModel.fromJson(response.getBody());
            presenter.responsePut(model);
            break;
        case DELETE:
            // Parse the message
            model = WorkflowModel.fromJson(response.getBody());
            presenter.responseDelete(model);
            break;
        }
    }

    /**
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseError(IRequest iReq) {
        System.err.println("The request to " + httpMethodToString(method)
                + " a workflow failed.");
    }

    /**
	 * 
	 */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        System.err.println("The request to " + httpMethodToString(method)
                + " a workflow failed.");

    }

    /**
     * @return The presenter
     */
    public WorkflowPresenter getPresenter() {
        return presenter;
    }

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
}

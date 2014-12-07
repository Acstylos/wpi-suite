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

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;

/**
 * 
 * 
 * @author TheFloorIsJava
 *
 */
public class WorkflowObserver implements RequestObserver {

    private WorkflowPresenter presenter;
    private HttpMethod method;
    
    /** Construct the observer
     * @param presenter
     * 				The presenter that make requests
     * @param method
     * 				method to talk to network
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
        System.err.println(iReq.getResponse().getBody() + method);
        // Store the response
        final ResponseModel response = iReq.getResponse();

        // Parse the message
        if(method == HttpMethod.GET){
        	final WorkflowModel[] models = WorkflowModel.fromJSONArray(response.getBody());
        	System.err.println(models[0].getTitle());
        	presenter.responseGet(models);
        }
        else{
        	final WorkflowModel model = WorkflowModel.fromJson(response.getBody());
        	System.err.println(model.getTitle());
        	switch (method) {
        	case GET:
        		break;
        	case POST:
        		presenter.responsePost(model);
        		break;
        	case PUT:
        		presenter.responsePut(model);
        		break;
        	case DELETE:
        		presenter.responseDelete(model);
        		break;
        	}
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
        return this.presenter;
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

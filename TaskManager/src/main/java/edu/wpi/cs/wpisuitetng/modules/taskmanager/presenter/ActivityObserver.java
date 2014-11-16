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
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class ActivityObserver implements RequestObserver {

    private ActivityPresenter presenter;
    private HttpMethod method;

    public ActivityObserver(ActivityPresenter presenter, HttpMethod method) {
	this.presenter = presenter;
	this.method = method;
    }
    
    public ActivityObserver(ActivityPresenter presenter) {
	this.presenter = presenter;
    }

    /**
     * Parse the ActivityViews from the response received by the network
     *
     */
    public void responseSuccess(IRequest iReq) {
	// Store the response
	final ResponseModel response = iReq.getResponse();

	if (method == HttpMethod.GET) {

	    // Parse the message
	    final ActivityModel[] models = ActivityModel.fromJsonArray(response
		    .getBody());
	    System.err.println(models[0].getActivity());
	    presenter.responseGet(models);
	} else {
	    // Parse the message
	    final ActivityModel model = ActivityModel.fromJson(response
		    .getBody());
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
    public void responseError(IRequest iReq) {
	System.err.println("The request to " + httpMethodToString(method)
		+ " an activity failed.");
    }

    /**
     *
     * @param iReq
     * @param exception
     */
    public void fail(IRequest iReq, Exception exception) {
	System.err.println("The request to " + httpMethodToString(method)
		+ " an activity failed.");
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

    /**
     * @return The presenter
     */
    public ActivityPresenter getPresenter() {
	return this.presenter;
    }
}
/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.comment;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observes the activity network response for activities
 * 
 * @author TheFloorIsJava
 *
 */
public class ActivityObserver implements RequestObserver {

    private ActivityPresenter presenter;
    private HttpMethod method;

    /**
     * Constructor for ActivityObserver.
     * 
     * @param presenter
     *            ActivityPresenter
     * @param method
     *            HttpMethod
     */
    public ActivityObserver(ActivityPresenter presenter, HttpMethod method) {
        this.presenter = presenter;
        this.method = method;
    }

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
     *            IRequest
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(IRequest)
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
     * Takes an action if the response results in an error. Specifically,
     * outputs that the request failed.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    public void responseError(IRequest iReq) {
        System.err.println("The request to " + httpMethodToString(method)
                + " an activity failed.");
    }

    /**
     * Takes an action if the response fails. Specifically, outputs that the
     * request failed.
     *
     * @param iReq
     * @param exception
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(IRequest,
     *      Exception)
     */
    public void fail(IRequest iReq, Exception exception) {
        System.err.println("The request to " + httpMethodToString(method)
                + " an activity failed.");
    }

    /**
     * Method httpMethodToString.
     * 
     * @param method_
     *            HttpMethod
     * 
     * @return String
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

    /**
     * 
     * @return The presenter
     */
    public ActivityPresenter getPresenter() {
        return this.presenter;
    }
}
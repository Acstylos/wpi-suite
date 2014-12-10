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

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observes the network response for buckets
 * @author TheFloorIsJava
 *
 */
public class BucketObserver implements RequestObserver {

    private BucketPresenter presenter;
    private HttpMethod method;


    /** Construct the observer
     * @param presenter
     * 				The presenter that make requests
     * @param method
     * 				method to talk to network
     */
    public BucketObserver(BucketPresenter presenter, HttpMethod method) {
        this.presenter = presenter;
        this.method = method;
    }

    /**
     * Parse the TaskViews from the response received by the network
     * 
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        // Store the response
        final ResponseModel response = iReq.getResponse();

        final BucketModel model;
        switch (iReq.getHttpMethod()) {
        case GET:
            // Parse the message
            model = BucketModel.fromJsonArray(response.getBody())[0];
            this.presenter.setModel(model);
            break;
        case POST:
            // Parse the message
            model = BucketModel.fromJson(response.getBody());
            this.presenter.responsePost(model);
            break;
        case PUT:
            // Parse the message
            model = BucketModel.fromJson(response.getBody());
            this.presenter.responsePut(model);
            break;
        case DELETE:
            // Parse the message
            model = BucketModel.fromJson(response.getBody());
            this.presenter.responseDelete(model);
            break;
        }
    }

    /**
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseError(IRequest iReq) {
        System.err.println("The request to " + httpMethodToString(method)
                + " a bucket failed.");
    }

    /**
     * 
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        System.err.println("The request to " + httpMethodToString(method)
                + " a bucket failed.");
    }

    /**
     * @param method_ The HttpMethod related to the request
     * @return The string corresponding to the specified HttpMethod
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
     * @return The presenter
     */
    public BucketPresenter getPresenter() {
        return this.presenter;
    }
}

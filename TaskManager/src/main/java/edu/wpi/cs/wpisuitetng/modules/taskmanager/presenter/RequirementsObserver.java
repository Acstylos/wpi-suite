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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Observes the result of network requests for requirements
 * 
 * @author TheFloorIsJava
 */
public class RequirementsObserver implements RequestObserver{
    TaskPresenter presenter;

    /**
     * Construct the observer
     * 
     * @param presenter
     *            The presenter that make the request
     */
    public RequirementsObserver(TaskPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Parse the Requirement from the response received by the network
     *
     * @param iReq
     *            IRequest Request to the server
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(IRequest)
     */
    public void responseSuccess(IRequest iReq) {
        System.out.println(Requirement.fromJsonArray(iReq.getResponse().getBody()));
        Requirement[] coreRequirements = Requirement.fromJsonArray(iReq.getResponse().getBody());
        presenter.addRequirementsToAllReqArray(coreRequirements);
        presenter.addRequirementsToAllReqName(coreRequirements);
        presenter.addRequirementsToView();

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
        System.err.println("User request failed");
        exception.printStackTrace();
    }

}

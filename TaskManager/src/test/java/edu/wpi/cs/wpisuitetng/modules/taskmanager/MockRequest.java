/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class was copied by The Floor Is Java from requirementsmanager,
 * and was written by Team Rolling Thunder. It was copied for the purpose
 * of testing.
 * @author justinhess
 * @version $Revision: 1.0 $
 */
public class MockRequest extends Request {
	
	protected boolean sent = false;

	/**
	 * Constructor for MockRequest.
	 * @param networkConfiguration NetworkConfiguration
	 * @param path String
	 * @param requestMethod HttpMethod
	 */
	public MockRequest(NetworkConfiguration networkConfiguration, String path, HttpMethod requestMethod) {
		super(networkConfiguration, path, requestMethod);
	}

	/**
	 * Method send.
	
	 * @throws IllegalStateException */
	@Override
	public void send() throws IllegalStateException {
		// don't actually send
		sent = true;
	}
	
	/**
	 * Method isSent.
	
	 * @return boolean */
	public boolean isSent() {
		return sent;
	}
}

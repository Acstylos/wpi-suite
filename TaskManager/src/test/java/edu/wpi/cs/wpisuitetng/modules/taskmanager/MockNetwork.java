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

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class was copied by The Floor Is Java from requirementsmanager,
 * and was written by Team Rolling Thunder. It was copied for the purpose
 * of testing.
 *
 * @author justinhess
 * @version $Revision: 1.0 $
 */
public class MockNetwork extends Network {
	
	protected MockRequest lastRequestMade = null;
	
	/**
	 * Method makeRequest.
	 * @param path String
	 * @param requestMethod HttpMethod
	
	 * @return Request */
	@Override
	public Request makeRequest(String path, HttpMethod requestMethod) {
		if (requestMethod == null) {
			throw new NullPointerException("requestMethod may not be null");
		}
		
		lastRequestMade = new MockRequest(defaultNetworkConfiguration, path, requestMethod); 
		
		return lastRequestMade;
	}
	
	/**
	 * Method getLastRequestMade.
	
	 * @return MockRequest */
	public MockRequest getLastRequestMade() {
		return lastRequestMade;
	}
}

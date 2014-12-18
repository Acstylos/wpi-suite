/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team The Floor is Java
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests methods in iteration.java and tests the creation of variants of Bucket
 * 
 * @author ChandlerWu
 *
 */

public class TestBucket {

    @Test
    public void createNonNullIteration() {
        assertNotNull(new BucketModel());
    }

}

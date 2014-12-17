/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestActivityView {

    @Test
    public void TestCreateNotNullActivityView1() {
        assertNotNull(new ActivityView());
    }
    
    @Test
    public void TestCreateNotNullActivityView2() {
        assertNotNull(new ActivityView("Activity"));
    }
    
    @Test
    public void TestMessage() {
        ActivityView aView = new ActivityView();
        assertEquals("", aView.getActivity());
        aView.setActivity("test message");
        assertEquals("test message", aView.getActivity());
    }  
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;
/**
 * test workflowModel's methods
 * @author theFloorIsJava
 *
 */
public class TestWorkflowModel {

    @Test
    public final void testWorkflowModel() {
        WorkflowModel test = new WorkflowModel();
        assertEquals(-1, test.getId());
        assertEquals("", test.getTitle());
        assertEquals(new ArrayList<>(), test.getBucketIds());
    }

    @Test
    public final void testWorkflowModelIntString() {
        WorkflowModel test = new WorkflowModel(10, "Due Soon");
        assertEquals(10, test.getId());
        assertEquals("Due Soon", test.getTitle());
        assertEquals(new ArrayList<>(), test.getBucketIds());
    }

    @Test
    public final void testToJson() {
        WorkflowModel test = new WorkflowModel(10, "Due Soon");
        assertEquals("{\"id\":10,\"defaultBucketIndex\":0,\"title\":\"Due Soon\",\"bucketIds\":[],\"permissionMap\":{}}" ,test.toJson());
    }

    @Test
    public final void testFromJson() {
        String json="{\"id\":10,\"title\":\"Due Soon\",\"bucketIds\":[],\"permissionMap\":{}}";
        WorkflowModel test = new WorkflowModel(10, "Due Soon");
        WorkflowModel test2 = new WorkflowModel(10, "Due Soon");
        assertTrue(test.fromJson(json).equals(test2.fromJson(json)));
    }

    @Test
    public final void testIdentify() {
        WorkflowModel test = new WorkflowModel(10, "Due Soon");
        WorkflowModel test2 = new WorkflowModel(20, "also a workflowmodel obj");
        assertTrue(test.identify(test) );
        assertFalse(test.identify(new Color ( 0,0,0)));
    }

    @Test
    public final void testGetBucketIds() {
        WorkflowModel test = new WorkflowModel(10, "Due Soon");
        test.getBucketIds().add(4);
        ArrayList <Integer> list = new ArrayList<Integer>();
        list.add(4);
        
        assertEquals(test.getBucketIds(),list );
    }

    @Test
    public final void testSetBucketIds() {
        WorkflowModel test = new WorkflowModel(10, "Due Soon");
        ArrayList <Integer> list = new ArrayList<Integer>();
        list.add(4);
        test.setBucketIds(list);
        assertEquals(test.getBucketIds(), list);
    }

    @Test
    public final void testGetTitle() {
        WorkflowModel test = new WorkflowModel(10, "Due Soon");
        assertEquals("Due Soon", test.getTitle());
    }

    @Test
    public final void testSetTitle() {
        WorkflowModel test = new WorkflowModel(10, "Due Soon");
        test.setTitle("new title");
        assertEquals("new title", test.getTitle());
    }

    @Test
    public final void testGetId() {
        WorkflowModel test = new WorkflowModel(10, "Due Soon");
        assertEquals(10, test.getId());
    }

    @Test
    public final void testSetId() {
        WorkflowModel test = new WorkflowModel(10, "Due Soon");
        test.setId(11);
        assertEquals(11, test.getId());
    }

    @Test
    public final void testCopyFrom() {
        WorkflowModel empty = new WorkflowModel();
        WorkflowModel copy=new WorkflowModel(99, "copied from");
        empty.copyFrom(copy);
        assertEquals(empty.getId(), 99);
        assertEquals(empty.getTitle(), "copied from");
        assertEquals(empty.getBucketIds(), new ArrayList<>());        
    }

}

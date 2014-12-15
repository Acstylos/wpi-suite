/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;


/**
 * WorkflowView is the panel that holds the list of buckets that represents the
 * full workflow of the project. The title of the workflow corresponds to the
 * project name the workflow is for.  
 * @author Thefloorisjava
 *
 */
public class WorkflowView extends JPanel
{

    private static final long serialVersionUID = -5937582878085666950L;
    private String title; // TODO: Get rid of title, we dont need it.
    private ArrayList<BucketView> bucketViews = new ArrayList<BucketView>();


    /**
     * Constructor for the panel that holds the workflow of buckets.
     * @param title String that defines how a bucket will be titled.
     */
    public WorkflowView(String title) {
        /* Buckets will be created left to right, never on top 
         * of each other.
         */
        this.setLayout(new MigLayout("fill"));
        Component topSpacerStrut = Box.createVerticalStrut(5);
        Component bottomSpacerStrut = Box.createVerticalStrut(5);
        Component leftSpacerStrut = Box.createHorizontalStrut(5);
        this.add(topSpacerStrut, "dock north");
        this.add(bottomSpacerStrut, "dock south");
        this.add(leftSpacerStrut, "dock west");
    }

    /**
     * @return A list of BucketViews corresponding to the buckets in the workflow process.
     */
    public List<BucketView> getBucketViews() {
        return this.bucketViews;
    }

    /**
     * @return A string defining how a bucket will be titled.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Adds buckets into the workFlowView by smashing them to the left, allowing them
     * to collide and sit next to what was added before it. 
     * @param buckets List of buckets corresponding to the buckets in the workflow process.
     */
    public void setBucketViews(ArrayList<BucketView> buckets){
        this.bucketViews = buckets;
        this.removeAll();
        for (BucketView bucket : bucketViews) {
            this.add(bucket, "dock west");
            Component spacerStrut = Box.createHorizontalStrut(5);
            this.add(spacerStrut, "dock west");
        }
    }

    /**
     * @param title A string defining how a bucket is titled.
     */
    public void setTitle(String title){
        this.title = title;
        this.setBorder(new TitledBorder(null, title, TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
    }

}

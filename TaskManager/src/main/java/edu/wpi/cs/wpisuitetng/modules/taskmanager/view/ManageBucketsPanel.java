/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Authors: Team TheFloorIsJava
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;
import javax.swing.JTextField;
import javax.swing.JList;

public class ManageBucketsPanel extends JPanel {

    static final long serialVersionUID = 3826396045506177079L;
    private List<Integer> bucketIds = new ArrayList<Integer>();
    private JPanel bucketListPanel = new JPanel();
    private JButton closeButton = new JButton("Close");
    private JButton moveBucketUpButton = new JButton("Up");
    private JButton deleteBucketButton = new JButton("Delete");
    private JButton moveBucketDownButton = new JButton("Down");
    private JButton addBucketButton = new JButton("Add Stage");
    private JTextField newBucketField = new JTextField();
    private JPanel addStagePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JList<String> bucketList = new JList<String>();
    private List<String> bucketNamesList = new ArrayList<String>();
    private String[] bucketNamesArray = {};

    /**
     * Create the panel.
     * @param model The workflow model this panel will take information from. 
     */
    public ManageBucketsPanel(WorkflowModel model) {
        this.bucketIds = new ArrayList<Integer>(model.getBucketIds());
        this.setLayout(new MigLayout("", "[grow][50%,grow]", "[][grow]"));
        
        add(addStagePanel, "cell 0 0,grow");
        addStagePanel.setLayout(new MigLayout("", "[grow][]", "[]"));
        addStagePanel.add(newBucketField, "cell 0 0,growx");
        newBucketField.setColumns(10);
        addStagePanel.add(addBucketButton, "cell 1 0");
        
        add(buttonPanel, "flowx,cell 0 1");
        buttonPanel.setLayout(new MigLayout("", "[]", "[]"));
        buttonPanel.add(moveBucketUpButton, "flowy,cell 0 0");
        buttonPanel.add(deleteBucketButton, "cell 0 0");
        buttonPanel.add(moveBucketDownButton, "cell 0 0");
        buttonPanel.add(closeButton, "cell 0 0");
        this.bucketListPanel.setLayout(new MigLayout("fill", "[grow]", "[grow]"));
        
        
        add(bucketListPanel, "cell 0 1,grow");
        
        bucketListPanel.add(bucketList, "cell 0 0,grow");
    }
    
    /**
     * Sets bucketIds to the given List.  
     * @param bucketIds List of bucketIds to set in this view.
     */
    public void setBucketIds(List<Integer> bucketIds){
        this.bucketIds = new ArrayList<Integer>(bucketIds);
    }
    
    /**
     * @return The list of BucketIds in this view.
     */
    public List<Integer> getBucketIds(){
        return this.bucketIds;
    }
    
    /**
     * @return The string in the newBucketField.
     */
    public String getNewBucketTitle(){
        return this.newBucketField.getText().trim();
    }
    
    /**
     * Adds a single bucket to the bucketList to be displayed.
     * @param bucket BucketModel to be added to the list.
     */
    public void addBucketToList(BucketModel bucket){
        // TODO: add buckets to the list.
    }
    
    /**
     * @param listener The listener to determine how closeButton will function.
     */
    public void addCloseButtonListener(ActionListener listener){
        this.closeButton.addActionListener(listener);
    }
    
    /**
     * @param listener The listener to determine how addBucketButton will function.
     */
    public void addAddBucketButtonListener(ActionListener listener){
        this.addBucketButton.addActionListener(listener);
    }
    
    /**
     * @param listener The listener to detemine how deleteBucketButton will function.
     */
    public void addDeleteBucketButtonListener(ActionListener listener){
        this.deleteBucketButton.addActionListener(listener);
    }

}

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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTextField;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;

import javax.swing.JScrollPane;

public class ManageBucketsPanel extends JPanel {

    static final long serialVersionUID = 3826396045506177079L;
    private List<Integer> bucketIds = new ArrayList<Integer>();
    private JPanel bucketListPanel = new JPanel();
    private JButton closeButton = new JButton("Close", Icons.CANCEL);
    private JButton moveBucketUpButton = new JButton("Up", Icons.MOVE_UP);
    private JButton deleteBucketButton = new JButton("Delete", Icons.DELETE);
    private JButton moveBucketDownButton = new JButton("Down", Icons.MOVE_DOWN);
    private JButton addBucketButton = new JButton("Add Stage", Icons.ADD);
    private JXTextField newBucketField = new JXTextField("Write a Stage Title...", Color.GRAY);
    private JPanel addStagePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JList<String> bucketList = new JList<String>();
    private final JButton setInitBucketButton = new JButton("Set Default");
    private final JScrollPane scrollPane = new JScrollPane();

    /**
     * Create the panel.
     * @param model The workflow model this panel will take information from. 
     */
    public ManageBucketsPanel(WorkflowModel model) {
        this.bucketIds = new ArrayList<Integer>(model.getBucketIds());
        this.setLayout(new MigLayout("", "[50%][50%]", "[][grow][]"));
        
        add(addStagePanel, "cell 0 0,grow");
        addStagePanel.setLayout(new MigLayout("", "[grow][]", "[]"));
        addStagePanel.add(newBucketField, "cell 0 0,growx");
        addStagePanel.add(addBucketButton, "cell 1 0");
        
        add(buttonPanel, "flowx,cell 0 1,aligny top");
        
        buttonPanel.setLayout(new MigLayout("", "[]", "[]"));
        buttonPanel.add(moveBucketUpButton, "flowy,cell 0 0,growx");
        buttonPanel.add(deleteBucketButton, "cell 0 0,growx");
        buttonPanel.add(moveBucketDownButton, "cell 0 0,growx");
        buttonPanel.add(setInitBucketButton, "cell 0 0,growx");
        bucketListPanel.setLayout(new MigLayout("fill", "[grow]", "[grow]"));
        
        add(bucketListPanel, "cell 0 1,grow");
        
        bucketListPanel.add(scrollPane, "cell 0 0,grow");
        scrollPane.setViewportView(bucketList);
        
        bucketList.setCellRenderer(new BucketListItemRenderer());
        bucketList.setBorder(new LineBorder(Color.LIGHT_GRAY));
        add(closeButton, "cell 0 2,alignx left,aligny center");
        
        newBucketField.getDocument().addDocumentListener(validateTitleField);
        validateField();
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
    public void addBucketNameArrayToList(String[] bucketNames){
        this.bucketList.setListData(bucketNames);
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
     * @param listener The listener to determine how deleteBucketButton will function.
     */
    public void addDeleteBucketButtonListener(ActionListener listener){
        this.deleteBucketButton.addActionListener(listener);
    }
    
    /**
     * @param listener The listener to determine how moveBucketUpButton will function.
     */
    public void addMoveBucketUpButtonListener(ActionListener listener){
        this.moveBucketUpButton.addActionListener(listener);
    }
    
    /**
     * @param listener The listener to determine how moveBucketDownButton will function.
     */
    public void addMoveBucketDownButtonListener(ActionListener listener){
        this.moveBucketDownButton.addActionListener(listener);
    }
    
    
    /**
     * @param listener The listener to determine how setInitBucketButton will function.
     */
    public void addSetInitBucketButtonListener(ActionListener listener){
        this.setInitBucketButton.addActionListener(listener);
    }

    /**
     * @return the index of the selected item in the Jlist
     */
    public int getBucketListIndex() {
        return this.bucketList.getSelectedIndex();
    }
    
    /**
     * @param index The index of the list to select.
     */
    public void setSelectedBucket(int index){
        this.bucketList.setSelectedIndex(index);
    }
    
    /**
     * Clears the add bucket field.
     */
    public void clearAddBucketField(){
        this.newBucketField.setText("");
    }
    
    private DocumentListener validateTitleField = new DocumentListener(){

        @Override
        public void insertUpdate(DocumentEvent e) {
            validateField();
            
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            validateField();
            
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            validateField();
            
        }
        
    };
    
    /**
     * Validates the title text field.
     */
    private void validateField(){
        boolean isTitleInvalid = newBucketField.getText().trim().isEmpty();
        if(isTitleInvalid){
            addBucketButton.setEnabled(false);
        } else {
            addBucketButton.setEnabled(true);
        }
    }

}

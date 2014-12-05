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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;

import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.SwingConstants;

public class RequirementListView extends JPanel{
    private static final long serialVersionUID = 2597998987471055654L;
    private JPanel assignedRequirementsPanel = new JPanel();
    private String[] unassignedRequirementsList;
    private JLabel assignedRequirementsLabel = new JLabel("Related Requirements");
    private JLabel unassignedRequirementsLabel = new JLabel("Unrelated Requirements");
    private JScrollPane assignedScrollPane = new JScrollPane();
    private JComboBox<Requirement> unassignedComboBox = new JComboBox<Requirement>();
    private TaskPresenter presenter;


    /**
     * Create the panel for requirement
     */
    public RequirementListView(TaskPresenter presenter) {
        this.setLayout(new MigLayout("", "[grow]", "[][grow][]"));
        this.presenter = presenter;
        
        this.add(assignedRequirementsLabel, "cell 0 0,alignx center");        
        this.add(unassignedRequirementsLabel, "cell 1 0,alignx center");
        
        this.assignedScrollPane.setViewportBorder(null);
        this.add(assignedScrollPane, "cell 0 1,grow");
        this.assignedScrollPane.setViewportView(assignedRequirementsPanel);
        this.assignedRequirementsPanel.setLayout(new MigLayout("fill"));
        
        //this.unassignedComboBox.setViewportBorder(null);
        this.add(unassignedComboBox, "cell 1 1,grow");
        this.unassignedComboBox.setModel(new DefaultComboBoxModel(new String[] {
                "-- None --" }));
        //this.unassignedComboBox.setViewportView(unassignedRequirementsPanel);
        //this.unassignedRequirementsList.setLayout(new MigLayout("fill"));
    }
    
    /**
     * 
     * @param req
     * @param assigned
     */
    public void addRequirementToList(Requirement req, boolean assigned) {
        RequirementListItem reqItem = new RequirementListItem(presenter, req, assigned);
        reqItem.addChangeListButtonListener(assigned);
        if(assigned) {
            reqItem.setAsRequirement();
            this.assignedRequirementsPanel.add(reqItem, "dock north");
            //this.unassignedRequirementsList.remove(reqItem);
        } else {
            //reqItem.unsetAsRequirement();
            this.unassignedComboBox.addItem(req);
            //this.unassignedRequirementsList.add(reqItem, "dock north");
            //this.assignedRequirementsPanel.remove(reqItem);
        }
        
        this.revalidate();
        this.repaint();
    }
    
    /**
     * 
     */
    public void removeAllRequirements() {
        this.assignedRequirementsPanel.removeAll();
       //this.unassignedRequirementsList.removeAll();
    }	
}

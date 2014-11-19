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


import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.WorkflowPresenter;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 * MainView is a scrollable window with a viewport that can
 * view only WorkflowViews.
 */
public class MainView extends JTabbedPane 
{
    private static final long serialVersionUID = -346061317795260862L;
    private JScrollPane workflowScrollPane = new JScrollPane();
    private WorkflowPresenter workflowPresenter = new WorkflowPresenter(0);
    private static final MainView mainView = new MainView();

    private MainView(){
        this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.addTab("Workflow", workflowScrollPane);

        JScrollPane archiveScrollPane = new JScrollPane();

        addTab("Archive", null, archiveScrollPane, null);

        JPanel archivePanel = new JPanel();
        archiveScrollPane.setViewportView(archivePanel);
        archivePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        archivePanel.add(new BucketView("Archive"), "cell 0 0,alignx center,growy");


        this.setWorkflowPresenter(workflowPresenter);

        /* As soon as this is added to a container, load the workflow */
        addPropertyChangeListener("ancestor", (PropertyChangeEvent evt) -> {
            this.workflowPresenter.load();
        });
    }

    public static MainView getInstance(){
        return mainView;
    }

    /**
     * Constructor for the scrollable main view.  
     */
    private MainView(WorkflowPresenter workflowPresenter){
        this.addTab("Workflow", workflowScrollPane);    
        this.setWorkflowPresenter(workflowPresenter);

        /* As soon as this is added to a container, load the workflow */
        addPropertyChangeListener("ancestor", (PropertyChangeEvent evt) -> {
            this.workflowPresenter.load();
        });
    }

    /**
     * @return the WorkflowPresenter being displayed
     */
    public WorkflowPresenter getWorkflowPresenter() {
        return this.workflowPresenter;
    }

    /**
     * @param workflowView The WorkflowView to be displayed
     */
    public void setWorkflowPresenter(WorkflowPresenter workflowPresenter) {
        this.workflowPresenter = workflowPresenter;
        this.workflowScrollPane.setViewportView(workflowPresenter.getView());
    }
}
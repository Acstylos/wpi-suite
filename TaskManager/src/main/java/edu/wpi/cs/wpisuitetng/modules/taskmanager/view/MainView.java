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
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.WorkflowPresenter;

/**
 * MainView is a scrollable window with a viewport that can view only
 * WorkflowViews.
 */
public class MainView extends JTabbedPane {
    private static final long serialVersionUID = -346061317795260862L;
    private JScrollPane workflowScrollPane = new JScrollPane();
    private WorkflowPresenter workflowPresenter = new WorkflowPresenter(0);
    private static final MainView mainView = new MainView();
    private boolean showArchived = true;
    private MainView() {
        this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.addTab("Workflow", Icons.WORKFLOW, workflowScrollPane);

        this.setWorkflowPresenter(workflowPresenter);

        /* As soon as this is added to a container, load the workflow */
        addPropertyChangeListener("ancestor", (PropertyChangeEvent evt) -> {
            this.workflowPresenter.load();
        });
    }

    public static MainView getInstance() {
        return mainView;
    }

    /**
     * Constructor for the scrollable main view.
     */
    private MainView(WorkflowPresenter workflowPresenter) {
        this();
        this.setWorkflowPresenter(workflowPresenter);
    }

    /**
     * @return the WorkflowPresenter being displayed
     */
    public WorkflowPresenter getWorkflowPresenter() {
        return this.workflowPresenter;
    }

    /**
     * @param workflowView
     *            The WorkflowView to be displayed
     */
    public void setWorkflowPresenter(WorkflowPresenter workflowPresenter) {
        this.workflowPresenter = workflowPresenter;
        this.workflowScrollPane.setViewportView(workflowPresenter.getView());
    }


    /** indicates if archived tasks are shown
     * @return if archived tasks are shown
     */
    public boolean getShowArchived() {
        return showArchived;
    }

    /**set if archived tasks are shown
     * @param showArchived indicates if archived tasks are shown
     */
    public void setShowArchived(boolean showArchived) {
        this.showArchived = showArchived;
    }
    
    /**
     * resets and reloads all buckets
     */
    public void resetAllBuckets(){
        for(Map.Entry<Integer, BucketPresenter> bucketEntry: getWorkflowPresenter().getBucketPresenters().entrySet()){
            BucketPresenter bucket = bucketEntry.getValue();                    
            bucket.addMiniTaskstoView();
        }    
    }
}
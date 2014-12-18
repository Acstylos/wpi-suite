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

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.Date;
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
    private WorkflowPresenter workflowPresenter = new WorkflowPresenter(1);
    private GhostGlassPane glassPane = new GhostGlassPane();
    private static final MainView mainView = new MainView();

    private MainView() {
        this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.addTab("Workflow", Icons.WORKFLOW, workflowScrollPane);

        this.setWorkflowPresenter(workflowPresenter);

        /* As soon as this is added to a container, load the workflow */
        addPropertyChangeListener("ancestor", (PropertyChangeEvent evt) -> {
            this.workflowPresenter.load();
        });
    }

    /**
     * Sets the glass pane when the main view is added to the window
     */
    public void addNotify() {
        super.addNotify();

        this.getRootPane().setGlassPane(glassPane);
    }

    /**
     * @return The glass pane for this component
     */
    public GhostGlassPane getGlassPane() {
        return glassPane;
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
        return workflowPresenter;
    }

    /**
     * @param workflowView
     *            The WorkflowView to be displayed
     */
    public void setWorkflowPresenter(WorkflowPresenter workflowPresenter) {
        this.workflowPresenter = workflowPresenter;
        workflowScrollPane.setViewportView(workflowPresenter.getView());
    }

    /**
     * resets and reloads all buckets
     */
    public void resetAllBuckets() {
        for (Map.Entry<Integer, BucketPresenter> bucketEntry : getWorkflowPresenter()
                .getBucketPresenters().entrySet()) {
            BucketPresenter bucket = bucketEntry.getValue();
            bucket.addMiniTaskstoView();
        }
    }
}

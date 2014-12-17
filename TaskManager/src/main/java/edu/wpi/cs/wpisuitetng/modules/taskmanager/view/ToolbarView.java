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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import java.awt.Font;

/**
 * Sets up upper toolbar of TaskManager tab
 */
public class ToolbarView extends JPanel
{
    private static final long serialVersionUID = 5489162021821230861L;
    
    private JButton createNewTaskButton = new JButton("<html>Create<br/>Task</html>", Icons.CREATE_TASK_LARGE);
    private JToggleButton tglbtnArchive = new JToggleButton("<html>Hide<br/>Archived</html>", Icons.HIDE_ARCHIVE_LARGE);
    private FilterView filterPanel = new FilterView();
    
    static {
        /* Change the default icons for JXDatePicker. */
        UIManager.put("JXDatePicker.arrowIcon", Icons.CALENDAR);
        UIManager.put("JXMonthView.monthDownFileName", Icons.LEFT_ARROW);
        UIManager.put("JXMonthView.monthUpFileName", Icons.RIGHT_ARROW);
    }
    /**
     * Creates and positions option buttons in upper toolbar
     * @param visible boolean
     * @throws IOException 
     */
    public ToolbarView() {
        setLayout(new MigLayout("", "[][][grow][]-5px", "0px[grow]"));

        add(createNewTaskButton, "cell 0 0");
        
        add(tglbtnArchive, "cell 1 0");
        tglbtnArchive.setSelected(false);        
        
        add(filterPanel, "cell 3 0,grow");
        registerCallbacks();
    }
    
    public void registerCallbacks(){
        
        /**
         * Adds a new TaskView Tab into the MainView
         */
        createNewTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get instance of the New-Bucket Presenter to add new tasks into
                int firstBucketId = MainView.getInstance().getWorkflowPresenter().getModel().getBucketIds().get(0);
                TaskPresenter taskPresenter = new TaskPresenter(0, MainView.getInstance().getWorkflowPresenter().getBucketPresenterById(firstBucketId), ViewMode.CREATING);
                MainView.getInstance().addTab(taskPresenter.getModel().getTitle(), Icons.CREATE_TASK, taskPresenter.getView());
                int tabCount = MainView.getInstance().getTabCount();
                taskPresenter.getView().setIndex(tabCount-1);
                MainView.getInstance().setSelectedIndex(tabCount-1);
            }
        });

        add(createNewTaskButton, "flowx,cell 0 0");        
        
        JButton manageBuckets = new JButton("<html>Manage<br/>Stages</html>");
        add(manageBuckets, "cell 0 0");
        manageBuckets.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // get instance of the New-Bucket Presenter to add new tasks into
                MainView.getInstance().getWorkflowPresenter().updateManageWorkflowView();
                MainView.getInstance().addTab("Manage Workflow", Icons.CREATE_TASK, MainView.getInstance().getWorkflowPresenter().getManageWorkflowView());
                int tabCount = MainView.getInstance().getTabCount();
                MainView.getInstance().setSelectedIndex(tabCount-1);
            }
        });
        
        /**
         * Hides or removes archives from the view.
         */
        tglbtnArchive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tglbtnArchive.isSelected()) {
                    /* Show all tasks */
                    tglbtnArchive.setText("<html>Hide<br/>Archived</html>");
                    BucketPresenter.getTaskFilter().setIncludeArchived(true);
                    tglbtnArchive.setIcon(Icons.HIDE_ARCHIVE_LARGE);
                } else {
                    /* Only show non-archived tasks */
                    tglbtnArchive.setText("<html>Show<br/>Archived</html>");
                    BucketPresenter.getTaskFilter().setIncludeArchived(false);
                    tglbtnArchive.setIcon(Icons.SHOW_ARCHIVE_LARGE);
                }

                MainView.getInstance().resetAllBuckets();
            }
        });
        
    }
} 

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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.HelpPresenter;
import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import net.miginfocom.swing.MigLayout;

/**
 * Sets up upper toolbar of TaskManager tab
 */
public class ToolbarView extends JPanel
{
    private static final long serialVersionUID = 5489162021821230861L;
    HelpView helpView = new HelpView();
    
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
                int defaultBucketId = MainView.getInstance().getWorkflowPresenter().getModel().getBucketIds().get(MainView.getInstance().getWorkflowPresenter().getDefaultBucketIndex());
                TaskPresenter taskPresenter = new TaskPresenter(0, MainView.getInstance().getWorkflowPresenter().getBucketPresenterById(defaultBucketId), ViewMode.CREATING);
                MainView.getInstance().addTab(taskPresenter.getModel().getTitle(), Icons.CREATE_TASK, taskPresenter.getView());
                int tabCount = MainView.getInstance().getTabCount();
                taskPresenter.getView().setIndex(tabCount-1);
                MainView.getInstance().setSelectedIndex(tabCount-1);
            }
        });
        
        JButton manageBuckets = new JButton("<html>Manage<br/>Workflow</html>");
        manageBuckets.setIcon(Icons.EDIT_WORKFLOW_LARGE);
        add(manageBuckets, "cell 0 0");
        
        manageBuckets.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // get instance of the New-Bucket Presenter to add new tasks into
                MainView.getInstance().getWorkflowPresenter().updateManageWorkflowView();
                MainView.getInstance().addTab("Manage Workflow", Icons.EDIT_WORKFLOW, MainView.getInstance().getWorkflowPresenter().getManageWorkflowView());
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

        JButton saveCSV = new JButton("<html>Export<br/>csv</html>");
        saveCSV.setIcon(Icons.EXPORT_CALENDAR);
        JFileChooser fc = new JFileChooser();
        add(saveCSV, "cell 0 0");
        
        /**
         * Adds a new TaskView Tab into the MainView
         */
        saveCSV.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get where to print to.
                int retval = fc.showSaveDialog(MainView.getInstance());

                // If not fail, attempt to write.
                if (retval == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    String csv = MainView.getInstance().getWorkflowPresenter().getCsv();

                    try {
                        FileWriter file = new FileWriter(f);
                        file.write(csv);
                        file.close();
                        System.out.println("Succeeded in writing CSV file. Path: " + f.getPath());
                    } catch (Exception e1) {
                        System.out.println("Failed to write to file: " + e1.getMessage());
                    }
                }
                // Print CSV to console.
                System.out.println(MainView.getInstance().getWorkflowPresenter().getCsv());
            }
        });    
        
        /**
         * Adds a help Tab into the MainView
         */
        JButton btnHelp = new JButton("<html>Help</html>");
        btnHelp.setIcon(Icons.HELP_LARGE);
        add(btnHelp, "cell 0 0");

        btnHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HelpPresenter helpPresenter = new HelpPresenter();
                MainView.getInstance().addTab("Help", Icons.HELP, helpPresenter.getView());
                int tabCount = MainView.getInstance().getTabCount();
                MainView.getInstance().setSelectedIndex(tabCount - 1);         
            }
        }); 
    }
}

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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.NewPieChartPanel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.HelpPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Sets up upper toolbar of TaskManager tab
 */
public class ToolbarView extends JPanel
{
    private static final long serialVersionUID = 5489162021821230861L;
    HelpView helpView = new HelpView();
    /**
     * Creates and positions option buttons in upper toolbar
     * @param visible boolean
     * @throws IOException 
     */
    public ToolbarView() {
        setLayout(new MigLayout("fill"));

        JButton createNewTaskButton = new JButton("<html>Create<br/>Task</html>");
        createNewTaskButton.setIcon(Icons.CREATE_TASK_LARGE);

        /**
         * Adds a new TaskView Tab into the MainView
         */
        createNewTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get instance of the New-Bucket Presenter to add new tasks into
                TaskPresenter taskPresenter = new TaskPresenter(0, MainView.getInstance().getWorkflowPresenter().getBucketPresenterById(1), ViewMode.CREATING);
                MainView.getInstance().addTab(taskPresenter.getModel().getTitle(), Icons.CREATE_TASK, taskPresenter.getView());
                int tabCount = MainView.getInstance().getTabCount();
                taskPresenter.getView().setIndex(tabCount-1);
                MainView.getInstance().setSelectedIndex(tabCount-1);
            }
        });

        add(createNewTaskButton, "flowx,cell 0 0");        

        JToggleButton tglbtnArchive = new JToggleButton("<html>Hide<br/>Archived</html>");
        tglbtnArchive.setIcon(Icons.HIDE_ARCHIVE_LARGE);
        tglbtnArchive.setSelected(true);
        add(tglbtnArchive, "cell 0 0");

        tglbtnArchive.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (tglbtnArchive.isSelected()) {
                    /* Show all tasks */
                    tglbtnArchive.setText("<html>Hide<br/>Archived</html>");
                    MainView.getInstance().setShowArchived(true);
                    tglbtnArchive.setIcon(Icons.HIDE_ARCHIVE_LARGE);
                } else {
                    /* Only show non-archived tasks */
                    tglbtnArchive.setText("<html>Show<br/>Archived</html>");
                    MainView.getInstance().setShowArchived(false);
                    tglbtnArchive.setIcon(Icons.SHOW_ARCHIVE_LARGE);
                }

                MainView.getInstance().resetAllBuckets();
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

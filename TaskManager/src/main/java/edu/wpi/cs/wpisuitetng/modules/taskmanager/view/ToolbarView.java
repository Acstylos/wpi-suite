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

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Sets up upper toolbar of TaskManager tab
 */
public class ToolbarView extends JPanel
{
    private static final long serialVersionUID = 5489162021821230861L;
    

    /**
     * Creates and positions option buttons in upper toolbar
     * @param visible boolean
     */
    public ToolbarView() {
        setLayout(new MigLayout("", "[fill]", "[grow]"));
        
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
        add(createNewTaskButton, "cell 0 0,growy");
        
        JToggleButton tglbtnArchive = new JToggleButton("<html>Show<br/>Archive</html>");
        add(tglbtnArchive, "cell 0 0,growy");
        
        tglbtnArchive.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(tglbtnArchive.getText() == "<html>Show<br/>Archive</html>"){//show everything
                    tglbtnArchive.setText("<html>Hide<br/>Archive</html>");
                    MainView.getInstance().setShowArchived(true);
                }
                
                else{//only shows non-archived
                    tglbtnArchive.setText("<html>Show<br/>Archive</html>");
                    MainView.getInstance().setShowArchived(false);
                }
                MainView.getInstance().resetAllBuckets();
            
            }
        });
    }
    

    
}

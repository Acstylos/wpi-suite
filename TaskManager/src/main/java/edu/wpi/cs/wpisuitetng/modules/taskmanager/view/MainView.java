package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;


import java.beans.PropertyChangeEvent;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.WorkflowPresenter;

/**
 * MainView is a scrollable window with a viewport that can
 * view only WorkflowViews.
 */
public class MainView extends JTabbedPane 
{
    private static final long serialVersionUID = -346061317795260862L;
    private JScrollPane scrollPane = new JScrollPane();
    //private WorkflowPresenter workflowPresenter;
    private WorkflowPresenter workflowPresenter = new WorkflowPresenter(0);
    private static final MainView mainView = new MainView();
    
    private MainView(){
        this.addTab("Workflow", scrollPane);    
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
        this.addTab("Workflow", scrollPane);    
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
        this.scrollPane.setViewportView(workflowPresenter.getView());
    }
}
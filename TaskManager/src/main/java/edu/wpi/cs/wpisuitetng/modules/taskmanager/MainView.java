package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.WorkflowPresenter;

/**
 * MainView is a scrollable window with a viewport that can
 * view only WorkflowViews. Eventually it will support viewing
 * of multiple types of JPanels.
 * 
 * @author Thefloorisjava
 */
public class MainView extends JScrollPane 
{
    private static final long serialVersionUID = -346061317795260862L;
    private WorkflowPresenter workflowPresenter;

    /**
     * Constructor for the scrollable main view.  
     */
    public MainView(){
        workflowPresenter = new WorkflowPresenter(0);
        setViewportView(workflowPresenter.getView()); // Make sure the panel can be scrolled upon
    }

    /**
     * @return the WorkflowView being displayed
     */
    public WorkflowPresenter getWorkflowPresenter() {
        return this.workflowPresenter;
    }

    /**
     * @param workflowView The WorkflowView to be displayed
     */
    public void setWorkflowPresenter(WorkflowPresenter workflowPresenter){
        this.workflowPresenter = workflowPresenter;
    }
}
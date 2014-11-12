package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;


import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.WorkflowView;

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
    private WorkflowView workflowView;

    /**
     * Constructor for the scrollable main view.  
     */
    public MainView(){ // Needs field WorkflowModel 
        workflowView = new WorkflowView("New Workflow");
        setViewportView(workflowView); // Make sure the panel can be scrolled upon
    }

    /**
     * @return the WorkflowView being displayed
     */
    public WorkflowView getWorkflowView() {
        return this.workflowView;
    }

    /**
     * @param workflowView The WorkflowView to be displayed
     */
    public void setWorkflowView(WorkflowView workflowView){
        this.workflowView = workflowView;
    }
}
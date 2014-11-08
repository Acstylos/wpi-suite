package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow.WorkflowView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow.BucketView;

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
	
	
	
	public MainView(){ // Needs field WorkflowModel 
		List<BucketView> bucketList = new ArrayList<BucketView>();
		boolean skip = false;
		BucketView bucket1 = new BucketView("New", skip);
		BucketView bucket2 = new BucketView("Scheduled", skip);
		BucketView bucket3 = new BucketView("In Progress", skip);
		BucketView bucket4 = new BucketView("Completed", skip);
		bucketList.add(bucket1);
		bucketList.add(bucket2);
		bucketList.add(bucket3);
		bucketList.add(bucket4);
		workflowView = new WorkflowView("New Workflow", bucketList); // Needs field BucketModel
		setViewportView(workflowView); // Make sure the panel can be scrolled upon
	}
	
	/**
     * @return the WorkflowView being displayed
     */
    public WorkflowView getWorkflowView() {
        return this.workflowView;
    }
    
    /**
     * 
     * @param The WorkflowView to be displayed
     */
    public void setWorkflowView(WorkflowView workflowView){
    	this.workflowView = workflowView;
    }
}
/*
 * 
 * 
 * 
 */


package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow.BucketView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow.WorkflowView;

/**
 * Creates 4 static buckets for the workflow and passes it to the view
 * 
 * @author TheFloorIsJava 
 * 
 */

public class WorkflowPresenter{
	
	private WorkflowView view;
	
	/**
	 * Constructor currently does nothing right now
	 * 
	 * @param view The view associated with this presenter
	 */
	public WorkflowPresenter(WorkflowView view) {
		this.view = view;
	}
	
	/**
	 * Creates 4 static bucket views to add to the view
	 * Then returns the list of BucketViews associated with view
	 * 
	 * @return List of BucketViews to be displayed in the view
	 */
	public List<BucketView> getBucketViews(){

        List<BucketView> bucketList = new ArrayList<BucketView>();
        BucketView bucket1 = new BucketView("New");
        BucketView bucket2 = new BucketView("Scheduled");
        BucketView bucket3 = new BucketView("In Progress");
        BucketView bucket4 = new BucketView("Completed");
        bucketList.add(bucket1);
        bucketList.add(bucket2);
        bucketList.add(bucket3);
        bucketList.add(bucket4);
		
		return bucketList;
	}
}

/*
 * 
 * 
 * 
 */


package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow.BucketView;
import javax.swing.JPanel;

/**
 * 
 * 
 * @author TheFloorIsJava
 * 
 */

public class BucketPresenter{
	
	private BucketView view;
	
	/**
	 * Constructor does nothing for now
	 * 
	 * @param view The view associated with this presenter
	 */
	public BucketPresenter(BucketView view) {
		this.view = view;
	}
	
	/**
	 * Returns the TaskViews associated with view
	 * Currently used JPanels in place of TaskViews
	 * 
	 * @return The list of TaskViews which are to be displayed in the view
	 */
	public List<JPanel> getTaskViews(){
		List<JPanel> taskViews;
		taskViews = new ArrayList<JPanel>();
		return taskViews;
	}
}

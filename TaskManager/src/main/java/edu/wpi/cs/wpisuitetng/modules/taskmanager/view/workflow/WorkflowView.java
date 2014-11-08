package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.util.List;

/**
 * WorkflowView is the panel that holds the list of buckets that represents the
 * full workflow of the project. The title of the workflow corresponds to the
 * project name the workflow is for.  
 * @author Thefloorisjava
 *
 */
public class WorkflowView extends JPanel
{
	
	private static final long serialVersionUID = -5937582878085666950L;
	private String title;
	private List<BucketView> bucketViews;
	
	public WorkflowView(String title, List<BucketView> bucketViews) { // Pass in WorkFlowModel (holds String "title" and list of BucketViews)
		for(BucketView bucket: bucketViews){
			bucket.setPreferredSize(new Dimension(250, 500));
			bucket.setMinimumSize(new Dimension(250, 500));
			bucket.setMaximumSize(new Dimension(500, 700));
			add(bucket);
		}
		
		/* Buckets will be created left to right, never on top 
		 * of each other.
		 */
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}

	public List<BucketView> getBucketViews() {
		return bucketViews;
	}

	public String getTitle() {
		return title;
	}
	
	public void setBucketViews(List<BucketView> buckets){
		this.bucketViews = buckets;
	}
	
	public void setTitle(String title){
		this.title = title;
	}

}

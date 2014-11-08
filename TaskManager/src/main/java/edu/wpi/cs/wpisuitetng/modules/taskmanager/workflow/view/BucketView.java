package edu.wpi.cs.wpisuitetng.modules.taskmanager.workflow.view;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Dimension;

public class BucketView extends JPanel
{
	
	private static final long serialVersionUID = -5937582878085666950L;
	
	
	public BucketView() {
		
		BucketPanel bucketPanel1 = new BucketPanel("New");
		bucketPanel1.setMaximumSize(new Dimension(500, 700));
		add(bucketPanel1);
		
		BucketPanel bucketPanel2 = new BucketPanel("Scheduled");
		bucketPanel2.setMaximumSize(new Dimension(500, 700));
		add(bucketPanel2);
		
		BucketPanel bucketPanel3 = new BucketPanel("In Progress");
		bucketPanel3.setMaximumSize(new Dimension(500, 700));
		add(bucketPanel3);
		
		BucketPanel bucketPanel4 = new BucketPanel("Complete");
		bucketPanel4.setMaximumSize(new Dimension(500, 700));
		add(bucketPanel4);
		
		/* Buckets will be created left to right, never on top 
		 * of each other.
		 */
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.util.List;

/**
 * BucketView is the view that displays a list of tasks. These tasks are
 * sorted by the title that is on the bucket. The default titles for the
 * 4 default buckets "New", "Scheduled", "In Progress", and "Completed".
 * @author Thefloorisjava
 *
 */
public class BucketView extends JPanel
{

	private static final long serialVersionUID = -5937582878085666950L;
	// CHANGE JPANEL TO BE TASKVIEWS WHEN MERGED
	private List<JPanel> taskViews;
	private String title;
	
	public BucketView(String title, boolean skip){ // Pass in title from BucketModel
		// If skip is false, don't add JPanels.
		// This is for testing purposes.
		if(skip){
			for(JPanel task: taskViews){
				add(task);
			}
		}
		// Tasks will be structured from top to bottom
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(250, 500));
		setMinimumSize(new Dimension(250, 500));
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}

	public List<JPanel> getTaskViews(){
		return this.taskViews;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTaskViews(List<JPanel> taskViews){
		this.taskViews = taskViews;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

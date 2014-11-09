package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.util.List;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;;

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
    private BucketPresenter presenter;

    /**
     * Constructor for BucketViews.
     * @param title Temporary constructor that will title the buckets
     */
    public BucketView(String title){ // Pass in title from BucketModel
    	presenter = new BucketPresenter(this);
    	this.taskViews = presenter.getTaskViews();
    	// Tasks will be structured from top to bottom
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 500));
        setMinimumSize(new Dimension(250, 500));
        setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
    }

    /**
     * @return Returns a list of JPanels. Will Eventually return a list of TaskViews
     */
    public List<JPanel> getTaskViews(){
        return this.taskViews;
    }

    /**
     * @return Returns the title of the bucket
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param taskViews A list of TaskViews
     */
    public void setTaskViews(List<JPanel> taskViews){
        this.taskViews = taskViews;
        for(JPanel task: taskViews){
            add(task);
        }
    }

    /**
     * @param title A string that corresponds to the title of the bucket
     */
    public void setTitle(String title) {
        this.title = title;
    }

}

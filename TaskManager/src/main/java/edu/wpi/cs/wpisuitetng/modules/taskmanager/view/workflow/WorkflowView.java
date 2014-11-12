package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.util.List;
import net.miginfocom.swing.MigLayout;

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

    /**
     * Constructor for the panel that holds the workflow of buckets.
     * @param title String that defines how a bucket will be titled.
     */
    public WorkflowView(String title) {
        /* Buckets will be created left to right, never on top 
         * of each other.
         */
        setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
        setLayout(new MigLayout("fill"));
    }

    /**
     * @return A list of BucketViews corresponding to the buckets in the workflow process.
     */
    public List<BucketView> getBucketViews() {
        return bucketViews;
    }

    /**
     * @return A string defining how a bucket will be titled.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Adds buckets into the workFlowView by smashing them to the left, allowing them
     * to collide and sit next to what was added before it. 
     * @param buckets List of buckets corresponding to the buckets in the workflow process.
     */
    public void setBucketViews(List<BucketView> buckets){
        this.bucketViews = buckets;
        for(BucketView bucket: bucketViews){
            bucket.setPreferredSize(new Dimension(250, 500));
            bucket.setMinimumSize(new Dimension(250, 500));
            bucket.setMaximumSize(new Dimension(500, 700));
            add(bucket, "dock west");
        }
    }

    /**
     * @param title A string defining how a bucket is titled.
     */
    public void setTitle(String title){
        this.title = title;
    }

}

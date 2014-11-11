package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;

/**
 * BucketView is the view that displays a list of tasks. These tasks are sorted
 * by the title that is on the bucket. The default titles for the 4 default
 * buckets "New", "Scheduled", "In Progress", and "Completed".
 * 
 * @author Thefloorisjava
 *
 */
public class BucketView extends JPanel {

    private static final long serialVersionUID = -5937582878085666950L;
    // CHANGE JPANEL TO BE TASKVIEWS WHEN MERGED
    private String title;
    private List<JPanel> taskViews;
    private JPanel taskViewHolderPanel;
    private JScrollPane scrollPane;
    private JButton addTaskButton;

    /**
     * Constructor for BucketViews.
     * 
     * @param title
     *            Temporary constructor that will title the buckets
     */
    public BucketView(String title) { // Pass in title from BucketModel
        // Tasks will be structured from top to bottom

        this.title = title;
        taskViews = new ArrayList<JPanel>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(250, 32767));
        setPreferredSize(new Dimension(250, 500));
        setMinimumSize(new Dimension(250, 500));
        setBorder(new TitledBorder(null, title, TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        setLayout(new MigLayout("", "[grow]", "[max][min]"));

        // Need a scroll pane to allow us to scroll through all tasks in the
        // bucketView.
        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        // This is added to the 0th column, 0th row in the layout.
        add(scrollPane, "cell 0 0, grow");

        /*
         * This panel will be holding all of the tasks. It's inside of a
         * scrollable pane, allowing multiple tasks to be added without changing
         * the screen, and allowing scrolling capability to see all tasks.
         */
        taskViewHolderPanel = new JPanel();
        taskViewHolderPanel.setBorder(null);
        scrollPane.setViewportView(taskViewHolderPanel);
        taskViewHolderPanel.setLayout(new MigLayout("fill"));

        addTaskButton = new JButton("Add New Task...");
        addTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO: make this add a task to model's list of tasks and call
                 * setTaskView from this class to update the tasks in the view.
                 */
            }
        });
        // This is added to the 0th column, 1st row in the layout, and keeps it
        // on the bottom.
        add(addTaskButton, "cell 0 1, grow");
    }

    /**
     * @return Returns a list of JPanels. Will Eventually return a list of
     *         TaskViews
     */
    public List<JPanel> getTaskViews() {
        return this.taskViews;
    }

    /**
     * @return Returns the title of the bucket
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param taskViews
     *            A list of TaskViews
     */
    public void setTaskViews(List<JPanel> taskViews) {
        this.taskViews = taskViews;
        for (JPanel task : taskViews) {
            taskViewHolderPanel.add(task, "dock north");
        }
    }

    /**
     * @param title
     *            A string that corresponds to the title of the bucket
     */
    public void setTitle(String title) {
        this.title = title;
    }

}

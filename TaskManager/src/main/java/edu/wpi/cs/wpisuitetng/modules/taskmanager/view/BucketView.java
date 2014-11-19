package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;

import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;

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
    private String title;
    private List<TaskView> taskViews;
    private JPanel taskViewHolderPanel;
    private JScrollPane scrollPane;
    private JButton addTaskButton;
    private BucketPresenter presenter;

    /**
     * Constructor for BucketViews.
     * @param title Temporary constructor that will title the buckets
     */
    public BucketView(String title) { // Pass in title from BucketModel
        // Tasks will be structured from top to bottom

        this.title = title;
        taskViews = new ArrayList<TaskView>();
        setMaximumSize(new Dimension(300, 32767));
        setPreferredSize(new Dimension(300, 500));
        setMinimumSize(new Dimension(300, 500));
        setBorder(new TitledBorder(null, title, TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        setLayout(new MigLayout("", "[grow]", "[max][min]"));
        
        // Need a scroll pane to allow us to scroll through all tasks in the bucketView. 
        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        // This is added to the 0th column, 0th row in the layout.
        add(scrollPane, "cell 0 0, grow");
        
        /* This panel will be holding all of the tasks. It's inside of a scrollable
         * pane, allowing multiple tasks to be added without changing the screen,
         * and allowing scrolling capability to see all tasks.
         */
        taskViewHolderPanel = new JPanel();
        taskViewHolderPanel.setBorder(null);
        scrollPane.setViewportView(taskViewHolderPanel);
        taskViewHolderPanel.setLayout(new MigLayout("fill"));
         
        addTaskButton = new JButton("Add New Task...");
        // This is added to the 0th column, 1st row in the layout, and keeps it on the bottom.
        add(addTaskButton, "cell 0 1, grow");
    }
    
    /**
     * Add an {@link ActionListener} that will be called when a task is added by the user
     * @param listener
     */
    public void addOnAddTaskListener(ActionListener listener){
        this.addTaskButton.addActionListener(listener);
    }

    /**
     * @return Returns a list of JPanels. Will Eventually return a list of TaskViews
     */
    public List<TaskView> getTaskViews() {
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
    public void setTaskViews(List<TaskView> taskViews) {
        taskViewHolderPanel.removeAll();
        
        this.taskViews = taskViews;
        for (TaskView task : taskViews) {
            taskViewHolderPanel.add(task, "dock north");
        }
    }
    
    /**
     * @param task 
     */
    public void addTaskToView(TaskView task){
        taskViewHolderPanel.add(task, "dock north");
    }

    /**
     * @param title A string that corresponds to the title of the bucket
     */
    public void setTitle(String title) {
        this.title = title;
        setBorder(new TitledBorder(null, title, TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
    }

}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

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
    private ArrayList<TaskView> taskViews = new ArrayList<TaskView>();
    private String title;
    private JLabel titleLabel = new JLabel();
    private JPanel titlePanel = new JPanel();
    private JPanel taskViewHolderPanel = new JPanel();
    private JScrollPane taskScrollPane = new JScrollPane();

    /**
     * Constructor for BucketViews.
     * @param title Temporary constructor that will title the buckets
     */
    public BucketView(String title) {
        // Ensure the layout and properties of this panel is correct
        this.title = title;
        this.setMaximumSize(new Dimension(300, 32767));
        this.setPreferredSize(new Dimension(300, 200));
        this.setMinimumSize(new Dimension(300, 200));
        this.setBackground(Color.LIGHT_GRAY);
        this.setBorder(new EmptyBorder(0, 5, 5, 5));
        this.setLayout(new MigLayout("fill"));
        this.titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        this.titleLabel.setText(title);
        
        // Start by adding the changeable title to the top of the view
        this.add(titlePanel, "dock north");
        this.titlePanel.setBackground(Color.LIGHT_GRAY);
        this.titlePanel.setBorder(null);
        this.titlePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.titlePanel.add(titleLabel, "cell 0 0, alignx center, aligny center");
        
        
        // Need a scroll pane to allow us to scroll through all tasks in the bucketView. 
        this.add(taskScrollPane, "dock north");
        this.taskViewHolderPanel.setBackground(Color.LIGHT_GRAY);
        this.taskViewHolderPanel.setLayout(new MigLayout("fill"));
        this.taskScrollPane.setViewportView(taskViewHolderPanel);    
    }
    
    /**
     * @return Returns a list of TaskViews
     */
    public ArrayList<TaskView> getTaskViews() {
        return this.taskViews;
    }

    /**
     * @return Returns the title of the bucket
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * TODO: DO WE NEED THIS?
     * @param taskViews A list of TaskViews
     */
    public void setTaskViews(ArrayList<TaskView> taskViews) {
        this.taskViews = taskViews;
        for (TaskView task : taskViews) {
            this.taskViewHolderPanel.add(task, "dock north");
        }
    }
    
    /**
     * Adds a single MiniTaskView to the bucket, with spacers
     * @param task The MiniTaskView to be added to the bucket
     */
    public void addTaskToView(TaskView task){
        this.taskViews.add(task);
        this.taskViewHolderPanel.add(task, "dock north");
        Component spacerStrut = Box.createVerticalStrut(5);
        this.taskViewHolderPanel.add(spacerStrut, "dock north");
    }

    /**
     * Changes the title label of the bucket to reflect the buckets name.
     * @param title A string that corresponds to the title of the bucket
     */
    public void setTitle(String title) {
        this.title = title;
        this.titleLabel.setText(title);
    }

}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class MiniTaskView extends JPanel {
    
    private Date dueDate;
    private String taskName;
    JLabel taskNameLabel = new JLabel();

    /**
     * Create the panel.
     */
    public MiniTaskView(String taskName, Date dueDate) {
        setLayout(new MigLayout("fill"));
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.add(taskNameLabel, "dock west");
        this.taskNameLabel.setText(taskName);
    }
    
    /**
     * @return the dueDate of the task
     */
    public Date getDueDate() {
        return this.dueDate;
    }

    /**
     * @return the task name of the task
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * @param dueDate the dueDate of the task to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    /**
     * @param taskName the title to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
        this.taskNameLabel.setText(taskName);
    }

}

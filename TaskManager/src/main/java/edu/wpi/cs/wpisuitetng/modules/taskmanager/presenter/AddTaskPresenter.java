/*
 * 
 * 
 * 
 */


package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.modules.taskmanager.workflow.model.Task;
import edu.wpi.cs.modules.taskmanager.workflow.model.Bucket;

/**
 * This presenter responds when a user clicks on the add new task button
 * on a specific bucket and adds a blank task to the workflow
 * 
 * @author TheFloorIsJava
 * 
 */

public class AddTaskPresenter implements ActionListener {
	
	private final Bucket model;
	
	/**
	 * 
	 * 
	 */
	public AddTaskPresenter(Bucket model) {
		this.model = model;
	}
	
	/*
	 * This method is called when a user clicks the add task button on a bucket
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Adds the task to the bucket	
		addTaskToModel();
	}
	
	/**
	 * This method is called when ...
	 * @param task
	 */
	public void addTaskToModel() {
		WorkflowTask task = new WorkflowTask();
		ArrayList<WorkflowTask> taskList = model.getTasks();
		// task.set...
		taskList.add(task);
		model.setTasks(taskList);
		
	}
}

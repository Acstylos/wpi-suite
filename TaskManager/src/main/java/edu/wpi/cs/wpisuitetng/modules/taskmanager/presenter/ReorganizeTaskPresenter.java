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

// import edu.wpi.cs.modules.taskmanager.workflow.model."";

/**
 * This presenter responds when a user attempts to change the position of 
 * a task in the order of a bucket
 * 
 * @author Lambert Wang
 * 
 */

public class ReorganizeTaskPresenter implements ActionListener {
	
	private final Bucket model;
	private final WorkflowTask task;
	private final int index;
	
	/**
	 * 
	 * 
	 */
	public ReorganizeTaskPresenter(Bucket model, WorkflowTask task, int index) {
		this.model = model;
		this.task = task;
		this.index = index;
	}
	
	/*
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		orderTaskInModel();
	}
	
	/**
	 * This method is called when ...
	 * @param task
	 */
	public void orderTaskInModel() {
		ArrayList<WorkflowModel> taskList = model.getTasks();
		if(index < taskList.size()){
			taskList.remove(task);
			taskList.add(index, task);
			model.setTasks(taskList);
		}
		
	}
}

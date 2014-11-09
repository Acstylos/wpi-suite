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
 * This presenter responds when a user attempts to move a task
 * from one bucket to another
 * 
 * @author TheFloorIsJava
 * 
 */

public class MoveTaskPresenter implements ActionListener {
	
	private final Bucket fromModel;
	private final WorkflowTask task;
	private final BucketPanel view;
	/**
	 * 
	 * 
	 */
	public MoveTaskPresenter(Bucket fromModel, BucketPanel view WorkflowTask task) {
		this.fromModel = fromModel;
		this.view = view;
		this.task = task;
		
	}
	
	/*
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		MoveTaskToModel(view.getMoveBucketId().getBucket());
		view.getMoveBucketId().setBucket(emptyBucket);
	}
	
	/**
	 * This method is called when ...
	 * @param task
	 */
	public void MoveTaskToModel(Bucket toModel) {
		ArrayList<WorkflowTask> fromTaskList = fromModel.getTasks();
		ArrayList<WorkflowTask> toTaskList = toModel.getTasks();
		fromTaskList.remove(task);
		toTaskList.add(task);
		fromModel.setTasks(fromTaskList);
		toModel.setTasks(toTaskList);
		
	}
}

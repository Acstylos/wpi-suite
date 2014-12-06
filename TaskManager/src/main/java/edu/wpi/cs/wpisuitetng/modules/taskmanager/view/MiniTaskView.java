/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

/**
 * This is the TaskView shown inside of buckets to reduce the amount of clutter on screen
 */
public class MiniTaskView extends JPanel {

	private Date dueDate;
	private String taskName;
	private String fullName;
	private JLabel taskNameLabel = new JLabel();
	private JButton editButton = new JButton("Edit");
	private JPanel userPanel = new JPanel();
	private JLabel dueDateLabel = new JLabel();
	private boolean expanded = false;
	private final JScrollPane userScrollPane = new JScrollPane();

	/**
	 * Create the panel.
	 * @param taskName Name of the task. Might be the short string.
	 * @param dueDate Date the task is due
	 * @param fullName Full Title of the task.
	 */
	public MiniTaskView(String taskName, Date dueDate, String fullName) {
		this.taskName = taskName;
		this.dueDate = dueDate;
		this.fullName = fullName;
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.userPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		this.setMaximumSize(new Dimension(150, 60));
	}

	/**
	 * @param taskName Name of the task. Might be the short string.
	 * @param dueDate Date the task is due
	 * @param fullName Full Title of the task.
	 */
	public void updateMiniTaskView(String taskName, Date dueDate, String fullName){
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");
		this.taskName = taskName;
		this.dueDate = dueDate;
		this.fullName = fullName;

		this.taskNameLabel.setText(this.fullName);
		this.taskNameLabel.setToolTipText(this.fullName);
		this.taskNameLabel.setIcon(Icons.TASK);

		if(dueDate != null){
			this.dueDateLabel.setText("Due : " + dateFormat.format(dueDate));
		}
	}

	/**
	 * Remove all of the components in the view, then add them back in
	 * with the proper layout for a collapsed view.
	 */
	public void setCollapsedView(){
		this.removeAll();
		this.setLayout(new MigLayout("fill"));
		this.add(taskNameLabel, "dock west");
		this.expanded = false;
		this.revalidate();
		this.repaint();
	}


	/**
	 * Remove all of the components in the view, then add them back in
	 * with the proper layout for an expanded view.
	 */
	public void setExpandedView(){
		this.removeAll();

		this.setLayout(new MigLayout("", "0[grow][grow]", "0[][][grow][]"));
		this.add(taskNameLabel, "cell 0 0 2 1");
		this.add(dueDateLabel, "cell 0 1");
		this.userScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.userScrollPane.setBorder(new TitledBorder(null, "Assigned Users", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		this.userScrollPane.setVisible(true);
		this.userPanel.setVisible(true);
		this.add(userScrollPane, "cell 0 2 2 1,grow");
		this.userScrollPane.setViewportView(userPanel);

		this.expanded = true;
		this.add(editButton, "cell 0 3,alignx left,aligny bottom");
		this.revalidate();
		this.repaint();
	}

	/**
	 * Adds all users in the UserList to the panel as text.
	 * @param userList List of user names to add to the panel.
	 */
	public void addUsersToUserPanel(List<String> userList){
		this.userPanel.removeAll();
		for(String name: userList){
			JLabel userLabel = new JLabel(name);
			this.userPanel.add(userLabel, "dock north");
		}
	}

	/**
	 * Add the listener for changing tabs
	 * @param listener  the event that will trigger the action
	 */     
	public void addOnClickOpenExpandedView(MouseListener listener){
		this.addMouseListener(listener);
		this.taskNameLabel.addMouseListener(listener);
		this.userPanel.addMouseListener(listener);
		this.userScrollPane.addMouseListener(listener);
	}

	/**
	 * Adds the listener for opening the editing tab of the task.
	 * @param listener The listener that reacts on button click.
	 */
	public void addOnClickEditButton(ActionListener listener){
		this.editButton.addActionListener(listener);
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
	 * @return Label of taskName
	 */
	public JLabel getTaskNameLabel() {
		return taskNameLabel;
	}

	/**
	 * @param dueDate the dueDate of the task to set
	 */
	public void setDueDate(Date dueDate) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		this.dueDate = dueDate;
		this.dueDateLabel.setText(dateFormat.format(dueDate));
	}
	/**
	 * @param taskName the title to set
	 * @param fullName the full name of the task
	 */
	public void setTaskName(String taskName, String fullName) {
		this.taskName = taskName;
		this.taskNameLabel.setText(fullName);
		this.taskNameLabel.setToolTipText(fullName);
	}

	/**
	 * @return fullName of task
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the task name to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return If this task is expanded or not.
	 */
	public boolean isExpanded(){
		return expanded;
	}


}

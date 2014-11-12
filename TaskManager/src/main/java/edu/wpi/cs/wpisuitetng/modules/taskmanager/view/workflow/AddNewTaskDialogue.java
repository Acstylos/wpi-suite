/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JFormattedTextField;

public class AddNewTaskDialogue extends JDialog implements ActionListener{

    private final JPanel contentPanel = new JPanel();
    private JTextField taskName;
    private JTextField description;
    private JFormattedTextField estimatedEffort;
    private JFormattedTextField dueDate;

    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//	try {
//	    AddNewTaskDialogue dialog = new AddNewTaskDialogue();
//	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//	    dialog.setVisible(true);
//	} catch (Exception e) {
//	    e.printStackTrace();
//	}
//    }

    /**
     * Create the dialog.
     */
    public AddNewTaskDialogue() {
    	setTitle("Add New Task");
	setBounds(100, 100, 418, 215);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	contentPanel.setLayout(new MigLayout("", "[52px][86px,grow]", "[20px][][][][][]"));
	{
		JLabel lblNewLabel = new JLabel("Task Name");
		contentPanel.add(lblNewLabel, "cell 0 1,alignx trailing");
	}
	/**
	 * Gets the task name field
	 */
	{
		taskName = new JTextField();
		contentPanel.add(taskName, "cell 1 1,growx,aligny top");
		taskName.setColumns(10);
	}
	{
		JLabel lblNewLabel_1 = new JLabel("Description");
		contentPanel.add(lblNewLabel_1, "cell 0 2,alignx trailing");
	}
	/**
	 * Gets the task description field
	 */
	{
		description = new JTextField();
		contentPanel.add(description, "cell 1 2,growx");
		description.setColumns(10);
	}
	{
		JLabel lblEstimatedEffort = new JLabel("Estimated Effort");
		contentPanel.add(lblEstimatedEffort, "cell 0 3,alignx trailing");
	}
	{
		estimatedEffort = new JFormattedTextField();
		contentPanel.add(estimatedEffort, "cell 1 3,growx");
	}
	{
		JLabel lblNewLabel_3 = new JLabel("Due Date");
		contentPanel.add(lblNewLabel_3, "cell 0 4,alignx trailing");
	}
	{
		dueDate = new JFormattedTextField(new Date());
		contentPanel.add(dueDate, "cell 1 4,growx");
	}
	{
	    /**
	     * Gives commands for the buttons
	     */
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	    {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	    }
	}
    }
    
    /**
     * Actions for the buttons to do. Right now there are outputs
     * for debugging.
     */
    public void actionPerformed(ActionEvent e){
	String action = e.getActionCommand();
	if (action.equals("OK")) {
	    System.out.println((String)taskName.getText());
	    System.out.println((String)description.getText());
	    System.out.println(estimatedEffort.getValue());
	    System.out.println(dueDate.getValue());
	    this.dispose();
	}
	if (action.equals("Cancel")) {
	    this.dispose();
	}
    }

}

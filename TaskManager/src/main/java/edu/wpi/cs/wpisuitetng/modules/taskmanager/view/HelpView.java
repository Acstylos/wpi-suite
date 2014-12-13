/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * Setup the view for help
 * @author TheFloorIsJava
 * @version
 */
public class HelpView extends JPanel{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -157101066248714214L;

	/**
	 * Button to close the help tab
	 */
	private JButton closeButton = new JButton("Close");

	/**
	 * Keep the status of if the help tab is opened or not
	 */
	static boolean isOpened = false;

	/**
	 * Create the help panel
	 */
	public HelpView() {
		setLayout(new MigLayout("fill", "[]", "[][]"));		
		final JLabel lblNewLabel = new JLabel("Please wait, help is on the way.");
		add(lblNewLabel, "cell 0 0,alignx center,aligny center");
		this.add(closeButton, "cell 0 1,alignx center,aligny bottom");
		this.closeButton.setIcon(Icons.CANCEL);
		this.closeButton.setVisible(true);
	}

	/** 
	 * Add the listener for the cancel Button
	 * @param listener 
	 *        The action that calls the listener
	 */
	public void addCloseOnClickListener(ActionListener listener){
		this.closeButton.addActionListener(listener);
	}
}

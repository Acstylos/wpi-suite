/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.HelpView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;

/**
 * This class created a helpView and gives the function of 
 * close button.
 * @author TheFloorIsJava
 * @version
 */
public class HelpPresenter {

	/**
	 * View of the help
	 */
	private static HelpView view = new HelpView();

	/**
	 * Constructs a HelpPresenter 
	 */
	public HelpPresenter() {
		registerCallbacks();
	}

	/**
	 * Register callbacks with the local view.
	 */
	private void registerCallbacks() {
		view.addCloseOnClickListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = MainView.getInstance().indexOfComponent(view);
				MainView.getInstance().remove(index);
				view.revalidate();
				view.repaint();
				MainView.getInstance().setSelectedIndex(0);
			}
		});
	}

	/**
	 * Getter for view
	 * @return return the help view
	 */
	public HelpView getView() {
		return view;
	}	
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;


import javax.swing.JPanel;
import javax.swing.JTextArea;


import net.miginfocom.swing.MigLayout;

/**
 * constructs message view for comments and history of tasks message: message
 * within comment box
 * 
 */
public class ActivityView extends JPanel {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7743312885538872898L;
    /**
     * Create the panel.
     */
    private JTextArea pastActivityText = new JTextArea();

    /**
     * Constructor sets up panel and colors
     */
    public ActivityView() {
        this.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.add(pastActivityText, "cell 0 0, grow");
        pastActivityText.setEditable(false);
        pastActivityText.setForeground(Color.BLACK);
        pastActivityText.setBackground(Color.LIGHT_GRAY);
        pastActivityText.setWrapStyleWord(true);
        pastActivityText.setLineWrap(true);
    }

    /**
     * Constructor sets up panel and colors
     * 
     * @param text
     * 
     */
    public ActivityView(String text) {
        this.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.add(pastActivityText, "cell 0 0, grow");
        pastActivityText.setEditable(false);
        pastActivityText.setForeground(Color.BLACK);
        pastActivityText.setBackground(Color.LIGHT_GRAY);
        pastActivityText.setWrapStyleWord(true);
        pastActivityText.setLineWrap(true);
        pastActivityText.setText(text);
    }

    /**
     * @param activity
     *            set to this text
     */
    public void setActivity(String activity) {
        pastActivityText.setText(activity);
    }

    /**
     * @return Activity Text
     */
    public String getActivity() {
        return pastActivityText.getText();
    }
}
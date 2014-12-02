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
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import net.miginfocom.swing.MigLayout;

/*constructs message view for comments and history of tasks
 * message: message within comment box
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
        this.pastActivityText.setEditable(false);
        this.pastActivityText.setForeground(Color.BLACK);
        this.pastActivityText.setBackground(Color.LIGHT_GRAY);
        this.pastActivityText.setWrapStyleWord(true);
        this.pastActivityText.setLineWrap(true);
    }

    /**
     * Constructor sets up panel and colors
     */
    public ActivityView(String text) {
        this.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.add(pastActivityText, "cell 0 0, grow");
        this.pastActivityText.setEditable(false);
        this.pastActivityText.setForeground(Color.BLACK);
        this.pastActivityText.setBackground(Color.LIGHT_GRAY);
        this.pastActivityText.setWrapStyleWord(true);
        this.pastActivityText.setLineWrap(true);
        this.pastActivityText.setText(text);
    }

    /**
     * sets the message within a MessageView and sets it up
     * 
     * @param message
     *            : message within MessageView
     */
    public void setMessage(String message) {
        this.pastActivityText.setText(message);
    }

    /**
     * @return the string on the Comment JTextField
     */
    public String getMessage() {
        return this.pastActivityText.getText();
    }

    /**
     * @param activity set to this text
     */
    public void setActivity(String activity) {
        this.pastActivityText.setText(activity);
    }

    /**
     * @param user set to this user
     */
    public void setUser(User user) {
        // TODO Auto-generated method stub

    }

    /**
     * @param date set to this date
     */
    public void setDate(Date date) {
        // TODO Auto-generated method stub

    }

    /**
     * @return Activity Text
     */
    public String getActivity() {
        return this.pastActivityText.getText();
    }

    /**
     * 
     * @return current Date and Time
     */
    public Date getDate() {
        // TODO Auto-generated method stub
        return new Date();
    }

    /**
     * 
     * @return User with name, username, password, id
     */
    public User getUser() {
        // TODO Auto-generated method stub
        return new User("Will", "Will", "Will", 0);
    }
}
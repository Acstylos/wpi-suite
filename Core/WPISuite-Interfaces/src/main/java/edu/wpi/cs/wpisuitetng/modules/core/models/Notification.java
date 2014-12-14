/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.core.models;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * The Data Model representation of a notification request. The client generates
 * this structure and sends it to /WPISuite/API/Notification/ in order to email
 * notification messages to other users.
 */
public class Notification extends AbstractModel {

    String subject, content;
    List<String> recipients;

    /*
     * @return The subject of the message
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The new subject of the message
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return The text of the message itself
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The text of the message itself
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The list email addresses to notify
     */
    public List<String> getRecipients() {
        return recipients;
    }

    /**
     * @param recipients The list email addresses to notify
     */
    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    /**
     * Serialize the message into a JSON string
     * 
     * @return a JSON representation of the message
     */
    @Override
    public String toJson() {
        return new Gson().toJson(this, Notification.class);
    }

    /**
     * Create a <code>Notification</code> object from the JSON string
     * representation
     * 
     * @param json
     *            a JSON representation of the message
     * @return
     */
    public static Notification fromJSON(String json) {
        return new Gson().fromJson(json, Notification.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Notification [subject=" + subject + ", content=" + content
                + ", recipients=" + recipients + "]";
    }

    /**
     * Unimplemented
     */
    @Override
    public Boolean identify(Object o) {
        return null;
    }

    /**
     * Unimplemented
     */
    @Override
    public void save() {
        return;
    }

    /**
     * Unimplemented
     */
    @Override
    public void delete() {
        return;
    }
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import edu.wpi.cs.wpisuitetng.modules.core.models.Notification;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;

/**
 * Servelet for sending email notifications from WPISuite
 * 
 * Email notifications are sent by the server when a request is made from a
 * client to notify other users about something. This hides the login details of
 * the sending account from the client.
 */
@WebServlet("/notification")
public class WPINotificationServlet extends HttpServlet {

    private static final long serialVersionUID = 270207518571957971L;

    private static final String SMTP_HOST_NAME = "smtp.googlemail.com";
    private static final int SMTP_PORT = 465;

    /**
     * {@inheritDoc}
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Notification notification = Notification.fromJSON(req.getReader()
                .readLine());

        /* Get the secret authentication info for this project from the database */
        String emailUsername, emailPassword;
        try {
            String projectIdNum = notification.getProject().getIdNum();
            Project project = ManagerLayer.getInstance().getProjects()
                    .getEntity(projectIdNum)[0];
            emailUsername = project.getAutomaticEmailAddress();
            emailPassword = project.getAutomaticEmailPassword();
        } catch (Exception e) {
            throw new ServletException(e);
        }

        try {
            /*
             * Send an email to the addresses marked as recipients of the
             * notification
             */
            Email email = new SimpleEmail();
            email.setAuthentication(emailUsername, emailPassword);
            email.setSSLOnConnect(true);

            email.setFrom(emailUsername, "WPISuite Task Manager");
            email.setSubject(notification.getSubject());
            email.setMsg(notification.getContent());
            for (String recipient : notification.getRecipients()) {
                email.addTo(recipient);
            }

            email.setHostName(SMTP_HOST_NAME);
            email.setSmtpPort(SMTP_PORT);
            email.send();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
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
            Project project = ManagerLayer.getInstance().getProjects()
                    .getEntityByName(null, notification.getProjectName())[0];
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
            HtmlEmail email = new HtmlEmail();
            email.setHostName(SMTP_HOST_NAME);
            email.setAuthentication(emailUsername, emailPassword);
            
            email.setHtmlMsg(notification.getContent())
                 .setSSLOnConnect(true)
                 .setFrom(emailUsername, "WPISuite Task Manager")
                 .setSubject(notification.getSubject())
                 .setSmtpPort(SMTP_PORT);
            
            for (String recipient : notification.getRecipients()) {
                email.addTo(recipient);
            }
            
            email.send();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

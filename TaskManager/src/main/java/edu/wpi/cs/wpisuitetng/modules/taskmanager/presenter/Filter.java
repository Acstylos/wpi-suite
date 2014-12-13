/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;

public class Filter {
    /**
     * This function tests if a task should be shown in the main view after
     * being filtered.
     * 
     * @param task
     *            the task to be compared to the filter cases
     * @return true if the task matches all of the filters
     */
    public boolean matches(TaskPresenter task) {
        Color filterColor = MainView.getInstance().getFilterColor();
        Date taskDueDate = task.getModel().getDueDate();
        Date filterStartDate = MainView.getInstance().getFilterStartDate();
        Date filterEndDate = MainView.getInstance().getFilterEndDate();
        String filterText = MainView.getInstance().getFilterText();
        String filterUser = MainView.getInstance().getFilterUser();

        if (!(filterStartDate == null || filterStartDate.equals("") || (taskDueDate
                .after(filterStartDate) || taskDueDate.equals(filterStartDate)))) {
            return false;
        }
        if (!((filterEndDate == null || filterEndDate.equals("") || (taskDueDate
                .before(filterEndDate) || taskDueDate.equals(filterEndDate))))) {
            return false;
        }
        if (!(filterColor.equals(task.getModel().getLabelColor()) || filterColor
                .equals(MainView.getInstance().getNoFilterColor()))) {
            return false;
        }
        if (!(filterText == null
                || Pattern.matches("(?i:.*" + filterText + ".*)", task
                        .getModel().getDescription()) || Pattern.matches(
                "(?i:.*" + filterText + ".*)", task.getModel().getTitle()))) {
            return false;
        }
        if (!(filterUser == null || filterUser.equals("") || testAssignedUser(
                task, filterUser))) {
            return false;
        }
        return true;

    }

    /**
     * This function is a helper function to determine if a task contains a user
     * who's username is in part or fully the username being filtered for.
     * 
     * @param task
     *            the task being filtered
     * @param filterUser
     *            the partial username to search for
     * @return true if the partial username is contained in the task
     */
    private boolean testAssignedUser(TaskPresenter task, String filterUser) {
        ArrayList<Integer> userIds = (ArrayList<Integer>) task
                .getAssignedUserList();
        ArrayList<String> userNames = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();
        for (int i : userIds) {
            userNames.add(task.idToUsername(i));
        }
        for (int i : userIds) {
            names.add(task.idToName(i));
        }
        for (String s : userNames) {
            if (Pattern.matches("(?i:.*" + filterUser + ".*)", s)) {
                return true;
            }
        }
        for (String s : names) {
            if (Pattern.matches("(?i:.*" + filterUser + ".*)", s)) {
                return true;
            }
        }
        return false;
    }
}

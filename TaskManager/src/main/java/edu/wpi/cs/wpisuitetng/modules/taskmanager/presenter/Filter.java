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

/**
 * The purpose of this class is to handle filtering for the fields that tasks
 * are filterable by.
 * 
 * @author TheFloorIsJava
 *
 */
public class Filter {

    private Date filterStartDate = null;
    private Date filterEndDate = null;
    private String filterText = null;
    private String filterUser = null;
    private boolean includeArchives = false;
    private Color noFilterColor = new Color(238, 238, 238);
    private Color filterColor = noFilterColor;

    /**
     * this function sets the color to filter by.
     * 
     * @param filterColor
     *            the color to filter by
     */
    public void setFilterColor(Color filterColor) {
        this.filterColor = filterColor;
    }

    /**
     * this function sets the start date to filter by
     * 
     * @param filterStartDate
     *            the date to start filtering by
     */
    public void setStartDate(Date filterStartDate) {
        this.filterStartDate = filterStartDate;
    }

    /**
     * this function sets the end date to filter by
     * 
     * @param filterEndDate
     *            the date the filter ends at
     */
    public void setEndDate(Date filterEndDate) {
        this.filterEndDate = filterEndDate;
    }

    /**
     * this function sets the text to filter by
     * 
     * @param filterText
     *            the text to filter by
     */
    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    /**
     * this function sets the user to filter by
     * 
     * @param filterUser
     *            the user to filter by
     */
    public void setFilterUser(String filterUser) {
        this.filterUser = filterUser;
    }

    /**
     * this function sets whether or not archived tasks should be shown.
     * 
     * @param includeArchived
     *            whether or not archived task should be shown.
     */
    public void setIncludeArchived(boolean includeArchived) {
        this.includeArchives = includeArchived;
    }

    /**
     * This function tests if a task should be shown in the main view after
     * being filtered.
     * 
     * @param task
     *            the task to be compared to the filter cases
     * @return true if the task matches all of the filters
     */
    public boolean matches(TaskPresenter task) {
        Date taskDueDate = task.getModel().getDueDate();

        if (!(filterStartDate == null || filterStartDate.equals("") || (taskDueDate
                .after(filterStartDate) || taskDueDate.equals(filterStartDate)))) {
            return false;
        }
        if (!((filterEndDate == null || filterEndDate.equals("") || (taskDueDate
                .before(filterEndDate) || taskDueDate.equals(filterEndDate))))) {
            return false;
        }
        if (!(filterColor.equals(noFilterColor) || filterColor.equals(task
                .getModel().getLabelColor()))) {
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
        if (!includeArchives && task.getModel().getIsArchived()) {
            return false;
        }
        return true;

    }

    /**
     * This function is a helper function to determine if a task contains a user
     * whose username is in part or fully the username being filtered for.
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

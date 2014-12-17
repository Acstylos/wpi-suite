/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.iterationcontroller.AddIterationController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.IterationDate;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * List of Iterations being pulled from the server
 * 
 * @version $Revision: 1.0 $
 * @author justinhess
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class IterationModel extends AbstractListModel {

    /**
     * The list in which all the Iterations for a single project are contained
     */
    private ArrayList<Iteration> listOfIterations;
    private int nextID; // the next available ID number for the Iterations that
    // are added.

    private Iteration backlog;

    // the static object to allow the Iteration model to be
    private static IterationModel instance;
    private ViewEventController viewEventController;
    private AddIterationController addIterationController;

    /**
     * Constructs an empty list of Iterations for the project
     */
    protected IterationModel() {
        backlog = null;
        listOfIterations = new ArrayList<Iteration>();
        nextID = 0;

        viewEventController = ViewEventController.getInstance();
        addIterationController = AddIterationController.getInstance();
    }

    /**
     * @return the instance of the Iteration model singleton.
     */
    public static IterationModel getInstance() {
        if (instance == null) {
            instance = new IterationModel();
        }

        return instance;
    }

    /**
     * Sets the backlog iteration for the iteration model
     * 
     * @param iter Iteration
     */
    public void setBacklog(Iteration iter) {
        this.backlog = iter;
    }

    /**
     * Adds a single Iteration to the Iterations of the project
     * 
     * @param newIter Iteration
     */
    public void addIteration(Iteration newIter) {
        listOfIterations.add(newIter);
        try {
            addIterationController.addIteration(newIter);
        } catch (Exception e) {
            //TODO Stop swallowing exceptions
            e.printStackTrace();
        }

        viewEventController.refreshTree();
    }

    /**
     * Provides the number of elements in the list of Iterations for the
     * project. This function is called internally by the JList in
     * NewIterationPanel. Returns elements in reverse order, so the newest
     * Iteration is returned first.
     * 
     * @return the number of Iterations in the project
     * @see javax.swing.ListModel#getSize()
     */
    public int getSize() {
        return listOfIterations.size();
    }

    /**
     * Provides the next ID number that should be used for a new Iteration that
     * is created.
     * 
     * @return the next open id number
     */
    public int getNextID() {

        return this.nextID++;
    }

    /**
     * This function takes an index and finds the Iteration in the list of
     * Iterations for the project. Used internally by the JList in
     * NewIterationModel.
     * 
     * @param index The index of the Iteration to be returned
     * @return the Iteration associated with the provided index
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public Iteration getElementAt(int index) {
        return listOfIterations.get(listOfIterations.size() - 1 - index);
    }

    /**
     * Removes all Iterations from this model
     * NOTE: One cannot simply construct a new instance of the model, because
     * other classes in this module have references to it. Hence, we manually
     * remove each Iteration from the model.
     */
    public void emptyModel() {
        int oldSize = getSize();
        Iterator<Iteration> iterator = listOfIterations.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
        viewEventController.refreshTable();
        viewEventController.refreshTree();
    }

    /**
     * Adds the given array of Iterations to the list
     * 
     * @param Iterations
     *            the array of Iterations to add
     */
    public void addIterations(Iteration[] iterations) {
        for (int i = 0; i < iterations.length; i++) {
            if (iterations[i].getName().equals("Backlog")) {
                backlog = iterations[i];
            }

            this.listOfIterations.add(iterations[i]);

            if (iterations[i].getId() >= nextID) {
                nextID = iterations[i].getId() + 1;
            }
        }

        if (backlog == null) {
            backlog = new Iteration(getNextID(), "Backlog");
            addIteration(backlog);
        }

        this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
        viewEventController.refreshTree();
    }

    /**
     * Returns the list of the Iterations
     * 
     * @return the Iterations held within the Iterationmodel.
     */
    public List<Iteration> getIterations() {
        return listOfIterations;
    }

    /**
     * Return the iteration with the specified name
     * 
     * @param forName name of the iteration
     * @return the iteration
     */
    public Iteration getIteration(String forName) {
        if (forName == null || forName.isEmpty() || forName.equals("Backlog")) {
            return backlog;
        }

        for (Iteration iter : listOfIterations) {
            if (iter.getName().equals(forName))
                return iter;
        }

        return null;
    }

    /**
     * Returns the earliest iteration that conflicts with the given dates
     * 
     * @param start the begin date
     * @param end the end date
     * @return the conflicting iteration
     */
    public Iteration getConflictingIteration(Date start, Date end) {
        if (start == null || end == null) {
            return null;
        }

        Iteration isValid = null;

        for (Iteration iter : listOfIterations) {
            if (iter == backlog)
                continue;
            boolean startGreaterOrEqual = start.after(iter.getEnd().getDate()) || start.equals(iter.getEnd().getDate());
            boolean endLessThanOrEqual = end.before(iter.getStart().getDate()) || end.equals(iter.getStart().getDate());

            if (!(startGreaterOrEqual || endLessThanOrEqual)) {
                isValid = iter;
            }
        }

        return isValid;
    }

    /**
     * Returns the iterations that the given date falls in.
     * A date can fall within 2 iterations if it is the end of one
     * iteration and the start of another iteration.
     * 
     * @param date the date to check for
     * @return the iterations
     */
    public List<Iteration> getIterationForDate(Date date) {
        List<Iteration> iter = new ArrayList<Iteration>();
        if (date == null)
            return iter;

        System.out.println("List Of Iterations size: "+listOfIterations.size());

        for (Iteration it : listOfIterations) {
            if (it == backlog)
                continue;

            IterationDate iterationStartDate = it.getStart();
            IterationDate iterationEndDate = it.getEnd();
            if (iterationStartDate != null && iterationEndDate != null) {
                Date startDate = iterationStartDate.getDate();
                Date endDate = iterationEndDate.getDate();

                boolean startValid = startDate.compareTo(date) <= 0;
                boolean endValid = endDate.compareTo(date) >= 0;

                if (startValid && endValid) {
                    iter.add(it);
                }
            }
        }

        return iter;
    }

    /**
     * Returns the backlog iteration
     * 
     * @return backlog iteration
     */
    public Iteration getBacklog() {
        return backlog;
    }

    /**
     * @return the listOfIterations
     */
    protected ArrayList<Iteration> getListOfIterations() {
        return this.listOfIterations;
    }

    /**
     * @param listOfIterations the listOfIterations to set
     */
    protected void setListOfIterations(ArrayList<Iteration> listOfIterations) {
        this.listOfIterations = listOfIterations;
    }

    /**
     * @param nextID the nextID to set
     */
    protected void setNextID(int nextID) {
        this.nextID = nextID;
    }

    /**
     * @param instance the instance to set
     */
    public static void setInstance(IterationModel instance) {
        IterationModel.instance = instance;
    }

    /**
     * @return the viewEventController
     */
    protected ViewEventController getViewEventController() {
        return this.viewEventController;
    }

    /**
     * @param viewEventController the viewEventController to set
     */
    protected void setViewEventController(ViewEventController viewEventController) {
        this.viewEventController = viewEventController;
    }

    /**
     * @return the addIterationController
     */
    protected AddIterationController getAddIterationController() {
        return this.addIterationController;
    }

    /**
     * @param addIterationController the addIterationController to set
     */
    protected void setAddIterationController(AddIterationController addIterationController) {
        this.addIterationController = addIterationController;
    }

}

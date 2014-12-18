/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.updater.UpdateEntityManager;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.updater.ChangeModel;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * @author thefloorisjava
 *
 */
public class ActivityEntityManager implements EntityManager<ActivityModel> {

    private Data db;

    /**
     * Constructs the entity manager. This constructor is called by
     * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
     * this happens, be sure to place add this entity manager to the map in the
     * ManagerLayer file.
     *
     * @param db
     *            a reference to the persistent database
     */
    public ActivityEntityManager(Data db) {
        this.db = db;
    }

    /**
     * Saves a Activity when it is received from a client
     *
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session,
     *      java.lang.String)
     */
    @Override
    public ActivityModel makeEntity(Session s, String content)
            throws BadRequestException, ConflictException, WPISuiteException {
        System.out.println("Make Activity: " + content);
        /* Make a new Activity corresponding to the JSON data */
        ActivityModel activityModel = ActivityModel.fromJson(content);

        int id = 1;
        for (ActivityModel model : getAll(s)) {
            if (model.getId() >= id) {
                id = model.getId() + 1;
            }
        }

        activityModel.setId(id);

        /* Save it to the database */
        if (!db.save(activityModel, s.getProject())) {
            throw new WPISuiteException("Error saving Activity to database");
        }

        /* Register this change with the UpdateEntityManager. */
        if (!activityModel.getIsAutogen())
            UpdateEntityManager
            .registerChange(new ChangeModel(HttpMethod.PUT,
                    ChangeModel.ChangeObjectType.ACTION, activityModel
                    .getTaskId()), s);

        return activityModel;
    }

    /**
     * Retrieves a single Activity from the database with the ID
     *
     * @param s
     *            the session
     * @param id
     *            the id number of the Activity to retrieve
     *
     * @return the Activity matching the given id
     * @throws NotFoundException
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session,
     *      String)
     */
    @Override
    public ActivityModel[] getEntity(Session s, String id)
            throws NotFoundException {
        System.out.println("Get Activity ID: " + id);
        final int intId = Integer.parseInt(id);

        if (intId < 1) {
            throw new NotFoundException();
        }
        ActivityModel[] activities = null;
        try {
            activities = db.retrieve(ActivityModel.class, "id", intId,
                    s.getProject()).toArray(new ActivityModel[0]);
        } catch (WPISuiteException e) {
            e.printStackTrace();
        }

        if (activities.length < 1 || activities[0] == null) {
            throw new NotFoundException();
        }

        System.out.println("Got: " + activities[0].toJson());

        return activities;
    }

    /**
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
     */
    @Override
    public ActivityModel[] getAll(Session s) {
        System.out.println("Get All Activities");
        return db.retrieveAll(new ActivityModel(), s.getProject()).toArray(
                new ActivityModel[0]);
    }

    /**
     * Updates the given Activities in the database
     *
     * @param session
     *            the session the Activity to be updated is in
     * @param content
     *            the updated Activity as a Json string
     * @return the old Activities prior to updating
     * @throws WPISuiteException
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
     * */
    @Override
    public ActivityModel update(Session s, String content)
            throws WPISuiteException {
        System.out.println("Update Activity: " + content);
        ActivityModel newActivityModel = ActivityModel.fromJson(content);
        /*
         * Because of the disconnected objects problem in db4o, we can't just
         * save updatedActivityModel. We have to get the original ActivityModel
         * from db4o, copy properties from updatedActivityModel, then save the
         * original ActivityModel again.
         */
        List<Model> models = db.retrieve(ActivityModel.class, "id",
                newActivityModel.getId(), s.getProject());

        if (models.size() == 0) {
            throw new NotFoundException();
        }

        ActivityModel currentModel = (ActivityModel) models.get(0);
        System.out.println("Old Activity: " + currentModel.toJson());

        // copy values to old ActivityModel and fill in our changeset
        // appropriately
        currentModel.copyFrom(newActivityModel);

        if (!db.save(currentModel, s.getProject())) {
            throw new WPISuiteException();
        } else {
            System.out.println("Sucessfully saved");
        }

        return currentModel;
    }

    /**
     * Saves a data model to the database
     *
     * @param s
     *            the session
     * @param model
     *            the model to be saved
     */
    @Override
    public void save(Session s, ActivityModel model) throws WPISuiteException {
        System.out.println("Saving Activity: " + model.toJson());
        if (!db.save(model, s.getProject())) {
            throw new WPISuiteException();
        }
    }

    /**
     * a method ensures users need to be added!!! Deletes a Activity from the
     * database
     *
     * @param s
     *            the session
     * @param id
     *            the id of the Activity to delete
     * @return true if the deletion was successful
     * @throws WPISuiteException
     *             if the deletion was not successful
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session,
     *      String)
     */
    @Override
    public boolean deleteEntity(Session s, String id) throws WPISuiteException {
        System.out.println("Delete Activity ID: " + id);
        if (db.delete(getEntity(s, id)[0]) != null) {
            return true;
        }
        else {
            throw new WPISuiteException("Problem Deleting Activity");
        }
    }

    /**
     * a method ensures users need to be added!!! Deletes all Activities from
     * the database
     *
     * @param s
     *            the session
     * @throws WPISuiteException
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
     */
    @Override
    public void deleteAll(Session s) throws WPISuiteException {
        // ensureRole(s, Role.ADMIN);
        System.out.println("Delet All Activities");
        if (db.deleteAll(new ActivityModel(), s.getProject()) != null) {
            return;
        }
        else {
            throw new WPISuiteException("Problem Deleting Activities.");
        }
    }

    /**
     * Returns the number of Activities in the database
     *
     * @return number of Activities stored
     * @throws WPISuiteException
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
     */
    @Override
    public int Count() throws WPISuiteException {
        return db.retrieveAll(new ActivityModel()).size();
    }

    /**
     * Method advancedGet.
     *
     * @param arg0
     *            Session
     * @param arg1
     *            String[]
     * @return String
     * @throws NotImplementedException
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session,
     *      String[])
     */
    @Override
    public String advancedGet(Session s, String[] args)
            throws NotImplementedException {
        throw new NotImplementedException();
    }

    /**
     * Method advancedPut.
     *
     * @param s
     *            Session
     * @param args
     *            String[]
     * @param content
     *            String
     * @return String
     * @throws NotImplementedException
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session,
     *      String[], String)
     */
    @Override
    public String advancedPut(Session s, String[] args, String content)
            throws NotImplementedException {
        throw new NotImplementedException();
    }

    /**
     * Method advancedPost.
     *
     * @param s
     *            Session
     * @param string
     *            String
     * @param content
     *            String
     * @return String
     * @throws NotImplementedException
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session,
     *      String, String)
     */
    @Override
    public String advancedPost(Session s, String string, String content)
            throws NotImplementedException {
        throw new NotImplementedException();
    }
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.IDModel;

/**
 * This is the entity manager for {@link BucketModel}s in the Bucket Manager
 * module.
 */
public class BucketEntityManager implements EntityManager<BucketModel> {

    private Data db;

    /**
     * Construct the entity manager. This is called by
     * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}.
     * 
     * @param db
     */
    public BucketEntityManager(Data db) {
        this.db = db;
    }

    /** @inheritDoc */
    @Override
    public BucketModel makeEntity(Session s, String content)
            throws BadRequestException, ConflictException, WPISuiteException {
        System.out.println("makeEntity");
        /* Make a new Bucket corresponding to the JSON data */
        BucketModel bucketModel = BucketModel.fromJSON(content);

        IDModel nextIDModel = new IDModel("bucket", 0, s.getProject());
        int nextID = nextIDModel.getNextID(db);
        nextIDModel.increment(db);

        bucketModel.setID(nextID);
        /* Save it to the database */
        if (!db.save(bucketModel, s.getProject())) {
            throw new WPISuiteException("Error saving Bucket to database");
        }

        return bucketModel;
    }

    /** @inheritDoc */
    @Override
    public BucketModel[] getEntity(Session s, String id)
            throws NotFoundException, WPISuiteException {
        System.out.println("get");
        /* Retrieve the Bucket model(s) with the given ID */
        List<Model> models = db.retrieve(BucketModel.class, "ID",
                Integer.parseInt(id), s.getProject());
        return models.toArray(new BucketModel[0]);
    }

    /** @inheritDoc */
    @Override
    public BucketModel[] getAll(Session s) throws WPISuiteException {
        System.out.println("getAll");
        /* Get all of the BucketModel objects */
        List<Model> Buckets = db.retrieveAll(new BucketModel(), s.getProject());
        return Buckets.toArray(new BucketModel[0]);
    }

    @Override
    public BucketModel update(Session s, String content)
            throws WPISuiteException {
        System.out.println("update");
        BucketModel newBucketModel = BucketModel.fromJSON(content);

        /* Retrieve the Bucket model(s) with the given ID */
        List<Model> models = db.retrieve(BucketModel.class, "ID",
                newBucketModel.getID(), s.getProject());
        if (models.size() == 0) {
            throw new NotFoundException();
        }

        /* Update the records to the new model */
        for (Model model : models) {
            ((BucketModel) model).copyFrom(newBucketModel);
        }

        return newBucketModel;
    }

    /** @inheritDoc */
    @Override
    public void save(Session s, BucketModel model) throws WPISuiteException {
        System.out.println("save");
        /* Save the Bucket */
        db.save(model);
    }

    /** @inheritDoc */
    @Override
    public boolean deleteEntity(Session s, String id) throws WPISuiteException {
        System.out.println("deleteEntity");
        // TODO Auto-generated method stub
        return false;
    }

    /** @inheritDoc */
    @Override
    public void deleteAll(Session s) throws WPISuiteException {
        System.out.println("deleteAll");
        // TODO Auto-generated method stub

    }

    /** @inheritDoc */
    @Override
    public int Count() throws WPISuiteException {
        return db.retrieveAll(new BucketModel()).size();
    }

    /** Not implemented */
    @Override
    public String advancedGet(Session s, String[] args)
            throws WPISuiteException {
        throw new NotImplementedException();
    }

    /** Not implemented */
    @Override
    public String advancedPut(Session s, String[] args, String content)
            throws WPISuiteException {
        throw new NotImplementedException();
    }

    /** Not implemented */
    @Override
    public String advancedPost(Session s, String string, String content)
            throws WPISuiteException {
        throw new NotImplementedException();
    }

}

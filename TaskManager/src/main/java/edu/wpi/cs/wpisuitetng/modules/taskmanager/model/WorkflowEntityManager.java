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

/**
 * This is the entity manager for {@link WorkflowModel}s in the Workflow Manager
 * module.
 */
public class WorkflowEntityManager implements EntityManager<WorkflowModel> {

    private Data db;

    /**
     * Construct the entity manager. This is called by
     * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}.
     * 
     * @param db
     */
    public WorkflowEntityManager(Data db) {
        this.db = db;
    }

    /** @inheritDoc */
    @Override
    public WorkflowModel makeEntity(Session s, String content)
            throws BadRequestException, ConflictException, WPISuiteException {
        System.out.println("makeEntity: " + content);
        /* Make a new Workflow corresponding to the JSON data */
        WorkflowModel workflowModel = WorkflowModel.fromJson(content);

        int id = 1;
        for (WorkflowModel model : getAll(s)){
            if(model.getID() >= id) {
                id = model.getID() + 1;
            }
        }
        	

        workflowModel.setID(id);
        
        /* Save it to the database */
        if (!db.save(workflowModel, s.getProject())) {
            throw new WPISuiteException("Error saving Workflow to database");
        }
        System.out.println("/makeEntity: " + workflowModel.toJson());

        return workflowModel;
    }

    /** @inheritDoc */
    @Override
    public WorkflowModel[] getEntity(Session s, String id)
            throws NotFoundException {
        System.out.println("get ID: " + id);
        final int intID = Integer.parseInt(id);
        if(intID < 1){
        	throw new NotFoundException();
        }
        WorkflowModel[] models = null;
        try{
        	models = db.retrieve(WorkflowModel.class,  "ID", intID,  s.getProject()).toArray(new WorkflowModel[0]);
        } catch (WPISuiteException e) {
        	e.printStackTrace();
        }
        /* Retrieve the Workflow model(s) with the given ID */
        if(models.length < 1 || models[0] == null){
        	throw new NotFoundException();
        }
        
        System.out.println("Got: "+models[0].toJson());
        
        return models;
    }

    /** @inheritDoc */
    @Override
    public WorkflowModel[] getAll(Session s) throws WPISuiteException {
        System.out.println("getAll");
        /* Get all of the WorkflowModel objects */
        List<Model> Workflows = db.retrieveAll(new WorkflowModel(),
                s.getProject());
        return Workflows.toArray(new WorkflowModel[0]);
    }

    @Override
    public WorkflowModel update(Session s, String content)
            throws WPISuiteException {
        System.out.println("Update: " + content);
        WorkflowModel newWorkflowModel = WorkflowModel.fromJson(content);

        /* Retrieve the Workflow model(s) with the given ID */
        List<Model> models = db.retrieve(WorkflowModel.class, "ID",
                newWorkflowModel.getID(), s.getProject());
        if (models.size() == 0) {
            throw new NotFoundException();
        }

        /* Update the records to the new model */
        WorkflowModel currentModel = (WorkflowModel)models.get(0);
        System.out.println("Old Model: "+currentModel.toJson());
        currentModel.copyFrom(newWorkflowModel);

        if(db.save(currentModel)){
        	System.out.println("Sucessfully saved");
        } else {
			throw new WPISuiteException();
		} 
        
        return currentModel;
    }

    /** @inheritDoc */
    @Override
    public void save(Session s, WorkflowModel model) throws WPISuiteException {
        System.out.println("save: " + model.toJson());
        /* Save the Workflow */
        db.save(model);
    }

    /** @inheritDoc */
    @Override
    public boolean deleteEntity(Session s, String id) throws WPISuiteException {
        System.out.println("deleteEntity: " + id);
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
        return db.retrieveAll(new WorkflowModel()).size();
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

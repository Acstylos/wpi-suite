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
 * This is the entity manager for {@link TaskModel}s in the Task Manager
 * module.
 */
public class TaskEntityManager implements EntityManager<TaskModel> {

    private Data db;

    /**
     * Construct the entity manager.  This is called by 
     * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}.
     * @param db
     */
    public TaskEntityManager(Data db) {
        this.db = db;
    }
    
    /** @inheritDoc */
    @Override
    public TaskModel makeEntity(Session s, String content)
            throws BadRequestException, ConflictException, WPISuiteException {
        System.out.println("makeEntity");
        /* Make a new task corresponding to the JSON data */
        TaskModel taskModel = TaskModel.fromJSON(content);

	IDModel nextIDModel = new IDModel("task", 0, s.getProject());
	int nextID = nextIDModel.getNextID(db);
	nextIDModel.increment(db);

	taskModel.setId(nextID);
	/* Save it to the database */
        if(!db.save(taskModel, s.getProject())) {
            throw new WPISuiteException("Error saving task to database");
        }
        
        return taskModel;
    }

    /** @inheritDoc */
    @Override
    public TaskModel[] getEntity(Session s, String id)
            throws NotFoundException, WPISuiteException {
        System.out.println("get");
        /* Retrieve the task model(s) with the given ID */
        List<Model> models = db.retrieve(TaskModel.class, "ID", Integer.parseInt(id), s.getProject());
        return models.toArray(new TaskModel[0]);
    }

    /** @inheritDoc */
    @Override
    public TaskModel[] getAll(Session s) throws WPISuiteException {
        System.out.println("getAll");
        /* Get all of the TaskModel objects */
        List<Model> tasks = db.retrieveAll(new TaskModel(), s.getProject());
        return tasks.toArray(new TaskModel[0]);
    }

    @Override
    public TaskModel update(Session s, String content) throws WPISuiteException {
        System.out.println("update");
        TaskModel newTaskModel = TaskModel.fromJSON(content);
        
        /* Retrieve the task model(s) with the given ID */
        List<Model> models = db.retrieve(TaskModel.class, "ID", newTaskModel.getID(), s.getProject());
        if(models.size() == 0) {
            throw new NotFoundException();
        }
        
        /* Update the records to the new model */
        for(Model model : models) {
            ((TaskModel)model).copyFrom(newTaskModel);
        }
        
        return newTaskModel;
    }

    /** @inheritDoc */
    @Override
    public void save(Session s, TaskModel model) throws WPISuiteException {
        System.out.println("save");
        /* Save the task */
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
       return db.retrieveAll(new TaskModel()).size();
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

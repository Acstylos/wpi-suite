package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;

public class WorkflowModelEntityManager implements EntityManager<WorkflowModel> {

    /** The database */
    Data db;

    /**
     * Constructs the entity manager. This constructor is called by
     * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
     * this happens, be sure to place add this entity manager to the map in
     * the ManagerLayer file.
     * 
     * @param db a reference to the persistent database
     */
    public WorkflowModelEntityManager(Data db)
    {
	this.db = db;
    }

    /**
     * Saves a Requirement when it is received from a client
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
     */
    @Override
    public WorkflowModel makeEntity(Session s, String content) throws WPISuiteException {
	final WorkflowModel newWorkflowModel = WorkflowModel.fromJson(content);
	if(!db.save(newWorkflowModel, s.getProject())) {
	    throw new WPISuiteException();
	}
	return newWorkflowModel;
    }

    /**
     * Retrieves a single workflow model from the database
     * @param s the session
     * @param id the id number of the workflow model to retrieve




     * @return the workflow model matching the given id * @throws NotFoundException * @throws NotFoundException * @throws NotFoundException
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String) */
    @Override
    public WorkflowModel[] getEntity(Session s, String id) throws NotFoundException {
	final int intId = Integer.parseInt(id);
	if(intId < 1) {
	    throw new NotFoundException();
	}
	WorkflowModel[] workflowmodels = null;
	try {
	    workflowmodels = db.retrieve(WorkflowModel.class, "id", intId, s.getProject()).toArray(new WorkflowModel[0]);
	} catch (WPISuiteException e) {
	    e.printStackTrace();
	}
	if(workflowmodels.length < 1 || workflowmodels[0] == null) {
	    throw new NotFoundException();
	}
	return workflowmodels;
    }


    /**
     * Retrieves all workflow models from the database
     * @param s the session



     * @return array of all stored workflow models * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
     */
    @Override
    public WorkflowModel[] getAll(Session s) {
	return db.retrieveAll(new WorkflowModel(), s.getProject()).toArray(new WorkflowModel[0]);
    }

    @Override
    //TODO
    public WorkflowModel update(Session s, String content)
	    throws WPISuiteException {
	WorkflowModel updatedWorkflowModel = WorkflowModel.fromJson(content);

	List<Model> oldWorkflowModels = db.retrieve(WorkflowModel.class, "id", updatedWorkflowModel.getID(), s.getProject());
	if(oldWorkflowModels.size() < 1 || oldWorkflowModels.get(0) == null) {
	    throw new BadRequestException("Requirement with ID does not exist.");
	}

	WorkflowModel existingWorkflowModel = (WorkflowModel)oldWorkflowModels.get(0);		

	// copy values to old requirement and fill in our changeset appropriately
	existingWorkflowModel.copyFrom(updatedWorkflowModel);

	if(!db.save(existingWorkflowModel, s.getProject())) {
	    throw new WPISuiteException();
	}

	return existingWorkflowModel;
    }

    /**
     * Saves a data model to the database
     * @param s the session
     * @param model the model to be saved
     */
    @Override
    public void save(Session s, WorkflowModel model) {
	db.save(model, s.getProject());
    }

    /**
     * Deletes a workflow model from the database
     * @param s the session
     * @param id the id of the workflow to delete
     */
    public boolean deleteEntity(Session s, String id) throws WPISuiteException {
	return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
    }

    @Override
    public String advancedGet(Session s, String[] args)
	    throws WPISuiteException {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Deletes all workflow model from the database
     * @param s the session
     * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
     */
    public void deleteAll(Session s) throws WPISuiteException {
	db.deleteAll(new WorkflowModel(), s.getProject());
    }

    /**
     * Returns the number of workflow models in the database
     * @return number of workflow models stored * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
     * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count() */
    public int Count() throws WPISuiteException {
	return db.retrieveAll(new WorkflowModel()).size();
    }

    @Override
    public String advancedPut(Session s, String[] args, String content)
	    throws WPISuiteException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String advancedPost(Session s, String string, String content)
	    throws WPISuiteException {
	// TODO Auto-generated method stub
	return null;
    }

}

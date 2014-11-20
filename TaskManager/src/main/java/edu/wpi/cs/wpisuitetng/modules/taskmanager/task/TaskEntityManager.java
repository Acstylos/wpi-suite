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
    	System.out.println("Make Task: " + content);
        // Make a new task corresponding to the JSON data
        TaskModel taskModel = TaskModel.fromJson(content);
        
        // Find the highest used ID and assign the next number to this task
        int id = 1;
        for(TaskModel model : getAll(s)) {
            if(model.getId() >= id) {
                id = model.getId() + 1;
            }
        }
        
        taskModel.setId(id);
        
        /* Save it to the database */
        if(!db.save(taskModel, s.getProject())) {
            throw new WPISuiteException("Error saving task to database");
        }
        
        return taskModel;
    }

    /** @inheritDoc */
    @Override
    public TaskModel[] getEntity(Session s, String id)
            throws NotFoundException {
    	System.out.println("Get Task ID: " + id);
        // Retrieve the task model(s) with the given ID
    	final int intId = Integer.parseInt(id);
    	
    	if(intId < 1){
    		throw new NotFoundException();
    	}
    	TaskModel[] models = null;
    	try{
    		models = db.retrieve(TaskModel.class, "id", intId, s.getProject()).toArray(new TaskModel[0]);
    	} catch (WPISuiteException e) {
    		e.printStackTrace();
    	}
    	
    	if(models.length < 1 || models[0] == null) {
    		throw new NotFoundException();
    	}
    	
    	System.out.println("Got: " + models[0].toJson());
    	
    	return models;
    }

    /** @inheritDoc */
    @Override
    public TaskModel[] getAll(Session s) throws WPISuiteException {
        System.out.println("Get All Tasks");
        /* Get all of the TaskModel objects */
        List<Model> tasks = db.retrieveAll(new TaskModel(), s.getProject());
        return tasks.toArray(new TaskModel[0]);
    }

    @Override
    public TaskModel update(Session s, String content) throws WPISuiteException {
        System.out.println("Update Task:" + content);
    	TaskModel newTaskModel = TaskModel.fromJson(content);
        
        /* Retrieve the task model(s) with the given ID */
        List<Model> models = db.retrieve(TaskModel.class, "id", newTaskModel.getId(), s.getProject());
        
        if(models.size() == 0) {
            throw new NotFoundException();
        }
        
        /* Update the records to the new model */
        TaskModel currentModel = (TaskModel)models.get(0);
        System.out.println("Old Task: " + currentModel.toJson());
        currentModel.copyFrom(newTaskModel);
        
        if(!db.save(currentModel, s.getProject())){
        	throw new WPISuiteException();
        } else {
        	System.out.println("Sucessfully saved");
        }
        
        return currentModel;
    }

    /** @inheritDoc */
    @Override
    public void save(Session s, TaskModel model) throws WPISuiteException {
        System.out.println("Saving Task: " + model.toJson());
        /* Save the task */
        db.save(model);
    }

    /** @inheritDoc */
    @Override
    public boolean deleteEntity(Session s, String id) throws WPISuiteException {
        System.out.println("Delete Task ID: " + id);
        // TODO Auto-generated method stub
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
    }

    /** @inheritDoc */
    @Override
    public void deleteAll(Session s) throws WPISuiteException {
        System.out.println("Delete All Tasks");
        db.deleteAll(new TaskModel(), s.getProject());
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

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

/**
 * This is the entity manager for the Bucket
 * @author Thefloorislava
 *
 */
public class BucketEntityManager implements EntityManager<BucketModel> {
	
	private Data db;
	
	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * @param db a reference to the persistent database
	 */
	public BucketEntityManager(Data db) {
		this.db = db;
	}
	
	/**
	 * Saves a Bucket when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public BucketModel makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
        System.out.println("Make Bucket: " + content);
        /* Make a new Bucket corresponding to the JSON data */
        BucketModel bucketModel = BucketModel.fromJson(content);

        int id = 1;
        for (BucketModel model : getAll(s)){
            if(model.getId() >= id) {
                id = model.getId() + 1;
            }
        }
        
        bucketModel.setId(id);
        
        /* Save it to the database */
        if (!db.save(bucketModel, s.getProject())) {
            throw new WPISuiteException("Error saving Workflow to database");
        }

        return bucketModel;
	}
	

	
	
	/**
	 * Retrieves a single Bucket from the database with the ID
	 * @param s the session
	 * @param id the id number of the Bucket to retrieve
	 * 
	 * @return the Bucket matching the given id 
	 * @throws NotFoundException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String)
	 */
	@Override
	public BucketModel[] getEntity(Session s, String id) throws NotFoundException {
		System.out.println("Get Task ID: " + id);
		final int intId = Integer.parseInt(id);
		
		if (intId < 1) {
			throw new NotFoundException();
		}
		BucketModel[] buckets = null;
		try {
			buckets = db.retrieve(BucketModel.class, "id", intId, s.getProject()).toArray(new BucketModel[0]);			
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		
		if (buckets.length < 1 || buckets[0] == null) {
			throw new NotFoundException();
		}
        
		System.out.println("Got: " + buckets[0].toJson());
		
        return buckets;
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public BucketModel[] getAll(Session s) {
        System.out.println("Get All Buckets");
		return db.retrieveAll(new BucketModel(), s.getProject()).toArray(new BucketModel[0]);
	}
	
	/**
	 * Updates the given Buckets in the database
	 * @param session the session the Bucket to be updated is in
	 * @param content the updated Bucket as a Json string	
	 * @return the old Buckets prior to updating 
	 * @throws WPISuiteException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String) 
	 * */
	@Override
	public BucketModel update(Session s, String content) throws WPISuiteException {
        System.out.println("Update Bucket: " + content);
		BucketModel newBucketModel = BucketModel.fromJson(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save updatedBucketModel.
		 * We have to get the original BucketModel from db4o, copy properties from updatedBucketModel,
		 * then save the original BucketModel again.
		 */
		List<Model> models = db.retrieve(BucketModel.class, "id", newBucketModel.getId(), s.getProject());

		if(models.size() == 0) {
			throw new NotFoundException();
		}
		
		BucketModel currentModel = (BucketModel)models.get(0);
        System.out.println("Old Bucket: " + currentModel.toJson());
		
		// copy values to old BucketModel and fill in our changeset appropriately
		currentModel.copyFrom(newBucketModel);
		
		if(!db.save(currentModel, s.getProject())) {
			throw new WPISuiteException();
		} else{
        	System.out.println("Sucessfully saved");
		}
		
		return currentModel;
	}
	
	/**
	 * Saves a data model to the database
	 * @param s the session
	 * @param model the model to be saved
	 */
	@Override
	public void save(Session s, BucketModel model) throws WPISuiteException {
		System.out.println("Saving Bucket: " + model.toJson());
		db.save(model, s.getProject());	
	}
	
	/**
	 * a method ensures users need to be added!!!
	 * Deletes a Bucket from the database
	 * @param s the session
	 * @param id the id of the Bucket to delete
	 * @return true if the deletion was successful 
	 * @throws WPISuiteException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String) 
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
        System.out.println("Delete Task ID: " + id);
		//ensureRole(s, Role.ADMIN);
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}
	
	/**
	 * a method ensures users need to be added!!!
	 * Deletes all Buckets from the database
	 * @param s the session
	 * @throws WPISuiteException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session) 
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		//ensureRole(s, Role.ADMIN);
		System.out.println("Delet All Buckets");
		db.deleteAll(new BucketModel(), s.getProject());
	}
	
	/**
	 * Returns the number of Buckets in the database
	 * @return number of Buckets stored 
	 * @throws WPISuiteException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count() 
	 */
	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll(new BucketModel()).size();
	}
	
	/**
	 * Method advancedGet.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @return String 
	 * @throws NotImplementedException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[])
	 */
	@Override
	public String advancedGet(Session s, String[] args)
			throws NotImplementedException {
		throw new NotImplementedException();
	}
	
	/**
	 * Method advancedPut.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @param arg2 String	
	 * @return String 
	 * @throws NotImplementedException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String)
	 */
	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws NotImplementedException {
		throw new NotImplementedException();
	}
	/**
	 * Method advancedPost.
	 * @param arg0 Session
	 * @param arg1 String
	 * @param arg2 String	
	 * @return String
	 * @throws NotImplementedException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String)
	 */
	@Override
	public String advancedPost(Session s, String string, String content)
			throws NotImplementedException {
		throw new NotImplementedException();
	}
	

}

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
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;

/**
 * This is the entity manager for the Bucket
 * @author Thefloorislava
 *
 */
public class BucketEntityManager implements EntityManager<BucketModel> {
	
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
        System.out.println("makeEntity: " + content);
        /* Make a new Bucket corresponding to the JSON data */
        BucketModel bucketModel = BucketModel.fromJson(content);

        int id = 1;
        for (BucketModel model : getAll(s)){
            if(model.getID() >= id) {
                id = model.getID() + 1;
            }
        }
        	

        bucketModel.setID(id);
        
        /* Save it to the database */
        if (!db.save(bucketModel, s.getProject())) {
            throw new WPISuiteException("Error saving Workflow to database");
        }
        System.out.println("/makeEntity: " + bucketModel.toJson());

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
        
        return buckets;
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public BucketModel[] getAll(Session s) {
        System.out.println("getAll");
		return db.retrieveAll(new BucketModel(), s.getProject()).toArray(new BucketModel[0]);
	}
	
	/**
	 * Saves a data model to the database
	 * @param s the session
	 * @param model the model to be saved
	 */
	@Override
	public void save(Session s, BucketModel model) throws WPISuiteException {
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
	 * Updates the given Buckets in the database
	 * @param session the session the Bucket to be updated is in
	 * @param content the updated Bucket as a Json string	
	 * @return the old Buckets prior to updating 
	 * @throws WPISuiteException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String) 
	 * */
	@Override
	public BucketModel update(Session session, String content) throws WPISuiteException {
		BucketModel updatedBucketModel = BucketModel.fromJson(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save updatedBucketModel.
		 * We have to get the original BucketModel from db4o, copy properties from updatedBucketModel,
		 * then save the original BucketModel again.
		 */
		List<Model> oldBucketModels = db.retrieve(BucketModel.class, "id", updatedBucketModel.getID(), session.getProject());
		if (oldBucketModels.size() < 1 || oldBucketModels.get(0) == null) {
			throw new BadRequestException("BucketModel with ID does not exist.");
		}
		BucketModel existingBucketModel = (BucketModel)oldBucketModels.get(0);
		
		// copy values to old BucketModel and fill in our changeset appropriately
		existingBucketModel.copyFrom(updatedBucketModel);
		
		if(!db.save(existingBucketModel, session.getProject())) {
			throw new WPISuiteException();
		}
		return existingBucketModel;
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

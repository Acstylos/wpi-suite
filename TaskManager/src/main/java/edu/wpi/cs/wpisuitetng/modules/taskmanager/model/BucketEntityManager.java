package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.Iteration;

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
	 * Saves a Workflow when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public BucketModel makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		final BucketModel newWorkflow = BucketModel.fromJson(content);
		if(!db.save(newWorkflow, s.getProject())){
			throw new WPISuiteException();
		}
		return newWorkflow;
	}
	

	
	
	/**
	 * Retrieves a single Bucket from the database
	 * @param s the session
	 * @param id the id number of the Bucket to retrieve
	 * 
	 * @return the Bucket matching the given id 
	 * @throws NotFoundException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String)
	 */
	@Override
	public BucketModel[] getEntity(Session s, String id) throws NotFoundException,
			WPISuiteException {
		final int intId = Integer.parseInt(id);
		if (intId < 1) {
			throw new NotFoundException();
		}
		BucketModel[] Buckets = null;
		try {
			Buckets = db.retrieve(BucketModel.class, "id", intId, s.getProject()).toArray(new BucketModel[0]);			
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if (Buckets.length < 1 || Buckets[0] == null) {
			throw new NotFoundException();
		}
		return Buckets;
	}
	
	/**
	 * Retrieves all Buckets from the database
	 * @param s the session
	 * @return array of all stored Buckets 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public BucketModel[] getAll(Session s) throws WPISuiteException {
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
		db.deleteAll(new Iteration(), s.getProject());
	}
	
	/**
	 * Returns the number of Buckets in the database
	 * @return number of Buckets stored 
	 * @throws WPISuiteException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count() 
	 */
	@Override
	public int Count() throws WPISuiteException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public BucketModel update(Session s, String content) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
	

}

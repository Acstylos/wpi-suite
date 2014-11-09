package edu.wpi.cs.wpisuitetng.modules.taskmanager.workflow.model.bucket;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;

public class BucketEntityManager implements EntityManager<Bucket> {
	
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
	public Bucket makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		final Bucket newWorkflow = Bucket.fromJson(content);
		if(!db.save(newWorkflow, s.getProject())){
			throw new WPISuiteException();
		}
		return newWorkflow;
	}
	

	@Override
	public Bucket[] getEntity(Session s, String id) throws NotFoundException,
			WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Bucket[] getAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Bucket update(Session s, String content) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void save(Session s, Bucket model) throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int Count() throws WPISuiteException {
		// TODO Auto-generated method stub
		return 0;
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

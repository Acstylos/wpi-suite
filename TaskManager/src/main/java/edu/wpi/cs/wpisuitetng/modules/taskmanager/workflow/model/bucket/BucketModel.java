package edu.wpi.cs.wpisuitetng.modules.taskmanager.workflow.model.bucket;

import java.util.List;

import javax.swing.AbstractListModel;

/**
 * List of Buckets being pulled from server by BucketEntityManager
 * 
 * @author ChandlerWu
 *
 */
public class BucketModel extends AbstractListModel<Bucket> {

	/**
	 * 	Serial Version User ID
	 */
	private static final long serialVersionUID = -3904707043809489144L;
	
	/**
	 * The list in which all the buckets for a single project are contained
	 */
	private List<Bucket> Buckets;
	
	/**
	 * the next available ID number for the Bucket 
	 */
	private int nextID;
	
	/**
	 * the static object to allow the bucket model to be
	 */
	private static BucketModel instance;
	
	
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Bucket getElementAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}

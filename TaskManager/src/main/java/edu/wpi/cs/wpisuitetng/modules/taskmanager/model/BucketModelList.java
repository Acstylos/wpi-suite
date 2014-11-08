package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.List;

import javax.swing.AbstractListModel;

/**
 * List of Buckets being pulled from server by BucketEntityManager
 * 
 * @author Thefloorislava
 *
 */
public class BucketModelList extends AbstractListModel<BucketModel> {

	/**
	 * 	Serial Version User ID
	 */
	private static final long serialVersionUID = -3904707043809489144L;
	
	/**
	 * The list in which all the buckets for a single project are contained
	 */
	private List<BucketModel> Buckets;
	
	/**
	 * the next available ID number for the Bucket 
	 */
	private int nextID;
	
	/**
	 * the static object to allow the bucket model to be
	 */
	private static BucketModelList instance;
	
	
	/**
	 * Provide the number of elements in the list of buckets
	 * 
	 * @return the number of bucket in the project
     * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return Buckets.size();
	}
	
	/**
     * This function takes an index and finds the bucket in the list of
     * buckets
     * 
     * @param index The index of the buckets to be returned
     * @return the bucket associated with the provided index * 
     * @see javax.swing.ListModel#getElementAt(int) 
     *            
     */
	@Override
	public BucketModel getElementAt(int index) {
		return Buckets.get(Buckets.size() - 1 -index);
	}

}

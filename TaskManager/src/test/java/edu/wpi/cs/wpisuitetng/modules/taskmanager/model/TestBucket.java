package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests methods in iteration.java and tests the creation of variants of Bucket
 * @author ChandlerWu
 *
 */

public class TestBucket {
	
	
	
	@Test
    public void createNonNullIteration() {
        assertNotNull(new BucketModel());    
    }
	
	@Test
    public void bucketGettersTest() {
		// Mock network
        Network.setInstance(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        
        BucketModel bk = new BucketModel();
        bk.setId(10);
        bk.setName("bucket10");
        
        assertEquals(10, bk.getId());
        assertEquals("bucket10", bk.getName());
        		
	}
	
	
	
}

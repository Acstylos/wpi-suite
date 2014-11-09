package edu.wpi.cs.wpisuitetng.modules.taskmanager.workflow.model.bucket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockNetwork;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;



public class TestBucket {
	
	
	@Before
	@Test
    public void createNonNullIteration() {
        assertNotNull(new Bucket());    
    }
	
	@Test
    public void bucketGettersTest() {
		// Mock network
        Network.setInstance(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        Bucket bk = new Bucket();
        bk.setId(10);
        bk.setName("bucket10");
        
        assertEquals(10, bk.getId());
        assertEquals("bucket10", bk.getName());
        		
	}
	
	
	
}

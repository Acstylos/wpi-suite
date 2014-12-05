/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ArchiveView extends JPanel {
    
    private BucketView archiveBucket = new BucketView("Archive");

    /**
     * Create the panel.
     */
    public ArchiveView() {
        setLayout(new MigLayout("fill"));
        add(archiveBucket, "cell 0 0, grow");
    }
    
    /**
     * @return the archive bucket
     */
    public BucketView getArchiveBucket() {
        return archiveBucket;
    }
    
    /**
     * Set the archive bucket within the view
     * @param archiveBucket
     */
    public void setArchiveBucket(BucketView archiveBucket) {
        this.archiveBucket = archiveBucket;
    }

}

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
    
    public BucketView getArchiveBucket() {
        return archiveBucket;
    }
    
    public void setArchiveBucket(BucketView archiveBucket) {
        this.archiveBucket = archiveBucket;
    }

}

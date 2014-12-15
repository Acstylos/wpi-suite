/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;

/**
 * Setup the view for help
 * @author TheFloorIsJava
 * @version
 */
public class HelpView extends JPanel{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -157101066248714214L;

    /**
     * Button to close the help tab
     */
    private JButton closeButton = new JButton("Close");

    /**
     * Keep the status of if the help tab is opened or not
     */
    static boolean isOpened = false;

    /**
     * Buffer to load image
     */
    private BufferedImage image;

    /**
     * The Scroll Pane
     */
    private JScrollPane scrollPane = new JScrollPane();

    /**
     * Panel inside of the scroll pane
     */
    private JPanel helpViewHolderPanel = new JPanel();

    /**
     * Create the help panel
     * @throws IOException 
     */
    public HelpView() {

        try {   
            URL url = getClass().getResource("test.jpg");
            image = ImageIO.read(new File(url.getPath()));
        } catch (IOException ex) {
            image = (BufferedImage) Icons.CANCEL;         
        }
        JLabel Ilab = new JLabel(new ImageIcon(image));

        setLayout(new MigLayout("fill", "[]", "[][]"));

        scrollPane.setBorder(null);
        this.add(scrollPane, "flowx,cell 0 0,alignx center,aligny center");
        scrollPane.setViewportView(helpViewHolderPanel);

        this.helpViewHolderPanel.setBorder(null);
        this.helpViewHolderPanel.setBackground(Color.LIGHT_GRAY);
        this.helpViewHolderPanel.setLayout(new MigLayout("fill"));       

        this.add(closeButton, "cell 0 1,alignx center,aligny center");
        this.closeButton.setIcon(Icons.CANCEL);
        this.closeButton.setVisible(true);

        helpViewHolderPanel.add(Ilab, "growx,aligny center");        
    }

    /** 
     * Add the listener for the cancel Button
     * @param listener 
     *        The action that calls the listener
     */
    public void addCloseOnClickListener(ActionListener listener){
        this.closeButton.addActionListener(listener);
    }   
}

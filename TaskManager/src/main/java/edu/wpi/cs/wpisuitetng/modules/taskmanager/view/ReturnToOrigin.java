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

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * This class returns the GhostPane back its original location
 * Adapted Code from Romain Guy
 */
public class ReturnToOrigin implements ActionListener {
    private boolean isInit;
    private long start;

    private Point startPoint;
    private Point endPoint;
    private GhostGlassPane glassPane;

    private static final double INIT_SPEED = 1000.0;
    private static final double INIT_ACC = 6000.0;

    /**
     * Constructs the fields for the returning glasspane
     * 
     * @param glassPane
     *            The dragged item being returned
     * @param start
     *            The point where the item is dropped
     * @param end
     *            The point where the item will end up returning to
     */
    public ReturnToOrigin(GhostGlassPane glassPane, Point start, Point end) {
        this.glassPane = glassPane;
        this.startPoint = start;
        this.endPoint = end;
        isInit = false;
    }

    /**
     * Starts the "animation" of the glasspane returning to the original
     * location. It will constantly recalculate the position of the item and
     * repaint it.
     */
    public void actionPerformed(ActionEvent e) {
        if (!isInit) {
            isInit = true;
            start = System.currentTimeMillis();
        }

        long elapsed = System.currentTimeMillis() - start;
        double time = (double) elapsed / 1000.0;

        /* slope */
        double a = (endPoint.y - startPoint.y)
                / (double) (endPoint.x - startPoint.x);
        /* y = mx + b */
        double b = endPoint.y - a * endPoint.x;

        /* Animation travel speed: 1/2*at^2 + vt, for the x direction */
        int travelX = (int) (INIT_ACC * time * time / 2.0 + INIT_SPEED * time);

        /* If the dragged is in the other direction switch directions */
        if (startPoint.x > endPoint.x) {
            travelX = -travelX;
        }

        /* Animation travel speed for the y direction */
        int travelY = (int) ((startPoint.x + travelX) * a + b);
        /* Distance remaining from the original location */
        int distanceX = (int) Math.abs(startPoint.x - endPoint.x);

        if (Math.abs(travelX) >= distanceX) {
            ((Timer) e.getSource()).stop();

            glassPane.setPoint(endPoint);
            glassPane.repaint();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    glassPane.setVisible(false);
                }
            });
            return;
        }

        glassPane.setPoint(new Point(startPoint.x + travelX, travelY));
        glassPane.repaint();
    }
}

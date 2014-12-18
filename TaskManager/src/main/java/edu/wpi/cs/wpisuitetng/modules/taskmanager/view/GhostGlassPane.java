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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Draws a transparent version of something that's being dragged and dropped
 */
public class GhostGlassPane extends JPanel {

    private AlphaComposite composite;
    private BufferedImage image;
    private Point point, startDrag;

    private final static double MAX_ROTATION = 8 * Math.PI / 180;
    private double rotation;

    /**
     * Construct the glass pane
     */
    public GhostGlassPane() {
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        point = null;

        /*
         * Make this panel transparent so we can render it over the rest of the
         * stuff
         */
        this.setOpaque(false);
    }

    /**
     * @param component
     *            The component to draw under the cursor
     * @param startDrag
     *            The initial position of the cursor, relative to the component
     */
    public void setGhostComponent(Component c, Point startDrag) {
        image = new BufferedImage(c.getWidth() + 2, c.getHeight() + 2,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.translate(1.0, 1.0);
        c.paint(g2d);

        rotation = 0.0;

        this.startDrag = startDrag;
    }

    /**
     * @param point
     *            The location of the cursor
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * @return The location of the cursor
     */
    public Point getPoint() {
        return point;
    }

    /**
     * @return The position of where the cursor started dragging
     */
    public Point getStartDragPoint() {
        return startDrag;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (isVisible()) {
            Graphics2D g2d = (Graphics2D) g;

            if (image != null && point != null) {
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.rotate(rotation, point.x, point.y);
                g2d.setComposite(composite);
                g2d.drawImage(image, point.x - startDrag.x, point.y
                        - startDrag.y, null);

                /* Gradually approach the maximum rotation as the user drags */
                if (rotation < MAX_ROTATION) {
                    rotation += MAX_ROTATION / 10.0;
                }
            }
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        /* Don't save the position of the ghost object between drags */
        if (!visible) {
            point = null;
        }
    }
}

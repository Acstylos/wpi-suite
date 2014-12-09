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

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Class Needed for JBoxCombo to render colorString to real color
 */
public class ColorRenderer extends JButton implements ListCellRenderer {
    public ColorRenderer() {
        setOpaque(true);

    }

    boolean b = false;

    /**
     * @param bg
     *            color for background of each JComboBox label.
     */
    public void setBackground(Color bg) {
        if (!b) {
            return;
        }

        super.setBackground(bg);
    }

    /**
     * sets each cell in the JComboBox to a specific color
     * 
     * @param list
     *            the list of cells in the JComboBox
     * @param value
     *            the color object
     * @param index
     *            the index of the list
     */
    public Component getListCellRendererComponent(JList list, Object value,
            int index,

            boolean isSelected, boolean cellHasFocus) {

        b = true;
        setText(evaluateColor(((Color) value).toString()));
        setBackground((Color) value);
        b = false;
        return this;
    }

    /**
     * Convert the string extracted from JComboBox to a string to be printed
     * out. Static because will be used in compareTo(beforeTask, afterTask) in
     * TaskPresenter.java
     * 
     * @param string
     *            representation of the color object.
     * @return string the English string of the color object
     */

    public static String evaluateColor(String c) {
        switch (c) {
        case "java.awt.Color[r=128,g=128,b=128]":
            return "gray";
        case "java.awt.Color[r=255,g=0,b=0]":
            return "red";
        case "java.awt.Color[r=0,g=0,b=255]":
            return "blue";
        case "java.awt.Color[r=0,g=255,b=255]":
            return "cyan";
        case "java.awt.Color[r=0,g=255,b=0]":
            return "green";

        default:
            return "No Label";

        }
    }
}

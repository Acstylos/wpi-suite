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

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class BucketListItemRenderer implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultRenderer
                .getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
        if (index == MainView.getInstance().getWorkflowPresenter()
                .getDefaultBucketIndex()) {
            value = (String) value + " (default)";
        }
        renderer.setText(isSelected ? "   " + (String) value : (String) value);
        return renderer;
    }

}

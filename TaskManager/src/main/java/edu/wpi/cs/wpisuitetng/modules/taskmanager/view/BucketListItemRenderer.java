package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class BucketListItemRenderer implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
        if(index == MainView.getInstance().getWorkflowPresenter().getDefaultBucketIndex()){
            value = (String) value + " (default)";
        }
        renderer.setText(isSelected ? "   " + (String) value : (String) value);
        return renderer;
    }

}

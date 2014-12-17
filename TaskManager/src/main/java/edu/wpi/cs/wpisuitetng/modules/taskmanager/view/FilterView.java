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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTextField;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;

/**
 * An Expandable and Collapsible panel which holds all filtering options
 * and elements for the Task Manager.
 * 
 * @author The Floor Is Java
 * 
 */
public class FilterView extends JPanel{

    private static final long serialVersionUID = 8216327558633962233L;
    private JLabel filterLabel = new JLabel(Icons.FILTER);
    private JPanel titleHolderPanel = new JPanel();
    private JPanel dateHolderPanel = new JPanel();
    private JScrollPane filterScrollPane = new JScrollPane();
    private JLabel filterByTextLabel = new JLabel("Filter By Text Fields");
    private JLabel filterByUserLabel = new JLabel("Filter By Users");

    private JXDatePicker filterStartDatePicker = new JXDatePicker();
    private JXDatePicker filterEndDatePicker = new JXDatePicker();
    private Color colorsFilterOptions[] = { new Color(238, 238, 238), Color.WHITE,
            Color.YELLOW, Color.RED, Color.GREEN, Color.MAGENTA, Color.GRAY };

    private JLabel startDateFilterLabel = new JLabel("Start Date");
    private JLabel endDateFilterLabel = new JLabel("End Date");
    private JLabel filterByColorLabel = new JLabel("Filter By Color");
    private JLabel filterByDateLabel = new JLabel("Filter By Date");
    private JXTextField filterByText = new JXTextField();
    private JXTextField filterByUser = new JXTextField();

    private JComboBox<Color> filterColorComboBox = new JComboBox<Color>(colorsFilterOptions);
    private Date upperDateBound=null;
    private Date lowerDateBound=null;
    private boolean isExpanded = false;
    private final JPanel filterHolderPanel = new JPanel();
    private final JSeparator dateSeperator = new JSeparator();

    
    /**
     * Constructs view contains all filters
     */
    public FilterView() {
        titleHolderPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        
        titleHolderPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        titleHolderPanel.add(filterLabel, "cell 0 0");
        filterScrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY));
        
        filterScrollPane.setViewportView(filterHolderPanel);
        filterHolderPanel.setLayout(new MigLayout("", "[]", "[][][][]"));
        filterHolderPanel.add(filterByTextLabel, "flowx,cell 0 0");
        filterHolderPanel.add(filterByColorLabel, "flowx, cell 0 1");
        filterHolderPanel.add(filterByText, "cell 0 0,grow");
        
        filterHolderPanel.add(filterByUserLabel, "flowx, cell 0 2");
        filterHolderPanel.add(filterByUser, "cell 0 2,grow");
        filterHolderPanel.add(filterColorComboBox, "cell 0 1,grow");
        filterHolderPanel.add(dateHolderPanel, "cell 0 3,grow");
        filterColorComboBox.setRenderer(new ColorRenderer());
        filterColorComboBox.setSelectedIndex(0);
        filterColorComboBox.setSize(new Dimension(20, 5));
        
        dateHolderPanel.setLayout(new MigLayout("", "[][][]", "[][]"));
        dateHolderPanel.add(filterByDateLabel, "flowx,cell 0 0,alignx left");
        dateSeperator.setBackground(Color.LIGHT_GRAY);
        dateSeperator.setSize(new Dimension(0, 2));
        dateSeperator.setOrientation(SwingConstants.VERTICAL);
        
        dateHolderPanel.add(dateSeperator, "cell 1 0 1 2,alignx center,growy");
        startDateFilterLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dateHolderPanel.add(startDateFilterLabel, "flowx,cell 2 0,alignx right");
        dateHolderPanel.add(endDateFilterLabel, "flowx,cell 2 1,alignx trailing");
        filterStartDatePicker.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dateHolderPanel.add(filterStartDatePicker, "cell 2 0,alignx right");
        dateHolderPanel.add(filterEndDatePicker, "cell 2 1,alignx trailing");
        setExpanded();
        
        registerCallbacks();
    }
    
    /**
     * set Filter View to collapsed view, hides filters
     */
    public void setCollapsed(){
        removeAll();
        setMinimumSize(new Dimension(10, 100));
        setMaximumSize(new Dimension(3000, 100));
        setLayout(new MigLayout("fill"));
        add(titleHolderPanel, "grow");
        revalidate();
        repaint();
    }
    
    /**
     * Expands filter view showing all filters
     */
    public void setExpanded(){
        removeAll();
        setMinimumSize(new Dimension(375, 100));
        setMaximumSize(new Dimension(3000, 100));
        setLayout(new MigLayout("", "[]-1px[grow]", "[grow]"));
        add(titleHolderPanel, "cell 0 0,grow");
        add(filterScrollPane, "cell 1 0,grow");
        revalidate();
        repaint();
        
    }

    /**
     * Gives filters operations
     */
    public void registerCallbacks(){
        DocumentListener startDateListener = new DocumentListener() {
            /** {@inheritDoc} */
            /* 
             * sets the earliest due date and limit for latest due date
             */
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());
                BucketPresenter.getTaskFilter().setStartDate(
                        filterStartDatePicker.getDate());
                setLowerDateBound(filterStartDatePicker.getDate());
                MainView.getInstance().resetAllBuckets();
            }

            /** {@inheritDoc} */
            /* 
             * sets the earliest due date and limit for latest due date
             */
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());
                BucketPresenter.getTaskFilter().setStartDate(
                        filterStartDatePicker.getDate());
                setLowerDateBound(filterStartDatePicker.getDate());
                MainView.getInstance().resetAllBuckets();

            }

            /** {@inheritDoc} */
            @Override
            /* 
             * allows any due date before latest and
             * takes limit off latest due date
             */
            public void removeUpdate(DocumentEvent e) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());
                BucketPresenter.getTaskFilter().setStartDate(null);
                setLowerDateBound(null);
                MainView.getInstance().resetAllBuckets();
            }
        };
        
        DocumentListener endDateListener = new DocumentListener() {
            /** {@inheritDoc} */
            /* 
             * sets the latest due date and limit for earliest due date
             */
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());
                BucketPresenter.getTaskFilter().setEndDate(
                        filterEndDatePicker.getDate());
                setUpperDateBound(filterEndDatePicker.getDate());
                MainView.getInstance().resetAllBuckets();
            }

            /** {@inheritDoc} */
            /* 
             * sets the latest due date and limit for earliest due date
             */
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());
                BucketPresenter.getTaskFilter().setEndDate(
                        filterEndDatePicker.getDate());
                setUpperDateBound(filterEndDatePicker.getDate());
                MainView.getInstance().resetAllBuckets();

            }

            /** {@inheritDoc} */
            @Override
            /* 
             * allows any due date after earliest and
             * takes limit off earliest due date
             */

            public void removeUpdate(DocumentEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());
                BucketPresenter.getTaskFilter().setEndDate(null);
                setUpperDateBound(null);
                MainView.getInstance().resetAllBuckets();
            }

        };
        
        filterStartDatePicker.getEditor().getDocument()
                .addDocumentListener(startDateListener);
        filterEndDatePicker.getEditor().getDocument()
                .addDocumentListener(endDateListener);
        filterStartDatePicker.getEditor().addMouseListener(new MouseListener() {

            /* 
             * Adds upper date limit for start date picker
             */
            @Override
            public void mouseClicked(MouseEvent arg0) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());

            }

            /* 
             * Adds upper date limit for start date picker
             */
            @Override
            public void mouseEntered(MouseEvent arg0) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());

            }
            
            /* 
             * Adds upper date limit for start date picker
             */
            @Override
            public void mouseExited(MouseEvent arg0) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());

            }

            /* 
             * Adds upper date limit for start date picker
             */
            @Override
            public void mousePressed(MouseEvent arg0) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());

            }

            /* 
             * Adds upper date limit for start date picker
             */
            @Override
            public void mouseReleased(MouseEvent arg0) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());

            }

        });
        
        filterEndDatePicker.getEditor().addMouseListener(new MouseListener() {
            /* 
             * Adds lower date limit for start date picker
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());

            }
            /* 
             * Adds lower date limit for start date picker
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());

            }
            /* 
             * Adds lower date limit for start date picker
             */
            @Override
            public void mouseExited(MouseEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());

            }

            /* 
             * Adds lower date limit for start date picker
             */
            @Override
            public void mousePressed(MouseEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());

            }

            /* 
             * Adds lower date limit for start date picker
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());

            }

        });

        filterColorComboBox.addItemListener(new ItemListener() {
            /* 
             * gets new color to filter by and resets buckets
             */
            @Override
            public void itemStateChanged(ItemEvent e) {
                BucketPresenter.getTaskFilter().setFilterColor(
                        (Color) filterColorComboBox.getSelectedItem());
                MainView.getInstance().resetAllBuckets();
            }

        });
        
        filterByText.getDocument().addDocumentListener(new DocumentListener() {
            /* 
             * Adds filtering by text in description or title
             */
            @Override
            public void changedUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterText(filterByText.getText());
                MainView.getInstance().resetAllBuckets();

            }

            /* 
             * Adds filtering by text in description or title
             */
            @Override
            public void insertUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterText(filterByText.getText());
                MainView.getInstance().resetAllBuckets();
            }

            /* 
             * Adds filtering by text in description or title
             */
            @Override
            public void removeUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterText(filterByText.getText());
                MainView.getInstance().resetAllBuckets();
            }

        });

        filterByUser.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            /* 
             * Adds filtering by users
             */
            public void changedUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterUser(filterByUser.getText());
                MainView.getInstance().resetAllBuckets();
            }

            @Override
            /* 
             * Adds filtering by users
             */
            public void insertUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterUser(filterByUser.getText());
                MainView.getInstance().resetAllBuckets();
            }

            @Override
            /* 
             * Adds filtering by users
             */
            public void removeUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterUser(filterByUser.getText());
                MainView.getInstance().resetAllBuckets();
            }

        });
        
        MouseListener expandListener = new MouseAdapter(){
            /* 
             * expands or collapses Filter View
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isExpanded){
                    setCollapsed();
                    isExpanded=false;
                }
                else{
                    setExpanded();
                    isExpanded=true;
                }
            }
            
        };
        titleHolderPanel.addMouseListener(expandListener);
        filterLabel.addMouseListener(expandListener);
    }
    
    
    /**gets highest date allowed by start date
     * @return upperDateBound highest date allowed
     */
    private Date getUpperDateBound() {
        return upperDateBound;
    }

    /**sets highest date allowed
     * @param upperDateBound highest date allowed by start date
     */
    private void setUpperDateBound(Date upperDateBound) {
        this.upperDateBound = upperDateBound;
    }

    /**gets lowest date allowed by finish date
     * @return lowest date allowed
     */
    private Date getLowerDateBound() {
        return lowerDateBound;
    }

    /*sets lowest date allowed
     * @param lowerDateBound lowest date allowed by finish date
     */
    private void setLowerDateBound(Date lowerDateBound) {
        this.lowerDateBound = lowerDateBound;
    }

    
}

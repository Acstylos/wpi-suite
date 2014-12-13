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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTextField;

/**
 * Sets up upper toolbar of TaskManager tab
 */
public class ToolbarView extends JPanel
{
    private static final long serialVersionUID = 5489162021821230861L;
    private Date upperDateBound=null;
    private Date lowerDateBound=null;
    
    static {
        /* Change the default icons for JXDatePicker. */
        UIManager.put("JXDatePicker.arrowIcon", Icons.CALENDAR);
        UIManager.put("JXMonthView.monthDownFileName", Icons.LEFT_ARROW);
        UIManager.put("JXMonthView.monthUpFileName", Icons.RIGHT_ARROW);
    }
    /**
     * Creates and positions option buttons in upper toolbar
     * @param visible boolean
     * @throws IOException 
     */
    public ToolbarView() {
        Color colorsFilterOptions[] = { new Color(238, 238, 238), Color.WHITE,
                Color.YELLOW, Color.RED, Color.GREEN, Color.MAGENTA, Color.GRAY };
        setLayout(new MigLayout("fill", "[grow][10%][10%][10%][10%]"));

        JButton createNewTaskButton = new JButton("<html>Create<br/>Task</html>");
        createNewTaskButton.setIcon(Icons.CREATE_TASK_LARGE);
        
        
        /**
         * Adds a new TaskView Tab into the MainView
         */
        createNewTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get instance of the New-Bucket Presenter to add new tasks into
                TaskPresenter taskPresenter = new TaskPresenter(0, MainView.getInstance().getWorkflowPresenter().getBucketPresenterById(1), ViewMode.CREATING);
                MainView.getInstance().addTab(taskPresenter.getModel().getTitle(), Icons.CREATE_TASK, taskPresenter.getView());
                int tabCount = MainView.getInstance().getTabCount();
                taskPresenter.getView().setIndex(tabCount-1);
                MainView.getInstance().setSelectedIndex(tabCount-1);
            }
        });

        add(createNewTaskButton, "flowx,cell 0 0");
        
        JToggleButton tglbtnArchive = new JToggleButton("<html>Hide<br/>Archived</html>");
        tglbtnArchive.setIcon(Icons.HIDE_ARCHIVE_LARGE);
        tglbtnArchive.setSelected(true);
        add(tglbtnArchive, "cell 0 0");

        JComboBox filterColorComboBox = new JComboBox<Color>(
                colorsFilterOptions);
        filterColorComboBox.setRenderer(new ColorRenderer());
        filterColorComboBox.setSelectedIndex(0);
        filterColorComboBox.setSize(new Dimension(20, 5));
        add(filterColorComboBox, "cell 1 0");
        filterColorComboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                BucketPresenter.getTaskFilter().setFilterColor(
                        (Color) filterColorComboBox.getSelectedItem());
                MainView.getInstance().resetAllBuckets();
            }

        });

        tglbtnArchive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tglbtnArchive.isSelected()) {
                    /* Show all tasks */
                    tglbtnArchive.setText("<html>Hide<br/>Archived</html>");
                    BucketPresenter.getTaskFilter().setIncludeArchived(true);
                    tglbtnArchive.setIcon(Icons.HIDE_ARCHIVE_LARGE);
                } else {
                    /* Only show non-archived tasks */
                    tglbtnArchive.setText("<html>Show<br/>Archived</html>");
                    BucketPresenter.getTaskFilter().setIncludeArchived(false);
                    tglbtnArchive.setIcon(Icons.SHOW_ARCHIVE_LARGE);
                }

                MainView.getInstance().resetAllBuckets();
            }
        });
        JXDatePicker filterStartDatePicker = new JXDatePicker();
        JXDatePicker filterEndDatePicker = new JXDatePicker();
        add(filterStartDatePicker, "cell 2 0");
        add(filterEndDatePicker, "cell 3 0");
        DocumentListener startDateListener = new DocumentListener() {
            /** {@inheritDoc} */
            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change update start");
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());
                BucketPresenter.getTaskFilter().setStartDate(
                        filterStartDatePicker.getDate());
                setLowerDateBound(filterStartDatePicker.getDate());
                MainView.getInstance().resetAllBuckets();
            }

            /** {@inheritDoc} */
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert update start");
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());
                BucketPresenter.getTaskFilter().setStartDate(
                        filterStartDatePicker.getDate());
                setLowerDateBound(filterStartDatePicker.getDate());
                MainView.getInstance().resetAllBuckets();

            }

            /** {@inheritDoc} */
            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove update start "
                        + filterStartDatePicker.getDate());
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());
                BucketPresenter.getTaskFilter().setStartDate(null);
                setLowerDateBound(null);
                MainView.getInstance().resetAllBuckets();
            }

        };
        DocumentListener endDateListener = new DocumentListener() {
            /** {@inheritDoc} */
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

            @Override
            public void mouseClicked(MouseEvent arg0) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());

            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());

            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());

            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                filterStartDatePicker.getMonthView().setUpperBound(
                        getUpperDateBound());

            }

        });
        filterEndDatePicker.getEditor().addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());

            }

            @Override
            public void mouseExited(MouseEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());

            }

            @Override
            public void mousePressed(MouseEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                filterEndDatePicker.getMonthView().setLowerBound(
                        getLowerDateBound());

            }

        });
        JLabel startDateFilter = new JLabel("Filter By Start Date");
        JLabel endDateFilter = new JLabel("Filter By End Date");
        add(startDateFilter, "cell 2 1");
        add(endDateFilter, "cell 3 1");

        JXTextField filterByText = new JXTextField();
        filterByText.setMinimumSize(new Dimension(100, 25));
        add(filterByText, "cell 4 0");

        filterByText.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterText(filterByText.getText());
                MainView.getInstance().resetAllBuckets();

            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterText(filterByText.getText());
                MainView.getInstance().resetAllBuckets();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterText(filterByText.getText());
                MainView.getInstance().resetAllBuckets();
            }

        });
        JXTextField filterByUser = new JXTextField();
        filterByUser.setMinimumSize(new Dimension(100, 25));
        add(filterByUser, "cell 5 0");

        filterByUser.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterUser(filterByUser.getText());
                MainView.getInstance().resetAllBuckets();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterUser(filterByUser.getText());
                MainView.getInstance().resetAllBuckets();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                BucketPresenter.getTaskFilter().setFilterUser(filterByUser.getText());
                MainView.getInstance().resetAllBuckets();
            }

        });
        JLabel filterByTextLabel = new JLabel("Filter By Text Fields");
        JLabel filterByUserLabel = new JLabel("Filter By Users");
        add(filterByTextLabel, "cell 4 1");
        add(filterByUserLabel, "cell 5 1");

    }

    /**gets highest date allowed by start date
     * @return upperDateBound highest date allowed
     */
    public Date getUpperDateBound() {
        return upperDateBound;
    }

    /**sets highest date allowed
     * @param upperDateBound highest date allowed by start date
     */
    public void setUpperDateBound(Date upperDateBound) {
        this.upperDateBound = upperDateBound;
    }

    /**gets lowest date allowed by finish date
     * @return lowest date allowed
     */
    public Date getLowerDateBound() {
        return lowerDateBound;
    }

    /*sets lowest date allowed
     * @param lowerDateBound lowest date allowed by finish date
     */
    public void setLowerDateBound(Date lowerDateBound) {
        this.lowerDateBound = lowerDateBound;
    }

} 
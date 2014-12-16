package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTextField;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;

public class FilterView extends JPanel{

    private JLabel filterLabel = new JLabel("<HTML>F<br>i<br>l<br>t<br>e<br>r</HTML>");
    private JPanel titleHolderPanel = new JPanel();
    private JPanel dateHolderPanel = new JPanel();
    JLabel filterByTextLabel = new JLabel("Filter By Text Fields");
    JLabel filterByUserLabel = new JLabel("Filter By Users");

    JXDatePicker filterStartDatePicker = new JXDatePicker();
    JXDatePicker filterEndDatePicker = new JXDatePicker();
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

    
    public FilterView() {
        setCollapsed();
        dateHolderPanel.setLayout(new MigLayout("", "[grow]", "[][]"));
        dateHolderPanel.add(startDateFilterLabel, "flowx, cell 0 0");
        dateHolderPanel.add(endDateFilterLabel, "flowx, cell 0 1");
        dateHolderPanel.add(filterEndDatePicker, "cell 0 1");
        dateHolderPanel.add(filterStartDatePicker, "cell 0 0");
        titleHolderPanel.setLayout(new MigLayout("fill"));
        titleHolderPanel.setBorder(new LineBorder(new Color(0,0,0)));
        filterColorComboBox.setRenderer(new ColorRenderer());
        filterColorComboBox.setSelectedIndex(0);
        filterColorComboBox.setSize(new Dimension(20, 5));
        registerCallbacks();
    }
    
    public void setCollapsed(){
        removeAll();
        setLayout(new MigLayout("fill"));
        filterLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
        this.add(titleHolderPanel, "dock west");
        this.revalidate();
        this.repaint();
    }
    
    public void setExpanded(){
        removeAll();
        setLayout(new MigLayout("", "[grow][][grow]", "[][][][][]"));
        add(titleHolderPanel, "cell 0 0 1 5, grow");
        add(filterByTextLabel, "cell 1 0");
        add(filterByColorLabel, "cell 1 1");
        add(filterByDateLabel, "cell 1 2");
        add(filterByTextLabel, "cell 1 0, grow");
        add(filterColorComboBox, "cell  2 1 , grow");
        add(dateHolderPanel, "cell 1 3 2 2, grow");
        add(filterByText, "cell 2 0, grow");
        this.revalidate();
        this.repaint();
        
    }

    public void registerCallbacks(){
        DocumentListener startDateListener = new DocumentListener() {
            /** {@inheritDoc} */
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

        filterColorComboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                BucketPresenter.getTaskFilter().setFilterColor(
                        (Color) filterColorComboBox.getSelectedItem());
                MainView.getInstance().resetAllBuckets();
            }

        });
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
        
        MouseListener expandListener = new MouseListener(){

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

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
            
        };
        titleHolderPanel.addMouseListener(expandListener);
        filterLabel.addMouseListener(expandListener);
        titleHolderPanel.add(filterLabel, "dock west");

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

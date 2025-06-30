/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.junaid.client;

import java.awt.Component;
import java.awt.Image;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author junaidxyz
 */
public class CustomTable {
    
    /*######################################################################################## CUSTOM MODELS*/
    public static DefaultTableModel MODEL_IISR = new DefaultTableModel(
        new Object[][] {},
        new Object[] { "flag", "name", "symbol", "select" }
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 0, 2 -> ImageIcon.class;
                case 3 -> Boolean.class;
                default -> String.class;
            };
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };
    
    public static DefaultTableModel MODEL_IISS = new DefaultTableModel(
        new Object[][] {},
        new Object[] { "code", "name", "flag", "symbol" }
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 2,3 -> ImageIcon.class;
                default -> String.class;
            };
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };
    
    /*######################################################################################## HELPER METHODS*/
    
    
    public void setRadioRenderer(JTable table, int column){
        table.getColumn(3).setCellRenderer(new RadioRenderer());
        table.getColumn(column).setCellEditor(new RadioEditor());
    }
    
    
    /*######################################################################################## HELPER CLASSES*/ 
    
    class RadioRenderer extends JRadioButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setSelected(Boolean.TRUE.equals(value));
            setHorizontalAlignment(SwingConstants.CENTER);
            return this;
        }
    }
    
    class RadioEditor extends DefaultCellEditor {
        private final JRadioButton radioButton = new JRadioButton();

        public RadioEditor() {
            super(new JCheckBox()); // Dummy, unused
            radioButton.setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                     int row, int column) {
            radioButton.setSelected(Boolean.TRUE.equals(value));
            return radioButton;
        }

        @Override
        public Object getCellEditorValue() {
            return radioButton.isSelected();
        }
    }


}

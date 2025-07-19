/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.junaid.client.ui.model;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

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
    
    public static DefaultTableModel getModelInstance_IISR(){
        return new DefaultTableModel(
        new Object[][] {},
        new Object[] { "flag", "name", "symbol", "select" }
    )
        {
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
    
    }
    
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
    
    public static DefaultTableModel getModelInstance_IISS(){
        return new DefaultTableModel(
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
    }
    
    public static DefaultTableModel getModelInstance_IISS_PV(){
        return new DefaultTableModel(
        new Object[][] {},
        new Object[] { "flag", "code", "symbol", "type" }
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 0,2 -> ImageIcon.class;
                default -> String.class;
            };
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
      };
    }
    
    /*######################################################################################## HELPER METHODS*/
    
    public static int getSelectedRadioRow(JTable table, int radioColumnIndex) {
        for (int i = 0; i < table.getRowCount(); i++) {
            Object value = table.getValueAt(i, radioColumnIndex);
            if (Boolean.TRUE.equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public void setRadioRenderer(JTable table, int columnIndex) {
        table.getColumnModel().getColumn(columnIndex).setCellRenderer(new RadioRenderer());
        table.getColumnModel().getColumn(columnIndex).setCellEditor(new RadioEditor(table, columnIndex));
    }


    public void setRadioRenderer(JTable table, String columnIdentifier) {
        int columnIndex = table.getColumn(columnIdentifier).getModelIndex();
        table.getColumn(columnIdentifier).setCellRenderer(new RadioRenderer());
        table.getColumn(columnIdentifier).setCellEditor(new RadioEditor(table, columnIndex));
    }


    public void setRadioRenderer(JTable table, int columnIndex, boolean yes) {
        try {
            if (columnIndex >= 0 && columnIndex < table.getColumnCount()) {
                TableColumn column = table.getColumnModel().getColumn(columnIndex);
                column.setCellRenderer(new RadioRenderer());
                column.setCellEditor(new RadioEditor(table, columnIndex));
            } else {
                throw new IllegalArgumentException("Column index out of bounds: " + columnIndex);
            }
        } catch (Exception e) {
            System.err.println("Error setting radio renderer: " + e.getMessage());
        }
    }

    /*######################################################################################## HELPER METHODS*/
    
    class RadioRenderer extends JRadioButton implements TableCellRenderer {
        public RadioRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(false);
            setBorderPainted(false);
            setFocusPainted(false);


            setPreferredSize(new Dimension(60, 60));
            setFont(getFont().deriveFont(18f));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setSelected(Boolean.TRUE.equals(value));

            if (isSelected) {
                setOpaque(true);
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setOpaque(false);
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            return this;
        }
    }

    class RadioEditor extends DefaultCellEditor {
        private final JRadioButton radioButton = new JRadioButton();
        private JTable table;
        private int radioColumn;

        public RadioEditor(JTable table, int radioColumn) {
            super(new JCheckBox());
            this.table = table;
            this.radioColumn = radioColumn;
            radioButton.setHorizontalAlignment(SwingConstants.CENTER);
            radioButton.setOpaque(false);
            radioButton.setBorderPainted(false);
            radioButton.setFocusPainted(false);

            radioButton.setPreferredSize(new Dimension(60, 60));
            radioButton.setFont(radioButton.getFont().deriveFont(18f));

            radioButton.addActionListener(e -> {
                if (radioButton.isSelected()) {
                    ensureSingleSelection();
                }
                stopCellEditing();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                     int row, int column) {
            radioButton.setSelected(Boolean.TRUE.equals(value));
            radioButton.setOpaque(false);
            return radioButton;
        }

        @Override
        public Object getCellEditorValue() {
            return radioButton.isSelected();
        }

        @Override
        public boolean stopCellEditing() {
            fireEditingStopped();
            return super.stopCellEditing();
        }

        private void ensureSingleSelection() {
            for (int i = 0; i < table.getRowCount(); i++) {
                if (i != table.getEditingRow()) {
                    table.setValueAt(false, i, radioColumn);
                }
            }
            table.repaint();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CustomGraphics;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomTable extends JTable {

    private final CustomTableModel model = new CustomTableModel();

    public CustomTable() {
        setModel(model);
        setRowHeight(60);
        setShowVerticalLines(false);
        setShowHorizontalLines(true);
        setGridColor(Color.LIGHT_GRAY);
        setTableHeader(null); // No column headers
        setBackground(Color.WHITE);
        setIntercellSpacing(new Dimension(0, 1)); // Only horizontal grid

        // Set custom renderer for all columns
        TableColumnModel columnModel = getColumnModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(new CustomCellRenderer());
        }
    }

    // Public method to add a row
    public void addRow(ImageIcon leftImage, String text, ImageIcon rightImage) {
        model.addRow(leftImage, text, rightImage);
    }

    // Public method to get text at a specific row
    public String getTextAt(int rowIndex) {
        return model.getTextAt(rowIndex);
    }

    // ========================
    // Custom Table Model
    // ========================
    private static class CustomTableModel extends AbstractTableModel {
        private final String[] columns = {"LeftImage", "Text", "RightImage"};
        private final List<Object[]> data = new ArrayList<>();

        public void addRow(ImageIcon left, String text, ImageIcon right) {
            data.add(new Object[]{left, text, right});
            fireTableRowsInserted(data.size() - 1, data.size() - 1);
        }

        public String getTextAt(int row) {
            if (row >= 0 && row < data.size()) {
                return (String) data.get(row)[1];
            }
            return null;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data.get(row)[col];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getRowCount() > 0 && getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            }
            return Object.class;
        }
    }

    // ========================
    // Custom Cell Renderer
    // ========================
    private static class CustomCellRenderer extends JLabel implements TableCellRenderer {
        public CustomCellRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            if (value instanceof ImageIcon) {
                ImageIcon icon = (ImageIcon) value;
                Image scaled = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(scaled));
                setText("");
            } else if (value instanceof String) {
                setIcon(null);
                setText((String) value);
                setFont(new Font("SansSerif", Font.PLAIN, 14));
                setHorizontalAlignment(LEFT);
            } else {
                setIcon(null);
                setText("");
            }

            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
            return this;
        }
    }
}

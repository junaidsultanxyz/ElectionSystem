import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class MyTableUI extends JFrame {

    public MyTableUI() {
        setTitle("Custom Table Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 150);
        setLocationRelativeTo(null);

        // Load images
        ImageIcon ptiFlag = new ImageIcon("pti.png");
        ImageIcon batSymbol = new ImageIcon("bat.png");

        // Table data
        Object[][] rowData = {
            {ptiFlag, "PTI", batSymbol}
        };
        String[] columnNames = {"Image", "Party", "Symbol"};

        // Table model
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(80);

        // Set custom renderers
        table.getColumnModel().getColumn(0).setCellRenderer(new CircularImageRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new IconWithTextRenderer());

        add(new JScrollPane(table));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyTableUI().setVisible(true));
    }
}

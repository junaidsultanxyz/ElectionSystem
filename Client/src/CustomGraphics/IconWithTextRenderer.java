import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class IconWithTextRenderer extends JPanel implements TableCellRenderer {
    private final JLabel imageLabel = new JLabel();
    private final JLabel textLabel = new JLabel("BAT", SwingConstants.CENTER);

    public IconWithTextRenderer() {
        setLayout(new BorderLayout());
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Serif", Font.PLAIN, 10));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);
        add(textLabel, BorderLayout.SOUTH);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (value instanceof ImageIcon) {
            imageLabel.setIcon((ImageIcon) value);
        } else {
            imageLabel.setIcon(null);
        }
        return this;
    }
}

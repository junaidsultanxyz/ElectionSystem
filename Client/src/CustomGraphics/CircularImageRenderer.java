import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;

class CircularImageRenderer extends JLabel implements TableCellRenderer {
    public CircularImageRenderer() {
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    private ImageIcon getCircularIcon(ImageIcon icon) {
        int size = Math.min(icon.getIconWidth(), icon.getIconHeight());
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, size, size));
        g2.drawImage(icon.getImage(), 0, 0, size, size, null);
        g2.dispose();

        return new ImageIcon(image);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (value instanceof ImageIcon) {
            setIcon(getCircularIcon((ImageIcon) value));
        } else {
            setIcon(null);
        }
        return this;
    }
}

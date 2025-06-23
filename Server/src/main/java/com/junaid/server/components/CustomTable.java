package com.junaid.server.components;

import java.awt.Image;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
        new Object[] { "code", "name", "flag", "symbol" } // if want to change the column name, change these, dont switch order
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 2,3 -> ImageIcon.class;
                default -> String.class;
            };
        }
    };
    
    /*######################################################################################## HELPER METHODS*/
    
    public void addRowVoting(JTable table, String partyCode, String partyName, String partyFlagPath, String partySymbolPath) throws MalformedURLException{
        int height = table.getRowHeight();
        
        ImageIcon partyFlag     = new ImageIcon(getClass().getResource(partyFlagPath)); // to load png
//        ImageIcon partyFlag     = new FlatSVGIcon(partyFlagPath, height, height); // to load svg

        ImageIcon partySymbol   = new ImageIcon(getClass().getResource(partySymbolPath));
//        ImageIcon partySymbol     = new FlatSVGIcon(partySymbolPath, height, height);
        
        // scaling is only needed when loading png, comment these scaled objects 
        Image scaled_partyFlag = partyFlag.getImage().getScaledInstance(height, height, Image.SCALE_SMOOTH);
        Image scaled_partySymbol = partySymbol.getImage().getScaledInstance(height, height, Image.SCALE_SMOOTH);
        
        Object[] data = new Object[] {
            partyCode,
            partyName,
            new ImageIcon(scaled_partyFlag), // passing on scaled instance since loading png, in case of svg, just pass the inital object we created that is commented
            new ImageIcon(scaled_partySymbol)
        };
        
        ((DefaultTableModel) table.getModel()).addRow(data);
    } // work in progress
    
    
    public static void hideTableHeader(JTable table) {
        table.setTableHeader(null);
        ((JScrollPane) table.getParent().getParent()).setColumnHeaderView(null);
    }
    
    /*######################################################################################## HELPER CLASSES*/ 


}
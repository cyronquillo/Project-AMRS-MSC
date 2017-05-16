package instantiation;

import javax.swing.JPanel;

public class Clear extends javax.swing.table.DefaultTableCellRenderer {
	JPanel panel = new JPanel();
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cellComponent.setBackground(java.awt.Color.WHITE);
        return cellComponent;
    }
}
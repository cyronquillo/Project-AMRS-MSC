package instantiation;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.Color;
import java.awt.Dimension;

public class Frame extends JFrame{
	public static JPanel panel = new JPanel();
	JFileChooser fc = new JFileChooser();
	public static JScrollPane scroll1 = new JScrollPane();
	String[] column;

	public static Initialization start = new Initialization("input/input.txt"); 
	public Frame(){
		super("Project AMRS");
		this.setPreferredSize(new Dimension(1000,700));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		panel.setLayout(null);

		start.populateTable();
		buildColumns();

		JTable table1 = new JTable(start.data, column); // creates a table for training data
		table1.setEnabled(false);
		table1.getColumnModel().getColumn(0).setPreferredWidth(100);
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scroll1.setViewportView(table1);
		scroll1.setBounds(40,40,800,150);
		FixedColumnTable fct = new FixedColumnTable(1, scroll1);
		panel.add(scroll1);

		this.setContentPane(panel);
		this.setFocusable(true);
		this.pack();																// packs the this
        this.setVisible(true);														// makes the this visible
        this.setLocationRelativeTo(null);
	}


	public void buildColumns(){
		int x = Initialization.clockcycle.size()+1;
		column = new String[x];
		column[0] = "Instructions";
		for(int i = 1; i < x; i++){
			column[i] = i +"";
		}
	}
}
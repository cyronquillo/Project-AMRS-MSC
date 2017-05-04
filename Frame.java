package instantiation;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.Color;
import java.awt.Dimension;

public class Frame extends JFrame{
	public static JPanel panel = new JPanel();
	JFileChooser fc = new JFileChooser();
	public static JScrollPane scroll1 = new JScrollPane();
	public static JScrollPane scroll2_1 = new JScrollPane();
	public static JScrollPane scroll2_2 = new JScrollPane();
	String[] column;
	String[] column1 = {"Register", "Value"};
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		

	public static Initialization start = new Initialization("input/input.txt"); 
	public Frame(){
		super("Project AMRS");
		this.setPreferredSize(new Dimension(1000,900));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		panel.setLayout(null);

		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		start.populateTable();
		buildColumns();

		start.populateValues();

		

		JTable table1 = new JTable(start.data, column); // creates a table for training data
		table1.setEnabled(false);
		table1.getColumnModel().getColumn(0).setPreferredWidth(120);
		table1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scroll1.setViewportView(table1);
		scroll1.setBounds(50,80,900,150);
		FixedColumnTable fct = new FixedColumnTable(1, scroll1);
		panel.add(scroll1);

		// creates button that enables user to choose a file 
		JButton file = new JButton("Choose File");
		file.setBounds(800, 20, 150, 30);
		panel.add(file);

		JButton next = new JButton("Next");
		next.setBounds(450, 40, 100, 30);
		panel.add(next);

		JTable table2_1 = new JTable(start.dataReg1, column1);
		table2_1.setEnabled(false);
		table2_1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table2_1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		scroll2_1.setViewportView(table2_1);
		scroll2_1.setBounds(50,250,200,534);
		panel.add(scroll2_1);

		JTable table2_2 = new JTable(start.dataReg2, column1);
		table2_2.setEnabled(false);
		table2_2.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table2_2.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		scroll2_2.setViewportView(table2_2);
		scroll2_2.setBounds(300,250,200,534);
		panel.add(scroll2_2);


		this.setContentPane(panel);
		this.setFocusable(true);
		this.pack();																// packs the this
        this.setVisible(true);														// makes the this visible
        this.setLocationRelativeTo(null);
		Initialization.clcy.showClockCycle();

	}


	public void buildColumns(){
		int x = Initialization.clcy.clockcycle.size()+1;
		column = new String[x];
		column[0] = "Instructions";
		for(int i = 1; i < x; i++){
			column[i] = i +"";
		}
	}


}
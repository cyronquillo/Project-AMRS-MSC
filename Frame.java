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
	public static JScrollPane scroll2 = new JScrollPane();
	public static JScrollPane scroll3 = new JScrollPane();
	public static JScrollPane scroll4 = new JScrollPane();
	public static JScrollPane scroll5 = new JScrollPane();
	String[] column;
	String[] column1 = {"Register", "Value"};
	String[] column2 = {"Address", "Instruction"};
	String[] column3 = {"Flag", "Values"};
	String[] column4 = {"HAZARDS", "#"};
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

		JTable table2 = new JTable(start.dataReg, column1);
		table2.setEnabled(false);
		table2.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table2.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		scroll2.setViewportView(table2);
		scroll2.setBounds(50,250,200,534);
		panel.add(scroll2);

		JTable table3 = new JTable(start.dataInst,column2);
		table3.setEnabled(false);
		table3.getColumnModel().getColumn(0).setPreferredWidth(180);
		table3.getColumnModel().getColumn(1).setPreferredWidth(300);
		scroll3.setViewportView(table3);
		scroll3.setBounds(585,250,150,200);
		panel.add(scroll3);

		JTable table4 = new JTable(start.dataFlags,column3);
		table4.setEnabled(false);
		table4.getColumnModel().getColumn(0).setPreferredWidth(300);
		table4.getColumnModel().getColumn(1).setPreferredWidth(180);
		scroll4.setViewportView(table4);
		scroll4.setBounds(800,250,150,70);
		panel.add(scroll4);

		JTable table5 = new JTable(start.dataHazards,column4);
		table5.setEnabled(false);
		table5.getColumnModel().getColumn(0).setPreferredWidth(300);
		table5.getColumnModel().getColumn(1).setPreferredWidth(180);
		scroll5.setViewportView(table5);
		scroll5.setBounds(800,365,150,86);
		panel.add(scroll5);

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
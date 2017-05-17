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
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Frame extends JFrame{
	public static JPanel panel = new JPanel();
	JFileChooser fc = new JFileChooser();
	private JScrollPane scroll1 = new JScrollPane();
	private JScrollPane scroll2_1 = new JScrollPane();
	private JScrollPane scroll2_2 = new JScrollPane();
	private JScrollPane scroll3 = new JScrollPane();
	private JScrollPane scroll4 = new JScrollPane();
	private JScrollPane scroll5 = new JScrollPane();
	private JScrollPane scrollTA;
	private JTable table1;
	private JTable table2_1;
	private JTable table2_2;
	private JTable table3;
	private JTable table4;
	private JTable table5;
	private JTextArea textArea;
	public JLabel errLabel = new JLabel("ERROR: Check Terminal on What to Resolve!");
	public JLabel ccLabel;
	public JLabel CIR;
	public JLabel PC;
	public JLabel MAR;
	public JLabel MBR;
	public JLabel totalStalls;
	public JLabel totalCC;
	public JButton next;

	String[] column;
	String[] column1 = {"Register", "Value"};
	String[] column2 = {"Address", "Instruction"};
	String[] column3 = {"Flag", "Values"};
	String[] column4 = {"HAZARDS", "#"};
	
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();			//makes the texts centered
	Yellow yellow = new Yellow();
	Clear clear = new Clear();	
	public static Initialization start = new Initialization(new File(".")); 
	public Frame(){
		super("Project AMRS");
		this.setPreferredSize(new Dimension(1000,575));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		panel.setLayout(null);

		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		errLabel.setBounds(200,100,400,100);
		errLabel.setVisible(false);
		panel.add(errLabel);
		textArea = new JTextArea(100,100);
 		scrollTA= new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
 		scrollTA.setBounds(600,80,300,150);
		panel.add(scrollTA);

		JLabel loadLabel = new JLabel("LOAD");
		loadLabel.setUI(new VerticalLabelUI(true));

		JButton test = new JButton();
		test.add(loadLabel);
		test.setBounds(900, 80, 43,150);
		test.addActionListener(new ActionListener(){		// creates a table showing the points and its corresponding classification in the selected file
			public void actionPerformed(ActionEvent e){
				try{
					BufferedWriter bw = new BufferedWriter(new FileWriter("load.txt"));	
					bw.write(textArea.getText());
					bw.close();
					loadValues(new File("load.txt"));
				} catch(Exception ex){}
			}
		});
		panel.add(test);

		start.populateTable();
		buildColumns();
		start.populateValues();

		table1 = new JTable(start.data, column); 
		table1.setEnabled(false);
		table1.getColumnModel().getColumn(0).setPreferredWidth(120);
		table1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		int cc = Initialization.clcy.clockcycle.size();
		
		for(int i = 1; i < cc+1; i++){
			table1.getColumnModel().getColumn(i).setPreferredWidth(20);
		}

		table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scroll1.setViewportView(table1);

		int x,y;
		if(cc >= 38) x = 120 + 38*20;
		else x = 120+20*(cc+1);
		if(start.data.length > 7) y = 150;
		else y = 17*(start.data.length+1);
		scroll1.setBounds(50,80,x,y);
		FixedColumnTable fct = new FixedColumnTable(1, scroll1);
		panel.add(scroll1);

		//table for registers 1-16 and its corresponding values
		table2_1 = new JTable(start.dataReg1, column1);
		table2_1.setEnabled(false);
		table2_1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table2_1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		scroll2_1.setViewportView(table2_1);
		scroll2_1.setBounds(50,250,200,278);
		panel.add(scroll2_1);

		//table for registers 17-32 and its corresponding values
		table2_2 = new JTable(start.dataReg2, column1);
		table2_2.setEnabled(false);
		table2_2.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table2_2.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		scroll2_2.setViewportView(table2_2);
		scroll2_2.setBounds(320,250,200,278);
		panel.add(scroll2_2);

		//table for instructions
		table3 = new JTable(start.dataInst,column2);
		table3.setEnabled(false);
		table3.getColumnModel().getColumn(0).setPreferredWidth(250);
		table3.getColumnModel().getColumn(1).setPreferredWidth(500);
		table3.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		scroll3.setViewportView(table3);
		if(start.dataInst.length > 11) scroll3.setBounds(585,250,200, 200);
		else scroll3.setBounds(585,250,200, 17*(start.dataInst.length+1));
		panel.add(scroll3);

		//table for flags and its corresponding values
		table4 = new JTable(start.dataFlags,column3);
		table4.setEnabled(false);
		table4.getColumnModel().getColumn(0).setPreferredWidth(300);
		table4.getColumnModel().getColumn(1).setPreferredWidth(180);
		table4.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		scroll4.setViewportView(table4);
		scroll4.setBounds(800,250,150,70);
		panel.add(scroll4);

		//table for hazards and the count of hazards that occurred
		table5 = new JTable(start.dataHazards,column4);
		table5.setEnabled(false);
		table5.getColumnModel().getColumn(0).setPreferredWidth(300);
		table5.getColumnModel().getColumn(1).setPreferredWidth(180);
		table5.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		scroll5.setViewportView(table5);
		scroll5.setBounds(800,365,150,86);
		panel.add(scroll5);

		//Label that shows the clock cycle
		ccLabel = new JLabel("Clock Cycle: " + Initialization.clcy.currentCC + " of " + (Initialization.clcy.clockcycle.size()-1));
		ccLabel.setBounds(50, 50, 300, 30);
		panel.add(ccLabel);

		//Shows the current CIR
		CIR = new JLabel("CIR: "+Initialization.CIR);
		CIR.setBounds(585, 450, 100, 30);
		panel.add(CIR);

		//Shows the current content of PC
		PC = new JLabel("PC: "+Integer.toString(Initialization.PC));
		PC.setBounds(585, 470, 100, 30);
		panel.add(PC);

		//Shows current content of MAR
		MAR = new JLabel("MAR: "+Integer.toString(Initialization.MAR));
		MAR.setBounds(585, 490, 100, 30);
		panel.add(MAR);

		//Shows current content of MBR
		String mbrContent = Initialization.MBR==null ? "null" : Initialization.MBR.getInstruction();
		MBR = new JLabel("MBR: "+mbrContent);
		MBR.setBounds(585, 510, 200, 30);
		panel.add(MBR);

		//Shows total number of stalls
		totalStalls = new JLabel("Stalls: "+Integer.toString(Initialization.clcy.getStalls()));
		totalStalls.setBounds(800, 450, 100, 30);
		panel.add(totalStalls);

		//Shows total clock cycle
		totalCC = new JLabel("Clock Cyles: "+Integer.toString(Initialization.clcy.clockcycle.size()-1));
		totalCC.setBounds(800, 470, 200, 30);
		panel.add(totalCC);

		//Button for that allows user to go to the next clock cycle
		next = new JButton("Next");
		next.setBounds(450, 40, 100, 30);
		panel.add(next);

		if(Initialization.fileErr == true) next.setVisible(false);
		else next.setVisible(true);
		
		next.addActionListener(new ActionListener(){		// creates a table showing the points and its corresponding classification in the selected file
			public void actionPerformed(ActionEvent e){
				Initialization.clcy.currentCC++;
				if(Initialization.clcy.currentCC < Initialization.clcy.clockcycle.size()){
					table1.getColumnModel().getColumn(Initialization.clcy.currentCC-1).setCellRenderer(yellow);
					if(Initialization.clcy.currentCC != 1) 
						table1.getColumnModel().getColumn(Initialization.clcy.currentCC-2).setCellRenderer(clear);

					Initialization.clcy.performInstructions(Initialization.clcy.currentCC);
					update();					
				}
				else{
					table1.getColumnModel().getColumn(Initialization.clcy.clockcycle.size()-2).setCellRenderer(clear);
					next.setVisible(false);

				}
				table1.repaint();
			}
		});

		//Button that allows user to choose a file that will be loaded
		JButton file = new JButton("Choose File");
		file.setBounds(800, 20, 150, 30);
		panel.add(file);
		file.addActionListener(new ActionListener(){		// creates a table showing the points and its corresponding classification in the selected file
			public void actionPerformed(ActionEvent e){
				fc.setCurrentDirectory(new File("."));
				fc.setDialogTitle("Selecting input.txt");
				int returnVal = fc.showOpenDialog(file);

				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					loadValues(file);
					

				}
			}
		});

		this.setContentPane(panel);
		this.setFocusable(true);
		this.pack();																// packs the this
        this.setVisible(true);														// makes the this visible
        this.setLocationRelativeTo(null);
		// Initialization.clcy.showClockCycle();

	}
	public void loadValues(File file){
		Initialization.storeTextArea(file);

		if(Initialization.fileErr == true){
			next.setVisible(false);
			errLabel.setVisible(true);
		}
		else{
			next.setVisible(true);
			errLabel.setVisible(false);
		}
		textArea.setText(start.getTextAreaData());
		start.populateTable();
		buildColumns();
		table1 = new JTable(start.data, column); 
		table1.setEnabled(false);
		table1.getColumnModel().getColumn(0).setPreferredWidth(120);
		table1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		int cc = Initialization.clcy.clockcycle.size();

		for(int i = 1; i < cc+1; i++){
			table1.getColumnModel().getColumn(i).setPreferredWidth(20);
		}

		table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scroll1.setViewportView(table1);

		int x,y;
		if(cc >= 20) x = 120 + 20*20;
		else x = 120+20*(cc+1);
		if(start.data.length > 7) y = 150;
		else y = 17*(start.data.length+1);
		scroll1.setBounds(50,80,x,y);
		FixedColumnTable fct = new FixedColumnTable(1, scroll1);

		update();
		for(int i = 0; i < Initialization.clcy.clockcycle.size(); i++){
			table1.getColumnModel().getColumn(i).setCellRenderer(clear);

		}

		table1.repaint();

	}
	public void update(){							//updates the values in the tables and of those in the labels.
		start.populateValues();


		table2_1 = new JTable(start.dataReg1, column1);
		table2_1.setEnabled(false);
		table2_1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table2_1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		scroll2_1.setViewportView(table2_1);

		table2_2 = new JTable(start.dataReg2, column1);
		table2_2.setEnabled(false);
		table2_2.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table2_2.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		scroll2_2.setViewportView(table2_2);

		table3 = new JTable(start.dataInst,column2);
		table3.setEnabled(false);
		table3.getColumnModel().getColumn(0).setPreferredWidth(250);
		table3.getColumnModel().getColumn(1).setPreferredWidth(500);
		table3.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		scroll3.setViewportView(table3);
		
		if(start.dataInst.length > 11) scroll3.setBounds(585,250,200, 200);
		else scroll3.setBounds(585,250,200, 17*(start.dataInst.length+1));
		
		table4 = new JTable(start.dataFlags,column3);
		table4.setEnabled(false);
		table4.getColumnModel().getColumn(0).setPreferredWidth(300);
		table4.getColumnModel().getColumn(1).setPreferredWidth(180);
		scroll4.setViewportView(table4);
		
		table5 = new JTable(start.dataHazards,column4);
		table5.setEnabled(false);
		table5.getColumnModel().getColumn(0).setPreferredWidth(300);
		table5.getColumnModel().getColumn(1).setPreferredWidth(180);
		scroll5.setViewportView(table5);

		ccLabel.setText("Clock Cycle: " + Initialization.clcy.currentCC + " of " + (Initialization.clcy.clockcycle.size()-1));
		CIR.setText("CIR: "+Initialization.CIR);
		PC.setText("PC: "+Integer.toString(Initialization.PC));
	 	MAR.setText("MAR: "+Integer.toString(Initialization.MAR));
		String mbrContent = Initialization.MBR==null ? "null" : Initialization.MBR.getInstruction();
		MBR.setText("MBR: "+mbrContent);
		totalStalls.setText("Stalls: "+Integer.toString(Initialization.clcy.getStalls()));
		totalCC.setText("Clock Cyles: "+Integer.toString(Initialization.clcy.clockcycle.size()-1));
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
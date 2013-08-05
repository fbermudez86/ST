package com.st.ui;

import com.st.src.PatternFinder;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JTextArea;

public class MainView extends JFrame  {
	private static final long serialVersionUID = 1L;
	private JTextField txtStringPattern;
	private JTextField txtNewString;
	private JTextField txtBasePath;
	private JTextField txtOutputFile;
	private JTextArea txtResultArea = new JTextArea();
	/**
	 * Create the panel.
	 */
	public MainView() {
		//JFrame properties
		setResizable(false);
		createUIComponents();
		this.setTitle("Search Technologies Coding Exercise");
		this.setSize(450, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	/*
	 * Initialize GUI Components 
	 */
	private void createUIComponents() {
		getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Search Technologies Coding Exercise");
		lblTitle.setBounds(10, 11, 362, 14);
		getContentPane().add(lblTitle);
		
		JLabel lblStringPattern = new JLabel("String Pattern");
		lblStringPattern.setBounds(10, 58, 110, 14);
		getContentPane().add(lblStringPattern);
		
		JLabel lblNewString = new JLabel("New String");
		lblNewString.setBounds(10, 83, 110, 14);
		getContentPane().add(lblNewString);
		
		JLabel lblBasePath = new JLabel("Base Path");
		lblBasePath.setBounds(10, 108, 110, 14);
		getContentPane().add(lblBasePath);
		
		txtStringPattern = new JTextField();
		txtStringPattern.setBounds(130, 55, 233, 20);
		getContentPane().add(txtStringPattern);
		txtStringPattern.setColumns(10);
		
		txtNewString = new JTextField();
		txtNewString.setBounds(130, 77, 233, 20);
		getContentPane().add(txtNewString);
		txtNewString.setColumns(10);
		
		txtBasePath = new JTextField();
		txtBasePath.setEditable(false);
		txtBasePath.setBounds(130, 102, 233, 20);
		getContentPane().add(txtBasePath);
		txtBasePath.setColumns(10);
		
		JButton btnSearchDir = new JButton("...");
		btnSearchDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				handleSearchDirEvent();
			}
		});
		btnSearchDir.setBounds(373, 102, 25, 20);
		getContentPane().add(btnSearchDir);
		
		JButton btnSearch = new JButton("Search Pattern");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				handleSearchAction();
			}
		});
		btnSearch.setBounds(130, 162, 141, 23);
		getContentPane().add(btnSearch);
		
		txtOutputFile = new JTextField();
		txtOutputFile.setEditable(false);
		txtOutputFile.setColumns(10);
		txtOutputFile.setBounds(130, 131, 233, 20);
		getContentPane().add(txtOutputFile);
		
		JLabel lblOutputFile = new JLabel("Output File");
		lblOutputFile.setBounds(10, 137, 110, 14);
		getContentPane().add(lblOutputFile);
		
		JButton btnOutputFile = new JButton("...");
		btnOutputFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				handleOutputFileSelection();
			}
		});
		btnOutputFile.setBounds(373, 131, 25, 20);
		getContentPane().add(btnOutputFile);
		
		
		txtResultArea.setEditable(false);
		txtResultArea.setBounds(10, 191, 424, 69);
		getContentPane().add(txtResultArea);
		
	}

	protected void handleOutputFileSelection() {
		final JFileChooser fc = new JFileChooser(); 
		fc.setFileSelectionMode(JFileChooser.SAVE_DIALOG);  
		int returnVal = fc.showDialog(this,"Select Output File");  
		if (returnVal == JFileChooser.APPROVE_OPTION)  
		{  
			File file = fc.getSelectedFile();
			String sfilename = file.getPath();  
			txtOutputFile.setText(sfilename);	
		}		
	}
	/*
	 *	Validates the params and calls the Groovy code 
	 */
	protected void handleSearchAction() {
		if(txtBasePath.getText().trim().isEmpty() || txtNewString.getText().trim().isEmpty() || txtStringPattern.getText().trim().isEmpty() || txtOutputFile.getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Please provide all the parameters", "Error",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			String params[] = {txtBasePath.getText().trim(), txtStringPattern.getText().trim(), txtNewString.getText().trim(), txtOutputFile.getText().trim()};
			PatternFinder pf = new PatternFinder(params);
			long startTime =System.nanoTime();   
			pf.findInPath();
			double estimatedTime = (System.nanoTime() - startTime)/1000000000;   
			txtResultArea.setText(String.format("Operation completed in %s seconds \nAn log file has been created: %s", (int)estimatedTime, params[3]));
		}
	}

	/*
	 * Shows the JFileChooser for the Directory selection
	 */
	protected void handleSearchDirEvent() {
		 final JFileChooser fc = new JFileChooser(); 
		 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
		 int returnVal = fc.showDialog(this,"Select Target Directory");  
		 if (returnVal == JFileChooser.APPROVE_OPTION)  
		 {  
			File file = fc.getSelectedFile();  
		 	String sfilename = file.getPath();  
		 	txtBasePath.setText(sfilename);
		 }		
	}
	
	public static void main(String[] args) throws Exception {
	    new MainView();
	}
}
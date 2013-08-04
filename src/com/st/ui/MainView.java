package com.st.ui;

import java.awt.Container;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import com.st.src.PatternFinder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;


public class MainView extends JFrame  {
	private static final long serialVersionUID = 1L;
	private JTextField txtStringPattern;
	private JTextField txtNewString;
	private JTextField txtBasePath;
	private Container cp = this.getContentPane();
	/**
	 * Create the panel.
	 */
	public MainView() {
		setResizable(false);
		createUIComponents();
		this.setTitle("Search Technologies Coding Exercise");
		this.setSize(450, 300);
		this.setVisible(true);
	}

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
		
		JTextArea txtResultArea = new JTextArea();
		txtResultArea.setEditable(false);
		txtResultArea.setBounds(10, 158, 424, 102);
		getContentPane().add(txtResultArea);
		
		JButton btnSearch = new JButton("Search Pattern");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				handleSearchAction();
			}
		});
		btnSearch.setBounds(129, 125, 141, 23);
		getContentPane().add(btnSearch);
		
	}

	@SuppressWarnings("unused")
	protected void handleSearchAction() {
		if(txtBasePath.getText().trim().isEmpty() || txtNewString.getText().trim().isEmpty() || txtStringPattern.getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Please provide all the parameters", "Error",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			String params[] = {txtBasePath.getText().trim(), txtStringPattern.getText().trim(), txtNewString.getText().trim()};
			PatternFinder pf = new PatternFinder();
			pf.findInPath(params[0], params[1], params[2]);
		}
		
	}

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
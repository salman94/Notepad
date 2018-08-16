package fileOperation;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

public class FileOperation implements ActionListener{
	JPanel middlePane, southPane, panel;
	JButton save, dontSave, cancel;
	JLabel middleLabel;
	String fileName = "Untitled";
	JDialog dialog = null;
	JFileChooser fileChooser = null;
	JFrame frm = null;
	File file = null;
	String filePath = null;
	JTextArea ta =null;
	String operationType = null;
	boolean taUpdateFlag = false;
	public FileOperation(JFrame frm, JTextArea ta, String fileName) {
		this.frm = frm;
		this.ta = ta;
		this.fileName = fileName;
		ta.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				taUpdateFlag = true;
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				taUpdateFlag = true;
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				taUpdateFlag = true;
			}
		});
		initlizeActionBox();
		
		
	}
	
	
	public void fileOperation() {
		if(fileName == "Untitled" && ta.getText().length() == 0) {
			if(operationType.equals("newFile"))
				newFile();
			if(operationType.equals("openFile"))
				openFile();
			if(operationType.equals("exitFile")) 
				System.exit(0);
			
		}
		else if(fileName == "Untitled" && ta.getText().length() != 0) {
			//System.out.println("hhere11");
			fileEventHandler();
			
			
		}
		else if(fileName != "Untitled") {
			if(taUpdateFlag == false) {
				if(operationType.equals("newFile"))
					newFile();
				if(operationType.equals("openFile"))
					openFile();
				if(operationType.equals("exitFile")) {
					System.exit(0);
				}
					
			}
			else {
				filePath = file.getPath();
				fileEventHandler();
				
			}
		}
	}
	
	public void fileEventHandler() {
		showDialog(frm);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				if(saveFile()) {
					if(operationType.equals("newFile"))
						newFile();
					if(operationType.equals("openFile"))
						openFile();
					if(operationType.equals("exitFile")) {
						System.exit(0);
					}
						//frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				
			}
		});
		dontSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				if(operationType.equals("newFile"))
					newFile();
				if(operationType.equals("openFile"))
					openFile();
				if(operationType.equals("exitFile")) {
					System.exit(0);
				}
					//frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				
			}
		});
	}
	public void openFileOperation(){
		operationType = "openFile";
		fileOperation();
	}
	
	public void newFileOperation(){
		operationType = "newFile";
		fileOperation();
	}
	public void exitFileOperation(){
		operationType = "exitFile";
		fileOperation();
	}
	/*public void closingOperation(){
		operationType = "exitFile";
		fileOperation();
	}*/
	
	
	public void newFile() {
		taUpdateFlag = false;
		fileName = "Untitled";
		filePath = null;
		ta.setText("");
		//System.out.println("hhere22");
		
		String title = fileName.replace(".txt", "") + " - "+ "Notepad";
		frm.setTitle(title);
		String labelString = "Do you want to set the changes to "+fileName+"?";
		middleLabel.setText(labelString);
		
	}
	
	public void openFile() {
		
		if(fileChooser == null)
			fileChooser = new JFileChooser();
		int i=fileChooser.showOpenDialog(frm);
		if(i == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			filePath = file.getPath();
			fileName = file.getName();
			String readFile = null;

			try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				ta.setText("");
				do {
					if(readFile == null) {
						readFile = br.readLine();
						ta.append(readFile);
					}
					else {
						readFile = br.readLine();
						if(readFile != null) {
							readFile += "\n";
							ta.append(readFile);
						}
					}
				}while(readFile != null);
				
			}catch(IOException exc) {
				exc.printStackTrace();
			}
			//ta.setText(readFile);
			//System.out.println(readFile);
		}
		String title = fileName.replace(".txt", "") + " - "+ "Notepad";
		frm.setTitle(title);
		String labelString = "Do you want to set the changes to "+fileName+"?";
		middleLabel.setText(labelString);
		taUpdateFlag = false;
	}
	
	public boolean saveFile() {
		if(fileName == "Untitled" || filePath == null) {
			if(saveAsFile()== true) {
				taUpdateFlag = false;
				return true;
			}
			else
				return saveAsFile();
			//return true;
			
		}
		else {
			//System.out.println("irfje");
			try(FileWriter fw = new FileWriter(filePath)){
				String s = ta.getText();
				fw.write(s);
			}
			catch(IOException exc) {
				
			}
			String title = fileName.replace(".txt", "") + " - "+ "Notepad";
			frm.setTitle(title);
			String labelString = "Do you want to set the changes to "+fileName+"?";
			middleLabel.setText(labelString);
			taUpdateFlag = false;
			
			return true;
			
		}
		
		
	}
	
	public boolean saveAsFile() {
		if(fileChooser == null) {
			fileChooser = new JFileChooser();
		}
		int i = fileChooser.showSaveDialog(frm);
		if(i == JFileChooser.APPROVE_OPTION) {
			File file1 = fileChooser.getSelectedFile();
			String fileName1, filePath1 ;
			fileName1 = file1.getName();
			filePath1 = file1.getPath();
			int a = 0;
			if(file1.exists()) {
				 a =JOptionPane.showConfirmDialog(fileChooser, fileName+" already exist. \nDo you want to replace it?");
			}
			if(a == JOptionPane.YES_OPTION) {
				//System.out.println("hi");
				file = file1;
				fileName = fileName1;
				filePath = filePath1;
				try(FileWriter fw = new FileWriter(filePath)){
					String s = ta.getText();
					fw.write(s);
				}
				catch(IOException exc) {
					
				}
				String title = fileName.replace(".txt", "") + " - "+ "Notepad";
				frm.setTitle(title);
				String labelString = "Do you want to set the changes to "+fileName+"?";
				middleLabel.setText(labelString);
				
				taUpdateFlag = false;
				return true;
			}
			else {
				return false;
			}
		}
		else
			return false;
		
			
	}
	
	
	
	void initlizeActionBox() {
		middlePane = new JPanel();
		middlePane.setLayout(new GridLayout(2,1));
		middlePane.add(new JLabel(" "));
		middleLabel = new JLabel();
		middleLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
		//middleLabel.setFont("", Font.BOLD, 16);
		middlePane.add(middleLabel);
		southPane = new JPanel(new FlowLayout());
		southPane.add(new JLabel(" "));
		save = new JButton("Save");
		dontSave = new JButton("Don't save");
		cancel = new JButton("Cancel");
		southPane.add(save);
		southPane.add(dontSave);
		southPane.add(cancel);
		panel = new JPanel(new GridLayout(3,1));
		panel.add(middlePane);
		panel.add(new JLabel(" "));
		panel.add(southPane);
		panel.setVisible(true);
		
		//frm.addWindowListener(frmClose);/*(new WindowAdapter() {
		/*	
			@Override
			public void windowClosing(WindowEvent e) {
				operationType = "exitFile";
				fileOperation();
				System.out.println("hii");
			}
			
		});*/
	
	}
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public void showDialog(Component parent) {
		Frame owner=null;
		if(parent instanceof Frame) 
			owner=(Frame)parent;
		else
			owner=(Frame)SwingUtilities.getAncestorOfClass(Frame.class,parent);
		if(dialog==null || dialog.getOwner()!=owner)
			{
			dialog=new JDialog(owner,false);
			String labelString = "Do you want to set the changes to "+fileName+"?";
			middleLabel.setText(labelString);
			dialog.getContentPane().add(panel);
			dialog.getRootPane().setDefaultButton(cancel);
			}
		dialog.setSize(350,200);
		dialog.setTitle("Notepad");
		dialog.setVisible(true);
		
	}

	
}
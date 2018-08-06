import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.CannotRedoException;
import javax.swing.JButton;

class Notepad implements ActionListener {
// Menu items...
	final String fileMenu = "File";
	final String editMenu = "Edit";
	final String formatMenu = "Format";
	final String viewMenu = "View";
	final String helpMenu = "Help";
	final String newFile = "New";
	final String openFile = "Open...";
	final String saveFile = "Save";
	final String saveAsFile = "Save As...";
	final String pageSetUpFile = "Page Setup...";
	final String printFile = "Print...";
	final String exitFile = "Exit";
	final String unDoEdit = "Undo";
	final String cutEdit = "Cut";
	final String copyEdit = "Copy";
	final String pasteEdit = "Paste";
	final String deleteEdit = "Delete";
	final String findEdit = "Find...";
	final String findNextEdit = "Find Next";
	final String replaceEdit = "Replace...";
	final String goToEdit = "GoTo...";
	final String selectAllEdit = "Select All";
	final String timeDateEdit = "Time/Date";
	final String wordWrapFormat = "Word Wrap";
	final String fontFormat = "Font...";
	final String statusBarView = "Status Bar ";
	final String viewHelpHelp = "View Help";
	final String aboutNotepadHelp = "About Notepad";
	
	
	String fileName = "Untitled";
	String applicationName = "Notepad";
	JFrame frm;
	JTextArea ta;
	JLabel statusBar;
	
	////////
		JMenuBar mb;
		JMenu newFileMenu;
		JMenu newEditMenu;
		JMenu newFormatMenu;
		JMenu newViewMenu;
		JMenu newHelpMenu;
		JMenuItem New;
		JMenuItem Open;
		JMenuItem Save;
		JMenuItem SaveAs;
		JMenuItem PageSetUp;
		JMenuItem Print;
		JMenuItem Exit;
		JMenuItem Undo;
		JMenuItem Cut;
		JMenuItem Copy;
		JMenuItem Paste;
		JMenuItem Delete;
		JMenuItem Find;
		JMenuItem FindNext;
		JMenuItem Replace;
		JMenuItem Goto;
		JMenuItem SelectAll;
		JMenuItem DateTime;
		JCheckBoxMenuItem WordWrap;
		JMenuItem Font;
		JCheckBoxMenuItem Status ;
		JMenuItem ViewHelp;;
		JMenuItem About;
	////////
	
	
	
//Constructor
	Notepad(){
		frm = new JFrame(fileName+" - "+applicationName);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		statusBar = new JLabel("Ln 1, Col 1",JLabel.RIGHT);
		ta = new JTextArea(30,60);
		frm.add(new JScrollPane(ta),BorderLayout.CENTER);
		frm.add(statusBar,BorderLayout.SOUTH);
		frm.add(new JLabel("  "),BorderLayout.EAST);  
		frm.pack();
		frm.setVisible(true);
		createMenu(frm);
	//For current cursur position
		ta.addCaretListener(new CaretListener()  {
			public void caretUpdate(CaretEvent e){
				int position = 0;
				int lineNumber = 0;
				int col = 0;
				try{
					position = ta.getCaretPosition();
					lineNumber = ta.getLineOfOffset(position);
					col = position - ta.getLineStartOffset(lineNumber);
					
				}
				catch(Exception exc){}
				if(ta.getText().length() == 0){
					lineNumber = 0;
					col = 0;
				}
				statusBar.setText("Ln "+(lineNumber+1) +", Col "+(col+1));
			}
			
		});
	}
	
// action performed event listener method
	public void actionPerformed(ActionEvent e){
		//for undo operation
		if(e.getSource() == Undo){
			
			UndoManager undoManager = new UndoManager();
			ta.getDocument().addUndoableEditListener(
				new UndoableEditListener() {
				  public void undoableEditHappened(UndoableEditEvent e) {
					undoManager.addEdit(e.getEdit());
					//updateButtons();
				  }
				});
			Undo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){ 
				try {
					undoManager.undo();
				} catch (CannotRedoException cre) {
					cre.printStackTrace();
				}
			}});
        //updateButtons();*/
		//undoManager.undo();
		}
		// for cut operation
		if(e.getSource() == Cut){
			try {
					ta.cut();
				}catch (CannotRedoException cre) {
					cre.printStackTrace();
				}
		}
		// for copy operation
		if(e.getSource() == Copy){
			try {
					ta.copy();
				}catch (CannotRedoException cre) {
					cre.printStackTrace();
				}
		}
		// for paste operation
		if(e.getSource() == Paste){
			try {
					ta.paste();
				}catch (CannotRedoException cre) {
					cre.printStackTrace();
				}
		}
		// for delete operation
		if(e.getSource() == Delete){
			try {
					ta.replaceSelection("");
				}catch (CannotRedoException cre) {
					cre.printStackTrace();
				}
		}
		// for selectAll
		if(e.getSource() == SelectAll){
			try {
					ta.selectAll();
				}catch (CannotRedoException cre) {
					cre.printStackTrace();
				}
		}
		
		
	}
	
// to add menu item on menubar
	JMenu createNewMenu(String menuName){
		JMenu newMenu = new JMenu(menuName);
		return newMenu;
	}
// to add menu item on menu	
	JMenuItem createMenuItem(String menuItemName, ActionListener al, int keyAcc){
		JMenuItem newMenuItem = new JMenuItem(menuItemName);
		newMenuItem.addActionListener(al);
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(keyAcc,ActionEvent.CTRL_MASK));
		return newMenuItem;
	}
	JMenuItem createMenuItemFunKey(String menuItemName, ActionListener al, int keyAcc){
		JMenuItem newMenuItem = new JMenuItem(menuItemName);
		newMenuItem.addActionListener(al);
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(keyAcc,0));
		return newMenuItem;
	}
	
	JMenuItem createMenuItem(String menuItemName, ActionListener al){
		JMenuItem newMenuItem = new JMenuItem(menuItemName);
		newMenuItem.addActionListener(al);
		
		return newMenuItem;
	}
	
	JCheckBoxMenuItem createCheckBoxMenuItem(String menuItemName, ActionListener al){
		JCheckBoxMenuItem newMenuItem = new JCheckBoxMenuItem(menuItemName);
		newMenuItem.addActionListener(al);
		return newMenuItem;
	}
	

	void createMenu(JFrame f){
		
		mb = new JMenuBar();
		newFileMenu = createNewMenu(fileMenu);
		newEditMenu = createNewMenu(editMenu);
		newFormatMenu = createNewMenu(formatMenu);
		newViewMenu = createNewMenu(viewMenu);
		newHelpMenu = createNewMenu(helpMenu);
		
		
		//for file menu
		New = createMenuItem(newFile, this, KeyEvent.VK_N);
		newFileMenu.add(New);
		Open = createMenuItem(openFile, this, KeyEvent.VK_O);
		newFileMenu.add(Open);
		Save = createMenuItem(saveFile, this, KeyEvent.VK_S);
		newFileMenu.add(Save);
		SaveAs = createMenuItem(saveAsFile, this);
		newFileMenu.add(SaveAs);
		newFileMenu.addSeparator();
		PageSetUp = createMenuItem(pageSetUpFile, this);
		newFileMenu.add(PageSetUp);
		Print = createMenuItem(printFile, this, KeyEvent.VK_P);
		newFileMenu.add(Print);
		newFileMenu.addSeparator();
		Exit = createMenuItem(exitFile, this);
		newFileMenu.add(Exit);
		//newFileMenu.addSeparator();
		
		//for edit menu
		Undo = createMenuItem(unDoEdit, this, KeyEvent.VK_Z);
		Undo.setEnabled(false);
		newEditMenu.add(Undo);
		newEditMenu.addSeparator();
		Cut = createMenuItem(cutEdit, this, KeyEvent.VK_X);
		Cut.setEnabled(false);
		newEditMenu.add(Cut);
		Copy = createMenuItem(copyEdit, this, KeyEvent.VK_C);
		Copy.setEnabled(false);
		newEditMenu.add(Copy);
		Paste = createMenuItem(pasteEdit, this, KeyEvent.VK_V);
		Paste.setEnabled(false);
		newEditMenu.add(Paste);
		Delete = createMenuItem(deleteEdit, this,KeyEvent.VK_DELETE);
		Delete.setEnabled(false);
		newEditMenu.add(Delete);
		newEditMenu.addSeparator();
		Find = createMenuItem(findEdit, this, KeyEvent.VK_F);
		Find.setEnabled(false);
		newEditMenu.add(Find);
		FindNext = createMenuItemFunKey(findNextEdit, this, KeyEvent.VK_F3);
		FindNext.setEnabled(false);
		newEditMenu.add(FindNext);
		Replace = createMenuItem(replaceEdit, this, KeyEvent.VK_H);
		newEditMenu.add(Replace);
		Goto = createMenuItem(goToEdit, this, KeyEvent.VK_G);
		newEditMenu.add(Goto);
		newEditMenu.addSeparator();
		SelectAll = createMenuItem(selectAllEdit, this, KeyEvent.VK_A);
		newEditMenu.add(SelectAll);
		DateTime = createMenuItemFunKey(timeDateEdit, this, KeyEvent.VK_F5);
		newEditMenu.add(DateTime);
		//newEditMenu.addSeparator();
		
		//for format menu
		WordWrap = createCheckBoxMenuItem(wordWrapFormat, this);
		newFormatMenu.add(WordWrap);
		Font = createMenuItem(fontFormat, this);
		newFormatMenu.add(Font);
		
		//for view menu
		Status = createCheckBoxMenuItem(statusBarView, this);
		newViewMenu.add(Status);
		
		//for help menu
		ViewHelp = createMenuItem(viewHelpHelp, this);
		newHelpMenu.addSeparator();
		newHelpMenu.add(ViewHelp);
		About = createMenuItem(aboutNotepadHelp, this);
		newHelpMenu.add(About);
		//adding menu listener to editmenu
		
		newEditMenu.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e){
				if(ta.getText().length()==0){
					Delete.setEnabled(false);
					Find.setEnabled(false);
					FindNext.setEnabled(false);
					Cut.setEnabled(false);
					Copy.setEnabled(false);
				}
				else{
					Find.setEnabled(true);
					FindNext.setEnabled(true);
					
				}
				if(ta.getSelectionStart() == ta.getSelectionEnd()){
					Cut.setEnabled(false);
					Copy.setEnabled(false);
					Delete.setEnabled(false);
				}
				else{
					Cut.setEnabled(true);
					Copy.setEnabled(true);
					Delete.setEnabled(true);
					Undo.setEnabled(true);
					Paste.setEnabled(true);
				}
				if(ta.getText().length()>0){
					Undo.setEnabled(true);
				}
				
			}
			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});
		
		
		mb.add(newFileMenu);
		mb.add(newEditMenu);
		mb.add(newFormatMenu);
		mb.add(newViewMenu);
		mb.add(newHelpMenu);
		
		
		
		
		
		f.setJMenuBar(mb);
	}
	
	
	
	
// main method	
	public static void main(String[] args){
		SwingUtilities.invokeLater( new Runnable(){
			public void run(){
				new Notepad();
			}
			
		});
	}
}
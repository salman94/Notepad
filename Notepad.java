import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.event.*;
import javax.swing.undo.UndoManager;
import javax.swing.undo.CannotRedoException;
import java.text.SimpleDateFormat;   
import java.util.Date; 
import findDialog.FindDialog;
import font.FontChooser;
import aboutNotepad.AboutNotepad;
import fileOperation.FileOperation;

class Notepad implements ActionListener, Printable {
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
	FindDialog findReplaceDialog = null;
	FontChooser fontDialog = null;
	FileOperation fileOperation = null;
	PrinterJob job = null;
	PageFormat pageFormat = null;
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
		frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		statusBar = new JLabel("Ln 1, Col 1",JLabel.RIGHT);
		ta = new JTextArea(30,60);
		frm.add(new JScrollPane(ta),BorderLayout.CENTER);
		frm.add(statusBar,BorderLayout.SOUTH);
		frm.add(new JLabel("  "),BorderLayout.EAST);  
		frm.pack();
		frm.setVisible(true);
		
		createMenu(frm);
		
		frm.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(fileOperation == null)
					fileOperation = new FileOperation(frm, ta, fileName);
				fileOperation.exitFileOperation();
			}
			
		});
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
	public void goTo(){
		try{
		int position = ta.getCaretPosition();
		int lineNumber = ta.getLineOfOffset(position);
		lineNumber++;
		int totalPosition = ta.getText().length();
		int totalLineNumber = ta.getLineOfOffset((totalPosition))+1;
		String directionLine = JOptionPane.showInputDialog(null, "Line number:",lineNumber);
		int inputlineNumber = Integer.parseInt(directionLine);
		if(inputlineNumber <= totalLineNumber){
			inputlineNumber = ta.getLineStartOffset((inputlineNumber-1));
			ta.setCaretPosition(inputlineNumber);
		}
		else{
			JOptionPane.showMessageDialog(null,"The line nymber is beyond the total number of lines.","Notepad - Goto Line",JOptionPane.PLAIN_MESSAGE);
		}
		
		}catch(Exception exc){
			
		}
	}
	
	public void actionPerformed(ActionEvent e){
		//for undo operation
		if(e.getSource() == Undo){
			
			UndoManager undoManager = new UndoManager();
			ta.getDocument().addUndoableEditListener(
				new UndoableEditListener() {
				  public void undoableEditHappened(UndoableEditEvent ee) {
					undoManager.addEdit(ee.getEdit());
					//updateButtons();
				  }
				});
			
			Undo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent eee){ 
				try{
					if(undoManager.canUndo()){
						
						undoManager.undo();
					}
				}
				catch(Exception  ex){
					ex.printStackTrace();
				}
				}});
        //updateButtons();*/
		//undoManager.undo();
		}
		// for cut operation
		if(e.getSource() == Cut){
			try {
					ta.cut();
				}
			catch(Exception ex){
					ex.printStackTrace();
				}
		}
		// for copy operation
		if(e.getSource() == Copy){
			try {
					ta.copy();
				}
			catch(Exception ex){
					ex.printStackTrace();
				}
		}
		// for paste operation
		if(e.getSource() == Paste){
			try {
					ta.paste();
				}
			catch(Exception ex){
					ex.printStackTrace();
				}
		}
		// for delete operation
		if(e.getSource() == Delete){
			try {
					ta.replaceSelection("");
				}
			catch(Exception ex){
					ex.printStackTrace();
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
		if(e.getSource() == DateTime){
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd-MM-yyyy");
			String strDate = formatter.format(date);
			ta.insert(strDate,ta.getCaretPosition());
		}
		if(e.getSource() == Goto){
			goTo();
		}
		if(e.getSource() == Find){
			
			if(ta.getText().length() == 0)
				return;
			if(findReplaceDialog == null)
				findReplaceDialog = new FindDialog(ta);
			findReplaceDialog.showDialog(frm,true);
			
		}
		
		if(e.getSource() == FindNext) {
			if(ta.getText().length() == 0)
				return;
			if(findReplaceDialog == null) {
				findReplaceDialog = new FindDialog(ta);
				findReplaceDialog.showDialog(frm, true);
			}
			findReplaceDialog.findNext();
			
		}
		if(e.getSource() == Replace) {
			if(findReplaceDialog == null) {
				findReplaceDialog = new FindDialog(ta);
			}
			findReplaceDialog.showDialog(frm, false);
			findReplaceDialog.replaceNext();
		}
		if(e.getSource() == WordWrap) {
			if(WordWrap.isSelected()) {
				ta.setLineWrap(true);
			}
			else {
				ta.setLineWrap(false);
			}
		}
		if(e.getSource() == Font) {
			
			if(fontDialog==null)
				fontDialog=new FontChooser(ta);

			fontDialog.showDialog(Notepad.this.frm);
		}
		if(e.getSource() == Status) {
			if(Status.isSelected())
				statusBar.setVisible(true);
			else
				statusBar.setVisible(false);
		}
		if(e.getSource() == ViewHelp) {
			///public static void openWebpage(String urlString) {
			String urlString = "https://answers.microsoft.com/en-us/windows/forum?"
								+ "sort=LastReplyDate&dir=Desc&tab=All&status=all&mod=&modAge=&advFil=&postedAfter=&postedBefore=&"
									+ "threadType=All&isFilterExpanded=false&page=1";
			    try {
			        Desktop.getDesktop().browse(new URL(urlString).toURI());
			    } catch (Exception ee) {
			        ee.printStackTrace();
			    }
		//	}
			
		}
		if(e.getSource() == About) {
			AboutNotepad abt = new AboutNotepad();
			abt.showDialog(frm);
			
		}
		
		
		if(e.getSource() == Open) {
			if(fileOperation == null)
				fileOperation = new FileOperation(frm, this.ta, this.fileName);
			fileOperation.openFileOperation();
			
		}
		if(e.getSource() == New) {
			if(fileOperation == null)
				fileOperation = new FileOperation(frm, this.ta, this.fileName);
			fileOperation.newFileOperation();
			
		}
		if(e.getSource() == SaveAs) {
			if(fileOperation == null)
				fileOperation = new FileOperation(frm, this.ta, this.fileName);
			fileOperation.saveAsFile();
			
		}
		if(e.getSource() == Save) {
			if(fileOperation == null)
				fileOperation = new FileOperation(frm, this.ta, this.fileName);
			fileOperation.saveFile();
			
		}
		if(e.getSource() == Exit) {
			if(fileOperation == null)
				fileOperation = new FileOperation(frm, this.ta, this.fileName);
			fileOperation.exitFileOperation();
			
		}
		if(e.getSource() == PageSetUp) {
			if(job == null)
				job = PrinterJob.getPrinterJob();
			pageFormat  = job.pageDialog(job.defaultPage());
			
		}
		if(e.getSource() == Print) {
			if(job == null)
			 job = PrinterJob.getPrinterJob();
			//System.out.println("Up");
			job.setPrintable(this);
			boolean ok = job.printDialog();
	         if (ok) {
	             try {
	            	// System.out.println("Down");
	                  job.print();
	             } catch (PrinterException ex) {
	              /* The job did not successfully complete */
	             }
	         }
		}
		
		
		
	}
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

		if (page > 0) { /* We have only one page, and 'page' is zero-based */
		return NO_SUCH_PAGE;
		}
		/* User (0,0) is typically outside the imageable area, so we must
		* translate by the X and Y values in the PageFormat to avoid clipping
		*/
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		/* Now we perform our rendering */
		String printContent = ta.getText();
		g.drawString(printContent, 100, 100);
		
		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
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
		Status.doClick();
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
					Goto.setEnabled(false);
				}
				else{
					Find.setEnabled(true);
					FindNext.setEnabled(true);
					Goto.setEnabled(true);
					
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
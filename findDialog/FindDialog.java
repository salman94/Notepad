package findDialog;
//import javax.swing.Border.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.border.*;


public class FindDialog  implements ActionListener{
	//public FindDialog(){};
	static JTextArea jta;
	JTextField findWhat = null, replaceWith = null ;
	JButton findNextButton, cancelButton, replaceButton, replaceAllButton;
	JCheckBox matchCase;
	JRadioButton up,down;
	JLabel jlbl;
	ButtonGroup bg;
	JDialog dialog;
	JPanel direction, checkPanel, buttonPanel, textPanel, panel;
	// for frame 
	JTextArea ta;
	FindDialog(){};
	public FindDialog(JTextArea jjta){
		this.ta = jjta;
		
		findWhat = new JTextField(10);
		findWhat.setHorizontalAlignment(SwingConstants.LEFT);
		
		replaceWith = new JTextField(10);
		replaceWith.setHorizontalAlignment(SwingConstants.LEFT);
		findNextButton = new JButton("Find next");
		cancelButton = new JButton("Cancel");
		replaceButton = new JButton("Replace");
		replaceAllButton = new JButton("Replace All");
		matchCase = new JCheckBox("Match case");
		up = new JRadioButton("Up");
		down = new JRadioButton("Down");
		bg = new ButtonGroup();
		bg.add(up);
		bg.add(down);
		Border etched = BorderFactory.createEtchedBorder();
		Border titled = BorderFactory.createTitledBorder(etched,"Direction");
		direction = new JPanel();
		direction.setBorder(titled);
		direction.setLayout(new GridLayout(1,2));
		direction.add(up);
		direction.add(down);
		
		checkPanel = new JPanel();
		checkPanel.setLayout(new GridLayout(1,2));
		checkPanel.add(matchCase);
		checkPanel.add(direction);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4,1));
		buttonPanel.add(findNextButton);
		buttonPanel.add(replaceButton);
		buttonPanel.add(replaceAllButton);
		buttonPanel.add(cancelButton);
		
		textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(3,2));
		textPanel.add(new JLabel("Find what: "));
		textPanel.add(findWhat);
		jlbl = new JLabel("Replace with:");
		textPanel.add(jlbl);
		textPanel.add(replaceWith);
		textPanel.add(new JLabel(" "));
		textPanel.add(new JLabel(" "));
		
		panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("       "),BorderLayout.NORTH);
		
		panel.add(textPanel,BorderLayout.CENTER);
		panel.add(buttonPanel,BorderLayout.EAST);
		panel.add(checkPanel,BorderLayout.SOUTH);
		panel.setSize(200,200);
		//panel.setVisible(true);
		
		//JFrame frm = new JFrame("frame");
		//frm.add(panel);
		//frm.pack();
		//frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frm.setSize(300,300);
		//frm.setVisible(true);
		findNextButton.addActionListener(this);
		replaceAllButton.addActionListener(this);
		replaceButton.addActionListener(this);
		cancelButton.addActionListener(this);
		findWhat.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent e){
				enableAndDisableButton();
			}
		});
		findWhat.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				enableAndDisableButton();
			}
		});
		
		
	}
	public void textValueChanged(Action te){
			enableAndDisableButton();
		}
	public void enableAndDisableButton(){
		if(findWhat.getText().length() == 0){
			findNextButton.setEnabled(false);
			replaceButton.setEnabled(false);
			replaceAllButton.setEnabled(false);
		}
		else{
			findNextButton.setEnabled(true);
			replaceButton.setEnabled(true);
			replaceAllButton.setEnabled(true);
		}
	}
	
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == cancelButton){
			dialog.setVisible(false);
		}
		if(e.getSource() == findNextButton){
			findNext();
		}
		if(e.getSource() == replaceButton){
			replaceNext();
		}
		if(e.getSource() == replaceAllButton){
			replaceAllNext();
		}
		
	}
	
	public int find(){
		int index = -1;
		String toFindWhat = findWhat.getText();
		String whereToFind = ta.getText();
		int currentIndex = ta.getCaretPosition();
		int startSelection = ta.getSelectionStart();
		int endSelection = ta.getSelectionEnd();
		if(up.isSelected()) {
			if(startSelection != endSelection)
				currentIndex = startSelection-1;
			if(matchCase.isSelected()) {
				index = whereToFind.lastIndexOf(toFindWhat,currentIndex);
			}
			else {
				index = whereToFind.toUpperCase().lastIndexOf(toFindWhat.toUpperCase(),currentIndex );
			}
		}
		else {
			if(startSelection != endSelection)
				currentIndex = startSelection+1;
			if(matchCase.isSelected()) {
				index = whereToFind.indexOf(toFindWhat,currentIndex);
			}
			else {
				index = whereToFind.toUpperCase().indexOf(toFindWhat.toUpperCase(),currentIndex );
			}
		}
		
		
		return index;
	}
	public void findNext() {
		int index = find();
		if(index != -1) {
			ta.setSelectionStart(index);
			ta.setSelectionEnd(index + findWhat.getText().length());
		}
		else {
			JOptionPane.showMessageDialog(null,"Can not Find","Notepad",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void replaceNext(){
		int index = find();
		int startSelection = ta.getSelectionStart();
		int endSelection = ta.getSelectionEnd();
		if(startSelection != endSelection) {
			ta.replaceSelection(replaceWith.getText());
			
		}
		if(index != -1) {
			index = find();
			ta.setSelectionStart(index);
			ta.setSelectionEnd(index+findWhat.getText().length());
		}
		else {
			JOptionPane.showMessageDialog(null, "Can not Find", "Notepad", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void replaceAllNext(){
		ta.setCaretPosition(0);
		int index = find();
		if(index == -1) {
			JOptionPane.showMessageDialog(null, "Can not Find", "Notepad", JOptionPane.ERROR_MESSAGE);
			return;
		}
		while(index != -1) {
			ta.replaceRange(replaceWith.getText(), index, index+findWhat.getText().length());
			ta.setCaretPosition(index+replaceWith.getText().length());
			index = find();
		}
		
	}
	
	
	public void showDialog(Component parent, boolean isFind){
		Frame owner=null;
		if(parent instanceof Frame) 
			owner=(Frame)parent;
		else
			owner=(Frame)SwingUtilities.getAncestorOfClass(Frame.class,parent);
		if(dialog==null || dialog.getOwner()!=owner)
			{
			dialog=new JDialog(owner,false);
			dialog.getContentPane().add(panel);
			dialog.getRootPane().setDefaultButton(findNextButton);
			}

			if(findWhat.getText().length()==0){
				findNextButton.setEnabled(false);
				replaceButton.setEnabled(false);
				replaceAllButton.setEnabled(false);
			}
				
			else
				findNextButton.setEnabled(true);

			replaceButton.setVisible(false);
			replaceAllButton.setVisible(false);
			replaceWith.setVisible(false);
			jlbl.setVisible(false);

			if(isFind)
			{
			//card.show(buttonPanel,"find");
			dialog.setSize(460,180);
			dialog.setTitle("Find");
			}
			else
			{
			replaceButton.setVisible(true);
			replaceAllButton.setVisible(true);
			replaceWith.setVisible(true);
			jlbl.setVisible(true);

			//card.show(buttonPanel,"replace");
			dialog.setSize(450,200);
			dialog.setTitle("Replace");
			}

			dialog.setVisible(true);
		
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				JFrame frm = new JFrame("frame");
				 jta = new JTextArea(3,10);
				jta.setText("Hi My name is Mohd Salman and I am Java Fullstack developer. My Purpose is to become");
				frm.add(jta);
				frm.pack();
				frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frm.setSize(300,300);
				
				frm.setVisible(true);
				FindDialog findDialog = new FindDialog();
				findDialog.showDialog(frm,false);
				
				//frm.showDialog(frm,true);
			}
		});
	}
}
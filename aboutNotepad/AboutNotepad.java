package aboutNotepad;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Frame;

public class AboutNotepad implements ActionListener{
	JButton ok;
	JLabel lbl;
	JPanel textPanel, centerPanel, southPanel, topPanel;
	JLabel panel;
	JPanel centerTextPanel;
	JDialog dialog = null;

	
	
	public AboutNotepad() {
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,3));
		topPanel.add(new JLabel(""));
		JLabel label = new JLabel("Notepad");
		label.setFont(new Font("Arial", Font.BOLD, 18));
		topPanel.add(label);
		topPanel.add(new JLabel(""));
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		String s = "This Notepad is created by Mohd Salman. He has used Eclipse IDE. If you find any bug. Please do report at salmanmnnitece@gmail.com";
		JLabel label2 = new JLabel("<html>"+s+"</html>");
		label2.setFont(new Font("Arial", Font.PLAIN, 14));
		centerPanel.add(label2, BorderLayout.CENTER);
		centerPanel.add(new JLabel(" "), BorderLayout.EAST);
		centerPanel.add(new JLabel(" "), BorderLayout.WEST);
		centerPanel.add(new JLabel(" "), BorderLayout.SOUTH);
		centerPanel.add(new JLabel(" "), BorderLayout.NORTH);
		
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		ok = new JButton("OK");
		southPanel.add(ok);
		
		
		panel = new JLabel();
		panel.setLayout(new BorderLayout());
		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dialog.setVisible(false);
				
			}
		}); 
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
			dialog.getContentPane().add(panel);
			dialog.getRootPane().setDefaultButton(ok);
			}
		dialog.setSize(450,350);
		dialog.setTitle("Font");
		dialog.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

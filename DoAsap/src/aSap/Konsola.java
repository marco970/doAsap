package aSap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Konsola extends JPanel {
	
	public Konsola(String a)	{
		super();
		
		//JFrame backFrame = new JFrame("panel boczny");
		
		setSize(600, 500);
		//add(backFrame);
		add(new JLabel(a));
	}
}

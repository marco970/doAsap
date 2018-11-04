package aSap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class NewForm implements  ActionListener, FocusListener {
	
	private MainTableModel model;
	private int rowNr;
	private JFrame newFrame;
	private JPanel contentPane;		//bo potrzebujemy tego w metodach
	private JPanel panel;			//jw
	private JTextField poleZZ;
	private JLabel poleZZlab;
	private JLabel errZZLab;
	
	boolean[] draw = {false, false, false, false, false, false, false}; 
	HashMap drawn = new HashMap();
	
	//przyciski
	private JButton btnSave = new JButton("Zapisz");
	private JButton btnCancel = new JButton("Anuluj");
	private JButton btnNext = new JButton("Dalej");
	private JButton btnBack = new JButton("Powrót");
	
	public NewForm(int rowNr, MainTableModel mod)	{
		
		this.model = mod;
		this.rowNr = rowNr;
		//ramka
		newFrame = new JFrame("Nowy ZZ");
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newFrame.setVisible(true);
		newFrame.setBounds(100, 100, 450, 600);
		//panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		newFrame.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		panel = new JPanel();
		contentPane.add(panel, "cell 0 0, grow");
		panel.setLayout(new MigLayout("", "[][][]", "[][][]"));
		//tytuł ramki
		JLabel title = new JLabel("Dodaj nowy wniosek");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		//przyciski
		btnSave.addActionListener(this);
		btnCancel.addActionListener(this);
		//btnNext.setText("Dalej");
		btnNext.addActionListener(this);
		btnBack.addActionListener(this);
		//pole ZZ
		poleZZ = new JTextField(13);
		poleZZlab = new JLabel();
		poleZZlab.setHorizontalAlignment(SwingConstants.LEFT);
		poleZZlab.setVisible(true);
		//poleZZlab.setVisible(false);
		poleZZ.addFocusListener(this);
		JLabel labZZ = new JLabel(model.getColumnName(0));
		//komunikat o błędzie
		errZZLab = new JLabel();
		errZZLab.setHorizontalAlignment(SwingConstants.LEFT);
		errZZLab.setForeground(Color.RED);
		errZZLab.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		panel.add(title, "dock north");
		panel.add(labZZ, "cell 0 1");
		panel.add(poleZZ, "cell 1 1");
		//panel.add(poleZZlab, "cell 1 1");
		panel.add(errZZLab, "cell 2 1");
		
		
		
		contentPane.add(btnCancel, "cell 0 1");
		contentPane.add(btnBack, "cell 0 1");
		contentPane.add(btnNext, "cell 0 1");
		contentPane.add(btnSave, "cell 0 1");
		btnSave.setEnabled(false);
		btnBack.setVisible(false);
		
		//tworzenie HashMapy
		
		
		
	}//koniec konstruktora
	//metody pomocnicze
	public void elReplace(Component added, Component removed, JPanel p, String migTarget)	{
		p.remove(removed);
		p.add(added,migTarget);
	}
	public void addOneTime(Component a, int indexColName, JPanel p, int migRow)	{
		Component[] componentArray = p.getComponents();
		boolean check = false;
		/*
		for (Component el: componentArray)	{
			if(!el.equals(a)) check = true;
			System.out.println("---"+el.toString());
		}
		System.out.println(check+">>>"+a.toString());
		*/
		if(!panel.isAncestorOf(a))	{
			p.add(new JLabel(model.getColumnName(indexColName)), "cell 0 "+migRow+"");
			p.add(a, "cell 1 "+migRow+"");
		}
		//if(!panel.isAncestorOf(a)) System.out.println("&&&"+a.toString());
	}
	
	
	@Override
	public void focusGained(FocusEvent eFg) {
		btnNext.setEnabled(true);
		btnNext.setText("Dalej");
		btnSave.setEnabled(false);
		
	}

	@Override
	public void focusLost(FocusEvent eFg) {
		String gotZZ = poleZZ.getText();
		int lengthZZ = gotZZ.length();
		SingleFieldValidator zzVal = new SingleFieldValidator("ZZ", gotZZ, model);
		errZZLab.setText(zzVal.getErrMessage());
		if (!zzVal.getValidationResult())	{//jeśli walidacja negatywna
			//errZZLab.setText(zzVal.getErrMessage());
			btnNext.setEnabled(false);
			poleZZ.requestFocus();
			//btnNext.setText("Cofnij");
			//System.out.println("asd"+zzVal.getErrMessage());
			//errZZLab.setText("asd"+zzVal.getErrMessage());
			
		}
		else {
			//errZZLab.setText("");
			btnNext.setEnabled(false);
			poleZZlab.setText(gotZZ);
			elReplace(poleZZlab, poleZZ, panel, "cell 1 1");
			//JLabel poleStatlab = new JLabel("open");
			addOneTime(new JLabel("open"), 4, panel, 2);
			//panel.add(new JLabel(model.getColumnName(4)),"cell 0 2");
			//panel.add(poleStatlab,"cell 1 2");
			
			btnSave.setEnabled(true);
			btnBack.setVisible(true);
		}


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command= e.getActionCommand();
		if(command.equals("Anuluj"))	{
			newFrame.setVisible(false);
		}
		//if(command.equals("Cofnij"))	poleZZ.requestFocus();
		if(command.equals("Powrót"))	{
			btnBack.setVisible(false);
			poleZZ.setText(poleZZ.getText());
			elReplace(poleZZ, poleZZlab, panel, "cell 1 1");
			poleZZ.requestFocus();
			//dodać status 

			//dodać przedmiot
			
			//dodać dostawca
			//dodać nazwę 
			//dodać spółkę
			
			
		}
	}

}

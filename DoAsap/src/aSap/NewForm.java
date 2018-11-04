package aSap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	private JLabel spolkaPole;
	
	boolean[] draw = {false, false, false, false, false, false, false}; 
	HashMap drawMap = new HashMap();
	ArrayList<Component> listaComp = new ArrayList<Component>(); //lista komponentów do visible
	
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
		
		//tworzenie HashMapy - o ile będzie potrzebna
		//tworzenie pozostałych elementów
		
		//dodać status 
		JLabel statusPolelab = new JLabel(model.getColumnName(4));
		listaComp.add(statusPolelab);
		JLabel statusPole = new JLabel("open");
		listaComp.add(statusPole);
		panel.add(statusPolelab,"cell 0 2");
		panel.add(statusPole,"cell 1 2");
		//dodać przedmiot
		JLabel przemiotPolelab = new JLabel(model.getColumnName(5));
		listaComp.add(przemiotPolelab);
		JTextArea przedmiotTa = new JTextArea(3,15);
		JScrollPane scrl = new JScrollPane(przedmiotTa);
		listaComp.add(scrl);
		panel.add(przemiotPolelab,"cell 0 3");
		panel.add(scrl,"cell 1 3");
		//dodać dostawca
		JLabel dostawcaPolelab = new JLabel(model.getColumnName(6));
		listaComp.add(dostawcaPolelab);
		JTextField dostawcaPole = new JTextField(15);
		listaComp.add(dostawcaPole);
		panel.add(dostawcaPolelab,"cell 0 4");
		panel.add(dostawcaPole,"cell 1 4");
		//dodać nazwę 
		JLabel nazwaPolelab = new JLabel(model.getColumnName(7));
		listaComp.add(nazwaPolelab);
		JTextField nazwaPole = new JTextField(15);
		listaComp.add(nazwaPole);
		panel.add(nazwaPolelab,"cell 0 5");
		panel.add(nazwaPole,"cell 1 5");
		//tryb postępowania
		JLabel trybPolelab = new JLabel(model.getColumnName(8));
		listaComp.add(trybPolelab);
		String[] tryby = {"przetarg", "z wolnej ręki", "inne"};
		JComboBox trybPole = new JComboBox<>(tryby);
		listaComp.add(trybPole);
		panel.add(trybPolelab,"cell 0 6");
		panel.add(trybPole,"cell 1 6");
		//dodać spółkę
		JLabel spolkaPolelab = new JLabel(model.getColumnName(9));
		listaComp.add(spolkaPolelab);
		spolkaPole = new JLabel();
		listaComp.add(spolkaPole);
		panel.add(spolkaPolelab,"cell 0 7");
		panel.add(spolkaPole,"cell 1 7");
		
		//przyciski
		contentPane.add(btnCancel, "cell 0 1");
		contentPane.add(btnBack, "cell 0 1");
		contentPane.add(btnNext, "cell 0 1");
		contentPane.add(btnSave, "cell 0 1");
		btnSave.setEnabled(false);
		btnBack.setVisible(false);
		makeThemVisible(false);
		
	}//koniec konstruktora
	//metody pomocnicze
	public void makeThemVisible(boolean a)	{
		for (Component el: listaComp)	el.setVisible(a);
	}
	public void elReplace(Component added, Component removed, JPanel p, String migTarget)	{
		p.remove(removed);
		p.add(added,migTarget);
	}
	public void addOneTime(Component a, int indexColName, JPanel p, int migRow)	{ //jeszcze zobaczymy, czy się przyda
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
			//System.out.println("asd"+zzVal.getSpolka());
			spolkaPole.setText(zzVal.getSpolka());
			//JLabel poleStatlab = new JLabel("open");
			//addOneTime(new JLabel("open"), 4, panel, 2);
			//panel.add(new JLabel(model.getColumnName(4)),"cell 0 2");
			//panel.add(poleStatlab,"cell 1 2");
			makeThemVisible(true);
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

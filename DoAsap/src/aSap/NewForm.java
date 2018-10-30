package aSap;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
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
	JPanel contentPane;		//bo potrzebujemy tego w metodach
	JPanel panel;			//jw
	JTextField poleZZ;
	JLabel errZZLab;
	
	//przyciski
	JButton btnSave = new JButton("Zapisz");
	JButton btnCancel = new JButton("Anuluj");
	JButton btnNext = new JButton();
	
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
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[][][]", "[][][]"));
		//tytuł ramki
		JLabel title = new JLabel("Dodaj nowy wniosek");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		//przyciski
		btnSave.addActionListener(this);
		btnCancel.addActionListener(this);
		btnNext.setText("Dalej");
		btnNext.addActionListener(this);
		//pole ZZ
		poleZZ = new JTextField(13);
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
		panel.add(errZZLab, "cell 2 1");
		
		
		contentPane.add(btnCancel, "cell 0 1");
		contentPane.add(btnNext, "cell 0 1");
		contentPane.add(btnSave, "cell 0 1");
		btnSave.setEnabled(false);
		
		
		
		
	}//koniec konstruktora
	

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
		if (!zzVal.getValidationResult())	{
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
			btnSave.setEnabled(true);
		}


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command= e.getActionCommand();
		if(command.equals("Anuluj"))	{
			newFrame.setVisible(false);
		}
		//if(command.equals("Cofnij"))	poleZZ.requestFocus();
	}

}

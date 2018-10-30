package aSap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.RowFilter.Entry;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

	
public class EkranGlowny implements ActionListener {
	
	private JFrame eg;
	private int x, y, width, height;
	private String tytul;
	private JMenuBar menuBar;
	private MainTableModel data;
	private JPopupMenu popupMenu;
	private JTable lista;
	//private ErrMessage errMessage;

	//do menu - string pierwszy nazwa menu, kolejne - nazwy MenuItemów
	String[] start = {"Start", "Nowy", "Exit"};
	String[] sort = {"Sort","Nieaktywne", "Aktywne","ToDo","Status","Tryb"};
	String[] toDo = {"ToDo", "Lista", "Notatki"};
	String[] notatki = {"Notatki","Nowa notatka","Edytuj"};
	String[] popupStr = {"modyfikacja", "zakończ postępowanie", "zawieś postepowanie"};
	//sortowanie filtrowanie
	TableRowSorter<MainTableModel> sorter;
	RowFilter<Object, Object> filter;
	
	public EkranGlowny()	{

		SwingUtilities.invokeLater(new Runnable() {
		      @Override
		      public void run() {
		        createGui(tytul);
		      }
		    });
	}
	
	public void createGui(String tytul)	{
		MainTableModel dane = new MainTableModel();
		data = dane;
		eg = new JFrame("ASap - Lista Postępowań");
		x=0;
		y=0;
		width = dane.getColumnCount()*100;
		height=	dane.getRowCount()*20+150;	
		eg.setSize(width, height);
		//JTextArea ta = new JTextArea(); //?? do czego to? wywalić
		/*
		 * trzeba stworzyć metodę, która zwróci w wyniku model ale bez wierszy "nieaktywnych" lub"aktywnych" 
		 * default - przeglądamy aktywne, przęłączenie na nieaktywne w menu
		 * mając otwartą listę musimy mieć możliwość zmiany statusu (popup menu)
		 * W takiej sytuacji wiersz powinien znknąć z listy - metoda fire ale pewnie także trzeba będzie jakoś "odświerzyć" 
		 * wyświetlanie aktywnych
		 * inaczej stawiając problem, pytanie, gdzie zorobić wybieranie aktywnych rekordów do wyświetlania, żeby było to odświerzanie
		 * gdzie są słuchacze metod fire? - spr w wykładzie.
		 * 
		 */
		
		lista = new JTable(dane);
		lista.setAutoCreateRowSorter(true);
		sorter = new TableRowSorter<MainTableModel>(dane);
		
		
	    filter = new RowFilter<Object, Object>() {
		      public boolean include(Entry entry) {
		        String status = (String) entry.getValue(4);
		        //System.out.println("include()= " +status+ ("".equals(status) || status == null));
		        return !("".equals(status) || status == null);
		        //return true;
		      }
		    };

		sorter.setRowFilter(filter);
		lista.setRowSorter(sorter);
		JScrollPane scroll = new JScrollPane(lista);
		eg.add(scroll);
		
		menuBar = new JMenuBar();

		doMassAddMenu(menuBar, start);
		doMassAddMenu(menuBar, sort);
		doMassAddMenu(menuBar, toDo);
		doMassAddMenu(menuBar, notatki);
		
		popupMenu = new JPopupMenu();
		doMassAddMenu(popupMenu, popupStr);
		//JMenuItem edit = new JMenuItem("Edycja");
		//JMenuItem close = new JMenuItem("Zakoncz");
		//popupMenu.add(edit);
		//edit.addActionListener(this);
		//close.addActionListener(this);
		//popupMenu.add(close);

		eg.setJMenuBar(menuBar); // f - oznacza obiekt typu JFrame
		lista.setComponentPopupMenu(popupMenu);
		lista.addMouseListener(new TableMouseListener(lista));

		eg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		eg.setVisible(true);
		//scroll.setVisible(true); - nie potrzeba
		
	}
	public void doMassAddMenu(JPopupMenu popup, String...args)	{
		//JMenu menu = new JMenu(args[0]);
		//popup.add(menu);
		for (int i =0; i<=args.length-1; i++)	{
			JMenuItem menuItem = mi(args[i]);
			popup.add(menuItem);
			//menuItem.addActionListener(this);
		}
	}
	public void doMassAddMenu(JMenuBar mb, String...args)	{
		JMenu menu = new JMenu(args[0]);
		mb.add(menu);
		for (int i =1; i<=args.length-1; i++)	{
			JMenuItem menuItem = mi(args[i]);
			menu.add(menuItem);
			
		}
	}
	public JMenuItem mi(String str)	{
		//Color col = colors.get(str.substring(1));
		JMenuItem mi = new JMenuItem(str);
		mi.addActionListener(this);	
		mi.setActionCommand(str);
		return mi;
	}
	public JMenuItem mi(String str, String acc, int mnem)	{
		JMenuItem mi = new JMenuItem(str);
		mi.addActionListener(this);	
		mi.setAccelerator(KeyStroke.getKeyStroke(acc));
		mi.setMnemonic(mnem);
		mi.setActionCommand(str);
		return mi;
	}
	public void doMassAddMenu(JMenu nazwa, JMenuItem...args)	{
		//JMenuItem Sep = null;
		for (JMenuItem el: args)	{
			if (el==null)	{
				nazwa.addSeparator();
			}
			else	{
				nazwa.add(el);
			}
		}
	}
	public MainTableModel getModel()	{	//nie jestem pewien, czy metoda jest potrzebna
		return data;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		String u = e.getActionCommand();
		//OknoForm okFo = null;		//znaczy oknoForm. 
		//int selectedRow = 0;
		//int realSelectedRow = 0;

		//Object z = e.getSource();
		//int selectedRow = lista.getSelectedRow();
		if (u.equals(start[2]))	{
			//eg.dispose();
			System.exit(0);
		}
		ErrMessageShow errMS = new ErrMessageShow(data);
		if (u.equals(start[1]))	{
			//new OpForm1("Dodaj nowe postępowanie", data.getRowCount()+1, data, errMS);
			new NewForm(data.getRowCount()+1, data);
		}
		if (u.equals(sort[1]))	{
			System.out.println("sort teraz "+u);
		    filter = new RowFilter<Object, Object>() {
			      public boolean include(Entry entry) {
			        String status = (String) entry.getValue(4);
			        //System.out.println("include()= " +status+ ("".equals(status) || status == null));
			        return !("".equals(status) || status == null);
			        //return true;
			      }
			    };
				sorter.setRowFilter(filter);
				lista.setRowSorter(sorter);

		}
		if (u.equals(sort[2]))	{
			System.out.println("sort teraz "+u);
		    filter = new RowFilter<Object, Object>() {
			      public boolean include(Entry entry) {
			        String status = (String) entry.getValue(4);
			        //System.out.println("include()= " +status+ ("".equals(status) || status == null));
			        return ("".equals(status) || status == null);
			        //return true;
			      }
			    };
				sorter.setRowFilter(filter);
				lista.setRowSorter(sorter);

		}
		if (u.equals(popupStr[0]))	{
			int selectedRow = lista.getSelectedRow();
			int realSelectedRow = lista.convertRowIndexToModel(selectedRow);
			new OpForm1("Edycja postępowania", realSelectedRow, data, errMS);
			
			//System.out.println(" to ma być " + popupStr[0] + lista.getSelectedRow() );
		}
		//okFo.addChangeListener(this); //czy to w ogóle jest potrzebne?
		if (u.equals(popupStr[1]))	{
			//System.out.println(" to ma być " + popupStr[1] + lista.getSelectedRow() );
		}
		
	}

}

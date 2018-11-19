package aSap;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarTest {
	
	

	  static Calendar kalend = new GregorianCalendar();

	  static String[] nazwaDnia = {  "niedziela", "poniedziałek", "wtorek",
	                                 "środa", "czwartek", "piątek", "sobota" };

	  static String[] nazwaMies = { "stycznia", "lutego", "marca", "kwietnia",
	                           "maja", "czerwca", "lipca", "sierpnia",
	                           "września", "października", "listopada", "grudnia"
	                         };
	  static int[] ldni = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	  int rok;
	  int mies;

	  public CalendarTest(int rok, int mies) {
	     this.rok = rok;
	     this.mies = mies - 1;
	     //kalend.set(rok, mies);
	     for (int i = 1; i<=ldni[mies]; i++)	{
	    	 kalend.set(rok, mies, i);
	    	 System.out.println("dziś jest: "+i+ " "+nazwaMies[mies]+ " roku "+rok+" "+nazwaDnia[kalend.get(Calendar.DAY_OF_WEEK)-1]);
	     }
	  }
	  public int getDayNo(int month)	{
		  return ldni[month];
	  }
	  
	  public String getCalendarInput(int dayNo) {
		  String CalendarOutput ="";
		  kalend.set(rok, mies, dayNo);
		  
		  return CalendarOutput;
	  }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalendarTest ct = new CalendarTest(2018, 2);
	}

}

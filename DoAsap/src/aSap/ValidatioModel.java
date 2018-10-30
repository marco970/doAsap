package aSap;

import java.lang.reflect.Field;
/*
 * To jest klasa, która ma służyć do przechowywania informacji, jakie walidacje
 * należy robić per dane pole.
 * W przypadku zmiany wektora danych, trzeba zmodyfikować zmienne w tej klasie
 */

public class ValidatioModel {
	 String[] ZZ = {"checkFormat","toShort", "notNull"};
	 String[] PZ = {"toShort"};
	 String[] WP = {"toShort"};
	 String[] DK = {"toShort"};
	
	public ValidatioModel()	{}
	
	public Field getField(String name) 
			throws NoSuchFieldException, SecurityException	{
		return this.getClass().getDeclaredField(name);
	}
	public String[] getValArray(String name)	{ 
		String[] a=null;
		try {
			a =(String[]) getClass().getDeclaredField(name).get(this);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return a;		
		
	}
	
	
	
}

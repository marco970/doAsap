package aSap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleFieldValidator {
	
	private boolean valDone = true;
	private String errMessage = "";
	private String fieldName = "";
	private MainTableModel model;
	private String vendor="";
	private String[] colData;
	int colPosition;
	Object[][] o;
	
	public SingleFieldValidator(String fieldName, String fieldValue, MainTableModel model)	{
		this.fieldName = fieldName;
		this.model = model;
		
		int i = model.getColumnPosition(fieldName);
		this.colPosition=i;
		this.o = model.getMatrix();
		for (Object[] el: o)	{
			
			if (el[i]==null) el[i] = "";
			//colData[i]=(String) el[i];
			System.out.println(el[i]);
			
		}
		
		
		ValidatioModel valModel = new ValidatioModel();
		

		String[] b = valModel.getValArray(fieldName);
		for(String el: b)	{
			//System.out.println(el+" - "+fieldValue);
			runMethod(el, fieldValue);
		}
		System.out.println(valDone+ " *** "+errMessage);
		doesExist(fieldValue);
	}//koniec konstruktora
	
	
	public boolean getValidationResult()	{
		return valDone;
	}
	public String getErrMessage()	{
		return errMessage;
	}
	//odpalanie metod
	public void runMethod(String methName, String fieldValue)	{
		Method[] methArray = this.getClass().getDeclaredMethods();
		for (Method methEl: methArray)	{
			//System.out.println("*"+methEl.getName()+" - "+methName);
			if (methEl.getName().equals(methName))	{
				//tu odpalam po prostu metodę
				try {
					methEl.invoke(this,fieldValue);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	//metody walidacyjne
		public void notNull(String field)	{	
			if("".equals(field)||field==null) {
				valOrg(false,"pole nie może być puste");
			}
			else valOrg(true,"");
		}
		public void toShort(String field)	{ 	//dodać dopuszczenie 0
			int dl = field.length();			//dodać definicję długości w zależności od pola
			int l = 13;
			if (dl==l || dl==0)	{
				valOrg(true,"");
			}
			else {
				valOrg(false,"nieprawidłowa długość numeru");
			}
		}
		public void doesExist(String field)	{
			
			
			for (Object[] el: o)	{
				int i = colPosition;

				if (!el[i].equals(field)||el[i].equals(""))	valOrg(true,"");
				else valOrg(false,"postępowanie o tym numerze już istnieje");
			}
		}
		public void checkFormat(String field)	{
			if (field.length()>=13)	{
			String fstPart = field.substring(0, 2);
			//char a = field.charAt(2);
			String sndPart = field.substring(3, 5);
			String trdPart = field.substring(6,12);
			
			if (fstPart.equals(fieldName+"/"))	valOrg(true,"");
			else valOrg(false,"nieprawidłowy format numeru");
			
			if (sndPart.equals("PLK") || sndPart.equals("PLI") || sndPart.equals("CPO") ) valOrg(true,"");
			else valOrg(false,"nieprawidłowy format numeru");
			
			if(trdPart.matches("[0-9]")) valOrg(true,"");
			else valOrg(false,"nieprawidłowy format numeru");
			}
			
			
			
		}
		//EoMetodyWalidacyjne	
		
		//metody pomocnicze
	public void valOrg(boolean val, String errMsg)	{
		if (valDone) valDone = val;
		if (errMessage.equals(""))	errMessage = errMsg;
	}
	

}//koniec klasy


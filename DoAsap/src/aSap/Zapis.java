package aSap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Zapis {
	
	MainTableModel model;
	
	
	public Zapis (MainTableModel model) throws IOException	{
		this.model = model;
		//System.out.println("row "+model.getRowCount()+" col "+ model.getColumnCount());
		
		String[] row = new String[model.getRowCount()];
		
		for (int j=0; j<=model.getRowCount()-1; j++)	{
			
			String singleRow ="";
			//System.out.println(row+j);
			
			for(int i = 0; i<= model.getColumnCount()-1; i++)	{
				if (model.getValueAt(j, i)==null)	{
					singleRow=singleRow.concat(";");
				}
				else	{
					singleRow=singleRow.concat(model.getValueAt(j, i).toString()+";");
				}
				//System.out.print(j+"-"+i+"-"+row);
				//System.out.println(j+"-"+i);
			}
			//System.out.println("\n");
			//System.out.println(singleRow);
			row[j]=singleRow;
			//saveFile("F:/aSapData/current_test.txt", row);
		}
		writeFile("F:/aSapData/Current3.txt", row);
		
	}
	
	public void writeFile(String filePath, String[] textLines)
		    throws IOException {
		  
		  FileWriter fileWriter = new FileWriter(filePath);
		  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		  
		  try {
		    for (String line : textLines) {
		      bufferedWriter.write(line);
		      bufferedWriter.newLine();
		    }
		  } finally {
		    bufferedWriter.close();
		  }
		}

}

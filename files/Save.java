package files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

/** JDK 7+. */
public class Save
{
	static String OUTPUT_FILE_NAME = "C:\\Users\\Robert Van Loon\\Dropbox\\Lithin\\workspace\\Text-Game\\files\\";
	static String RESET = "C:\\Users\\Robert Van Loon\\Dropbox\\Lithin\\workspace\\Text-Game\\files\\";
	
	static view.Out output = new view.Out();
	static view.In input = new view.In();
	
	public static void saveCharacter(HashMap<String, LinkedHashMap<String, Double>> saveData, String fileName) throws IOException
	{
		
		try{
			//Set the name of the file to the characters name
			OUTPUT_FILE_NAME = RESET +  "characters\\"+fileName+".txt";
			//Check to see if file exists
			File binFile = new File(OUTPUT_FILE_NAME);
			if(!binFile.exists())
				binFile.createNewFile();
			
			FileOutputStream fileOut = new FileOutputStream(OUTPUT_FILE_NAME);
			
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(saveData);
			out.close();
			fileOut.close();
			output.out.println("Serialized data is saved in "+OUTPUT_FILE_NAME);
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}

} 
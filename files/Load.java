package files;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

import model.*;

/**  
Converting binary data into different forms.

<P>Reads binary data into memory, and writes it back out.
(If your're actually copying a file, there are better ways to do this.)

<P>Buffering is used when reading and writing files, to minimize the number 
of interactions with the disk.
*/
public final class Load
{

	/** Change these settings before running this class. */
	private static String INPUT_FILE_NAME = System.getProperty("user.dir")+"\\files";
	private static String RESET = System.getProperty("user.dir")+"\\files";

	static view.Out output = new view.Out();
	static view.In input = new view.In();
	

   @SuppressWarnings("unchecked")
   public static void loadCharacter(String name)
   {
	   
      INPUT_FILE_NAME = RESET + "\\savegames\\"+name;
      try
      {
         FileInputStream fileIn = new FileInputStream(INPUT_FILE_NAME);
         ObjectInputStream in = new ObjectInputStream(fileIn);
         model.Char.character = (HashMap<String, LinkedHashMap<String, Double>>) in.readObject();
         in.close();
         fileIn.close();
      }catch(IOException i)
      {
         i.printStackTrace();
         return;
      }catch(ClassNotFoundException c)
      {
         output.out.println("HashMap<String, LinkedHashMap<String, Double>> class not found");
         c.printStackTrace();
         return;
      }
   }
}
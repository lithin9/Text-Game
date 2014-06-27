package controllers;


import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import view.WordUtils;


//An entry point class to handle multiple types of application modes
public class Controller
{
	public static void main(String[] args)
	{
		//Set up output stream to display timestamp
		System.setOut(new PrintStream(System.out)
		{
			public void println(String x)
			{
				super.println(
			//			new SimpleDateFormat("MM/dd kk:mm:ss ").format(new Date())
						//+"\n"
						(WordUtils.wrap(x, 75)));
			}
		});
		
		System.out.println("Welcome to this game");
		
		model.Char character = new model.Char();
		//character.levelSystemExample(100);
		character.newCharacter();
		//model.Intro.testIntro();
		character.printStats();
	}
}
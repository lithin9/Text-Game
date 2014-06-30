package view;

import java.util.Scanner;


public class In 
{
	//declare input scanner
	Scanner in;
	
	public In()
	{
		//Define input scanner
		in = new Scanner(System.in);
	}
	
	public String getNextLine()
	{
		in.reset();
		//Collect users input
		String input = in.nextLine();
		//Return user input
		return input;
	}
}

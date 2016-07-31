package view;

import java.util.NoSuchElementException;
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
	
	public String nextLine()
	{
		in.reset();
		//Collect users input
		String input = "";
		try{
			input = in.nextLine();
		} catch(NoSuchElementException e){
			System.out.println("Wait here");
		}
		//Return user input
		return input;
	}

	public Integer nextInt() {
		String userInput = this.nextLine();
		try{
			return Integer.parseInt(userInput);
		} catch(NumberFormatException e) {
			return -1;
		}
	}
}

package controllers;



//An entry point class to handle multiple types of application modes
public class Controller
{
	static view.Out output = new view.Out();
	static view.In input = new view.In();
	
	public static void main(String[] args)
	{
		model.Char character = new model.Char();
		output.out.println("Please select an option!\n\n1. Load Game.\n2. New Game\n3. Help.");
		switch(input.getNextLine()){
		case "1":
			System.out.println("Please enter the name of the character you wish to load.");
			character.load(input.getNextLine());
			character.printStats();
			break;
		case "2":
			System.out.println("Creating new character!");
			character.newCharacter();
			
			break;
		case "3":
			System.out.println("This feature has not yet been implemented.");
			break;
		}
	}
}
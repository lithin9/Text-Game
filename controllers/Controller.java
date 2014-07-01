package controllers;



//An entry point class to handle multiple types of application modes
public class Controller
{
	static view.Out output = new view.Out();
	static view.In input = new view.In();
	
	public static void main(String[] args)
	{
		model.Char character = new model.Char();
		startMenu: while(true)
		{
			output.out.println("Please select an option!\n\n1. Load Game.\n2. New Game\n3. Help.");
			optionMenu: switch(input.nextLine()){
			case "1":
				output.out.println("Please enter the name of the character you wish to load.");
				character.load(input.nextLine());
				character.printStats();
				break optionMenu;
			case "2":
				output.out.println("Creating new character!");
				character.newCharacter();
				
				break optionMenu;
			case "3":
				output.out.println("This feature has not yet been implemented.");
				break optionMenu;
			case "4":
				output.out.println("Good byte");
				break startMenu;
			}
		}
	}
}
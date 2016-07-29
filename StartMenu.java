import view.*;

//An entry point class to handle multiple types of application modes
public class StartMenu
{
	static view.Out output = new view.Out();
	static view.In input = new view.In();
	static characters.Character character;
	
	public static void main(String[] args)
	{
		startMenu: while(true)
		{
			output.out.println("Please select an option!\n\n1. Load Game.\n2. New Game\n3. Help.");
			character = new characters.Character();
			optionMenu: switch(input.nextLine()){
			case "1":
				character.isNew = false;
				character.isPlayerCharacter = true;
				output.out.println("Not yet implemented.");
				/*output.out.println("Please enter the name of the character you wish to load.");
				character.load(input.nextLine());
				character.printStats();*/
				break optionMenu;
			case "2":
				output.out.println("Creating new character!\n");
				character.isNew = true;
				character.isPlayerCharacter = true;
				character.start();
				//character.newCharacter();
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
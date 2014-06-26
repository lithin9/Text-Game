import java.util.Scanner;


public class Main {

	/**
	 * @param args I'll do this shit later
	 */
	public static void main(String[] args) 
	{
		Intro i = new Intro();
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to this fuckin' text game, please wait for options. " +
				"Or kill yourself. I don't fucking care\n");
		
		System.out.println("1.\tStart anew.\n" +
				"2.\tContinue (not workin)\n" +
				"3.\tInstructions\n");
		System.out.println("Please enter a selection: ");
		
		switch (input.nextLine()) {
		case "1": 
			//input.close();For whatever reason this fucked everythhing up
			i.intro();
		break;
		case "2": 
			System.out.println("This will be implemented when we start working on saving to a text file,\n" +
					"which in the future will be embedded in the program itself\n");
		break;
		case "3": 
			System.out.println("In this game you will be able to create a character through a small\n" +
					"interactive intro/tutorial. Whenever prompted for text commands, you will\n" +
					"normally be given a number of options, though most options will be hidden.\n");
					//add more?
		break;
		}
		input.close();
	}
	public void chapterSelect(String chapterSelect)
	{
		if(chapterSelect == "")
			chapterSelect = "intro";
		while(true)
		{
			if(chapterSelect.equals("intro"))
			{
				Intro i = new Intro();
				chapterSelect = i.intro();
				continue;
			}
			
		}
	}
}

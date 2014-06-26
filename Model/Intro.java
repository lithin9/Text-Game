import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import View.WordUtils;


public class Intro extends Char
{
	Failure failure = new Failure();
	int x = 0;
	int y = 0;
	int[][] walkableGrid = new int[10][10];
	boolean sick = true;
	boolean lyingDown = true;
	boolean dirtyClothes = true;
	boolean hidden = false;
	boolean bleeding = false;
	int maxHunger = 100;
	int hunger = 25;
	Commands command = new Commands();
	
	public void testIntro()
	{
		newCharacter();
		//System.out.println()
		String test = "This is a test of the word wrapping capabilities of this motherfuckinggoddamnit program that I downloaded. I don't know how many words this is, but I suppose we'll fucking find out, now won't we? Shiiiet.";
		test = WordUtils.wrap(test, 30, "test", true);
		System.out.println(test);
	}
	
	public void buildMapGrid()
	{
		//Manually initilized walkable grid points
		
		//Fill in the rest to avoid nullpointers
		for(int i=0; i<walkableGrid.length; i++)
		{
			for(int j=0; j<walkableGrid[i].length; j++)
			{
				if(walkableGrid[i][j] != 1)
				{
					walkableGrid[i][j] = -1;
				}
			}
		}
	}
	
	public String intro() 
	{
		
		Scanner input = new Scanner(System.in);
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String charName = "Unnamed";
		System.out.println("Consciousness returns to you, dizzy visions blur your"
						+ "\nmind, and your heavy eyes awaken, you feel heavy,"
						+ "\nweak and nausious as if you had been drinking and"
						+ "\nfighting all night long, and you lie in an alleyway.");
		Beginning: while(true)
		{
			if(hunger >= maxHunger)
			{
				System.out.println("You starved to death.");
				failure.end();
			}
			
			String userInput = "";
			
			try {
				userInput = input.nextLine();
			} catch (Exception e) {
				System.out.println("Error reading user input.");
				e.printStackTrace();
			}
			userInput = userInput.toLowerCase();
			
			if(userInput.contains("smell"))
			{
				if(sick)
				{
					System.out.println("You smell your environment, and the horrible"
							+ "\nstentch of sweat and garbage is so"
							+ "\nstrong that your eyes water up and get blurry.");
				}
				if(!sick)
				{
					System.out.println("You smell your environment, and the horrible"
							+ "\nstentch of vomit, sweat and garbage is so"
							+ "\nstrong that your eyes water up and get blurry.");
					System.out.println("Smelling the environment has made you feel sick again.");
					sick = true;
				}
				continue Beginning;
			}
			else if(userInput.contains("vomit") || userInput.contains("throw up") || userInput.contains("sick"))
			{
				if(sick)
				{
					if(lyingDown)
					{
						System.out.println("Still lying down, you vomit all over yourself, choking"
										+ "\n on your own vomit, you drown.");
						failure.end();
					}
					else
					{
						System.out.print("You gladly vomit your sickness away, thanking the gods"
										+ "\nthat most of your sickness fades away from you, though"
										+ "\nnow you feel very hungry.");
						hunger += 30;
						sick = false;
					}
				}
				else
				{
					System.out.println("You struggle to vomit again, and in doing so make yourself hungier."
							+ "Careful, least ye starve to death.");
					hunger += 5;
				}
				continue Beginning;
			}
			else if(userInput.contains("look") || userInput.contains("examine"))
			{
				if(userInput.contains("self") || userInput.contains("me"))
				{
					if(charName.equals("Unnamed"))
					{
						System.out.println("Your eyes instantly become fixated and mesmerized"
								+ "\nupon a tattoo on your arm with a name written on it."
								+ "\nChoose your name:");
						charName = charName();
						System.out.println(charName+", that's what it says bold 'n clear and"
										+ "\nthough your mind is slow to put the pieces together you"
										+ "\nfinally figure out... it must be your name... "+charName
										+ "\nYou repeat it expecting it to help you remember something,"
										+ "\nbut nothing comes to mind, your memory is gone.");
					}
					else
					{
						System.out.println("Your eyes still fixate to your arm, the tattoo haunts you,"
										+ "\nwhy the fuck did you get a tattoo with your name on it?"
										+ "\nOne would hope that something as this important looking tattoo would"
										+ "\nprovide some answers, unfortunately it's nothing but your name."
										+ charName+"... that's all you see in it at least.");
					}
					continue Beginning;
				}
				else if(userInput.contains("environment") || userInput.contains("area") || userInput.contains("around"))
				{
					System.out.println("You look around with your dizzy eyes, sick to your"
									+ "\nstomach your surroundings make it worse, and"
									+ "\non the verge of vomitting, you examine your"
									+ "\nenvironment, the lighting is dark, but light"
									+ "\nenough to see the broken glass on the ground"
									+ "\nthe only things with enough light on them are a"
									+ "\ndumpster, a door, and the outline of what appears"
									+ "\nto be a man running towards you at the end of the alleyway.");
					continue Beginning;
				}
				else if(userInput.contains("ground") || userInput.contains("glass") || userInput.contains("shards"))
				{
					System.out.println("There appears to be some nicely shaped shards"
									+ "\nof glass on the ground, perfect for shanking"
									+ "\nsomeone, if the need arises.");
				}
				else if(userInput.contains("head") || userInput.contains("arm") || userInput.contains("leg") || userInput.contains("torso"))
				{
					//TODO: finish this.
				}
				else
				{
					System.out.println("Look at what?");
				}
				continue Beginning;
			}
			else if(userInput.contains("listen") || userInput.contains("hear") || userInput.contains("recieve sound"))
			{
				System.out.println("You hear someone running loudly towards you,"
				 		+ "\nand your mind instantly reaches for your weapon,"
				 		+ "\nand your legs jump into a prepared stance,"
						+ "\nready to kill. The mysterious being grows closer,"
						+ "\nand whilst they get closer");
			}
			else if(userInput.contains("stand") || userInput.contains("get up") || userInput.contains("sit up"))
			{
				if(lyingDown && dirtyClothes)
				{
					lyingDown = false;
					System.out.println("You stand up, and see some dirt fall off your clothes, man you're fucking dirty.");
				}
				else if(lyingDown)
				{
					lyingDown = false;
					System.out.println("You stand up.");
				}
				else
				{
					System.out.println("You are already standing up, idiot.");
				}
				continue Beginning;
			}
			else if(userInput.contains("sit") || userInput.contains("lie down"))
			{
				System.out.println("You lie back down on the gross, dirty ground.");
				lyingDown = true;
				dirtyClothes = true;
			}
			else if(userInput.contains("wait") || userInput.contains("stand still") || userInput.contains("do nothing"))
			{
				System.out.println("You bravely wait, standing still awaiting your fate.");
				break;
			}
			else if(userInput.contains("help") || userInput.contains("commands") || userInput.contains("what do i do"))
			{
				String commandsList = "Try looking around, or moving forward.";//TODO: We can save this string as a series of commands they have used. 
				//I don't know how I want them described though.
			}
			else
				System.out.println("What?");
		}
		
		try {
			input.close();
		} catch (Exception e) {
			System.out.println("Error closing input buffer");
			e.printStackTrace();
		}
		
		return "nextChapterName";
	}

}

package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import files.Save;
import files.Load;
import model.Randomizer;

public class Char 
{
	public static String name = new String();
	
	static view.Out output = new view.Out();
	static view.In input = new view.In();
	
	public static HashMap<String, LinkedHashMap<String, Double>> character = new HashMap<String, LinkedHashMap<String, Double>>();

	
	/**
	 * Runs all the functions that set up the save file for a new game.
	 */
	public void newPlayerCharacter()
	{
		//Get name 
		output.out.println("Please enter a name: \n");
		name = input.nextLine();
		
		//Get desired character class
		output.out.println("Please choose a class:\n1.Fighter\n2.Acolyte\n3.Rogue");
		LinkedHashMap<String, Double> charClass = new LinkedHashMap<String, Double>();
		switch (input.nextLine()) {
		case "1": 
			//charClass.put("Main", "Fighter");
			charClass.put("Main Class", 1.0);
		break;
		case "2":
			//charClass.put("Main", "Acolyte");
			charClass.put("Main Class", 2.0);
		break;
		case "3": 
			//charClass.put("Main", "Rogue");
			charClass.put("Main Class", 3.0);
		break;
		}
		character.put("Classes", charClass);
		
		generateCharacter: while(true)
		{
			buildCharInfo();
			generateBonusStats();
			buildBaseStats();
			generateRandomStats();
			buildDependantStats();
			output.out.println("Below are the generated stats:\n");
			printStats();
			output.out.println("Re-roll? Y/N");
			questionLoop: while(true)
			{
				if(input.nextLine().equalsIgnoreCase("n"))
				{
					break generateCharacter;
				}
				else if(input.nextLine().equalsIgnoreCase("y"))
					continue generateCharacter;
				else
				{
					output.out.println("That is unacceptable");
					continue questionLoop;
				}
			}
						
		}
		
		buildLimbStatus();
		buildCharPerks();
		buildGameInfo();
		
		save();
	}

	public void newNonPlayerCharacter() {
		
	}
	
	public void save()
	{
		try {
			Save.saveCharacter(character, name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the save file attached to the give name
	 * @param charName
	 */
	public void load(String charName)
	{
		Load.loadCharacter(charName);
	}
	
	/**
	 * Sets the character into like experience, level, skillpoints, attributePoints, and morality
	 * All values default to zero, uses charInfo[][].
	 */
	private void buildCharInfo() 
	{
		
		LinkedHashMap<String, Double> value = new LinkedHashMap<String, Double>();
		
		value.put("Experience", 0.0);
		value.put("Level", 1.0);
		value.put("Skill Points", 3.0);
		value.put("Attribute Points", 50.0);
		value.put("Morality", 0.0);
		
		character.put("Character Information", value);
		
		value = null;
	}

	/**
	 * Sets the defaults for the character stats.
	 */
	private void buildBaseStats()
	{
		LinkedHashMap<String, Double> value = new LinkedHashMap<String, Double>();
		
		value.put("Strength", 1.0);
		value.put("Endurance", 1.0);
		value.put("Constitution", 1.0);
		
		value.put("Intellect", 1.0);
		value.put("Perception", 1.0);
		value.put("Wisdom", 1.0);
		
		value.put("Agility", 1.0);
		value.put("Accuracy", 1.0);
		value.put("Concentration", 1.0);
		
		character.put("Character Attributes", value);
		
		value = null;
	}
	
	/**This will generate the stats that are dependant on 
	 * 
	 */
	private void buildDependantStats()
	{
		LinkedHashMap<String, Double> value = new LinkedHashMap<String, Double>();
				
		
		//Main stats gain 5 points per level, Secondary stats gain 2.5 points per level, Ternary stats gain 1.25 points per level
		value.put("Health", (character.get("Character Attributes").get("Constitution") * 4.0));			//Fighter Main Stat, Rogue Secondary Stat, Acolyte Ternary Stat
		
		value.put("Magicka", (character.get("Character Attributes").get("Wisdom") * 4.0));
		/*Acolyte Main Stat, Rogue Ternary Stat, Fighter Ternary Stat
		 *Determines the amount of magical abilities that can be used in a fight
		 */
		//Self explainatory
		value.put("Stamina", (character.get("Character Attributes").get("Endurance") * 4.0));
		/*Rogue Main Stat, Fighter Secondary Stat, Acolyte Ternary Stat
		 *Determines the amount of melee abilities that can be used per fight
		 */
		//Character Status Recovery
		//Recovery is disable in hardcore mode.
		value.put("Health Regen", ((character.get("Character Attributes").get("Endurance") + character.get("Character Attributes").get("Constitution"))/ 10));	//(Endurance+Constitution)/10
		value.put("Magicka Regen", ((character.get("Character Attributes").get("Intellect") + character.get("Character Attributes").get("Concentration"))/ 10));	//(Intellect+Concentration)/10
		value.put("Stamina Regen", ((character.get("Character Attributes").get("Agility") + character.get("Character Attributes").get("Concentration"))/ 10));	//(Agility+Concentration)/10
		
		//Defensive Status
		value.put("Armor Class", ((character.get("Character Attributes").get("Endurance") / character.get("Character Information").get("Level")) / 10));
		/*
		 * Armor Class determines physical damage reduction: (Armor Class + (yourLevel - enemyLevel)) / yourLevel(or their level)) = Percent of damage to take away from
		 * Example: You have 23 armor class, you're level 10 and the enemy is level 12. (23+(10-12))/10 = 2.1
		 */
		value.put("Magic Resistance", 0.5);
		/*
		 * //TODO: Convert resistances into individual magics
		 * 
		 * poison is now a magic
		 * Poison Resistance is the effectiveness of poisons against you
		 * Calculated by if (level of posion - poison resistance) is less than 1, in which case the poison won't do anything,
		 * but for every point above 1 it will do 2 points of damage
		 */

		value.put("Resiliance", ((character.get("Character Attributes").get("Endurance") / 3) + character.get("Character Information").get("Level")));//Used to be critical resistance
		/*
		 * The percent chance that you'll not get critically hit, if the enemy scores a critical
		 */
		
		
		value.put("Dodge", (character.get("Character Attributes").get("Agility") + character.get("Character Information").get("Level")));
		/*
		 * Percent chance that the character can dodge an attack.
		 * Note that the enemies agility will negate your dodge chance.
		 */
		value.put("Block", (character.get("Character Attributes").get("Constitution") + character.get("Character Information").get("Level")));
		/*
		 *The percent chance that your character is able to block something.
		 */
		value.put("Parry", (character.get("Character Attributes").get("Accuracy") + character.get("Character Information").get("Level")));
		/*
		 * The percent chance you are accurate enough to parry an attack.
		 */
		
		
		//Offensive Status
		value.put("Melee Attack Power", ((character.get("Character Attributes").get("Strength") * 1.33) + character.get("Character Information").get("Level")));
		/*
		 * Determined by (Strength/Level)/2
		 * This will be how much damage is done by the character.
		 */
		value.put("Magic Attack Power", ((character.get("Character Attributes").get("Intellect") * 1.33) + character.get("Character Information").get("Level")));
		/*
		 * Determined by (Intelligence/Level)/2
		 * This will be how much spell damage is done by the character.
		 * Spells will have formulas that incorporate this. Includes healing power.
		 */
		value.put("Ranged Attack Power", ((character.get("Character Attributes").get("Concentration") * 1.33) + character.get("Character Information").get("Level")));
		/*
		 * Ranged attack power.
		 */
		
		
		value.put("Critical Chance", ((character.get("Character Attributes").get("Accuracy") / 3) + character.get("Character Information").get("Level")));
		/*
		 * Determined by accuracy.
		 */
		value.put("Placeholder", 0.0);
		value.put("Instagib", 0.0);
		/*
		 * Determined by Combat mastery.
		 */
		
		
		value.put("Physical Hit Chance", ((character.get("Character Attributes").get("Agility") * 4) + character.get("Character Information").get("Level")));
		/*
		 * Determined by agility
		 */
		value.put("Magical Hit Chance", ((character.get("Character Attributes").get("Concentration") * 4) + character.get("Character Information").get("Level")));
		/*
		 * 
		 */
		value.put("Ranged Hit Chance", ((character.get("Character Attributes").get("Accuracy") * 4) + character.get("Character Information").get("Level")));
		/*
		 * 
		 */
		
		//TODO: Add in attacks per turn
		//TODO: add in moves per turn
		//TODO: Initiative - The amount of turns per turn-cycle you get.
		//if(charClass.get("Main").equals("Fighter"))
		value.put("Physical Combat Mastery", (character.get("Character Attributes").get("Strength") + character.get("Character Attributes").get("Endurance") + character.get("Character Attributes").get("Constitution")));
		//else if(charClass.get("Main").equals("Acolyte"))
		value.put("Mental Combat Mastery", (character.get("Character Attributes").get("Intellect") + character.get("Character Attributes").get("Perception") + character.get("Character Attributes").get("Wisdom")));
		//else if(charClass.get("Main").equals("Rogue"))
		value.put("Ranged Combat Mastery", (character.get("Character Attributes").get("Agility") + character.get("Character Attributes").get("Accuracy") + character.get("Character Attributes").get("Concentration")));
		//else
		//	value.put("Combat Mastery", 0.0);
		/*
		 * Combat mastery is determined by adding all three stats of the chosen class' stat group: physical, mental, ability/skill 
		 * and dividing it by the current level. Dividing by level ensures a scale, but because your combat mastery has decreased, 
		 * doesn't mean you are less of a fighter, it means others on your level are better.
		 * Combat Mastery: Adds a bonus to all your combat checks related to your class. Say you are a ranger, and you go for a long ranged
		 * shot, you will get a bonus of the value of your combat mastery to be able to hit that. Refuse to put stats in your main, and your
		 * combat will get rusty.
		 */
		
		if(character.get("Classes").get("Main Class") == 1.0)
			value.put("Instagib", ((character.get("Character Attributes").get("Strength") + character.get("Character Attributes").get("Endurance") + character.get("Character Attributes").get("Constitution") + character.get("Character Attributes").get("Intellect") + character.get("Character Attributes").get("Perception") + character.get("Character Attributes").get("Wisdom") + character.get("Character Attributes").get("Agility") + character.get("Character Attributes").get("Accuracy") + character.get("Character Attributes").get("Concentration")) / 1000));
		
		/*
		if(character.get("Classes").get("Main Class") == 1.0)
			value.put("Instagib", (character.get("Character Attributes").get("Strength") + character.get("Character Attributes").get("Endurance") + character.get("Character Attributes").get("Constitution")));
		else if(character.get("Classes").get("Main Class") == 2.0)
			value.put("Instagib", (character.get("Character Attributes").get("Intellect") + character.get("Character Attributes").get("Perception") + character.get("Character Attributes").get("Wisdom")));
		else if(character.get("Classes").get("Main Class") == 3.0)
			value.put("Instagib", (character.get("Character Attributes").get("Agility") + character.get("Character Attributes").get("Accuracy") + character.get("Character Attributes").get("Concentration")));
		*/
	      
		character.put("Character Dependant Attributes", value);
		
		value = null;
	}
	
	private void generateBonusStats()
	{
		double gainStatAt = 0.0;
		double amount = 0.0;
		while(true)
		{
			double random = Randomizer.randomizer(100.0);
			if(random > gainStatAt || random == 0)
			{
				if(random == 100.0)//0 means exactly 100.
					amount++;//Give a bonus stat for scoring so well.
				amount++;
				gainStatAt += 20.0;
				if(gainStatAt > 90.0)
					gainStatAt = 90.0;
			}
			else
				break;
		}
		character.get("Character Information").put("Attribute Points", (character.get("Character Information").get("Attribute Points") + amount));
		output.out.println("Your character has "+amount+" bonus attribute points");
	}

	private void generateRandomStats()
	{
		double physicalStatChanceMin = 0.0;
		double physicalStatChanceMax = 0.0;
		double magicalStatChanceMin = 0.0;
		double magicalStatChanceMax = 0.0;
		double skillStatChanceMin = 0.0;
		double skillStatChanceMax = 0.0;
		double points = character.get("Character Information").get("Attribute Points");
		
		if(character.get("Classes").get("Main Class") == 1.0) //Fighter
		{
			 physicalStatChanceMin = 25.0;
			 physicalStatChanceMax = 74.99;
			 magicalStatChanceMin = 0.0;
			 magicalStatChanceMax = 24.99;
			 skillStatChanceMin = 75.0;
			 skillStatChanceMax = 100.0;
		}
		else if(character.get("Classes").get("Main Class") == 2.0)//Acolyte
		{
			 physicalStatChanceMin = 0.0;
			 physicalStatChanceMax = 24.99;
			 magicalStatChanceMin = 25.0;
			 magicalStatChanceMax = 74.99;
			 skillStatChanceMin = 75.0;
			 skillStatChanceMax = 100.0;
		}
		else if(character.get("Classes").get("Main Class") == 3.0)//Rogue
		{
			 physicalStatChanceMin = 75.0;
			 physicalStatChanceMax = 100.0;
			 magicalStatChanceMin = 0.0;
			 magicalStatChanceMax = 24.99;
			 skillStatChanceMin = 25.0;
			 skillStatChanceMax = 74.99;
		}
		for(int i=0; i<points; i++)
		{
			double random = Randomizer.randomizer(100);
			double randomStat = Randomizer.randomizer(3);
			if(magicalStatChanceMin <= random && magicalStatChanceMax >= random)
			{
				if(randomStat >= 0.0 && randomStat <= 0.99)
					character.get("Character Attributes").put("Intellect", (character.get("Character Attributes").get("Intellect") + 1));
				else if(randomStat >= 1.0 && randomStat <= 1.99)
					character.get("Character Attributes").put("Perception", (character.get("Character Attributes").get("Perception") + 1));
				else if(randomStat >= 2.0 && randomStat <= 3.0)
					character.get("Character Attributes").put("Wisdom", (character.get("Character Attributes").get("Wisdom") + 1));
			}
			else if(physicalStatChanceMin <= random && physicalStatChanceMax >= random)
			{
				if(randomStat >= 0.0 && randomStat <= 0.99)
					character.get("Character Attributes").put("Strength", (character.get("Character Attributes").get("Strength") + 1));
				else if(randomStat >= 1.0 && randomStat <= 1.99)
					character.get("Character Attributes").put("Endurance", (character.get("Character Attributes").get("Endurance") + 1));
				else if(randomStat >= 2.0 && randomStat <= 3.0)
					character.get("Character Attributes").put("Constitution", (character.get("Character Attributes").get("Constitution") + 1));
			}
			else if(skillStatChanceMin <= random && skillStatChanceMax >= random)
			{
				if(randomStat >= 0.0 && randomStat <= 0.99)
					character.get("Character Attributes").put("Agility", (character.get("Character Attributes").get("Agility") + 1));
				else if(randomStat >= 1.0 && randomStat <= 1.99)
					character.get("Character Attributes").put("Accuracy", (character.get("Character Attributes").get("Accuracy") + 1));
				else if(randomStat >= 2.0 && randomStat <= 3.0)
					character.get("Character Attributes").put("Concentration", (character.get("Character Attributes").get("Concentration") + 1));
			}
			
			character.get("Character Information").put("Attribute Points", (character.get("Character Information").get("Attribute Points") - 1));
		}
	}
	
	/**
	 * @deprecated
	 */
	private void generateRandomStats_old()
	{
		double physicalStatChanceMin = 0.0;
		double physicalStatChanceMax = 0.0;
		double magicalStatChanceMin = 0.0;
		double magicalStatChanceMax = 0.0;
		double skillStatChanceMin = 0.0;
		double skillStatChanceMax = 0.0;
		double points = character.get("Character Information").get("Attribute Points");
		
		if(character.get("Classes").get("Main Class") == 1.0) //Fighter
		{
			 physicalStatChanceMin = 25.0;
			 physicalStatChanceMax = 74.99;
			 magicalStatChanceMin = 0.0;
			 magicalStatChanceMax = 24.99;
			 skillStatChanceMin = 75.0;
			 skillStatChanceMax = 100.0;
		}
		else if(character.get("Classes").get("Main Class") == 2.0)//Acolyte
		{
			 physicalStatChanceMin = 0.0;
			 physicalStatChanceMax = 24.99;
			 magicalStatChanceMin = 25.0;
			 magicalStatChanceMax = 74.99;
			 skillStatChanceMin = 75.0;
			 skillStatChanceMax = 100.0;
		}
		else if(character.get("Classes").get("Main Class") == 3.0)//Rogue
		{
			 physicalStatChanceMin = 75.0;
			 physicalStatChanceMax = 100.0;
			 magicalStatChanceMin = 0.0;
			 magicalStatChanceMax = 24.99;
			 skillStatChanceMin = 25.0;
			 skillStatChanceMax = 74.99;
		}
		for(int i=0; i<points; i++)
		{
			double random = Randomizer.randomizer(100);
			double randomStat = Randomizer.randomizer(3);
			if(magicalStatChanceMin <= random && magicalStatChanceMax >= random)
			{
				if(randomStat >= 0.0 && randomStat <= 0.99)
					character.get("Character Attributes").put("Intellect", (character.get("Character Attributes").get("Intellect") + 1));
				else if(randomStat >= 1.0 && randomStat <= 1.99)
					character.get("Character Attributes").put("Perception", (character.get("Character Attributes").get("Perception") + 1));
				else if(randomStat >= 2.0 && randomStat <= 3.0)
					character.get("Character Attributes").put("Wisdom", (character.get("Character Attributes").get("Wisdom") + 1));
			}
			else if(physicalStatChanceMin <= random && physicalStatChanceMax >= random)
			{
				if(randomStat >= 0.0 && randomStat <= 0.99)
					character.get("Character Attributes").put("Strength", (character.get("Character Attributes").get("Strength") + 1));
				else if(randomStat >= 1.0 && randomStat <= 1.99)
					character.get("Character Attributes").put("Endurance", (character.get("Character Attributes").get("Endurance") + 1));
				else if(randomStat >= 2.0 && randomStat <= 3.0)
					character.get("Character Attributes").put("Constitution", (character.get("Character Attributes").get("Constitution") + 1));
			}
			else if(skillStatChanceMin <= random && skillStatChanceMax >= random)
			{
				if(randomStat >= 0.0 && randomStat <= 0.99)
					character.get("Character Attributes").put("Agility", (character.get("Character Attributes").get("Agility") + 1));
				else if(randomStat >= 1.0 && randomStat <= 1.99)
					character.get("Character Attributes").put("Accuracy", (character.get("Character Attributes").get("Accuracy") + 1));
				else if(randomStat >= 2.0 && randomStat <= 3.0)
					character.get("Character Attributes").put("Concentration", (character.get("Character Attributes").get("Concentration") + 1));
			}
			
			character.get("Character Information").put("Attribute Points", (character.get("Character Information").get("Attribute Points") - 1));
		}
	}
	
	/**
	 * Sets the limb status for each limb, including hands, feet, head and torso.
	 * The value 8 means that it is normal. Goes up to ten and as low as 0.
	 * 0 = gone
	 * 1 = 
	 */
	private void buildLimbStatus() 
	{
		LinkedHashMap<String, Double> left = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> center = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> right = new LinkedHashMap<String, Double>();
		
		
		
		center.put("Head", 8.0);
		center.put("Right Eye", 8.0);
		center.put("Left Eye", 8.0);
		center.put("Neck", 8.0);
		center.put("Torso", 8.0);
		center.put("Back", 8.0);
		center.put("Hips", 8.0);
		
		character.put("Center Limb Status", center);
		
		right.put("Right Shoulder", 8.0);
		right.put("Right Arm", 8.0);
		right.put("Right Elbow", 8.0);
		right.put("Right Hand", 8.0);
		right.put("Right Leg", 8.0);
		right.put("Right Knee", 8.0);
		right.put("Right Foot", 8.0);
		
		character.put("Right Limb Status", right);
		
		left.put("Left Shoulder", 8.0);
		left.put("Left Arm", 8.0);
		left.put("Left Elbow", 8.0);
		left.put("Left Hand", 8.0);
		left.put("Left Leg", 8.0);
		left.put("Left Knee", 8.0);
		left.put("Left Foot", 8.0);
		
		character.put("Left Limb Status", left);
	}
	
	/**
	 * Updates the status of a limb
	 * @param key
	 * @param value
	 */
	private void setLimbStatus(String key, double value)
	{
		//limbInformation.get("Limb Status").put(key, value);
	}
	
	/**
	 * Gets the status of a limb.
	 * @param key
	 * @param value
	 */
	private void LimbStatus(String key)
	{
		//limbInformation.get("Limb Status").get(key);
	}
	
	/**
	 * Assigns random perks.
	 */
	private void buildCharPerks()
	{
		//TODO: Determine randomized perks, set to array
	}
	
	/**
	 * Not sure what to put into here. 
	 */
	private void buildGameInfo()
	{
		//TODO: finish what the game info array should be, then default it in here. 
	}
	
	//Level up stuffs
	/**
	 * Adds a new stat or updates a new one.
	 * If you're trying to update a stat and spell the name of it wrong, 
	 * it'll add it in but that won't do anything because no other function will understand the new stat.
	 * You should never have to add a stat that isn't already defined. 
	 * @param value
	 * @param key
	 */
	private void addStat(String key, double value)
	{
		if(character.get("Character Attributes").get(key) != null)
			character.get("Character Attributes").put(key, character.get("Character Attributes").get(key)+value);
		else
			character.get("Character Attributes").put(key, value);
	}

	/**
	 * Print out all the stats of the character
	 */
	public void printStats()
	{
		
		Set<String> characterKeys = character.keySet();
		int space = 0;
		
		for(String infoKey: characterKeys)
		{
			Set<String> detailKeys = character.get(infoKey).keySet();
			for(String key: detailKeys)
			{
				if(space%3 == 0)
					output.out.println("\n");
				output.out.println(key+": "+character.get(infoKey).get(key));
				space++;
			}
			space = 0;
		}
		
		/*Set<String> attributeKeys = character.get("Character Information").keySet();
		int space = 0;
		for(String key: attributeKeys)
		{
			if(space%3 == 0)
				output.out.print("\n");
			output.out.println(key+": "+character.get("Character Attributes").get(key));
			space++;
		}
		
		space = 0;
		Set<String> dependantAttributeKeys = character.get("Character Dependant Attributes").keySet();
		for(String key: dependantAttributeKeys)
		{
			if(space%3 == 0)
				output.out.print("\n");
			output.out.println(key+": "+character.get("Character Dependant Attributes").get(key));
			space++;
		}
		
		space = 0;
		Set<String> limbKeys = limbStatus.keySet();
		for(String key: limbKeys)
		{
			if(space%3 == 0)
				output.out.print("\n");
			output.out.println(key+": "+limbStatus.get(key));
			output.out.println(limbStatusReport(key));
			space++;
		}*/
	}

	/**
	 * records the users input for a new character name.
	 * @return
	 */
	public String newCharName()
	{
		Scanner input = new Scanner(System.in);
		name = input.nextLine();
		
		return name;
	}
	
	/**
	 * returns the numerical value of the requested character information.
	 * If no key is found, then returns -1
	 * @param key
	 * @return Integer value of charInfo
	 */
	public Double getCharacterInformation(String key)
	{
		if(character.get("Character Information").get(key) != null)
			return character.get("Character Information").get(key);
		else
			return -1.0;
	}
	
	/**
	 * Sets or adds a new key and value into the charInfo HashMap
	 * @param key
	 * @param value
	 */
	public void setCharacterInformation(String key, Double value)
	{
		character.get("Character Information").put(key, value);
	}
	
	public void levelSystemExample(int max)
	{
		double baseRequirement = 100;
		for(int i=0; i<max; i++)
		{
			if(i != 0)
				baseRequirement = (baseRequirement*1.25) + 100;
			output.out.println("The exp requirement for level "+(i+1)+" is: "+baseRequirement);
		}
	}
	
	public String limbStatusReport(String limbQuery)
	{
		double limbStatusNum = 0.0;//limbInformation.get("Limb Status").get(limbQuery);
		long random = System.nanoTime()%11;
		
		if(limbQuery.equals(""))
			return "That limb doesn't exist.";
		
		if(limbStatusNum > 10.0 || limbStatusNum < 0.0)
			return "Either you're a fucking robot with titanium limbs, or you changed the save file, good job, cheater.";
		
		//TODO: Finish these 132 status reports
		if(random == limbStatusNum)
		{//if the random number happens to be the same as the status number, then tell it how it is.
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "Ain't gonna lie, your "+limbQuery+" is fucking gone.";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "Your "+limbQuery+" is dead and decaying. Better go get that removed.";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "You can clearly see your bone sticking out of your "+limbQuery+", you better get that put back in.";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "You have a broken "+limbQuery+", you should see a doctor about getting it fixed.";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "These is a deep cut on your "+limbQuery+".";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "You have a cut on your "+limbQuery+".";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "Your "+limbQuery+" is quite bruised.";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "Your "+limbQuery+" is very sore.";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "You feel like your "+limbQuery+" is normal.";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "You feel very good about the status of your "+limbQuery+".";
			if(limbStatusNum == 10)//Perfect
				return "Your "+limbQuery+" feels fucking amazing and more powerful than ever.";
		}
		if(random == 0)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "Whatever";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";	
		}
		if(random == 1)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "Though you can still feel "+limbQuery+", I assure you, it's gone forever.";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";	
		}
		if(random == 2)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "whatever";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 3)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "whatever";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 4)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "whatever";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 5)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "whatever";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 6)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "whatever";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 7)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "whatever";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 8)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "whatever";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 9)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "whatever";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 10)
		{
			if(limbStatusNum >= 0 && limbStatusNum < 1)//Gone
				return "whatever";
			if(limbStatusNum >= 1 && limbStatusNum < 2)//Decaying
				return "whatever";
			if(limbStatusNum >= 2 && limbStatusNum < 3)//Compound fracture
				return "whatever";
			if(limbStatusNum >= 3 && limbStatusNum < 4)//Broken
				return "whatever";
			if(limbStatusNum >= 4 && limbStatusNum < 5)//Deeply cut
				return "whatever";
			if(limbStatusNum >= 5 && limbStatusNum < 6)//Cut
				return "whatever";
			if(limbStatusNum >= 6 && limbStatusNum < 7)//Bruised
				return "whatever";
			if(limbStatusNum >= 7 && limbStatusNum < 8)//Sore
				return "whatever";
			if(limbStatusNum >= 8 && limbStatusNum < 9)//Normal
				return "whatever";
			if(limbStatusNum >= 9 && limbStatusNum < 10)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		//if for whatever reason it reaches here, meaning it couldn't successfully find the limb or condition number, then print out an ambigious statement about it.
		return "I've got nothing to tell you about that...";
	}
}

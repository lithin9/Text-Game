package model;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Set;

public class Char 
{
	String name = new String();//retrieveStats("name");
	
	LinkedHashMap<String, String> charClass = new LinkedHashMap<String, String>();
	LinkedHashMap<String, Integer> charInfo = new LinkedHashMap<String, Integer>();
	LinkedHashMap<String, Double> charAttributes = new LinkedHashMap<String, Double>();//Previously Stats
	LinkedHashMap<String, Double> dependantCharAttributes = new LinkedHashMap<String, Double>();
	LinkedHashMap<String, Double> skillAttributes = new LinkedHashMap<String, Double>();
	LinkedHashMap<String, Double> limbStatus = new LinkedHashMap<String, Double>();
	LinkedHashMap<String, Double> charStatus = new LinkedHashMap<String, Double>();
	LinkedHashMap<String, Double> charPerks = new LinkedHashMap<String, Double>();
	LinkedHashMap<String, Double> gameInfo = new LinkedHashMap<String, Double>();
	LinkedHashMap<String, Double> hardCoreInfo = new LinkedHashMap<String, Double>();
	LinkedHashMap<String, Double> playerInfluence = new LinkedHashMap<String, Double>();
	LinkedHashMap<String, Double> equipment = new LinkedHashMap<String, Double>();

	
	//TODO: lookup hash mapping.
	/**
	 * Runs all the functions that set up the save file for a new game.
	 */
	public void newCharacter()
	{
		//Set all variables.
		//Things like experience, level, skill points, attribute points
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter a name: ");
		name = input.nextLine();
		System.out.println("Please choose a class:\n1.Fighter\n2.Rogue\n3.Acolyte");
		switch (input.nextLine()) {
		case "1": 
			charClass.put("Main", "Fighter");
		break;
		case "2":
			charClass.put("Main", "Rogue");
		break;
		case "3": 
			charClass.put("Main", "Acolyte");
		break;
		}
		buildCharInfo();
		generateBonusStats();
		buildBaseStats();
		generateRandomStats();
		buildDependantStats();
		buildLimbStatus();
		buildCharStatus();
		buildCharPerks();
		buildGameInfo();
		
		saveFile();
		input.close();
	}

	/**
	 * Sets the character into like experience, level, skillpoints, attributePoints, and morality
	 * All values default to zero, uses charInfo[][].
	 */
	private void buildCharInfo() 
	{
		charInfo.put("Experience", 0);
		charInfo.put("Level", 1);
		charInfo.put("Skill Points", 3);
		charInfo.put("Attribute Points", 50);
		charInfo.put("Morality", 0);
	}

	/**
	 * Sets the defaults for the character stats.
	 */
	private void buildBaseStats()
	{
		//Character Status - Columns
		
		/*NOTE: Until level 5, where the player will be able to choose a class,
		 *they will gain 0.4 points in the attribute charactistic of their class:
		 *fighter:physical,acolyte:mental,rogue:ability
		 *They will also gain 0.2 in all others.
		 *However, once they reach level 5 it will assign 0.1 in all stats until they choose a sub-class
		 */
		/*Attributes
		 * //Physical Status\\
		 *Main stats gain 2 points per level, Secondary stats gain 1 points per level, Ternary stats gain 0.5 points per level
		 *Everything else gains 0.1 points per level. This isn't a lot, but it'll add up and help characters with little wisdom keep balanced.
		 */
		charAttributes.put("Strength", 1.0);
		/*//Warrior Main Stat, Berserker Secondary Stat, Assassin Ternary Stat
		 *This divided by level, then divided by 2 will determine the amount of attack power per level gained
		 */
		charAttributes.put("Endurance", 1.0);
		/*Berserker Main Stat, Templar Secondary Stat, Druid Ternary Stat
		 * Endurance divided by level, then divided by ten will be the amount of armor class per level added.
		 * Every point of endurance gained will add 4 stamina
		 * Endurance divided by level is the critical resistance
		 */
		charAttributes.put("Constitution", 1.0);
		/*Templar Main Stat, Warrior Secondary Stat, Warlock Ternary Stat
		 *Every point of constitution will add 4 health.
		 *Constitution divided by level is the block chance
		 */
		  //Mental Status\\
		charAttributes.put("Intellect", 1.0);
		/*Wizard Main Stat, Druid Secondary Stat, Shadow Ternary Stat
		 *This is going to be a bitch for most people: Intellect divided by level is how fast you gain exp.
		 *Say you have 5 int and level 2, you'll get 2.5 times the amount of exp. So Wizards, though incredibly 
		 *weak will make up for it by growing stronger, faster. Intellect also determines magic power 
		 */
		charAttributes.put("Perception", 1.0);
		/*Warlock Main Stat, Wizard Secondary Stat, Ranger Ternary Stat
		 *Perception plus level is the percent chance to notice things when first entering a coordinate (x,y) 
		 *I'm not sure if I should allow going back and forth to continue searching, I think it won't matter.
		 *Not everything will have secrets. Plus who is going to want to type go south, go north a hundred times for chance of maybe finding something?
		 */
		charAttributes.put("Wisdom", 1.0);
		/*Druid Main Stat, Warlock Secondary Stat, Templar Ternary Stat
		 *Wisdom divided by level, plus 3 will be the amount of stat points available to spend per levelup. 
		 *The plus three ensures that no one will get stuck, never being able to raise their wisdom.
		 *Wisdom is also Magical Resistence, Wisdom divided by level, then divided by ten will be the amount of magical resistence per level added.
		 *Ability/Skill Status
		 */
		charAttributes.put("Agility", 1.0);
		/* Assassin Main Stat, Shadow Secondary Stat, Berserker Ternary Stat
		 * Agility determines the amount of attacks per turn and dodge chance and moves per turn
		 * Dodge chance equals agility divided by level
		 * With a maximum of 10 moves per turn, unless using a mount, MPT is determined by agility divided by 5, and is only needed in combat
		 * Attacks per turn determined by the following formula: (yourAgility+(yourLevel-EnemyLevel))/(enemyAgility+(enemyLevel-youLevel))
		 * For example, following the formula a player with the following stats vs an enemy with the following stats will have this many attacks per turn:
		 * (30+(15-17))/(23+(17-15)) = 1.12 attacks per turn. Each attack will spend 1 APT(attacks per turn), unless using a special ability,
		 * leaving them with an additional .12 APT, which adds up and will eventually allow them to attack twice. Same goes for enemies,
		 * Say he had 10 points less agility it he would have .72 APT, and wouldn't be able to attack until the second turn, unless he started the fight.
		 * The enemy would have the advantage with 1.38 APT. This will make agility a very useful survival skill.
		 * Though focusing in only agility, or any single stat will cause bad results, even if this a very strong stat.
		 * Note: Even if the user hasn't enough APT to make an attack, they will be able to use items, and move away from the enemy. 
		 * SIDENOTE: Enemy movement algorithm, since sideways movement is allowed, calculate the movement needed to get near enemy by taking player coordinates,
		 * and if x is the same but y is different, move north or south, else if x is different by y is the same move west or east,
		 * (sideways movements) else if x and y are different, then take enemy's x coordinate and subtract the player's x coordinate,
		 * if positive then need enemy needs to move east, if negative then needs to move west
		 * same with y, and if negative result the move south, if positive then move north.
		 * Now with sideways movements, if x is positive and y is negative, go south east. if x = positive and y = positive go north east
		 * if x is negative and y is negative, go south west. if x is negative and y is positive go north west
		 * Very simple shit. if obstacle is in the way then try to go around, idk lol
		 * Obstacles will block view. If an enemy can't see you, he will go where he last saw you, otherwise wander aimlessly. Same with stealth.
		 */
		charAttributes.put("Accuracy", 1.0);
		/* Ranger Main Stat, Assassin Secondary Stat, Warrior Ternary Stat 
		 * Determines hit chance. Level plus (accuracy )
		 * Determines crit chance. Accuracy divided by 3.
		 * TODO:Finish formula
		 */
		charAttributes.put("Concentration", 1.0);//Shadow Main Stat, Ranger Secondary Stat, Wizard Ternary Stat
		/*
		 * Determines magical hit chance
		 */
		
		/*
		 * Level Scaling System:
		 * Dividing stats by level ensures that the user is constantly on par with the scales of the current level monster,
		 * while combat efficiency is often determined with level difference in mind, scaling doesn't mean weaker fighter, just updated standards. 
		 * This also means that if you don't constantly improve one subject, you will become weaker in that subject(compared to others your level)
		 * This adds a competition among stats. As well as difficult choices, whether to keep a balanced char and suffer in all aspects,
		 * put all your points in wisdom until you can gain like 20 per level, or increase stats related to your class and skills?
		 * Wisdom path may be good in the very long run, but it will make them incredibly vulnerable. They will have to stay away from combat.
		 */
		
		/*
		 * Leveling system:
		 * The user must obtain enough exp to equal or go over ((level * 100)+100). Say level 0 characters will need 100 exp,
		 * lvl 1 will need 200 and so on. I believe that in order to make this work, monsters/enemies must be insane to kill,
		 * but characters will also be able to other things like quests. IDK, it needs some work. I want it to be in such a way that 
		 * players will have to strategically fight their enemies. They will have to play to their strengths, and letting one enemy 
		 * get past them could be fatal. Hardcore mode will make all enemies have double stats. There will be mini bosses that can appear out of no where.
		 */
		/*
		 * Combat system:
		 * You will be able to target specific limbs. Use Slice on Left Arm, will cause a character to use the skill slice against the enemies
		 * left arm, and will do damage to that limb based on the percent of health taken away, so if someone has status 10 limb (perfect)
		 * and they deal 20 damage out of the enemies total health of 30, that's 66.66... percent, meaning the limb will suffer all the way to 
		 * status 4, which is a deep cut. Now if they had limb status 8, then their limb would be totally fucked (down to status 2 which is 
		 * dead/decaying). And they wouldn't be able to use that arm. This will allow disabling enemies. Spells and skills can be targeted for 
		 * disabling, so instead of having to kill enemies, you can simply disable them and kill them after, or leave them alone.
		 * This will help survivability when surrounded by enemies. It will also help less tankier characters. Say you are a ranger, and want to stop
		 * everyone from getting near you, you will simply shoot their feet or legs. Not specifying a limb to hit will cause it to randomly choose one
		 * random(time%10) <-- returns values 0-9. Which can represent every limb. Magic: Some spells are going to be more useful than others,
		 * restoring limb status (to a degree, like once at 0, that limb is gone forever, if at 1 then you need to be a master to get it back,
		 * but it will probably kill you before then). Things like frost and electricity will help in disabling enemies, things like fire and electricity
		 * will help in dealing damage to multiple enemies, things like frost and wind will help in slowing/halting enemies. Combining spells will be
		 * another subject. And by that I mean that I haven't thought of it yet.
		 * Classes will have varying numbers of skills they can use. Main classes (fighter, rogue, acolyte) will all have very few skills, basic attacks 
		 */
		/*
		 * Story:
		 * Since I am using these lines to define much of the game mechanics, I might as well tell a little about the story.
		 * The player will wake up in one of 4 cities, based on race. They will awake in one of three areas in that city, based on class.
		 * They will instantly be put into danger, waking up in the streets, with a massive hangover. Or some shit like that. I'm debating whether or not to follow the dragon age 
		 * style of intro where the user chooses a background 'n shit like that. 
		 * Another reason why I don't know how I want it done, is because I want people to be able to do a rogue like legacy, where they can find their previous dead body and
		 * have portion of their gear. I'll do this by letting them have a continue choice when they die. This will essentially create a new character, allowing them to choose new
		 * class, race, other shit. But the file will have data about all previously kill characters. Unique items will be lost forever if the character wearing them died, and
		 * the item was "looted", or I can make it just a possibility to find the gear in the nearby area. Quest important items will always be found. 
		 * I just want to make sure to prevent duplicating items, say a master ring of fire that grants the user use of master level fire spells, without needing to know them,
		 * it'll sort of just be a random spell effect each use.
		 * 
		 * Another thing to consider: Randomly generated dungeons 'n shit. Walkable maps, like Diablo, maybe have them auto generated, or have a set of premade maps were we can
		 * spice it up for all NEW characters, not legacy chars, they should retain the same so they can easily find their previous life, or if in hardcore mode have it change the
		 * map every time they go into it, make them really confused.
		 */
		
		/*Class descriptions
		 * Main Class: Fighter
		 * Main Stat: Health
		 * Secondary Stat: Stamina
		 * Ternary Stat: Magicka
		 * Description: Fighters are the tanks of the rpg, they will have the most health and focus mostly on melee combat, and surviving enough damage to deal it back. They can wear
		 * heavier equipment and stronger, bigger weapons with ease.
		 * Subclass: Warrior
		 * Warriors are a well rounded sub class of fighter, warriors specialize in balance of the melee art, focusing in strength
		 */
		
		
	}
	
	private void buildDependantStats()
	{
		//Main stats gain 5 points per level, Secondary stats gain 2.5 points per level, Ternary stats gain 1.25 points per level
		dependantCharAttributes.put("Health", (charAttributes.get("Constitution") * 4.0));			//Fighter Main Stat, Rogue Secondary Stat, Acolyte Ternary Stat
		
		//Self explainatory
		dependantCharAttributes.put("Stamina", (charAttributes.get("Endurance") * 4.0));
		/*Rogue Main Stat, Fighter Secondary Stat, Acolyte Ternary Stat
		 *Determines the amount of melee abilities that can be used per fight
		 */
		dependantCharAttributes.put("Magicka", (charAttributes.get("Wisdom") * 4.0));
		/*Acolyte Main Stat, Rogue Ternary Stat, Fighter Ternary Stat
		 *Determines the amount of magical abilities that can be used in a fight
		 */
		//Character Status Recovery
		//Recovery is disable in hardcore mode.
		dependantCharAttributes.put("Health Regen", ((charAttributes.get("Endurance") + charAttributes.get("Constitution"))/ 10));	//(Endurance+Constitution)/10
		dependantCharAttributes.put("Magicka Regen", ((charAttributes.get("Intellect") + charAttributes.get("Concentration"))/ 10));	//(Intellect+Concentration)/10
		dependantCharAttributes.put("Stamina Regen", ((charAttributes.get("Agility") + charAttributes.get("Concentration"))/ 10));	//(Agility+Concentration)/10
		
		//Defensive Status
		dependantCharAttributes.put("Armor Class", ((charAttributes.get("Endurance") / charInfo.get("Level")) / 10));
		/*
		 * Armor Class determines physical damage reduction: (Armor Class + (yourLevel - enemyLevel)) / yourLevel(or their level)) = Percent of damage to take away from
		 * Example: You have 23 armor class, you're level 10 and the enemy is level 12. (23+(10-12))/10 = 2.1
		 */
		dependantCharAttributes.put("Magic Resistance", 0.5);//TODO: Convert resistances into individual magics
		/*
		 *
		 */

		dependantCharAttributes.put("Resiliance", ((charAttributes.get("Endurance") / 3) + charInfo.get("Level")));//Used to be critical resistance
		/*
		 *
		 */
		dependantCharAttributes.put("Dodge", (charAttributes.get("Constitution") + charInfo.get("Level")));
		/*
		 * Percent chance that the character can dodge an attack.
		 * Note that the enemies agility will negate your dodge chance.
		 */
		dependantCharAttributes.put("Block", (charAttributes.get("Constitution") + charInfo.get("Level")));
		/*
		 *
		 */
		dependantCharAttributes.put("Poison Resistance", 0.0);
		/*
		 * Poison Resistance is the effectiveness of poisons against you
		 * Calculated by if (level of posion - poison resistance) is less than 1, in which case the poison won't do anything,
		 * but for every point above 1 it will do 2 points of damage
		 */
		
		//Offensive Status
		dependantCharAttributes.put("Attack Power", ((charAttributes.get("Endurance") * 1.33) + charInfo.get("Level")));
		/*
		 * Determined by (Strength/Level)/2
		 * This will be how much damage is done by the character.
		 */
		dependantCharAttributes.put("Magic Power", ((charAttributes.get("Intellect") * 1.33) + charInfo.get("Level")));
		/*
		 * Determined by (Intelligence/Level)/2
		 * This will be how much spell damage is done by the character.
		 * Spells will have formulas that incorporate this. Includes healing power.
		 */
		dependantCharAttributes.put("Critical Chance", ((charAttributes.get("Accuracy") / 3) + charInfo.get("Level")));
		/*
		 * Determined by accuracy.
		 */
		dependantCharAttributes.put("Physical Hit Chance", ((charAttributes.get("Accuracy") * 4) + charInfo.get("Level")));
		/*
		 * Determined by accuracy
		 */
		dependantCharAttributes.put("Magical Hit Chance", ((charAttributes.get("Concentration") * 4) + charInfo.get("Level")));
		/*
		 * 
		 */
		if(charClass.get("Main").equals("Fighter"))
			dependantCharAttributes.put("Combat Mastery", (charAttributes.get("Strength") + charAttributes.get("Endurance") + charAttributes.get("Constitution")));
		else if(charClass.get("Main").equals("Acolyte"))
			dependantCharAttributes.put("Combat Mastery", (charAttributes.get("Intellect") + charAttributes.get("Perception") + charAttributes.get("Wisdom")));
		else if(charClass.get("Main").equals("Rogue"))
			dependantCharAttributes.put("Combat Mastery", (charAttributes.get("Agility") + charAttributes.get("Accuracy") + charAttributes.get("Concentration")));
		else
			dependantCharAttributes.put("Combat Mastery", 0.0);
		/*
		 * Combat mastery is determined by adding all three stats of the chosen class' stat group: physical, mental, ability/skill 
		 * and dividing it by the current level. Dividing by level ensures a scale, but because your combat mastery has decreased, 
		 * doesn't mean you are less of a fighter, it means others on your level are better.
		 * Combat Mastery: Adds a bonus to all your combat checks related to your class. Say you are a ranger, and you go for a long ranged
		 * shot, you will get a bonus of the value of your combat mastery to be able to hit that. Refuse to put stats in your main, and your
		 * combat will get rusty.
		 */
	}
	
	private void generateBonusStats()
	{
		double gainStatAt = 0.0;
		while(true)
		{
			double random = randomizer(100.0);
			if(random > gainStatAt || random == 0)
			{
				if(random == 100.0)//0 means exactly 100.
					charInfo.put("Attribute Points", (charInfo.get("Attribute Points") + 1));//Give a bonus stat for scoring so well.
				charInfo.put("Attribute Points", (charInfo.get("Attribute Points") + 1));
				gainStatAt += 20.0;
				if(gainStatAt > 90.0)
					gainStatAt = 90.0;
			}
			else
				break;
		}
	}

	private void generateRandomStats()
	{
		double physicalStatChanceMin = 0.0;
		double physicalStatChanceMax = 0.0;
		double magicalStatChanceMin = 0.0;
		double magicalStatChanceMax = 0.0;
		double skillStatChanceMin = 0.0;
		double skillStatChanceMax = 0.0;
		
		if(charClass.get("Main").equals("Fighter"))
		{
			 physicalStatChanceMin = 30.0;
			 physicalStatChanceMax = 69.99;
			 magicalStatChanceMin = 0.0;
			 magicalStatChanceMax = 29.99;
			 skillStatChanceMin = 70.0;
			 skillStatChanceMax = 100.0;
		}
		else if(charClass.get("Main").equals("Acolyte"))
		{
			 physicalStatChanceMin = 0.0;
			 physicalStatChanceMax = 29.99;
			 magicalStatChanceMin = 30.0;
			 magicalStatChanceMax = 69.99;
			 skillStatChanceMin = 70.0;
			 skillStatChanceMax = 100.0;
		}
		else if(charClass.get("Main").equals("Rogue"))
		{
			 physicalStatChanceMin = 70.0;
			 physicalStatChanceMax = 100.0;
			 magicalStatChanceMin = 0.0;
			 magicalStatChanceMax = 29.99;
			 skillStatChanceMin = 30.0;
			 skillStatChanceMax = 69.99;
		}
		for(int i=1; i<charInfo.get("Attribute Points"); i++)
		{
			double random = randomizer(100);
			double randomStat = randomizer(3);
			if(magicalStatChanceMin <= random && magicalStatChanceMax >= random)
			{
				if(randomStat >= 0.0 && randomStat <= 0.99)
					charAttributes.put("Intellect", (charAttributes.get("Intellect") + 1));
				else if(randomStat >= 1.0 && randomStat <= 1.99)
					charAttributes.put("Perception", (charAttributes.get("Perception") + 1));
				else if(randomStat >= 2.0 && randomStat <= 3.0)
					charAttributes.put("Wisdom", (charAttributes.get("Wisdom") + 1));
			}
			else if(physicalStatChanceMin <= random && physicalStatChanceMax >= random)
			{
				if(randomStat >= 0.0 && randomStat <= 0.99)
					charAttributes.put("Strength", (charAttributes.get("Strength") + 1));
				else if(randomStat >= 1.0 && randomStat <= 1.99)
					charAttributes.put("Endurance", (charAttributes.get("Endurance") + 1));
				else if(randomStat >= 2.0 && randomStat <= 3.0)
					charAttributes.put("Constitution", (charAttributes.get("Constitution") + 1));
			}
			else if(skillStatChanceMin <= random && skillStatChanceMax >= random)
			{
				if(randomStat >= 0.0 && randomStat <= 0.99)
					charAttributes.put("Agility", (charAttributes.get("Agility") + 1));
				else if(randomStat >= 1.0 && randomStat <= 1.99)
					charAttributes.put("Accuracy", (charAttributes.get("Accuracy") + 1));
				else if(randomStat >= 2.0 && randomStat <= 3.0)
					charAttributes.put("Concentration", (charAttributes.get("Concentration") + 1));
			}
		}
	}
	
	/**
	 * @deprecated
	 */
	private void generateBonusStats_old()
	{
		int gainStatAt = 40;
		int additionalStatCount = 0;
		while(true)
		{
			long random = (System.nanoTime()%100);
			if(random > gainStatAt || random == 0)
			{
				if(random == 0)//0 means exactly 100.
					additionalStatCount++;//Give a bonus stat for scoring so well.
				additionalStatCount++;
				gainStatAt = gainStatAt + 20;
				if(gainStatAt < 90)
					gainStatAt = 90;
			}
			else
				break;
		}
		if(additionalStatCount > 0)
		{
			for(int i=0; i<additionalStatCount; i++)
			{
				int random = (int) randomizer(12.0);
				
				switch(random){
				case 0:
					addStat("Strength", 1);
					break;
				case 1:
					addStat("Endurance", 1);
					break;
				case 2:
					addStat("Constitution", 1);
					break;
				case 3:
					addStat("Intellect", 1);
					break;
				case 4:
					addStat("Perception", 1);
					break;
				case 5:
					addStat("Wisdom", 1);
					break;
				case 6:
					addStat("Agility", 1);
					break;
				case 7:
					addStat("Accuracy", 1);
					break;
				case 8:
					addStat("Concentration", 1);
					break;
				case 9:
					addStat("Agility", 1);
					break;
				case 10:
					addStat("Accuracy", 1);
					break;
				case 11:
					addStat("Concentration", 1);
					break;
				}
			}
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
		limbStatus.put("Head", 8.0);
		limbStatus.put("Right Eye", 8.0);
		limbStatus.put("Left Eye", 8.0);
		limbStatus.put("Neck", 8.0);
		limbStatus.put("Torso", 8.0);
		limbStatus.put("Back", 8.0);
		limbStatus.put("Right Shoulder", 8.0);
		limbStatus.put("Left Shoulder", 8.0);
		limbStatus.put("Right Arm", 8.0);
		limbStatus.put("Left Arm", 8.0);
		limbStatus.put("Right Hand", 8.0);
		limbStatus.put("Left Hand", 8.0);
		limbStatus.put("Right Leg", 8.0);
		limbStatus.put("Left Leg", 8.0);
		limbStatus.put("Right Foot", 8.0);
		limbStatus.put("Left Foot", 8.0);
	}
	
	/**
	 * Assign stats to a new character
	 */
	private void buildCharStatus()
	{
		//TODO: Randomize stats, I guess.
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
	

	/**
	 * Creates a new save file and records all the random stuffs.
	 */
	private void saveFile()
	{
		//TODO: write the save file
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
		if(charAttributes.get(key) != null)
			charAttributes.put(key, charAttributes.get(key)+value);
		else
			charAttributes.put(key, value);
	}


	/**
	 * Print out all the stats of the character
	 */
	public void printStats()
	{
		Set<String> attributeKeys = charAttributes.keySet();
		int space = 0;
		for(String key: attributeKeys)
		{
			if(space%3 == 0)
				System.out.print("\n");
			System.out.println(key+": "+charAttributes.get(key));
			space++;
		}
		
		Set<String> dependantAttributeKeys = dependantCharAttributes.keySet();
		for(String key: dependantAttributeKeys)
		{
			if(space%3 == 0)
				System.out.print("\n");
			System.out.println(key+": "+dependantCharAttributes.get(key));
			space++;
		}
	}
	

	/**
	 * Load the save file attached to the 
	 * @param charName
	 */
	public void loadGame(String charName)
	{
		//TODO: Load in the save file based on charName
		//Use file reader or whatever, arrays are differentiated by ||, elements seperated by commas
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
	public Integer getCharacterInformation(String key)
	{
		if(charInfo.get(key) != null)
			return charInfo.get(key);
		else
			return -1;
	}
	
	
	/**
	 * Sets or adds a new key and value into the charInfo HashMap
	 * @param key
	 * @param value
	 */
	public void setCharacterInformation(String key, Integer value)
	{
		charInfo.put(key, value);
	}
	
	
	public double randomizer(double max)
	{
		double random = Math.random();
		DecimalFormat df = new DecimalFormat("#.##");
		
		return Double.valueOf(df.format(max*random));
	}
	
	public double randomizer(double max, double modifier)
	{
		double random = Math.random();

		DecimalFormat df = new DecimalFormat("#.##");
		return Double.valueOf(df.format((max*random)+modifier));
	}
	
	public double[] randomizer(double max, int rolls)
	{
		double random;
		
		DecimalFormat df = new DecimalFormat("#.##");
		double[] results = new double[rolls];
		for(int i=0; i<rolls; i++)
		{
			random = Math.random();
			results[i] = Double.valueOf(df.format(max*random));
		}
		return results;
	}
	
	public double[] randomizer(double max, int rolls, double modifier)
	{
		double random;
		
		double[] results = new double[rolls];
		for(int i=0; i<rolls; i++)
		{
			random = Math.random();

			DecimalFormat df = new DecimalFormat("#.##");
			results[i] = Double.valueOf(df.format((max*random)+modifier));
		}
		return results;
	}
	
	public void levelSystemExample(int max)
	{
		double baseRequirement = 100;
		for(int i=0; i<max; i++)
		{
			if(i != 0)
				baseRequirement = (baseRequirement*1.25) + 100;
			System.out.println("The exp requirement for level "+(i+1)+" is: "+baseRequirement);
		}
	}
	
	public String limbStatusReport(String limbQuery)
	{
		double limbStatusNum = limbStatus.get(limbQuery);;
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

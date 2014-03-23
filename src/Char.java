import java.util.Scanner;


public class Char 
{
	String name = new String();//retrieveStats("name");
	String charClass = new String();//retrieveStats("class");
	String[][] charInfo = new String[2][4];
	String[][] Stats = new String[3][27];//retrieveStats2dArr("stats");
	String[][] limbStatus = new String[2][10];//retrieveStats2dArr("limbStatus");
	String[][] charStatus = new String[2][9];
	String[][] charPerks = new String[2][20];//TODO: fix this number
	String[][] gameInfo = new String[2][3];
	String[][] hardCoreInfo = new String[2][5];//These settings will be used only for characters selecting hardcore
	String[][] playerInfluence = new String[2][3];
	String[][] equipment = new String[2][10];//[0][i] = Armor slot names, [1][i] = armor ID(links to a armor Class with all the ids and related stats.
	
	
	//TODO: lookup hash mapping.
	/**
	 * Runs all the functions that set up the save file for a new game.
	 */
	public void newCharacter()
	{
		//Set all variables.
		//Things like experience, level, skill points, attribute points
		@SuppressWarnings("resource")//TODO: check to see if closing input will fuck up other scanners
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter a name: ");
		name = input.nextLine();
		System.out.print("\nPlease choose a class:\n1.Fighter\n2.Rogue\n3.Acolyte\n");
		switch (input.nextLine()) {
		case "1": 
			charClass = "Fighter";
		break;
		case "2":
			charClass = "Rogue";
		break;
		case "3": 
			charClass = "Acolyte";
		break;
		}
		newCharInfo();
		newBaseStats();
		newSkills();
		newLimbStatus();
		newCharStatus();
		newCharPerks();
		newGameInfo();
		
		newSaveFile();
	}
	/**
	 * Sets the character into like experience, level, skillpoints, attributePoints, and morality
	 * All values default to zero, uses charInfo[][].
	 */
	private void newCharInfo() 
	{
		charInfo[0][0] = "Experience";
		charInfo[0][1] = "Level";
		charInfo[0][2] = "SkillPoints";
		charInfo[0][3] = "AttributePoints";
		charInfo[0][4] = "Morality";

		charInfo[1][0] = "0";
		charInfo[1][1] = "0";
		charInfo[1][2] = "0";
		charInfo[1][3] = "0";
		charInfo[1][4] = "0";
	}
	/**
	 * Sets the defaults for the character stats.
	 */
	private void newBaseStats()
	{
		//Character Status - Columns
		//Main stats gain 5 points per level, Secondary stats gain 2.5 points per level, Ternary stats gain 1.25 points per level
		Stats[0][0] = "Health";			//Fighter Main Stat, Rogue Secondary Stat, Acolyte Ternary Stat
		//Self explainatory
		Stats[0][1] = "Stamina";		
		/*Rogue Main Stat, Fighter Secondary Stat, Acolyte Ternary Stat
		 *Determines the amount of melee abilities that can be used per fight
		 */
		Stats[0][2] = "Magicka";			
		/*Acolyte Main Stat, Rogue Ternary Stat, Fighter Ternary Stat
		 *Determines the amount of magical abilities that can be used in a fight
		 */
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
		Stats[0][3] = "Strength";
		/*//Warrior Main Stat, Berserker Secondary Stat, Assassin Ternary Stat
		 *This divided by level, then divided by 2 will determine the amount of attack power per level gained
		 */
		Stats[0][4] = "Endurance";		
		/*Berserker Main Stat, Templar Secondary Stat, Druid Ternary Stat
		 * Endurance divided by level, then divided by ten will be the amount of armor class per level added.
		 * Every point of endurance gained will add 4 stamina
		 * Endurance divided by level is the critical resistance
		 */
		Stats[0][5] = "Constitution";	
		/*Templar Main Stat, Warrior Secondary Stat, Warlock Ternary Stat
		 *Every point of constitution will add 4 health.
		 *Constitution divided by level is the block chance
		 */
		  //Mental Status\\
		Stats[0][6] = "Intellect";		
		/*Wizard Main Stat, Druid Secondary Stat, Shadow Ternary Stat
		 *This is going to be a bitch for most people: Intellect divided by level is how fast you gain exp.
		 *Say you have 5 int and level 2, you'll get 2.5 times the amount of exp. So Wizards, though incredibly 
		 *weak will make up for it by growing stronger, faster. Intellect also determines magic power 
		 */
		Stats[0][7] = "Perception";
		/*Warlock Main Stat, Wizard Secondary Stat, Ranger Ternary Stat
		 *Perception plus level is the percent chance to notice things when first entering a coordinate (x,y) 
		 *I'm not sure if I should allow going back and forth to continue searching, I think it won't matter.
		 *Not everything will have secrets. Plus who is going to want to type go south, go north a hundred times for chance of maybe finding something?
		 */
		Stats[0][8] = "Wisdom";
		/*Druid Main Stat, Warlock Secondary Stat, Templar Ternary Stat
		 *Wisdom divided by level, plus 3 will be the amount of stat points available to spend per levelup. 
		 *The plus three ensures that no one will get stuck, never being able to raise their wisdom.
		 *Wisdom is also Magical Resistence, Wisdom divided by level, then divided by ten will be the amount of magical resistence per level added.
		 *Ability/Skill Status
		 */
		Stats[0][9] = "Agility";
		/* Assassin Main Stat, Shadow Secondary Stat, Berserker Ternary Stat
		 * Agility determines the amount of attacks per turn and dodge chance and moves per turn
		 * Dodge chance equals agility divided by level
		 * With a maximum of 10 moves per turn, unless using a mount, MPT is determined by agility divided by 5, and is only needed in combat
		 * Attacks per turn determined by the following formula: (yourAgility+(yourLevel-EnemyLevel))/(enemyAgility+(enemyLevel-youLevel))
		 * For example, following the formula a player with the following stats vs an enemy with the following stats will have this many attacks per turn:
		 * (30+(15-17))/(23+(17-15)) = 1.12 attacks per turn. Each attack will spend 1 APT(attacks per turn), unless using a special ability,
		 * leaving them with an additional .12 APT, which adds up and will eventually allow them to attack twice. Same goes for enemies,
		 * Say he had 10 points less agility it he would have .72 APT, and wouldn't be able to attack until the second turn, unless he started the fight.
		 * The enemy would have the advantage with 1.38 APT. This will make agility and very useful survival skill.
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
		 */
		Stats[0][10] = "Accuracy";
		/* Ranger Main Stat, Assassin Secondary Stat, Warrior Ternary Stat 
		 * Determines hit chance
		 * TODO:Finish formula
		 */
		Stats[0][11] = "Concentration";	//Shadow Main Stat, Ranger Secondary Stat, Wizard Ternary Stat
		/*
		 * Determines magical hit chance
		 */
		//Character Status Recovery
		Stats[0][12] = "Health Regen";	//(Endurance+Constitution)/10
		Stats[0][13] = "Magicka Regen";	//(Intellect+Concentration)/10
		Stats[0][14] = "Stamina Regen";	//(Agility+Concentration)/10
		
		//Defensive Status
		Stats[0][15] = "Armor Class";
		/*
		 * Armor Class determines physical damage reduction: (Armor Class + (yourLevel - enemyLevel)) / yourLevel(or their level)) = Percent of damage to take away from
		 * Example: You have 23 armor class, you're level 10 and the enemy is level 12. (23+(10-12))/10 = 
		 */
		Stats[0][16] = "Magic Resistance";
		/*
		 *
		 */
		Stats[0][17] = "Critical Resistance";
		/*
		 *
		 */
		Stats[0][18] = "Dodge Chance";
		/*
		 *
		 */
		Stats[0][19] = "Block Chance";
		/*
		 *
		 */
		Stats[0][20] = "Poison Resistance";
		/*
		 * Poison Resistance is the effectiveness of poisons against you
		 * Calculated by if (level of posion - poison resistance) is less than 1, in which case the poison won't do anything,
		 * but for every point about 1 it will do 2 points of damage
		 */
		
		//Offensive Status
		Stats[0][21] = "Attack Power";
		/*
		 * Determined by (Strength/Level)/2
		 * This will be how much damage is done by the character.
		 */
		Stats[0][22] = "Magic Power";
		/*
		 * Determined by (Intelligence/Level)/2
		 * This will be how much spell damage is done by the character.
		 * Spells will have formulas that incorporate this. Includes healing power.
		 */
		Stats[0][23] = "Critical Chance";
		/*
		 * Determined by.. not sure yet, agility is already OP as fuck.
		 */
		Stats[0][24] = "Physical Hit Chance";
		/*
		 *
		 */
		Stats[0][25] = "Magical Hit Chance";
		/*
		 *
		 */
		Stats[0][26] = "Combat Mastery";
		/*
		 * Combat mastery is determined by adding all three stats of the chosen class' stat group: physical, mental, ability/skill 
		 * and dividing it by the current level. Dividing by level ensures a scale, but because your combat mastery has decreased, 
		 * doesn't mean you are less of a fighter, it means others on your level are better.
		 * Combat Mastery: Adds a bonus to all your combat checks related to your class. Say you are a ranger, and you go for a long ranged
		 * shot, you will get a bonus of the value of your combat mastery to be able to hit that. Refuse to put stats in your main, and your
		 * combat will get rusty.
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
		//Defualt Values
		Stats[1][0] = "10";				//Health
		Stats[1][1] = "20";				//Stamina
		Stats[1][2] = "15";				//Magicka
		//Physical Status
		Stats[1][3] = "5";				//Strength
		Stats[1][4] = "5";				//Endurance
		Stats[1][5] = "5";				//Constitution
		//Mental Status
		Stats[1][6] = "5";				//Intellect
		Stats[1][7] = "5";				//Perception
		Stats[1][8] = "5";				//Wisdom
		//Ability/Skill Status
		Stats[1][9] = "5";				//Agility
		Stats[1][10] = "5";				//Accuracy 
		Stats[1][11] = "5";				//Concentration
		//Recovery Status
		Stats[1][12] = "1";				//(Endurance+Constitution)/10//Health Regen
		Stats[1][13] = "1";				//"(Intellect+Concentration)/10";//Magicka Regen
		Stats[1][14] = "1";				//"(Agility+Concentration)/10";//Stamina Regen
		//Defensive Status
		Stats[1][15] = "0.5";			//Armor Class
		Stats[1][16] = "0.5";			//Magic Resistance
		Stats[1][17] = "5";				//Critical Resistance
		Stats[1][18] = "5";				//Dodge Chance
		Stats[1][19] = "5";				//Block Chance
		Stats[1][20] = "0";				//Poison Resistance
		//Offensive Status
		Stats[1][21] = "2.5";			//Attack Power
		Stats[1][22] = "2.5";			//Magic Power
		Stats[1][23] = "";				//Critical Chance
		Stats[1][24] = "";				//Hit Chance
		Stats[1][25] = "";				//Magical Hit Chance
		Stats[1][26] = "15";			//Combat Mastery
		
	}
	private void newSkills()
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
				int random = (int) (System.nanoTime()%9);
				switch(random){
				case 0:
					addStat(1,"Strength");
					break;
				case 1:
					addStat(1,"Endurance");
					break;
				case 2:
					addStat(1,"Constitution");
					break;
				case 3:
					addStat(1,"Intellect");
					break;
				case 4:
					addStat(1,"Perception");
					break;
				case 5:
					addStat(1,"Wisdom");
					break;
				case 6:
					addStat(1,"Agility");
					break;
				case 7:
					addStat(1,"Accuracy");
					break;
				case 8:
					addStat(1,"Concentration");
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
	private void newLimbStatus() 
	{
		limbStatus[0][0] = "Head";
		limbStatus[1][0] = "Torso";
		limbStatus[2][0] = "Right Arm";
		limbStatus[3][0] = "Left Arm";
		limbStatus[4][0] = "Right Leg";
		limbStatus[5][0] = "Left Leg";
		limbStatus[6][0] = "Right Hand";
		limbStatus[7][0] = "Left Hand";
		limbStatus[8][0] = "Right Foot";
		limbStatus[9][0] = "Left Foot";

		limbStatus[0][1] = "8";
		limbStatus[1][1] = "8";
		limbStatus[2][1] = "8";
		limbStatus[3][1] = "8";
		limbStatus[4][1] = "8";
		limbStatus[5][1] = "8";
		limbStatus[6][1] = "8";
		limbStatus[7][1] = "8";
		limbStatus[8][1] = "8";
		limbStatus[9][1] = "8";
	}
	private void newCharStatus()
	{
		
	}
	private void newCharPerks()
	{
		//TODO: Determine randomized perks, set to array
	}
	private void newGameInfo()
	{
		//TODO: finish what the game info array should be, then default it in here. 
	}
	private void newSaveFile()
	{
		//TODO: write the save file
	}
	
	//Level up stuffs
	private void addStat(int numberOfStats, String stat)
	{
		//TODO: switch statement that'll add appropriate number of stats to the appropriate stat. Will also update combat stuffs
	}
	/**
	 * Print out all the stats of the user
	 */
	public void printStats()
	{
		for(int i=0; i<Stats.length; i++)
		{
			System.out.println(Stats[0][i]+": "+Stats[1][i]+".");
		}
	}
	
	public void loadGame(String charName)
	{
		//TODO: Load in the save file based on charName
		//Use file reader or whatever, arrays are differentiated by ||, elements seperated by commas
	}
	public String charName()
	{
		Scanner input = new Scanner(System.in);
		String userName = input.nextLine();
		return userName;
	}
	
	public String charInfoReport(String infoQuery)
	{
		String infoToReport = "";
		
		for(int i=0; i<charInfo.length; i++)
		{
			if(infoQuery.equals(charInfo[i][0]))
			{
				infoToReport = charInfo[i][1];
			}
		}
		return infoToReport;
	}
	public String charInfoUpdate()
	{
		return null;
	}
	
	public String limbStatusReport(String limbQuery)
	{
		String limbToReport = "";
		int limbStatusNum = 0;
		long random = System.nanoTime()%11;
		
		for(int i=0; i<limbStatus.length; i++)
		{
			if(limbQuery.equals(limbStatus[i][0]))
			{
				limbToReport = limbStatus[i][0];
				limbStatusNum = Integer.valueOf(limbStatus[i][1]);
			}
		}
		
		if(limbToReport.equals(""))
			return "That limb doesn't exist.";
		
		if(limbStatusNum > 10 || limbStatusNum < 0)
			return "Either you're a fucking robot with titanium limbs, or you changed the save file, good job, cheater.";
		
		//TODO: Finish these 132 status reports
		if(random == limbStatusNum)
		{//if the random number happens to be the same as the status number, then tell it how it is.
			if(limbStatusNum == 0)//Gone
				return "Ain't gonna lie, your "+limbToReport+" is fucking gone.";
			if(limbStatusNum == 1)//Decaying
				return "Your "+limbToReport+" is dead and decaying. Better go get that removed.";
			if(limbStatusNum == 2)//Compound fracture
				return "You can clearly see your bone sticking out of your "+limbToReport+", you better get that put back in.";
			if(limbStatusNum == 3)//Broken
				return "You have a broke "+limbToReport+", you should see a doctor about getting it fixed.";
			if(limbStatusNum == 4)//Deeply cut
				return "These is a deep cut on your "+limbToReport+".";
			if(limbStatusNum == 5)//Cut
				return "You have a cut on your "+limbToReport+".";
			if(limbStatusNum == 6)//Bruised
				return "Your "+limbToReport+" is quite bruised.";
			if(limbStatusNum == 7)//Sore
				return "Your "+limbToReport+" is very sore.";
			if(limbStatusNum == 8)//Normal
				return "You feel like your "+limbToReport+" is normal.";
			if(limbStatusNum == 9)//Very good
				return "You feel very good about the status of your "+limbToReport+".";
			if(limbStatusNum == 10)//Perfect
				return "Your "+limbToReport+" feels fucking amazing and more powerful than ever.";
		}
		if(random == 0)
		{
			if(limbStatusNum == 0)//Gone
				return "Whatever";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";	
		}
		if(random == 1)
		{
			if(limbStatusNum == 0)//Gone
				return "Though you can still feel "+limbToReport+", I assure you, it's gone forever.";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";	
		}
		if(random == 2)
		{
			if(limbStatusNum == 0)//Gone
				return "whatever";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 3)
		{
			if(limbStatusNum == 0)//Gone
				return "whatever";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 4)
		{
			if(limbStatusNum == 0)//Gone
				return "whatever";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 5)
		{
			if(limbStatusNum == 0)//Gone
				return "whatever";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 6)
		{
			if(limbStatusNum == 0)//Gone
				return "whatever";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 7)
		{
			if(limbStatusNum == 0)//Gone
				return "whatever";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 8)
		{
			if(limbStatusNum == 0)//Gone
				return "whatever";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 9)
		{
			if(limbStatusNum == 0)//Gone
				return "whatever";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		if(random == 10)
		{
			if(limbStatusNum == 0)//Gone
				return "whatever";
			if(limbStatusNum == 1)//Decaying
				return "whatever";
			if(limbStatusNum == 2)//Compound fracture
				return "whatever";
			if(limbStatusNum == 3)//Broken
				return "whatever";
			if(limbStatusNum == 4)//Deeply cut
				return "whatever";
			if(limbStatusNum == 5)//Cut
				return "whatever";
			if(limbStatusNum == 6)//Bruised
				return "whatever";
			if(limbStatusNum == 7)//Sore
				return "whatever";
			if(limbStatusNum == 8)//Normal
				return "whatever";
			if(limbStatusNum == 9)//Very good
				return "whatever";
			if(limbStatusNum == 10)//Perfect
				return "whatever";
		}
		//if for whatever reason it reaches here, meaning it couldn't successfully find the limb or condition number, then print out an ambigious statement about it.
		return "I've got nothing to tell you about that...";
	}
	
}
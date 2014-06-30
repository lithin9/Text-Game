package model;

import java.util.LinkedHashMap;
import java.util.HashMap;

public class SkillList
{

	public String NonCombatSkillUse()
	{
		return null;
	}
	
	public String CombatSkillUse()
	{
		return null;
	}
	
	private String SkillList(String charClass, int skill, String infoSelecter)
	{
		return null;
	}
	//Fighter based skill sets
	private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> SkillsList(int skill)
	{
		//String = Skill Name, LinkedHashMap<String = skill information, Double = information value>
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> Skills = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>();
		//This is the container that holds all of the skill information.
		LinkedHashMap<String, LinkedHashMap<String, Double>> SkillInfo = new LinkedHashMap<String, LinkedHashMap<String, Double>>();
		//String: Effect Name/ID, Double: Value that effect will have. Say you have stun, and the value is 2, they'll be stunned for 2 turns, depending on how the hit scores. If it's critical, then it'll be double.
		LinkedHashMap<String, Double> Effects = new LinkedHashMap<String, Double>();
		//Level requirement for this skill
		LinkedHashMap<String, Double> LevelReq = new LinkedHashMap<String, Double>();
		//Specifc requirements for the skill.
		LinkedHashMap<String, Double> Requirements = new LinkedHashMap<String, Double>();
		//All the weapons that this skill can be used with.
		LinkedHashMap<String, Double> ApplicableWeapons = new LinkedHashMap<String, Double>();
		
		
		//Skill slash
		Effects.put("Bleed", 0.75);
		Effects.put("Stun", 2.0);
		//Class name that it's available to. and the level it's available to them
		LevelReq.put("Fighter", 1.0);
		LevelReq.put("Rogue", 1.0);
		LevelReq.put("Acolyte", 5.0);
		
		//The name of the weapon that this skill can be used with. And the multiplier at which it does damage.
		ApplicableWeapons.put("Sword", 1.5);
		
		SkillInfo.put("Effects", Effects);
		SkillInfo.put("Level Requirement", LevelReq);
		SkillInfo.put("Requirements", Requirements);
		SkillInfo.put("Applicable Weapons", ApplicableWeapons);
		
		Skills.put("Slash", SkillInfo);

		SkillInfo = null;
		Effects = null;
		LevelReq = null;
		Requirements = null;
		
		return Skills;
	}
	
	public LinkedHashMap<String, LinkedHashMap<String, Double>> WarriorSkills(int skill)
	{
		return null;
	}
	
	public LinkedHashMap<String, LinkedHashMap<String, Double>> BerserkerSkills(int skill)
	{
		return null;
	}
	public LinkedHashMap<String, LinkedHashMap<String, Double>> DefenderSkills(int skill)
	{
		return null;
	}
	//Acolyte based skill sets
	public LinkedHashMap<String, LinkedHashMap<String, Double>> AcolyteSkills(int skill)
	{
		return null;
	}
	public LinkedHashMap<String, LinkedHashMap<String, Double>> MageSkills(int skill)
	{
		return null;
	}
	public LinkedHashMap<String, LinkedHashMap<String, Double>> SorcererSkills(int skill)
	{
		return null;
	}
	public LinkedHashMap<String, LinkedHashMap<String, Double>> WizardSkills(int skill)
	{
		return null;
	}
	//Rogue base skill sets
	public LinkedHashMap<String, LinkedHashMap<String, Double>> RogueSkills(int skill)
	{
		return null;
	}
	public LinkedHashMap<String, LinkedHashMap<String, Double>> AssassinSkills(int skill)
	{
		return null;
	}
	public LinkedHashMap<String, LinkedHashMap<String, Double>> RangerSkills(int skill)
	{
		return null;
	}
	public LinkedHashMap<String, LinkedHashMap<String, Double>> ThiefSkills(int skill)
	{
		return null;
	}
}

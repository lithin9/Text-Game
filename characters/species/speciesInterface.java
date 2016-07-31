package characters.species;

//TODO: 
interface speciesInterface {
	String info = "";
	Boolean isPlayable = null;

	Double minHeight = 0.0; //Unit is CM
	Double avgHeight = 0.0; //Unit is CM
	Double maxHeight = 0.0; //Unit is CM

	//Attributes
	//Melee attributes
	// Strength
	Double minStrength = 0.0;
	Double avgStrength = 0.0;
	Double maxStrength = 0.0;
	// Endurance
	Double minEndurance = 0.0;
	Double avgEndurance = 0.0;
	Double maxEndurance = 0.0;
	// Constitution
	Double minConstitution = 0.0;
	Double avgConstitution = 0.0;
	Double maxConstitution = 0.0;
	//Mental attributes
	// Intellect
	Double minIntellect = 0.0;
	Double avgIntellect = 0.0;
	Double maxIntellect = 0.0;
	// Perception
	Double minPerception = 0.0;
	Double avgPerception = 0.0;
	Double maxPerception = 0.0;
	// Wisdom
	Double minWisdom = 0.0;
	Double avgWisdom = 0.0;
	Double maxWisdom = 0.0;
	//Skill attributes
	// Agility
	Double minAgility = 0.0;
	Double avgAgility = 0.0;
	Double maxAgility = 0.0;
	// Accuracy
	Double minAccuracy = 0.0;
	Double avgAccuracy = 0.0;
	Double maxAccuracy = 0.0;
	// Concentration
	Double minConcentration = 18.0;
	Double avgConcentration = 0.0;
	Double maxConcentration = 0.0;
	
	String getInfo();
	Boolean getIsPlayable();
	Double getMinHeight();
	Double getAvgHeight();
	Double getMaxHeight();

	Double getMinStrength();
	Double getAvgStrength();
	Double getMaxStrength();
	Double getMinEndurance();
	Double getAvgEndurance();
	Double getMaxEndurance();
	Double getMinConstitution();
	Double getAvgConstitution();
	Double getMaxConstitution();
	Double getMinIntellect();
	Double getAvgIntellect();
	Double getMaxIntellect();
	Double getMinPerception();
	Double getAvgPerception();
	Double getMaxPerception();
	Double getMinWisdom();
	Double getAvgWisdom();
	Double getMaxWisdom();
	Double getMinAgility();
	Double getAvgAgility();
	Double getMaxAgility();
	Double getMinAccuracy();
	Double getAvgAccuracy();
	Double getMaxAccuracy();
	Double getMinConcentration();
	Double getAvgConcentration();
	Double getMaxConcentration();
}

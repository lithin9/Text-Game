package characters.species;

class Human implements speciesInterface {
	private static String info = "Human species info!";
	private final Boolean isPlayable = true;

	private final Double minHeight = 121.0; //Unit is CM
	private final Double avgHeight = 182.0; //Unit is CM
	private final Double maxHeight = 200.0; //Unit is CM

	private final Double minOrganWeight = 9.5; //Unit is KG
	private final Double maxOrganWeight = 18.9; //Unit is KG
	private final Double minBoneWeight = 11.5; //Unit is KG
	private final Double maxBoneWeight = 22.3; //Unit is KG
	private final Double minFatWeight = 1.0; //Unit is KG
	private final Double maxFatWeight = 16.1; //Unit is KG

	//Attributes
	//Melee attributes
	// Strength
	private final Double minStrength = 20.0;
	private final Double avgStrength = 40.0;
	private final Double maxStrength = 55.0;
	// Endurance
	private final Double minEndurance = 20.0;
	private final Double avgEndurance = 35.0;
	private final Double maxEndurance = 60.0;
	// Constitution
	private final Double minConstitution = 20.0;
	private final Double avgConstitution = 35.0;
	private final Double maxConstitution = 45.0;
	//Mental attributes
	// Intellect
	private final Double minIntellect = 10.0;
	private final Double avgIntellect = 40.0;
	private final Double maxIntellect = 55.0;
	// Perception
	private final Double minPerception = 10.0;
	private final Double avgPerception = 32.0;
	private final Double maxPerception = 48.0;
	// Wisdom
	private final Double minWisdom = 15.0;
	private final Double avgWisdom = 28.0;
	private final Double maxWisdom = 65.0;
	//Skill attributes
	// Agility
	private final Double minAgility = 30.0;
	private final Double avgAgility = 35.0;
	private final Double maxAgility = 55.0;
	// Accuracy
	private final Double minAccuracy = 1.0;
	private final Double avgAccuracy = 30.0;
	private final Double maxAccuracy = 55.0;
	// Concentration
	private final Double minConcentration = 18.0;
	private final Double avgConcentration = 40.0;
	private final Double maxConcentration = 60.0;


	public String getInfo() {
		return info;
	}

	public Boolean getIsPlayable() {
		return isPlayable;
	}

	public Double getMinOrganWeight() {
		return minOrganWeight;
	}

	public Double getMaxOrganWeight() {
		return maxOrganWeight;
	}

	public Double getMinBoneWeight() {
		return minBoneWeight;
	}

	public Double getMaxBoneWeight() {
		return maxBoneWeight;
	}

	public Double getMinFatWeight() {
		return minFatWeight;
	}

	public Double getMaxFatWeight() {
		return maxFatWeight;
	}

	public Double getMinHeight() {
		return minHeight;
	}

	public Double getAvgHeight() {
		return avgHeight;
	}

	public Double getMaxHeight() {
		return maxHeight;
	}

	public Double getMinStrength() {
		return minStrength;
	}

	public Double getAvgStrength() {
		return avgStrength;
	}

	public Double getMaxStrength() {
		return maxStrength;
	}

	public Double getMinEndurance() {
		return minEndurance;
	}

	public Double getAvgEndurance() {
		return avgEndurance;
	}

	public Double getMaxEndurance() {
		return maxEndurance;
	}

	public Double getMinConstitution() {
		return minConstitution;
	}

	public Double getAvgConstitution() {
		return avgConstitution;
	}

	public Double getMaxConstitution() {
		return maxConstitution;
	}

	public Double getMinIntellect() {
		return minIntellect;
	}

	public Double getAvgIntellect() {
		return avgIntellect;
	}

	public Double getMaxIntellect() {
		return maxIntellect;
	}

	public Double getMinPerception() {
		return minPerception;
	}

	public Double getAvgPerception() {
		return avgPerception;
	}

	public Double getMaxPerception() {
		return maxPerception;
	}

	public Double getMinWisdom() {
		return minWisdom;
	}

	public Double getAvgWisdom() {
		return avgWisdom;
	}

	public Double getMaxWisdom() {
		return maxWisdom;
	}

	public Double getMinAgility() {
		return minAgility;
	}

	public Double getAvgAgility() {
		return avgAgility;
	}

	public Double getMaxAgility() {
		return maxAgility;
	}

	public Double getMinAccuracy() {
		return minAccuracy;
	}

	public Double getAvgAccuracy() {
		return avgAccuracy;
	}

	public Double getMaxAccuracy() {
		return maxAccuracy;
	}

	public Double getMinConcentration() {
		return minConcentration;
	}

	public Double getAvgConcentration() {
		return avgConcentration;
	}

	public Double getMaxConcentration() {
		return maxConcentration;
	}
}
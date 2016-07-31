package characters;

import characters.species.Species;

/**
 * Model object for characters
 */
public class Char {
	private String name;
	protected characters.species.Species species;
	protected characters.attributes.Strength strength;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

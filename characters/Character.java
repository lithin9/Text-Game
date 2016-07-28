package characters;

public class Character {
	//model.Char character = new model.Char();

	static view.Out output = new view.Out();
	static view.In input = new view.In();
	
	public String name = new String();
	public Boolean isPlayerCharacter;
	public Boolean isNew;
	protected characters.species.Species race;
	protected characters.attributes.Strength strength;
	
	public void start() {
		if(isPlayerCharacter) {
			if(isNew) { //Create new character
				newPlayerCharacter();
			} else {
				load();
			}
		} else {
			newNonPlayerCharacter();
		}
	}
	
	public void newPlayerCharacter() {
		//TODO: Move character creation into here.
		//Let's start off with the character name!
		createName();
		//Next let's ask what kinda race they wanna be.
		race = new characters.species.Species();
		race.selectNewSpecies();
		//Next let's ask them what kinda stats they want.
		output.out.println("Thanks for choosing your race, next let's choose some stats!");
		
	}
	
	public void newNonPlayerCharacter() {
		//TODO: Automate character creation
	}
	
	public void load() {
		
	}
	
	public void createName() {
		if(isPlayerCharacter) {
			output.out.println("Please enter a character name.\n");
			name = input.nextLine();
			output.out.println("You wrote: "+name);
		} else {
			//Generate random name. look at Names txt in files package
			output.out.println("Not yet implmented.\n");
		}
	}
}

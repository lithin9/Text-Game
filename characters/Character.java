package characters;

/**
 * Controller for Char
 */
public class Character {
	//modelOld.Char character = new modelOld.Char();

	static view.Out output = new view.Out();
	static view.In input = new view.In();

	private Char Char = new Char();

	public Boolean isPlayerCharacter;
	public Boolean isNew;


	public Char newChar() {
		if(isPlayerCharacter) {
			if(isNew) { //Create new character
				return this.newPlayerCharacter();
			} else {
				return load();
			}
		} else {
			return newNonPlayerCharacter();
		}
	}

	public Char newPlayerCharacter() {
		//TODO: Move character creation into here.
		//Let's newChar off with the character name!
		createName();
		//Next let's ask what kinda species they wanna be.
		Char.species = new characters.species.Species();
		Char.species.selectNewSpecies();
		//Next let's ask them what kinda stats they want.
		output.out.println("Thanks for choosing your species, next let's choose some stats!");



		return Char;
	}

	public Char newNonPlayerCharacter() {
		//TODO: Automate character creation
		return null;
	}

	public Char load() {

		return null;
	}

	public void createName() {
		if(isPlayerCharacter) {
			output.out.println("Please enter a character name.\n");
			String name = input.nextLine();
			Char.setName(name);
			output.out.println("You wrote: " + name);
		} else {
			//Generate random name. look at Names txt in files package
			output.out.println("Not yet implmented.\n");
		}
	}
}

package characters.species;

import java.util.LinkedHashMap;
import java.util.Set;

public class Species {

	static view.Out output = new view.Out();
	static view.In input = new view.In();
	//TODO: call each attribute and set a min + max
	//TODO: set bonuses to each attribute
	//TODO: Think of other species based shit
	protected LinkedHashMap<String, speciesInterface> speciesObjList = new LinkedHashMap<String, speciesInterface>();
	public speciesInterface curSpecies;
	
	public Species() {
		//Collection of available species.
		characters.species.Human human = new characters.species.Human();
		speciesObjList.put("Human", human);
	}
	
	public void selectNewSpecies() {
		Boolean SpeciesNotSelected = true;
		speciesInterface selectedChoice = null;
		LinkedHashMap<Integer, String> keyChoices = new LinkedHashMap<Integer, String>();
		speciesSelect: while(SpeciesNotSelected) {
			//Output for each species
			int space = 0;
			Set<String> detailKeys = speciesObjList.keySet();
			
			for(String key: detailKeys)
			{
				if(speciesObjList.get(key).getIsPlayable()) {
					if(space % 3 == 0)
						output.out.println("\n");
					output.out.println(++space + ". " + key + ": " + speciesObjList.get(key).getInfo());
					keyChoices.put(space, key);
				}
			}
			Integer raceChoice = input.nextInt();

			if(keyChoices.containsKey(raceChoice)) {
				//Ask for choice
				confirmMenu: while(true) {
					//Get further details, ask if that's what they want, 
					output.out.println("1. Confirm choice.\n2. Get more info about species.\n3. Go back to Race Selection\n");
					Integer menuChoice = input.nextInt();
					//if confirm then set selectedChoice and break out of speciesSelect, else if return then set confirmMenu = true and keep going. display choices again
					if(menuChoice == 1) {
						//Get selected choice
						selectedChoice = speciesObjList.get(keyChoices.get(raceChoice)); //(raceChoice);
						break speciesSelect;
					} else if(menuChoice == 2) {
						output.out.println("Get more info. ");
						output.out.println(speciesObjList.get(keyChoices.get(raceChoice)).getInfo());
					} else if(menuChoice == 3) {
						output.out.println("\nReturning to Species list.\n\n");
						break confirmMenu;
					} else {
						output.out.println("I didn't recognize that input as a valid choice.");
					}
				}
			} else {
				output.out.println("I didn't recognize that input as a valid choice.");
			}
			
		}
		curSpecies = selectedChoice;
	}
}

package characters.species;

import java.util.LinkedHashMap;
import java.util.Set;

public class Species {

	static view.Out output = new view.Out();
	static view.In input = new view.In();
	//TODO: call each attribute and set a min + max
	//TODO: set bonuses to each attribute
	//TODO: set weight min max
	//TODO: set height min max
	//TODO: Think of other race based shit
	protected LinkedHashMap<String, characters.species.speciesInterface> speciesObjList = new LinkedHashMap<String, characters.species.speciesInterface>();
	
	public Species() {
		characters.species.Human human = new characters.species.Human();
		speciesObjList.put("Human", human);
	}
	
	public characters.species.speciesInterface selectNewSpecies() {
		Boolean SpeciesNotSelected = true;
		characters.species.speciesInterface selectedChoice = new characters.species.Human();;
		LinkedHashMap<Integer, String> keyChoices = new LinkedHashMap<Integer, String>();
		speciesSelect: while(SpeciesNotSelected) {
			//Output for each species
			int space = 0;
			Set<String> detailKeys = speciesObjList.keySet();
			
			for(String key: detailKeys)
			{
				if(space%3 == 0)
					output.out.println("\n");
				output.out.println(++space+". "+key+": "+speciesObjList.get(key).getInfo());
				keyChoices.put(space, key);
			}
			String raceChoiceInput = input.nextLine();
			Integer raceChoice = 0;
			try{
				raceChoice = Integer.parseInt(raceChoiceInput);
			} catch(NumberFormatException e) {
				raceChoice = -1;
			}
			output.out.println("Retreived input: "+raceChoiceInput);
			if(keyChoices.containsKey(raceChoice)) {
				//Ask for choice
				confirmMenu: while(true) {
					//Get further details, ask if that's what they want, 
					output.out.println("1. Confirm choice.\n2. Get more info about race.\n3. Go back to Race Selection\n");
					String menuInput = input.nextLine();
					Integer menuChoice = 0;
					try{
						menuChoice = Integer.parseInt(menuInput);
					} catch(NumberFormatException e) {
						menuChoice = -1;
					}
					//if confirm then set selectedChoice and break out of speciesSelect, else if return then set confirmMenu = true and keep going. display choices again
					if(menuChoice == 1) {
						//Get selected choice
						SpeciesNotSelected = false;
						selectedChoice = speciesObjList.get(keyChoices.get(raceChoice)); //(raceChoice);
						break speciesSelect;
					} else if(menuChoice == 2) {
						output.out.println("Get more info. ");
						output.out.println(speciesObjList.get(keyChoices.get(raceChoice)).getInfo());
					} else if(menuChoice == 3) {
						output.out.println("\nReturning to Species list.\n\n");
						break confirmMenu;
					} else {
						output.out.println("I didn't recognize that input as a valid choice: |"+menuInput+"|");
					}
				}
			} else {
				output.out.println("I didn't recognize that input as a valid choice: |"+raceChoiceInput+"|");
			}
			
		}
		return selectedChoice;
	}
}

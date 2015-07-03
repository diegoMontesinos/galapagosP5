package galapagos.genetic.selection;

import galapagos.genetic.DNA;

/**
 * A selection method is the way to pick the mates to crossover
 * for get the new population with the offsprings.
 *
 * Each method for selection must follow the Darwin principle of best-fitness
 * sample but taking care of limit the diversity.
 *
 * @author Diego Montesinos
 */
public interface SelectionMethod {

	/** 
	 * Select a set of couples from an evaluated population.
	 *
	 * The population must be already evaluated.
	 * Need the total fitness of the population and a flag saying
	 * what is wanted: minimize or maximize the fitness.
	 * 
	 * @param population the evaluated population.
	 * @param populationFitness the sum of population fitness
	 * @param minimize what is wanted: minimze or maximize the fitness
	 * @return the set of copules selected
	 */	
	public DNA[][] select(DNA[] population, double populationFitness, boolean minimize);

}
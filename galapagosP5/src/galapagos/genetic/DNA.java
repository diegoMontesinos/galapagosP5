package galapagos.genetic;

/**
 * It represents the genotype (genetic material) of a sample
 * in a population.
 *
 * @author Diego Montesinos
 */
public interface DNA {

	/**
	 * Set a rate of fitness to this genotype.
	 *
	 * @param fitness the fitness value.
	 */
	public void setFitness(double fitness);

	/**
	 * Get the fitness of the genotype.
	 *
	 * @return double the fitness value.
	 */
	public double getFitness();

	/**
	 * Cross this genotype with another, given a crossover method.
	 * Returns the offspring result of the crossover.
	 *
	 * @param mate the mate for cross.
	 * @param crossoverMethod a method for crossover.
	 * @return the offspring result of the crossover.
	 */
	public DNA[] crossover(DNA mate, CrossoverMethod crossoverMethod);

	/**
	 * Mutate the DNA with a given method.
	 *
	 * @param mutationMethod the method for mutate.
	 */
	public void mutate(MutationMethod mutationMethod);

}
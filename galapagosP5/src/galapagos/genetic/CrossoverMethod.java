package galapagos.genetic;

/**
 * A crossover metod recombine the genetic material (DNA)
 * of two samples resulting in a n-new DNA sons called
 * the offspring.
 *
 * @author Diego Montesinos
 */
public interface CrossoverMethod {

	/**
	 * Cross two DNA samples and return the offspring.
	 *
	 * @param mate1 the first mate for cross.
	 * @param mate2 the second mate for cross.
	 * @return the offspring.
	 */
	public Object crossover(DNA mate1, DNA mate2);

}
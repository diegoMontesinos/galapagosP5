package galapagos.genetic;

/**
 * Mutation method that disturb the genetic material
 * of a sample.
 *
 * A mutation is a change or disturb in the generic material
 * DNA that produce a varition in the characteristics of the
 * sample. It present in a spontaneous way and it can be
 * inheritable to the offspring.
 *
 * @author Diego Montesinos
 */
public interface MutationMethod {

	/**
	 * Mutate the given genetic material.
	 *
	 * @param dna the genetic material to mutate.
	 */
	public void mutate(DNA dna);

}
package galapagos.genetic.algorithm;

import galapagos.genetic.DNA;
import galapagos.genetic.codification.CodificationMethod;

public interface DNAGeneticAlgorithm extends DNA {

	/**
	 * Return the genetic material (genotye) as a string.
	 *
	 * @return the DNA as a string
	 */
	public String getGenotype();

	/**
	 * Return the DNA as a phenotype (an Object).
	 *
	 * The possible solution perse, is called the phenotype.
	 * The phenotype is the object that have the characteristics
	 * coded in the genetic material (DNA).
	 *
	 * @param codificationMethod the method to decode the DNA
	 * @return the phenotype object
	 */
	public Object getPhenotype(CodificationMethod codificationMethod);

}
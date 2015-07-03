package galapagos.genetic.algorithm;

import galapagos.genetic.Sample;
import galapagos.genetic.algorithm.codification.CodificationMethod;

public interface SampleGeneticAlgorithm extends Sample {

	public String getGenotype();

	public Object getFenotype(CodificationMethod codificationMethod);

}
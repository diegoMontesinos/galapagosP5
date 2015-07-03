package galapagos.genetic.selection;

import galapagos.genetic.Sample;

public interface SelectionMethod {
	
	public Sample[][] select(Sample[] population, double populationFitness, boolean minimize);

}
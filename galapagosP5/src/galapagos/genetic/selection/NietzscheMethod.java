package galapagos.genetic.selection;

import galapagos.genetic.DNA;

public class NietzscheMethod implements SelectionMethod {

	public DNA[][] select(DNA[] population, double populationFitness, boolean minimize) {

		int nCouples = population.length % 2 == 0 ? (population.length / 2) : (population.length / 2) + 1;
		DNA[][] mating = new DNA[nCouples][2];

		for (int i = 0; i < nCouples; i++) {
			int mate1 = (i * 2) % population.length;
			int mate2 = ((i * 2) + 1) % population.length;

			mating[i][0] = population[mate1];
			mating[i][1] = population[mate2];
		}

		return mating;
	}
}
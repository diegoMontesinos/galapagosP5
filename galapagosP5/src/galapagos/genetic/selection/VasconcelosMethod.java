package galapagos.genetic.selection;

import galapagos.genetic.DNA;
import java.util.Random;

public class VasconcelosMethod implements SelectionMethod {

	Random rand;

	public VasconcelosMethod() {
		this.rand = new Random();
	}

	public DNA[][] select(DNA[] population, double populationFitness, boolean minimize) {
		int nCouples = population.length % 2 == 0 ? (population.length / 2) : (population.length / 2) + 1;
		DNA[][] mating = new DNA[nCouples][2];

		int top = 0;
		int bottom = population.length - 1;
		for (int i = 0; i < nCouples; i++) {
			mating[i][0] = population[top];
			mating[i][1] = population[bottom];

			top++;
			bottom--;
		}

		return mating;
	}

}
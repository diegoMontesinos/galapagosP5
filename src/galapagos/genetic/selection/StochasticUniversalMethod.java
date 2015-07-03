package galapagos.genetic.selection;

import galapagos.genetic.Sample;
import java.util.Random;

public class StochasticUniversalMethod implements SelectionMethod {

	Random rand;

	public StochasticUniversalMethod() {
		this.rand = new Random();
	}	
	
	public Sample[][] select(Sample[] population, double populationFitness, boolean minimize) {
		
		double[] selectionProbs = new double[population.length];
		if(minimize) {
			double[] fitnessAux = new double[population.length];
			double totalFitnessAux = 0.0;

			for (int i = 0; i < population.length; i++) {
				fitnessAux[i] = population[i].getFitness() != 0.0 ? 1.0 / population[i].getFitness() : populationFitness;
				totalFitnessAux += fitnessAux[i];
			}

			for (int i = 0; i < population.length; i++) {
				selectionProbs[i] = fitnessAux[i] / totalFitnessAux;
			}
		} else {
			for (int i = 0; i < population.length; i++) {
				selectionProbs[i] = population[i].getFitness() / populationFitness;
			}
		}

		int nCouples = population.length % 2 == 0 ? (population.length / 2) : (population.length / 2) + 1;
		Sample[][] mating = new Sample[nCouples][2];

		double amount = (1.0 / (double) population.length);
		double needle = rand.nextDouble();
		int j;

		for (int i = 0; i < nCouples; i++) {
			j = rangeRoulette(needle, selectionProbs);
			mating[i][0] = population[j];

			needle += amount;
			if(needle >= 1.0) {
				needle = needle - 1.0;
			}

			j = rangeRoulette(needle, selectionProbs);
			mating[i][1] = population[j];

			needle += amount;
			if(needle >= 1.0) {
				needle = needle - 1.0;
			}
		}

		return mating;
	}

	private int rangeRoulette(double needle, double[] selectionProbs) {
		int j = 0;
		double roulette = selectionProbs[0];
		while(roulette < needle) {
			j++;
			roulette += selectionProbs[j];
		}

		return j;
	}

}
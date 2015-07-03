package galapagos.genetic.selection;

import galapagos.genetic.DNA;
import java.util.Random;

public class TournamentMethod implements SelectionMethod {

	private int kTournaments;
	private Random rand;

	public TournamentMethod(int kTournaments) {
		this.rand = new Random();
		this.kTournaments = kTournaments;
	}

	public DNA[][] select(DNA[] population, double populationFitness, boolean minimize) {
		int nCouples = population.length % 2 == 0 ? (population.length / 2) : (population.length / 2) + 1;
		DNA[][] mating = new DNA[nCouples][2];

		for (int i = 0; i < nCouples; i++) {
			for (int j = 0; j < 2; j++) {
				mating[i][j] = tournament(population, minimize);
			}
		}

		return mating;
	}

	private DNA tournament(DNA[] population, boolean minimize) {
		DNA[] contenders = new DNA[this.kTournaments];
		for (int i = 0; i < contenders.length; i++) {
			int indRandom = ((int) Math.floor(rand.nextDouble() * ((double) population.length)));
			contenders[i] = population[indRandom];
		}

		double bestFitness = minimize ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
 		DNA winner = null;

 		for (int i = 0; i < contenders.length; i++) {
 			boolean bestFound = minimize ? (bestFitness > contenders[i].getFitness()) : (bestFitness < contenders[i].getFitness());
			if(bestFound) {
				bestFitness = contenders[i].getFitness();
				winner = contenders[i];
			}
		}

		return winner;
	}

}
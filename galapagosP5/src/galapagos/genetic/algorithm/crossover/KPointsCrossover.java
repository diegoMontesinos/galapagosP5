package galapagos.genetic.algorithm.crossover;

import galapagos.genetic.DNA;
import galapagos.genetic.algorithm.DNAGeneticAlgorithm;
import galapagos.genetic.CrossoverMethod;
import java.util.Random;
import java.util.Arrays;

public class KPointsCrossover implements CrossoverMethod {

	private int kPoints;
	private Random rand;

	public KPointsCrossover(int kPoints) {
		this.kPoints = kPoints;
		this.rand = new Random();
	}

	public Object crossover(DNA mate1, DNA mate2) {
		String genMate1 = ((DNAGeneticAlgorithm) mate1).getGenotype();
		String genMate2 = ((DNAGeneticAlgorithm) mate2).getGenotype();

		int[] crossPoints = new int[this.kPoints];
		for (int i = 0; i < this.kPoints; i++) {
			crossPoints[i] = rand.nextInt(genMate1.length());
		}
		Arrays.sort(crossPoints);

		String genSon1 = "";
		String genSon2 = "";
		int lastInd = 0;
		for (int i = 0; i < (crossPoints.length + 1); i++) {
			String slice1 = i < crossPoints.length ? genMate1.substring(lastInd, crossPoints[i]) : genMate1.substring(lastInd);
			String slice2 = i < crossPoints.length ? genMate2.substring(lastInd, crossPoints[i]) : genMate2.substring(lastInd);

			if(i % 2 == 0) {
				genSon1 += slice1;
				genSon2 += slice2;
			} else {
				genSon1 += slice2;
				genSon2 += slice1;
			}

			lastInd = crossPoints[i % crossPoints.length];
		}

		String[] genotypeSons = { genSon1, genSon2 };
		return genotypeSons;
	}
}
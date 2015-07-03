package galapagos.genetic;

public interface Sample {

	public void setFitness(double fitness);

	public double getFitness();

	public Sample[] crossover(Sample mate, CrossoverMethod crossoverMethod);

	public void mutate(MutationMethod mutationMethod);
	
}
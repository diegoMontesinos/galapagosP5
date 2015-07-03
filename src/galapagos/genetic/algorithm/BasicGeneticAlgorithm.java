package galapagos.genetic.algorithm;

import galapagos.genetic.Sample;
import galapagos.genetic.selection.SelectionMethod;
import galapagos.genetic.selection.RouletteMethod;
import galapagos.genetic.CrossoverMethod;
import galapagos.genetic.algorithm.crossover.KPointsCrossover;
import galapagos.genetic.algorithm.codification.CodificationMethod;
import galapagos.genetic.MutationMethod;
import java.util.Random;
import java.util.Comparator;
import java.util.Arrays;
import java.util.LinkedList;

public abstract class BasicGeneticAlgorithm {

	// Generador de numeros aleatorios
	public Random rand;

	// Variables de poblacion
	public int nPopulation;
	public SampleGeneticAlgorithm[] population;

	// Método de codificacion
	private CodificationMethod codificationMethod;

	// Comparador de individuos
	private Comparator<SampleGeneticAlgorithm> comparatorSample;

	// Operador de seleccion
	private SelectionMethod selectionMethod;

	// Maximizar o minimizar
	private boolean minimize;

	// Operador de Cruza
	private CrossoverMethod crossoverMethod;
	private double crossProb;

	// Operador de Mutación
	private MutationMethod mutationMethod;

	// Elitismo
	private int elitism;
	private LinkedList<SampleGeneticAlgorithm> elite;

	// Estadisticos poblacional
	private SampleGeneticAlgorithm bestSample;
	private double avgFitness;

	public BasicGeneticAlgorithm(int nPopulation, boolean minimize) {
		this.nPopulation = nPopulation;
		this.minimize = minimize;

		this.rand = new Random();

		this.comparatorSample = new Comparator<SampleGeneticAlgorithm>() {
			public int compare(SampleGeneticAlgorithm o1, SampleGeneticAlgorithm o2) {
				if (o1.getFitness() < o2.getFitness()) {
					return -1;
				}
				else if (o1.getFitness() > o2.getFitness()) {
					return 1;
				}
				else {
					return 0;
				}
			}
		};

		// Configuracion por default
		this.selectionMethod = new RouletteMethod();
		this.crossoverMethod = new KPointsCrossover(1);
		this.crossProb = 0.9;

		this.elitism = 0;
		this.elite = new LinkedList<SampleGeneticAlgorithm>();
	}

	public abstract void generateInitialPopulation();

	public abstract double fitness(Object fenotype);
	
	public double evaluatePopulation() {

		// Califica a toda la población actual
		double populationFitness = 0.0;
		for (int i = 0; i < this.nPopulation; i++) {
			SampleGeneticAlgorithm sample = this.population[i];
			Object fenotype = sample.getFenotype(this.codificationMethod);
			double sampleFitness = fitness(fenotype);

			sample.setFitness(sampleFitness);
			populationFitness += sampleFitness;
		}

		return populationFitness;
	}

	public SampleGeneticAlgorithm[][] selection(double populationFitness) {

		// Poblacion a trabajar
		SampleGeneticAlgorithm[] workPopulation = null;

		// Si hay elitismo se prepara la poblacion a trabajar
		if(this.elitism > 0) {
			workPopulation = new SampleGeneticAlgorithm[this.nPopulation + this.elite.size()];

			System.arraycopy(this.population, 0, workPopulation, 0, this.nPopulation);
			System.arraycopy(this.elite.toArray(), 0, workPopulation, this.nPopulation, this.elite.size());

			for (int i = 0; i < this.elite.size(); i++) {
				SampleGeneticAlgorithm sample = this.elite.get(i);
				populationFitness += sample.getFitness();
			}
		} else {
			workPopulation = this.population;
		}

		// Ordenamos la poblacion con un comparador dado por su fitness
		Arrays.sort(workPopulation, this.comparatorSample);

		// Usa el metodo de seleccion obteniendo las parejas de individuos
		Sample[][] selection = this.selectionMethod.select(workPopulation, populationFitness, this.minimize);

		// Forzar el cast Sample a SampleGeneticAlgorithm
		SampleGeneticAlgorithm[][] couples = new SampleGeneticAlgorithm[selection.length][2];
		for (int i = 0; i < selection.length; i++) {
			couples[i][0] = (SampleGeneticAlgorithm) selection[i][0];
			couples[i][1] = (SampleGeneticAlgorithm) selection[i][1];
		}

		// Regresa a las parejas
		return couples;
	}

	public SampleGeneticAlgorithm[] crossover(SampleGeneticAlgorithm[][] couples) {

		SampleGeneticAlgorithm[] newPopulation = new SampleGeneticAlgorithm[this.nPopulation];
		for (int i = 0, j = 0; j < this.nPopulation; i++, j += 2) {

			// Tomamos la pareja actual
			SampleGeneticAlgorithm[] couple = couples[i % couples.length];

			// Echamos un volado para la cruza
			if(rand.nextDouble() <= crossProb) {
				// Los cruzamos
				SampleGeneticAlgorithm[] sons = (SampleGeneticAlgorithm[]) couple[0].crossover(couple[1], this.crossoverMethod);

				// Pasamos a sus hijos
				newPopulation[j % newPopulation.length] = sons[0];
				newPopulation[(j + 1) % newPopulation.length] = sons[1];
			} else {

				// Los pasamos directamente
				newPopulation[j % newPopulation.length] = couple[0];
				newPopulation[(j + 1) % newPopulation.length] = couple[1];
			}
		}

		return newPopulation;
	}

	public void evolve() {

		// Califica a toda la poblacion
		double populationFitness = evaluatePopulation();

		// Actualiza la elite y estadisticas de la poblacion
		updateElite();

		/************************
		 * OPERADOR - SELECCIÓN *
		 ************************/
		SampleGeneticAlgorithm[][] couples = selection(populationFitness);

		/********************
		 * OPERADOR - CRUZA *
		 ********************/
		SampleGeneticAlgorithm[] newPopulation = crossover(couples);

		/***********************
		 * OPERADOR - MUTACION *
		 ***********************/
		for (int i = 0; i < newPopulation.length; i++) {
			newPopulation[i].mutate(this.mutationMethod);
		}

		// Reemplazamos la poblacion vieja por la nueva
		this.population = newPopulation;
	}

	public void setCodificationMethod(CodificationMethod codificationMethod) {
		this.codificationMethod = codificationMethod;
	}

	public void setComparator(Comparator<SampleGeneticAlgorithm> comparatorSample) {
		this.comparatorSample = comparatorSample;
	}

	public void setSelectionMethod(SelectionMethod selectionMethod) {
		this.selectionMethod = selectionMethod;
	}

	public void setCrossoverMethod(CrossoverMethod crossoverMethod) {
		this.crossoverMethod = crossoverMethod;
	}

	public void setCrossProb(double crossProb) {
		this.crossProb = crossProb;
	}

	public CodificationMethod getCodificationMethod() {
		return this.codificationMethod;
	}

	public Comparator<SampleGeneticAlgorithm> getComparator() {
		return this.comparatorSample;
	}

	public SelectionMethod getSelectionMethod() {
		return this.selectionMethod;
	}

	public CrossoverMethod getCrossoverMethod() {
		return this.crossoverMethod;
	}

	public int getElitism() {
		return this.elitism;
	}

	public void setElitism(int elitism) {
		this.elitism = elitism;
	}

	private void updateElite() {

		SampleGeneticAlgorithm bestOfGen = null;
		double bestFitness = this.minimize ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;

		double sum = 0.0;
		for (int i = 0; i < this.nPopulation; i++) {
			SampleGeneticAlgorithm sample = this.population[i];
			double sampleFitness = sample.getFitness();

			sum += sampleFitness;

			boolean bestFound = this.minimize ? (bestFitness > sampleFitness) : (bestFitness < sampleFitness);
			if(bestFound) {
				bestFitness = sampleFitness;
				bestOfGen = sample;
			}
		}

		this.avgFitness = sum / ((double) this.nPopulation);
		this.bestSample = bestOfGen;

		if(this.elitism > 0) {
			if(this.elite.size() >= this.elitism) {
				int worstIndex = worstEliteElem(bestOfGen);
				if(worstIndex != -1) {
					this.elite.remove(worstIndex);
					this.elite.add(worstIndex, bestOfGen);
				}
			} else {
				this.elite.add(bestOfGen);
			}
		}
	}

	private int worstEliteElem(SampleGeneticAlgorithm sample) {
		for (int i = 0; i < this.elite.size(); i++) {
			SampleGeneticAlgorithm eliteElem = this.elite.get(i);
			boolean isBest = this.minimize ? (eliteElem.getFitness() > sample.getFitness()) : (eliteElem.getFitness() < sample.getFitness());

			if(isBest) {
				return i;
			}
		}

		return -1;
	}

	/* 
	 * Método: bestSample
	 *   Regresa el mejor individuo de la generacion actual.
	 */
	public SampleGeneticAlgorithm bestSample() {
		return this.bestSample;
	}

	/* 
	 * Método: bestFitness
	 *   Regresa el fitness del mejor individuo de la generacion actual.
	 */
	public double bestFitness() {
		return this.bestSample.getFitness();
	}

	public Object bestFenotype() {
		return this.bestSample.getFenotype(this.codificationMethod);
	}

	/* 
	 * Método: bestFitness
	 *   Regresa el fitness promedio de la generacion actual.
	 */
	public double averageFitness() {
		return this.avgFitness;
	}

}
public class TriangleGA extends BasicGeneticAlgorithm {

  public Triangle target;
  private TriangleCodification triangleCodif;

  public TriangleGA(int nPopulation, Triangle target) {
    super(nPopulation, true);

    this.target = target;

    this.triangleCodif = new TriangleCodification();
    setCodificationMethod(triangleCodif);
    setSelectionMethod(new TournamentMethod(4));
    setCrossProb(0.9);
    setMutationMethod(new MutationMethod() {
      void mutate(DNA dna) {
        TriangleDNA triangleDNA = (TriangleDNA) dna;
        
        String genotype = triangleDNA.getGenotype();
        for (int i = 0; i < genotype.length (); i++) {
          if (random(1.0) <= 0.01) {
            String newBit = genotype.charAt(i) == '0' ? "1" : "0";
            genotype = genotype.substring(0, i) + newBit + genotype.substring(i + 1);
          }
        }

        String[] parsedGen = triangleDNA.parseGenotype(genotype);
        triangleDNA.genP1 = parsedGen[0];
        triangleDNA.genP2 = parsedGen[1];
        triangleDNA.genP3 = parsedGen[2];

        triangleDNA.genR = parsedGen[3];
        triangleDNA.genG = parsedGen[4];
        triangleDNA.genB = parsedGen[5];
      }
    }
    );
  }

  public void generateInitialPopulation() {
    TriangleDNA[] newPopulation = new TriangleDNA[this.nPopulation];

    for (int i = 0; i < this.nPopulation; i++) {
      PVector p1 = new PVector(random(0, width / 2), random(0, height));
      PVector p2 = new PVector(random(0, width / 2), random(0, height));
      PVector p3 = new PVector(random(0, width / 2), random(0, height));

      float r = random(0, 255);
      float g = random(0, 255);
      float b = random(0, 255);

      String[] gens = (String[]) triangleCodif.encode(new Triangle(p1, p2, p3, r, g, b));

      newPopulation[i] = new TriangleDNA(gens[0], gens[1], gens[2], gens[3], gens[4], gens[5]);
    }

    this.population = newPopulation;
  }

  public double fitness(Object fenotype) {
    Triangle obj = (Triangle) fenotype;

    return target.getDistance(obj);
  }
}


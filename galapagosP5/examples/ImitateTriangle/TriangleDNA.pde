
class TriangleDNA implements DNAGeneticAlgorithm {

  String genP1, genP2, genP3;
  String genR, genG, genB;
  double fitness;

  TriangleDNA(String genP1, String genP2, String genP3, String genR, String genG, String genB) {
    this.genP1 = genP1;
    this.genP2 = genP2;
    this.genP3 = genP3;

    this.genR = genR;
    this.genG = genG;
    this.genB = genB;

    this.fitness = Double.POSITIVE_INFINITY;
  }

  String getGenotype() {
    return this.genP1 + this.genP2 + this.genP3 + this.genR + this.genG + this.genB;
  }

  Object getPhenotype(CodificationMethod codificationMethod) {
    return codificationMethod.decode(this);
  }

  void setFitness(double fitness) {
    this.fitness = fitness;
  }

  double getFitness() {
    return this.fitness;
  }

  public DNA[] crossover(DNA mate, CrossoverMethod crossoverMethod) {
    String[] genSons = (String[]) crossoverMethod.crossover(this, mate);

    String[] gensSon1 = parseGenotype(genSons[0]);
    String[] gensSon2 = parseGenotype(genSons[1]);

    TriangleDNA son1 = new TriangleDNA(gensSon1[0], gensSon1[1], gensSon1[2], gensSon1[3], gensSon1[4], gensSon1[5]);
    TriangleDNA son2 = new TriangleDNA(gensSon2[0], gensSon2[1], gensSon2[2], gensSon2[3], gensSon2[4], gensSon2[5]);
    TriangleDNA[] sons = {
      son1, son2
    };

    return sons;
  }

  void mutate(MutationMethod mutationMethod) {
    mutationMethod.mutate(this);
  }

  String[] parseGenotype(String genotype) {
    String[] parsedGen = new String[6];

    parsedGen[0] = genotype.substring(0, 64);
    parsedGen[1] = genotype.substring(64, 128);
    parsedGen[2] = genotype.substring(128, 192);
    parsedGen[3] = genotype.substring(192, 224);
    parsedGen[4] = genotype.substring(224, 256);
    parsedGen[5] = genotype.substring(256);

    return parsedGen;
  }
}


import galapagos.genetic.selection.*;
import galapagos.genetic.codification.*;
import galapagos.genetic.*;
import galapagos.genetic.algorithm.crossover.*;
import galapagos.genetic.algorithm.*;

Triangle target;
TriangleGA triangleGA;

void setup() {
  size(1000, 500);

  target = new Triangle(new PVector(159, 35), new PVector(62, 415), new PVector(413, 253), 7, 14, 134);
  triangleGA = new TriangleGA(20, target);
  triangleGA.generateInitialPopulation();
}

void draw() {
  background(255);
  target.render();

  triangleGA.evolve();

  TriangleDNA bestTS = (TriangleDNA) triangleGA.bestSample();
  Triangle bestT = (Triangle) bestTS.getPhenotype(triangleGA.getCodificationMethod());
  pushMatrix();
  translate(width / 2, 0);
  bestT.render();
  popMatrix();

  println(bestTS.getFitness());
}

void mouseMoved() {
  triangleGA.target.p3.x = mouseX;
  triangleGA.target.p3.y = mouseY;
}

void mousePressed() {
  triangleGA.target.r = random(0, 255);
  triangleGA.target.g = random(0, 255);
  triangleGA.target.b = random(0, 255);
}

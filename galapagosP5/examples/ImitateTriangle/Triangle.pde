class Triangle {

  PVector p1, p2, p3;
  float r, g, b;

  Triangle(PVector p1, PVector p2, PVector p3, float r, float g, float b) {
    this.p1 = p1;
    this.p2 = p2;
    this.p3 = p3;

    this.r = r;
    this.g = g;
    this.b = b;
  }

  void render() {
    noStroke();
    fill(this.r, this.g, this.b);

    triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
  }

  double getDistance(Triangle t) {
    double distP1 = PVector.dist(this.p1, t.p1);
    double distP2 = PVector.dist(this.p2, t.p2);
    double distP3 = PVector.dist(this.p3, t.p3);
    double distCol = PVector.dist(new PVector(this.r, this.g, this.b), new PVector(t.r, t.g, t.b));

    double dist = (distCol + distP1 + distP2 + distP3) / 4.0;
    return dist;
  }
}


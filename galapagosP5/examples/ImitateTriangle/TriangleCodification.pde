
class TriangleCodification implements CodificationMethod {

  private GrayFixedPoint numCodif;

  TriangleCodification() {
    this.numCodif = new GrayFixedPoint(16, 15);
  }

  Object encode(Object obj) {
    Triangle t = (Triangle) obj;

    String[] gens = new String[6];
    gens[0] = ((String) numCodif.encode((double) t.p1.x)) + ((String) numCodif.encode((double) t.p1.y));
    gens[1] = ((String) numCodif.encode((double) t.p2.x)) + ((String) numCodif.encode((double) t.p2.y));
    gens[2] = ((String) numCodif.encode((double) t.p3.x)) + ((String) numCodif.encode((double) t.p3.y));

    gens[3] = (String) numCodif.encode((double) t.r);
    gens[4] = (String) numCodif.encode((double) t.g);
    gens[5] = (String) numCodif.encode((double) t.b);

    return gens;
  }

  Object decode(Object obj) {
    TriangleDNA ts = (TriangleDNA) obj;

    double x1 = (Double) numCodif.decode(ts.genP1.substring(0, 32));
    double y1 = (Double) numCodif.decode(ts.genP1.substring(32));

    double x2 = (Double) numCodif.decode(ts.genP2.substring(0, 32));
    double y2 = (Double) numCodif.decode(ts.genP2.substring(32));

    double x3 = (Double) numCodif.decode(ts.genP3.substring(0, 32));
    double y3 = (Double) numCodif.decode(ts.genP3.substring(32));

    double r = (Double) numCodif.decode(ts.genR);
    double g = (Double) numCodif.decode(ts.genG);
    double b = (Double) numCodif.decode(ts.genB);

    return new Triangle(new PVector((float) x1, (float) y1), new PVector((float) x2, (float) y2), new PVector((float) x3, (float) y3), (float) r, (float) g, (float) b);
  }
}


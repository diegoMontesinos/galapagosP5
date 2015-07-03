package galapagos.genetic.algorithm.codification;

public interface CodificationMethod {

	public Object encode(Object obj);

	public Object decode(Object obj);

}
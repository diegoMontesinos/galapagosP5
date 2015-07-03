package galapagos.genetic.codification;

/**
 * A codification method is a function that
 * map an Object to another Object.
 *
 * Is widely use in Genetics for separate
 * cleanly the phenotype and the genotype.
 *
 * Encode and decode methods are used like
 * map and inverse map respectively.
 *
 * @author Diego Montesinos
 */
public interface CodificationMethod {

	/**
	 * Encode an object into another.
	 *
	 * @param obj Objet to encode.
	 * @return The object encoded.
	 */
	public Object encode(Object obj);

	/**
	 * Decode an object into another.
	 *
	 * @param obj Objet to decode.
	 * @return The object decoded.
	 */
	public Object decode(Object obj);

}
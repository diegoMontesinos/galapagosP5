package galapagos.genetic.codification;

/**
 * Codification method for decimal numbers.
 * Given a range and a accuracy level, generate a mapping
 * to a range of binary numbers.
 *
 * @author  Diego Montesinos
 */
public class BinaryCodification implements CodificationMethod {

	// Range and amount between numbers
	public double min, max, amount;

	// Binary string lenght
	public int binaryLen;

	public BinaryCodification(double min, double max, double accuracy) {
		this.min = min;
		this.max = max;

		// Get the binary string lenght
		double sizeRange = (this.max - this.min) * Math.pow(10.0, accuracy);
		this.binaryLen = (int) Math.ceil(Math.log(sizeRange) / Math.log(2.0));

		// Get the amount between numbers
		this.amount = (this.max - this.min) / (Math.pow(2.0, (double) this.binaryLen) - 1.0);
	}

	public Object encode(Object obj) {

		// Get the value
		double value = ((Double) obj).doubleValue();

		// If is in range
		if(this.min <= value && value <= this.max) {

			// Se obtiene su mapeo a valor entero
			int intValue = (int) Math.ceil((value - this.min) / this.amount);

			// Se crea la cadena binaria
			String str = Integer.toBinaryString(intValue);
			int diff = binaryLen - str.length();
			if(diff > 0) {
				for (int i = 0; i < diff; i++) {
					str = "0" + str;
				}
			}

			return str;
		}
		
		return null;
	}

	public Object decode(Object obj) {
		String str = (String) obj;
		
		// Se obtiene el valor entero y se mapea a decimal
		double intValue = (double) Integer.parseInt(str, 2);
		double x = (intValue * this.amount) + this.min;

		return new Double(x);
	}

}
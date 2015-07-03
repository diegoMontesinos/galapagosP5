package galapagos.genetic.algorithm.codification;

public class BinaryCodification implements CodificationMethod {

	// Rango y distancia entre numeros
	public double min, max, amount;

	// Longitud de la cadena binaria
	public int binaryLen;

	public BinaryCodification(double min, double max, double accuracy) {
		this.min = min;
		this.max = max;

		// Se obtiene la longitud de la cadena binaria
		double sizeRange = (this.max - this.min) * Math.pow(10.0, accuracy);
		this.binaryLen = (int) Math.ceil(Math.log(sizeRange) / Math.log(2.0));

		// Se obtiene la distancia entre numeros
		this.amount = (this.max - this.min) / (Math.pow(2.0, (double) this.binaryLen) - 1.0);
	}

	public Object encode(Object obj) {

		// Si cae dentro del rango
		double value = ((Double) obj).doubleValue();
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
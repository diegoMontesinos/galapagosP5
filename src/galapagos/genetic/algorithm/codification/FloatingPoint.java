package galapagos.genetic.algorithm.codification;

public class FloatingPoint implements CodificationMethod {

	private int significandLen;
	private int exponentLen;
	private int excess;

	public FloatingPoint(int significandLen, int exponentLen, int excess) {
		this.significandLen = significandLen;
		this.exponentLen = exponentLen;
		this.excess = excess;
	}

	public Object encode(Object obj) {
		double value = ((Double) obj).doubleValue();

		// Codifica el significando
		String integerStr = Integer.toBinaryString((int) Math.abs(value));
		String fractionStr = fractionToBase2(Math.abs(value) - ((int) Math.abs(value)), this.significandLen - integerStr.length());
		String significand = integerStr + fractionStr;

		// Obtenemos el exponente
		int exponent = integerStr.length() - (significand.indexOf("1") + 1);

		// Normalizamos el significando y rellenamos con ceros
		significand = significand.substring(integerStr.length() - exponent);
		while(significand.length() < this.significandLen) {
			significand += "0";
		}

		// Normalizamos el exponente con el sesgo y rellenamos con ceros
		exponent += this.excess;
		String exponentStr = Integer.toBinaryString(exponent);
		while(exponentStr.length() < this.exponentLen) {
			exponentStr = "0" + exponentStr;
		}

		// Codifica el signo
		String binaryStr = value >= 0 ? "0" : "1";

		// Armamos la representacion
		binaryStr = binaryStr + exponentStr + significand;

		return binaryStr;
	}

	public Object decode(Object obj) {
		String str = (String) obj;
		
		String exponentStr = str.substring(1, this.exponentLen + 1);
		int exponent = base2ToInteger(exponentStr) - this.excess;

		String significandStr = "1" + str.substring(this.exponentLen + 1);
		String integerStr = "";
		String fractionStr = "";

		if(exponent < 0) {
			integerStr = "0";

			int shift = (int) Math.abs(exponent + 1);
			fractionStr = significandStr;
			for (int i = 0; i < shift; i++) {
				fractionStr = "0" + fractionStr;
			}
		} else {
			integerStr = significandStr.substring(0, exponent + 1);
			fractionStr = significandStr.substring(exponent + 1);
		}

		double x = ((double) base2ToInteger(integerStr)) + base2ToFraction(fractionStr);
		if(str.charAt(0) == '1') {
			x *= -1.0;
		}

		return new Double(x);
	}

	private String fractionToBase2(double x, int fractionLen) {
		String base2 = "";
		for (int i = 0; i < fractionLen; i++) {
			x = 2.0 * x;
			if(x >= 1.0) {
				base2 += "1";
			} else {
				base2 += "0";
			}

			x = Math.abs(x) - (int) x;
		}

		return base2;
	}

	private int base2ToInteger(String str) {
		int n = 0;
		double pow = 0.0;
		for (int i = str.length() - 1; i >= 0; i--) {
			if(str.charAt(i) == '1') {
				n += (int) Math.pow(2.0, pow);
			}
			pow += 1.0;
		}

		return n;
	}

	private double base2ToFraction(String str) {
		double fractionValue = 0.0;
		double power = 1.0;
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == '1') {
				fractionValue += (1.0 / Math.pow(2.0, power));
			}
			power += 1.0;
		}

		return fractionValue;
	}

}
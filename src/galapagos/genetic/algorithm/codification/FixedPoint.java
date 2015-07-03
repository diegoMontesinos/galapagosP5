package galapagos.genetic.algorithm.codification;

public class FixedPoint implements CodificationMethod {

	protected int integerLen;
	protected int fractionLen;

	public FixedPoint(int integerLen, int fractionLen) {
		this.integerLen = integerLen;
		this.fractionLen = fractionLen;
	}

	public Object encode(Object obj) {
		double value = ((Double) obj).doubleValue();

		// Codifica el signo
		String binaryStr = value >= 0 ? "0" : "1";

		// Codifica la parte entera
		int integerValue = (int) Math.abs(value);
		binaryStr += integerToBase2(integerValue);

		// Codifica la parte fraccionaria
		double fractionValue = Math.abs(value) - integerValue;
		binaryStr += fractionToBase2(fractionValue);

		return binaryStr;
	}

	public Object decode(Object obj) {
		
		String str = (String) obj;
		String integerStr = str.substring(1, this.integerLen + 1);
		double integerValue = base2ToInteger(integerStr);

		String fractionalStr = str.substring(this.integerLen + 1, this.fractionLen + this.integerLen + 1);
		double fractionValue = base2ToFraction(fractionalStr);

		double x = integerValue + fractionValue;
		if(str.charAt(0) == '1') {
			x *= -1.0;
		}

		return new Double(x);
	}

	public int getIntegerLen() {
		return this.integerLen;
	}

	public int getFractionLen() {
		return this.fractionLen;
	}

	private String integerToBase2(int x) {
		String base2 = Integer.toBinaryString(x);
		if(base2.length() < integerLen) {
			int diff = integerLen - base2.length();
			for (int i = 0; i < diff; i++) {
				base2 = "0" + base2;
			}
		}
		
		return base2;
	}

	private double base2ToInteger(String str) {
		double integerValue = 0.0;
		double power = 0.0;
		for (int i = str.length() - 1; i >= 0; i--) {
			if(str.charAt(i) == '1') {
				integerValue += Math.pow(2.0, power);
			}
			power += 1.0;
		}

		return integerValue;
	}

	private String fractionToBase2(double x) {
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

package galapagos.genetic.codification;

public class GrayFixedPoint extends FixedPoint implements CodificationMethod {

	public GrayFixedPoint(int integerLen, int fractionLen) {
		super(integerLen, fractionLen);
	}

	public Object encode(Object obj) {
		double value = ((Double) obj).doubleValue();

		// Codifica el signo
		String binaryStr = value >= 0 ? "0" : "1";

		// Obtenemos su codificacion en base 2
		String binaryStrBase2 = (String) super.encode(value);

		// Obtenemos la parte entera codificada en base 2 y la convertimos a gray
		String integerStr = binaryStrBase2.substring(1, this.integerLen + 1);
		binaryStr += base2ToGray(integerStr);

		// Obtenemos la parte fraccionaria codificada en base 2 y la convertimos a gray
		String fractionalStr = binaryStrBase2.substring(this.integerLen + 1, this.fractionLen + this.integerLen + 1);
		binaryStr += base2ToGray(fractionalStr);

		return binaryStr;
	}

	public Object decode(Object obj) {
		String str = (String) obj;
		String binaryStr = str.substring(0, 1);

		String integerStr = str.substring(1, this.integerLen + 1);
		binaryStr += grayToBase2(integerStr);

		String fractionalStr = str.substring(this.integerLen + 1, this.fractionLen + this.integerLen + 1);
		binaryStr += grayToBase2(fractionalStr);

		return super.decode(binaryStr);
	}

	private String base2ToGray(String binaryStr) {
		String gray = "" + binaryStr.charAt(0);

		for (int i = 1; i < binaryStr.length(); i++) {
			char b1 = binaryStr.charAt(i - 1);
			char b2 = binaryStr.charAt(i);

			gray += ("" + xor(b1, b2));
		}

		return gray;
	}

	private String grayToBase2(String binaryStr) {
		String base2 = "" + binaryStr.charAt(0);
		for (int i = 1; i < binaryStr.length(); i++) {
			char b1 = base2.charAt(i - 1);
			char b2 = binaryStr.charAt(i);

			base2 += ("" + xor(b1, b2));
		}

		return base2;
	}

	private char xor(char b1, char b2) {
		if (b1 == b2) {
			return '0';
		} 
		else {
			return '1';
		}
	}

}

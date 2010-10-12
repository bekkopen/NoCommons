package no.bekk.bekkopen.common;


/**
 * Base Validator class for all StringNumber based Validators.
 */
public abstract class StringNumberValidator {

    public static final String ERROR_INVALID_CHECKSUM = "Invalid checksum : ";

    public static final String ERROR_SYNTAX = "Only digits are allowed : ";
    
    private static int[] BASE_MOD11_WEIGHTS = new int[]{2,3,4,5,6,7};

    protected StringNumberValidator() {
        super();
    }

    /**
     * Calculate the check sum for the given weights and number.
     * 
     * @param weights
     *            The weights
     * @param number
     *            The number
     * @return The checksum
     */
    protected static int calculateMod11CheckSum(int[] weights, StringNumber number) {
        int c = calculateChecksum(weights, number, false) % 11;
        if (c == 1) {
            throw new IllegalArgumentException(ERROR_INVALID_CHECKSUM + number);
        }
        return c == 0 ? 0 : 11 - c;
    }

    /**
     * Calculate the check sum for the given weights and number.
     * 
     * @param weights
     *            The weights
     * @param number
     *            The number
     * @return The checksum
     */
    protected static int calculateMod10CheckSum(int[] weights, StringNumber number) {
        int c = calculateChecksum(weights, number, true) % 10;
        return c == 0 ? 0 : 10 - c;
    }

	private static int calculateChecksum(int[] weights, StringNumber number, boolean tverrsum) {
		int checkSum = 0;
        for (int i = 0; i < weights.length; i++) {
        	int product = weights[i] * number.getAt(weights.length-1-i);
        	if (tverrsum) {
                checkSum += (product > 9? product - 9 : product);
        	} else {
                checkSum += product;
        	}
        }
		return checkSum;
	}

    protected static void validateLengthAndAllDigits(String numberString,
            int length) {
        if (numberString == null || numberString.length() != length) {
            throw new IllegalArgumentException(ERROR_SYNTAX + numberString);
        }
        validateAllDigits(numberString);
    }

	protected static void validateAllDigits(String numberString) {
        if (numberString == null || numberString.length() <= 0) {
            throw new IllegalArgumentException(ERROR_SYNTAX + numberString);
        }
		for (int i = 0; i < numberString.length(); i++) {
            if (!Character.isDigit(numberString.charAt(i))) {
                throw new IllegalArgumentException(ERROR_SYNTAX + numberString);
            }
        }
	}

	protected static int[] getMod10Weights(StringNumber k) {
		int[] weights = new int[k.getLength()-1];
		for (int i = 0; i < weights.length; i++) {
			if ((i % 2) == 0) {
				weights[i] = 2;
			} else {
				weights[i] = 1;
			}
		}
		return weights;
	}

	protected static int[] getMod11Weights(StringNumber k) {
		int[] weights = new int[k.getLength()-1];
		for (int i = 0; i < weights.length; i++) {
			int j = i % BASE_MOD11_WEIGHTS.length;
			weights[i] = BASE_MOD11_WEIGHTS[j];
		}
		return weights;
	}

}

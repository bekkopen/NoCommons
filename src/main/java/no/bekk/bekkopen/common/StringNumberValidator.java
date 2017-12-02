package no.bekk.bekkopen.common;


/**
 * Base Validator class for all StringNumber based Validators.
 */
public abstract class StringNumberValidator {

   public static final String ERROR_SYNTAX = "Only digits are allowed : ";
   
   protected StringNumberValidator() {
      super();
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
}

package no.bekk.bekkopen.common;

/*-
 * #%L
 * NoCommons
 * %%
 * Copyright (C) 2014 - 2023 BEKK open source
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */


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

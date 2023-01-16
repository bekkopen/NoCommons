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

public class Checksums {
    public static final String ERROR_INVALID_CHECKSUM = "Invalid checksum : ";
    private static int[] BASE_MOD11_WEIGHTS = new int[]{2, 3, 4, 5, 6, 7};
 
    /**
     * Calculate the check sum for the given weights and number.
     *
     * Does not allow the remainder to be 1 so that checksum should be 10
     * and throws an IllegalArgumentException if that should be the case.
     * 
     * @param weights The weights
     * @param number  The number
     * @return The checksum
     */
    public static int calculateMod11CheckSum(int[] weights, StringNumber number) {
       int c = calculateChecksum(weights, number, false) % 11;
       if (c == 1) {
          throw new IllegalArgumentException(ERROR_INVALID_CHECKSUM + number);
       }
       return c == 0 ? 0 : 11 - c;
    }
    
    /**
     * Calculate the check sum for the given weights and number.
     * 
     * Allow checksum to be `-`.
     *
     * @param weights The weights
     * @param number  The number
     * @return The checksum
     */
    public static String calculateMod11CheckSumAllowDash(int[] weights, StringNumber number) {
       int c = calculateChecksum(weights, number, false) % 11;
       if (c == 1) {
          return "-";
       }
       return String.valueOf((c == 0 ? 0 : 11 - c));
    }
 
    /**
     * Calculate the check sum for the given weights and number.
     *
     * @param weights The weights
     * @param number  The number
     * @return The checksum
     */
    public static int calculateMod10CheckSum(int[] weights, StringNumber number) {
       int c = calculateChecksum(weights, number, true) % 10;
       return c == 0 ? 0 : 10 - c;
    }
 
    public static int calculateChecksum(int[] weights, StringNumber number, boolean tverrsum) {
       int checkSum = 0;
       for (int i = 0; i < weights.length; i++) {
          int product = weights[i] * number.getAt(weights.length - 1 - i);
          if (tverrsum) {
             checkSum += (product > 9 ? product - 9 : product);
          } else {
             checkSum += product;
          }
       }
       return checkSum;
    }
    
    public static int[] getMod10Weights(StringNumber k) {
       int[] weights = new int[k.getLength() - 1];
       for (int i = 0; i < weights.length; i++) {
          if ((i % 2) == 0) {
             weights[i] = 2;
          } else {
             weights[i] = 1;
          }
       }
       return weights;
    }
 
    public static int[] getMod11Weights(StringNumber k) {
       int[] weights = new int[k.getLength() - 1];
       for (int i = 0; i < weights.length; i++) {
          int j = i % BASE_MOD11_WEIGHTS.length;
          weights[i] = BASE_MOD11_WEIGHTS[j];
       }
       return weights;
    }
    
}

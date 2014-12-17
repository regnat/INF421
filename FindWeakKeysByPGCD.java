/*
 * FindWeakKeysByPGCD.java
 * A class to find weakness in some rsa keys by calculating pgcds betweens them
 */

import java.io.IOException;
import java.math.BigInteger;
import java.io.PrintWriter;

class FindWeakKeysByPGCD {
  public static void main (String[] args) {
    FindWeakKeysByPGCD("../files/keys100.txt");
  }

  static BigInteger[] parseKeysFile(String keysFile) {
    return null; //TODO
  }

  static void FindWeakKeysByPGCD(String keysFile) {
    BigInteger[] keys = parseKeysFile(keysFile);
    for (int i=0; i < keys.length; i++) {
      for (int j=i+1; j < keys.length; j++) {

      }
    }
  }
}

/**
 * FindWeakKeysByPGCD.java
 * A class to find weakness in some rsa keys by calculating pgcds betweens them
 */

import java.io.IOException;
import java.math.BigInteger;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashMap;

class FindWeakKeysByPGCD {

  private static String outFile = "weakKeys.txt";

  public static void main (String[] args) {
    if (args.length > 0) {
      FindWeakKeysByPGCD(args[0]);
    }
    else {
      FindWeakKeysByPGCD("../files/keys100.txt");
    }
  }

  /**
   * Compute gcds between elements of list1 and list2
   *
   * @param list1 the first list of integers
   *
   * @param list2 the second list of integers
   *
   * @return a map associating to each element of the first list on of his factors (if possible)
   */
  static HashMap<BigInteger, BigInteger> computeGCDs(LinkedList<BigInteger> list1, LinkedList<BigInteger> list2) {
    HashMap<BigInteger, BigInteger> computedGcds = new HashMap<BigInteger, BigInteger>();
    for (BigInteger key1 : list1) {
      for (BigInteger key2 : list2) {
        BigInteger gcd = key1.gcd(key2);
        if (!gcd.equals(BigInteger.ONE)) {
          computedGcds.put(key1, gcd);
          break;
        }
      }
    }
    return computedGcds;
  }

  /**
   * Compute GCDs between all the keys in the given list to find common prime factors
   *
   * @param keysFile The list containing public keys
   */
  static void FindWeakKeysByPGCD(LinkedList<BigInteger> keys) {
      LinkedList<BigInteger> keys2 = new LinkedList<BigInteger>(keys);
      BigInteger pgcd = null;
      Set<BigInteger> knownWeakKeys = new HashSet<BigInteger>();
      System.out.println("Calculating the common factors... ");
      System.out.print("--> Computing GCDs...");
      KeyWriter.clear(outFile);
      for (BigInteger key1 : keys) {
        keys2.poll();
        for (BigInteger key2 : keys2) {
          pgcd = key1.gcd(key2);
          if (!pgcd.equals(BigInteger.ONE)) {
            KeyWriter.addToWeakKeys(outFile, key1, pgcd, knownWeakKeys);
            KeyWriter.addToWeakKeys(outFile, key2, pgcd, knownWeakKeys);
          }
        }
      }
      System.out.println("Done.");
      System.out.println("Done.");
  }

  /**
   * Compute GCDs between all the keys in the given file to find common prime factors
   *
   * @param keysFile The file containing public keys
   */
  static void FindWeakKeysByPGCD(String keysFile) {
    try {
      LinkedList<BigInteger> keys = FileParser.parseKeysFile(keysFile);
      FindWeakKeysByPGCD(keys);
    }
    catch ( IOException e) {
      e.printStackTrace();
    }
  }
}

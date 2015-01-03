/**
 * FindWeakKeysByPGCD.java
 * A class to find weakness in some rsa keys by calculating pgcds betweens them
 */

import java.io.IOException;
import java.math.BigInteger;
import java.io.FileWriter;
import java.util.Scanner;
import java.nio.file.Paths;
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

  static void FindWeakKeysByPGCD(LinkedList<BigInteger> keys) {
      LinkedList<BigInteger> keys2 = new LinkedList<BigInteger>(keys);
      BigInteger pgcd = null;
      Set<BigInteger> knownWeakKeys = new HashSet<BigInteger>();
      System.out.println("Calculating the common factors... ");
      System.out.print("--> Computing GCDs...");
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
   * Compute GCDs between keys to find common prime factors
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

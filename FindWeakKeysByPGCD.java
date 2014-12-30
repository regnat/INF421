/*
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
import fileParser.FileParser;

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
   * Adds the two give keys to the file containing the weak keys
   */
  static void addToWeakKeys(BigInteger key, BigInteger pgcd, Set<BigInteger> knownWeakKeys) {
    if (!knownWeakKeys.contains(key)) {
      try {
        FileWriter writer = new FileWriter(outFile, true);
        writer.write(String.format("%s  = %s * %s\n", key.toString(), pgcd.toString(), key.divide(pgcd).toString()));
        writer.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      knownWeakKeys.add(key);
    }
    else {
      System.out.println("Clé déjà trouvée");
    }
  }

  /**
   * Compute GCDs between keys to find common prime factors
   */
  static void FindWeakKeysByPGCD(String keysFile) {
    try {
      LinkedList<BigInteger> keys = FileParser.parseKeysFile(keysFile);
      LinkedList<BigInteger> keys2 = new LinkedList<BigInteger>(keys);
      BigInteger pgcd = null;
      Set<BigInteger> knownWeakKeys = new HashSet<BigInteger>();
      for (BigInteger key1 : keys) {
        keys2.poll();
        for (BigInteger key2 : keys2) {
          pgcd = key1.gcd(key2);
          if (!pgcd.equals(BigInteger.ONE)) {
            addToWeakKeys(key1, pgcd, knownWeakKeys);
            addToWeakKeys(key2, pgcd, knownWeakKeys);
          }
        }
      }
    }
    catch ( IOException e) {
      e.printStackTrace();
    }
  }
}

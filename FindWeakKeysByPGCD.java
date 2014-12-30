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

  static int getLineCount(String fileToCount) throws IOException {
    int lineCount = 0;
    Scanner scanner = new Scanner(Paths.get(fileToCount));
    while (scanner.hasNextLine()) {
      scanner.nextLine();
      lineCount++;
    }
    scanner.close();
    return lineCount;
  }

  static BigInteger[] parseKeysFile(String keysFile) throws IOException {
    Scanner scanner = new Scanner(Paths.get(keysFile));
    int lineCount = getLineCount(keysFile);
    BigInteger[] keys = new BigInteger[lineCount];
    for (int i=0; i<lineCount; i++) {
      scanner.findInLine(":");
      keys[i] = scanner.nextBigInteger();
      scanner.nextLine();
    }
    scanner.close();
    return keys;
  }

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

  static void FindWeakKeysByPGCD(String keysFile) {
    try {
      BigInteger[] keys = parseKeysFile(keysFile);
      BigInteger pgcd = null;
      Set<BigInteger> knownWeakKeys = new HashSet<BigInteger>();
      for (int i=0; i < keys.length; i++) {
        for (int j=i+1; j < keys.length; j++) {
          pgcd = keys[i].gcd(keys[j]);
          if (!pgcd.equals(BigInteger.ONE)) {
            addToWeakKeys(keys[i], pgcd, knownWeakKeys);
            addToWeakKeys(keys[j], pgcd, knownWeakKeys);
          }
        }
      }
    }
    catch ( IOException e) {
      e.printStackTrace();
    }
  }
}

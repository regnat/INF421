/*
 * KeyWriter.java
 * Common utils to parse key files
 */

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Set;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class KeyWriter {
  private static void addToWeakKeys(String outFile, BigInteger key, BigInteger pgcd) {
    try {
      FileWriter writer = new FileWriter(outFile, true);
      writer.write(String.format("%s  == %s * %s\n", key.toString(), pgcd.toString(), key.divide(pgcd).toString()));
      writer.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void addToWeakKeys(String outFile, BigInteger key, BigInteger pgcd, Set<BigInteger> knownWeakKeys) {
    if (!knownWeakKeys.contains(key)) {
      addToWeakKeys(outFile, key, pgcd);
      knownWeakKeys.add(key);
    }
  }

  public static void addToWeakKeys(String outFile, HashMap<BigInteger, BigInteger> couples) {
    for (Map.Entry<BigInteger, BigInteger> couple : couples.entrySet()) {
      if (!couple.getValue().equals(BigInteger.ZERO) && !couple.getValue().equals(BigInteger.ONE)) {
        addToWeakKeys(outFile, couple.getKey(), couple.getValue());
      }
    }
  }
}

/*
 * KeyWriter.java
 * Common utils to parse key files
 */

import java.io.IOException;
import java.math.BigInteger;
import java.util.Set;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>KeyWriter is a tool to write easily keys and their factors in a file</b>
 * 
 */
public class KeyWriter {
	/**
	 * Write a key and its factors in a file
	 * @param outFile
	 * 			Filepath to the output file
	 * @param key
	 * 			The key we want to write
	 * @param pgcd
	 * 			One of the factors of the key. The other one is computed by this function.
	 */
  private static void addToWeakKeys(String outFile, BigInteger key, BigInteger pgcd) {
    try {
      FileWriter writer = new FileWriter(outFile, true);
      writer.write(String.format("%s  == %s x %s\n", key.toString(), pgcd.toString(), key.divide(pgcd).toString()));
      writer.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Write a key in a file if it hasn't already be written
   * @param outFile
   * 		Filepath to the output file
   * @param key
   * 		The key we want to write
   * @param pgcd
   * 		One of the factors of the key. The other one is computed by this function.
   * @param knownWeakKeys
   * 		List of the key already written in the file.
   */
  public static void addToWeakKeys(String outFile, BigInteger key, BigInteger pgcd, Set<BigInteger> knownWeakKeys) {
    if (!knownWeakKeys.contains(key)) {
      addToWeakKeys(outFile, key, pgcd);
      knownWeakKeys.add(key);
    }
  }

  /**
   * Empty a file
   *
   * @param outFile The file to empty
   *
   */
  public static void clear(String outFile) {
    try {
      FileWriter writer = new FileWriter(outFile, false);
      writer.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Write a set of keys and factors in a file
   * @param outFile
   * 			Filepath to the output file
   * @param couples
   * 			List of the couples keys/factor. The other factor is computed in the function.
   */
  public static void genWeakKeysFile(String outFile, HashMap<BigInteger, BigInteger> couples) {
    clear(outFile);
    for (Map.Entry<BigInteger, BigInteger> couple : couples.entrySet()) {
      if (!couple.getValue().equals(BigInteger.ZERO) && !couple.getValue().equals(BigInteger.ONE)) {
        addToWeakKeys(outFile, couple.getKey(), couple.getValue());
      }
    }
  }
  
}

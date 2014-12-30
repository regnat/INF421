package fileParser;

/*
 * FileParser.java
 * Common utils to parse key files
 */

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.LinkedList;

public class FileParser {
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

  public static LinkedList<BigInteger> parseKeysFile(String keysFile) throws IOException {
    System.out.print("Reading the keys from " + keysFile + "... ");
    Scanner scanner = new Scanner(Paths.get(keysFile));
    /* int lineCount = getLineCount(keysFile); */
    /* BigInteger[] keys = new BigInteger[lineCount]; */
    LinkedList<BigInteger> keys = new LinkedList<BigInteger>();
    while (scanner.hasNextLine()) {
      scanner.findInLine(":");
      keys.add(scanner.nextBigInteger());
      scanner.nextLine();
    }
    scanner.close();
    System.out.println("Done.");
    return keys;
  }
}

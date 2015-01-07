/*
 * FileParser.java
 * Common utils to parse key files
 */

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * <b>File parser is a tool to easily read keys from a file</b>
 *
 */
public class FileParser {
	/**
	 * Count the number of line of a file
	 * @param fileToCount
	 * 			Path to the file
	 * @return Number of line of the file fileToCount
	 * @throws IOException
	 * 				If the function can't access to the file.
	 */
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

  /**
   * Extract the keys from a file.
   * The file must follow the scheme : key number  x..x: yyyyyyy...yyy 
   * @param keysFile
   * 			Path to the file
   * @return List of the keys read from the file
   * @throws IOException
   * 		If the function can't access to the file.
   */
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

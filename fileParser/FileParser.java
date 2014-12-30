package fileParser;

/*
 * FileParser.java
 * Common utils to parse key files
 */

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.nio.file.Paths;

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

  public static BigInteger[] parseKeysFile(String keysFile) throws IOException {
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
}

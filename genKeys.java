/* package code; */
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * <b>The class used to generate te keys.</b>
 */
final class genKeys {

/**
 * The dir in which store the keys
 */
    private static final String OUT_DIR = "./keys";

/**
 * Default constructor
 */
    private genKeys() {
    }

/**
 * generate the keyfiles in asn1 format.
 * @param inFile the path to the file
 * which contains the factorized keys in the form "n = p x q"
 * @throws IOException if inFile is not existent
 */
    static void genKeyFiles(final String inFile) throws IOException {
        final Scanner scanner = new Scanner(Paths.get(inFile));
        int fileNum = 0;

        while (scanner.hasNextLine()) {
            BigInteger n;
            BigInteger p;
            BigInteger q;
            n = scanner.nextBigInteger();
            scanner.findInLine("==");
            p = scanner.nextBigInteger();
            scanner.findInLine("x");
            q = scanner.nextBigInteger();

            genasn1(p, q, n, fileNum);
            fileNum += 1;
            scanner.nextLine();
        }
    }

/**
 * generate the asn1 file corresponding to th egiven key
 * @param n the public key
 * @param p the first factor of n
 * @param q the second factor of n
 * @throws IOException if outFile is not existent
 */
    static void genasn1(final BigInteger p,
            final BigInteger q, final BigInteger n
            , int fileNum
            ) 
    throws IOException {

        final FileWriter writer = new FileWriter(
                OUT_DIR + "/" +  fileNum,
                true
                );
        final BigInteger pubExp = new BigInteger("65537");
        final BigInteger privExp = pubExp.modInverse(
                p.subtract(BigInteger.ONE).multiply(
                    q.subtract(BigInteger.ONE)
                    )
                );
        final BigInteger modulus = p.multiply(q);
        final BigInteger e1 = privExp.mod(p.subtract(BigInteger.ONE));
        final BigInteger e2 = privExp.mod(q.subtract(BigInteger.ONE));
        final BigInteger coeff = q.modInverse(p);

        writer.write(
                "asn1=SEQUENCE:rsa_key\n"
                + "\n"
                + "[rsa_key]\n"
        );
        writer.write(genAsn1Line("version", BigInteger.ZERO));
        writer.write(genAsn1Line("modulus", modulus));
        writer.write(genAsn1Line("pubExp", pubExp));
        writer.write(genAsn1Line("privExp", privExp));
        writer.write(genAsn1Line("p", p));
        writer.write(genAsn1Line("q", q));
        writer.write(genAsn1Line("e1", e1));
        writer.write(genAsn1Line("e2", e2));
        writer.write(genAsn1Line("coeff", coeff));
        writer.close();
    }

/**
 * Generate an asn1 line in the form var=INTEGER:val.
 * @param var the variable
 * @param val the value
 * @return the line
 */
    private static String genAsn1Line(final String var, final BigInteger val) {
        return String.format("%s=INTEGER:%s\n", var, val.toString());
    }

    public static void main(String[] args) throws IOException {
        genKeyFiles("weakKeys.txt");
    }
}

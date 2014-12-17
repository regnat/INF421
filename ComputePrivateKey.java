/*
 * ComputePrivateKey.java
 * Compute a private key for openssl given two prime numbers and a public exposant
 */

import java.math.BigInteger;
import java.io.PrintWriter;
import java.io.IOException;

class ComputePrivateKey {
  static void ComputePrivateKey(BigInteger p, BigInteger q, BigInteger exp) {
    BigInteger privExp = exp.modInverse(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
    BigInteger e1 = privExp.mod(p.subtract(BigInteger.ONE));
    BigInteger e2 = privExp.mod(q.subtract(BigInteger.ONE));
    BigInteger coeff = q.modInverse(p);
    BigInteger modulus = p.multiply(q);
    printAsn1(p.toString(), q.toString(), exp.toString(), privExp.toString(), e1.toString(), e2.toString(), coeff.toString(), modulus.toString());
  }

  static private void printAsn1(
      String p,
      String q,
      String exp,
      String privExp,
      String e1,
      String e2,
      String coeff,
      String modulus
      )
  {
    try
    {
      PrintWriter writer = new PrintWriter("cle.asn1", "UTF-8");
      writer.println("asn1=SEQUENCE:rsa_key\n");
      writer.println("[rsa_key]\nversion=INTEGER:0");
      writer.printf("modulus=INTEGER:%s\n", modulus);
      writer.printf("pubExp=INTEGER:%s\n", exp);
      writer.printf("privExp=INTEGER:%s\n",privExp);
      writer.printf("p=INTEGER:%s\n",p);
      writer.printf("q=INTEGER:%s\n",q);
      writer.printf("e1=INTEGER:%s\n",e1);
      writer.printf("e2=INTEGER:%s\n",e2);
      writer.printf("coeff=INTEGER:%s\n",coeff);
      writer.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static void main (String[] args) {
    ComputePrivateKey(new BigInteger("269827501991372501711607231818050318973"), new BigInteger("328445938775505526685905856413145772287"), new BigInteger("65537"));
  }
}

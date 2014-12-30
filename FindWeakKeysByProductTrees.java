import java.math.BigInteger;
import java.util.HashSet;
import java.util.LinkedList;
import fileParser.FileParser;
import java.io.IOException;


public class FindWeakKeysByProductTrees {

  /**
   * 
   * Looks for the common factor of keys in "keys"
   * Uses product and leftover trees
   * Can't find a common factor in cases such as : ab / bc / ca, ie when "cycle" of factors
   * Return a set (we don't want twice the same factor...
   * 
   */
  public static HashSet<BigInteger> FindWeakKeysTree(LinkedList<BigInteger> keys){

    //Build the product tree and the leftover tree
    System.out.print("--> Building the product tree... ");
    ProductTree pTree = ProductTree.BuildFromLeafs(keys);
    System.out.println("Done.");

    System.out.print("--> Building the leftover tree... ");
    BigIntTree lTree = pTree.buildLeftoverTree();
    System.out.println("Done.");

    //Look for common factor
    System.out.print("--> Looking for common factor... ");
    HashSet<BigInteger> factors = new HashSet<BigInteger>();
    solveLeftoverTree(factors, pTree, lTree);
    System.out.println("Done");

    return factors;
  }

  /**
   * Recursive auxiliary function wrapped in FindWeakKeysTree.
   * Depth first walk in the two trees in order to get the gcd.
   */
  private static void solveLeftoverTree(HashSet<BigInteger> factors, ProductTree pTree, BigIntTree lTree){

    //If a leftover is null, we can't find any factor on the branch
    if(lTree.getVal().equals(BigInteger.ZERO)){
      return;
    }

    //Depth first walk in the two tree simultaneously
    if(pTree.getlSubTree() != null){
      solveLeftoverTree(factors, pTree.getlSubTree(), lTree.getlSubTree());
      solveLeftoverTree(factors, pTree.getrSubTree(), lTree.getrSubTree());
    }
    //If on a leaf, calculate the biggest common factor and keep it if different from 1.
    else{
      BigInteger n = lTree.getVal().divide(pTree.getVal()).gcd(pTree.getVal());
      if(!n.equals(BigInteger.ONE))
        factors.add(n);
    }
  }

  /**
   * 
   * Looks for the common factor of keys in "keys"
   * Uses product trees and leftover list (save memory)
   * Can't find a common factor in cases such as : ab / bc / ca, ie when "cycle" of factors
   * Return a set (we don't want twice the same factor...
   * 
   */
  public static HashSet<BigInteger> FindWeakKeysList(LinkedList<BigInteger> keys){

    //Build the product tree and the leftover list
    System.out.print("--> Building the product tree... ");
    ProductTree pTree = ProductTree.BuildFromLeafs(keys);
    System.out.println("Done.");
    System.out.print("--> Building the leftover list... ");
    LinkedList<BigInteger> lList = pTree.buildLeftoverList();
    System.out.println("Done.");

    //Look for common factor
    HashSet<BigInteger> factors = new HashSet<>();

    System.out.print("--> Looking for common factor... ");
    solveLeftoverList(factors, pTree, lList);
    System.out.println("Done.");

    return factors;
  }

  /**
   *
   * Recursive auxiliary function wrapped in FindWeakKeys.
   * Depth first walk in pTree in order to get the gcd with the corresponding rest of lList.
   * Warning : modify (destroy, actually) lList
   */
  private static void solveLeftoverList(HashSet<BigInteger> factors, ProductTree pTree, LinkedList<BigInteger> lList){

    //Depth first walk in pTree
    if(pTree.getlSubTree() != null){
      solveLeftoverList(factors, pTree.getlSubTree(), lList);
      solveLeftoverList(factors, pTree.getrSubTree(), lList);
    }
    //If on a leaf, calculate the biggest common factor and keep it if different from 1.
    else{
      BigInteger n = lList.pop();
      //Get rid of useless rests
      if(!n.equals(BigInteger.ZERO)){
        n = n.divide(pTree.getVal()).gcd(pTree.getVal());
        if(!n.equals(BigInteger.ONE))
          factors.add(n);
      }
    }
  }

  public static void main(String[] args) {

    String path = "../files/keys1000.txt";
    if (args.length > 0) {
      path = args[0];
    }
    /* LinkedList<BigInteger> keys = keysFromFile(path); */
    try {
      LinkedList<BigInteger> keys = FileParser.parseKeysFile(path);
      System.out.println("Calculating the common factors... ");
      HashSet<BigInteger> factors = FindWeakKeysList(keys);
      System.out.println("Done.");
      System.out.println("\n" + factors.size() + " factors found");
    }
    catch ( IOException e) {
      e.printStackTrace();
    }
  }

}

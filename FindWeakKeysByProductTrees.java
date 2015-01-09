import java.math.BigInteger;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

/**
 * <b>Found the weak keys thanks to product and leftover trees</b>
 * Contains the functions that find the common factors in keys
 * using the algorithm based on product tree.
 *
 */
public class FindWeakKeysByProductTrees {

	/**
	 * Filename of the output
	 * Weak keys and their factors will be written here
	 * Syntax : x == a x b
	 */
  static String outFile = "weakKeys.txt";
  
  /**
   * Looks for the common factor of keys in "keys"
   * Uses product and leftover trees
   * Can't find a common factor in cases such as : ab / bc / ca, ie when "cycle" of factors
   * @see FindWeakKeysByProductTrees#FindWeakKeysList(LinkedList)
   * @see FindWeakKeysByProductTrees#solveLeftoverTree(LinkedList, ProductTree, BigIntTree)
   * @param keys
   * 		List of the keys that might share common factors
   * @return List of the common factors
   */
  public static LinkedList<BigInteger> FindWeakKeysTree(LinkedList<BigInteger> keys){

    //Build the product tree and the leftover tree
    System.out.print("--> Building the product tree... ");
    ProductTree pTree = ProductTree.BuildFromLeafs(keys);
    System.out.println("Done.");

    System.out.print("--> Building the leftover tree... ");
    BigIntTree lTree = pTree.buildLeftoverTree();
    System.out.println("Done.");

    //Look for common factor
    System.out.print("--> Looking for common factor... ");
    LinkedList<BigInteger> factors = new LinkedList<BigInteger>();
    solveLeftoverTree(factors, pTree, lTree);
    System.out.println("Done");

    return factors;
  }
  
  /**
   * Recursive auxiliary function wrapped in FindWeakKeysTree.
   * Depth first walk in the two trees in order to compute gcds and find commons factors according to the project's algorithm.
   * @see FindWeakKeysByProductTrees#FindWeakKeysTree(LinkedList)
   * @param factors
   * 			Buffer storing the factors already found.
   * @param pTree
   * 			The product tree build whose leaf are the keys.
   * @param lTree
   * 			The leftover tree build from the product tree pTree
   */
  private static void solveLeftoverTree(LinkedList<BigInteger> factors, ProductTree pTree, BigIntTree lTree){

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
   * 
   * Return a set (we don't want twice the same factor...
   * 
   */
  
  /**
   * Looks for the common factor of keys in "keys"
   * Uses product trees and leftover list (in order to save memory)
   * Can't find a common factor in cases such as : ab / bc / ca, ie when it exists a "cycle" of factors
   * @see FindWeakKeysByProductTrees#FindWeakKeysTree(LinkedList)
   * @see FindWeakKeysByProductTrees#computeFactors(HashMap, ProductTree, LinkedList)
   * @param keys
   * 		List of the keys that might share common factors
   * @return List of the common factors
   */
  public static HashMap<BigInteger, BigInteger> FindWeakKeysList(LinkedList<BigInteger> keys){

    //Build the product tree and the leftover list
    System.out.println("Calculating the common factors... ");
    System.out.print("--> Building the product tree... ");
    ProductTree pTree = ProductTree.BuildFromLeafs(keys);
    System.out.println("Done.");
    System.out.print("--> Building the leftover list... ");
    LinkedList<BigInteger> lList = pTree.buildLeftoverList();
    LinkedList<BigInteger> weakKeysList = new LinkedList<BigInteger>();
    LinkedList<BigInteger> zeroList = new LinkedList<BigInteger>();
    System.out.println("Done.");

    //Look for common factor
    HashMap<BigInteger, BigInteger> factors = new HashMap<BigInteger, BigInteger>();
    HashMap<BigInteger, BigInteger> factorsAt0 = new HashMap<BigInteger, BigInteger>();

    System.out.print("--> Looking for common factor... ");
    computeFactors(factors, pTree, lList);

    System.out.print("--> Looking for still unfound but foundable factors... ");
    for(Map.Entry<BigInteger, BigInteger> entry : factors.entrySet()) {
        if (entry.getValue().equals(BigInteger.ONE)) {
            weakKeysList.add(entry.getKey());
            if (entry.getValue().equals(BigInteger.ZERO)) {
                zeroList.add(entry.getKey());
            }
        }
    }

    factorsAt0 = FindWeakKeysByPGCD.computeGCDs(zeroList, weakKeysList);
    System.out.println("Done.");
    System.out.print("--> Saving found prime factors to file " + outFile + "... ");
    factors.putAll(factorsAt0);
    KeyWriter.addToWeakKeys(outFile, factors);
    System.out.println("Done.");

    return factors;
  }

  /**
   *
   * Recursive auxiliary function wrapped in FindWeakKeys.
   * Depth first walk in pTree in order to get the gcd with the corresponding rest of lList.
   * Warning : modify (destroy, actually) lList
   */
  
  /**
   * Recursive auxiliary function wrapped in FindWeakKeys.
   * Depth first walk in pTree in order to get the gcd with the corresponding rest of lList.
   * Warning : modify (destroy, actually) the list of leftovers.
   * @param factors
   * 			The list of the couples keys/factors already found. 
   * @param pTree
   * 			The product tree build whose leaf are the keys.
   * @param lList
   * 			The list of leftovers computed from the product tree.
   */
  private static void computeFactors(HashMap<BigInteger, BigInteger> factors, ProductTree pTree, LinkedList<BigInteger> lList){

    //Depth first walk in pTree
    if(pTree.getlSubTree() != null){
      computeFactors(factors, pTree.getlSubTree(), lList);
      computeFactors(factors, pTree.getrSubTree(), lList);
    }
    //If on a leaf, calculate the biggest common factor (if we are not null)
    else{
      BigInteger n = lList.pop();
      n = n.divide(pTree.getVal()).gcd(pTree.getVal());
      if (n.equals(pTree.getVal())) {
        n = BigInteger.ZERO;
      }
      factors.put(pTree.getVal(),n);
    }
  }

  
  public static void main(String[] args) {

    String path = "./files/keys100000.txt";
    if (args.length > 0) {
      path = args[0];
    }
    /* LinkedList<BigInteger> keys = keysFromFile(path); */
    try {
      LinkedList<BigInteger> keys = FileParser.parseKeysFile(path);
      HashMap<BigInteger, BigInteger> factors = FindWeakKeysList(keys);
    }
    catch ( IOException e) {
      e.printStackTrace();
    }
  }

}

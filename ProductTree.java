import java.math.BigInteger;
import java.util.LinkedList;

/**
 * <b>ProductTree implements a product tree of BigInteger.</b>
 * It is a binary tree where the value of every internal node is the product of the values of its sons.
 * So, either a node is a leaf, either its value is the product of those of its sons.
 * @see BigIntTree
 * 
 */
public class ProductTree extends BigIntTree {
		
	/* ----------------------  Constructor ---------------------*/
	
	/**
	 * Construct a leaf (no sons)
	 * @see BigIntTree#BigIntTree(BigInteger)
	 * @param v
	 * 		The value to store in the leaf.
	 */
	public ProductTree(BigInteger v) {
		super(v);
	}
	
	/**
	 * Construct an internal node (two sons).
	 * The value of the node is the product of the values of the sons.
	 * @see BigIntTree#BigIntTree(BigIntTree, BigIntTree, BigInteger)
	 * @param l
	 * 			The left son of this node.
	 * @param r
	 * 			The right son of this node.
	 */
	public ProductTree(ProductTree l, ProductTree r) {
		super(l, r, null);
		val = l.getVal().multiply(r.getVal());
	}
	
	/*------------------------- Getters / Setters -------------------*/
	/**
	 * Getter to the left son.
	 * @see BigIntTree#getlSubTree()
	 * @return The left son of this node.
	 */
	public ProductTree getlSubTree(){
		return (ProductTree)super.getlSubTree();
	}
	
	/**
	 * Getter to the right son. 
	 * @see BigIntTree#getrSubTree()
	 * @return The right son of this node.
	 */
	public ProductTree getrSubTree(){
		return (ProductTree)super.getrSubTree();
	}
	
	/*------------------------- Static methods to build product tree ------------------------------------*/
	
	/**
	 * Construct a product tree from a given list of leafs' values.
	 * Use a queue in order to keep the tree well balance and simpliy implementation.
	 * @param leafs
	 * 			The values of the leafs of the tree.
	 * @return A product tree which leafs have the given values.
	 */
	public static ProductTree BuildFromLeafs(LinkedList<BigInteger> leafs){
		
		LinkedList<ProductTree> forest = new LinkedList<>();
		for(BigInteger i: leafs){
			forest.add(new ProductTree(i));
		}
		
		while(true){
			ProductTree r= forest.pop();
			if(forest.isEmpty())
				return r;
			
			forest.addLast(new ProductTree(r, forest.pop()));
		}
	}
	
	
	/*---------------------------- Methods to construct leftovers ---------------------------------------*/
	
	/**
	 * Construct a leftover tree from this product tree, according to the project's definition.
	 * @see ProductTree#buildLeftoverTreeAux(BigInteger)
	 * @return A tree in which every nodes is equal to the modulo of its father's value by the square of this product tree's node value. 
	 */
	public BigIntTree buildLeftoverTree() {

		return buildLeftoverTreeAux(val);
	}
	
	//Recursive auxiliary function wrapped in buildLeftoverTree.
	
	/**
	 * Recusive function wrapped in builLeftoverTree that actually compute the leftover tree.
	 * @see ProductTree#buildLeftoverTree()
	 * @see ProductTree#buildLeftoverList()
	 * @param leftover
	 * 				The value of its father node, requiered to compute the value of this node.
	 * @return the leftover tree created from this product tree.
	 */
	private BigIntTree buildLeftoverTreeAux(BigInteger leftover){
		
		BigInteger v = leftover.mod(val.pow(2));

		//If not in a leaf, build the subtrees and return the result.
		//As a BigIntTree node has zero or two sons, it's useless to check if rSubTree != null
		if(getlSubTree() != null){
			return new BigIntTree(getlSubTree().buildLeftoverTreeAux(v), getrSubTree().buildLeftoverTreeAux(v), v);
		}
		
		//If in a leaf, one only have to build a leaf
		return new BigIntTree(v);
	}
	
	
	
	
	/**
	 * Construct a leftover list from this product tree, according to the project's definition.
	 * Only the leafs of a leftover tree are useful, one can save memory by not building the whole tree.
	 * 
	 * @see ProductTree#buildLeftoverListAux(BigInteger)
	 * @see ProductTree#buildLeftoverTree()
	 * @return A tree in which every nodes
	 */
	public LinkedList<BigInteger> buildLeftoverList() {
		
		LinkedList<BigInteger> res = new LinkedList<>();
		buildLeftoverListAux(res, val);
		
		return res;
		
	}
	
	/**
	 * Recusive function wrapped in builLeftoverList that actually compute the leftover tree.
	 * Run a depth-first path in order to store leftovers in the right order in the list.
	 * @see ProductTree#buildLeftoverTree()
	 * @see ProductTree#buildLeftoverList()
	 * @param res
	 * 			Buffer storing the result as we build it. As java concatenates two linked list in O(n), it's better to keep the result as a parameter.
	 * @param leftover
	 * 				The value of its father node, requiered to compute the value of this node.
	 * @return the leftover tree created from this product tree.
	 */
	private void buildLeftoverListAux (LinkedList<BigInteger> res, BigInteger leftover){
		
		BigInteger v = leftover.mod(val.pow(2));
		
		//If in a leaf, add the new value to res
		//As a BigIntTree node has zero or two sons, it's useless to check if rSubTree == null too.
		if(getlSubTree() == null){
			res.addLast(v);
		}
		//Recursive call
		else{
			getlSubTree().buildLeftoverListAux(res, v);
			getrSubTree().buildLeftoverListAux(res, v);
		}
	}
	
	
}

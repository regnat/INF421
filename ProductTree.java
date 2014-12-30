import java.math.BigInteger;
import java.util.LinkedList;

/**
 * 
 * Binary tree of BigInteger.
 * Each nodes is expected to have two or zero sons.
 *
 */
class BigIntTree {
	
	protected BigInteger val;
	private BigIntTree rSubTree;
	private BigIntTree lSubTree;
	
	/* ----------------------  Constructor ---------------------*/
	
	//For a leaf
	public BigIntTree(BigInteger v) {
		val = v;
		rSubTree = null;
		lSubTree = null;
	}
	
	//For an internal node
	public BigIntTree(BigIntTree l, BigIntTree r, BigInteger v) {
		
		//Two non-null sons are expected
		if(r == null || l == null)
			throw new IllegalArgumentException("A node must have two or no sons.");
		
		rSubTree = r;
		lSubTree = l;
		val = v;
	}
	
	/*----------------------  Getter / Setters -----------------------*/
	public BigInteger getVal(){
		return val;
	}
	
	public BigIntTree getlSubTree(){
		return lSubTree;
	}
	
	public BigIntTree getrSubTree(){
		return rSubTree;
	}
	
	//string from BigIntTree, in order to display a product tree
	//wrapper of toStringAux
	public String toString(){
		StringBuilder s = new StringBuilder();
		
		toStringAux(s, 0);
			
		return s.toString();
	}
		
	//Depth first recursive function to write a BigIntTree into a String
	//Wrapped in toString
	private void toStringAux(StringBuilder s, int i){
		
		for(int k=0; k<i; k++)
			s.append(' ');
		
		String sVal = val.toString();
		s.append("-->");
		s.append(sVal);
		
		s.append("\n");
		
		if(lSubTree != null)
			lSubTree.toStringAux(s, i+sVal.length()+3);
		if(rSubTree != null)
			rSubTree.toStringAux(s, i+sVal.length()+3);
	}
}

/**
 * 
 * Product tree : binary tree of big integer
 * Either a node is a leaf, either its value is the product of those of its sons. 
 * 
 */
public class ProductTree extends BigIntTree {
		
	/* ----------------------  Constructor ---------------------*/
	
	//For a leaf
	public ProductTree(BigInteger v) {
		super(v);
	}
	
	//For an internal node
	public ProductTree(ProductTree l, ProductTree r) {
		super(l, r, null);
		val = l.getVal().multiply(r.getVal());
	}
	
	/*------------------------- Getters / Setters -------------------*/
	public ProductTree getlSubTree(){
		return (ProductTree)super.getlSubTree();
	}
	
	public ProductTree getrSubTree(){
		return (ProductTree)super.getrSubTree();
	}
	
	/*------------------------- Static methods to build product tree ------------------------------------*/
	
	//Construct a product tree from a given list of leafs' values.
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
	
	//Construct a leftover tree from the product tree
	public BigIntTree buildLeftoverTree() {

		return buildLeftoverTreeAux(val);
	}
	
	//Recursive auxiliary function wrapped in buildLeftoverTree.
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
	
	
	
	
	
	//Only the leaf of the leftover tree are useful.
	//Build the list of leftover tree leafs' values without building the whole tree to save memory
	public LinkedList<BigInteger> buildLeftoverList() {
		
		LinkedList<BigInteger> res = new LinkedList<>();
		buildLeftoverListAux(res, val);
		
		return res;
		
	}
	
	//Recursive auxiliary function wrapped in buildLeftoverList
	//Run a depth-first path in order to store leftovers in the right order in the list
	//As java merge concatenates two linked list in O(n), it's better to keep the result as a parameter.
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

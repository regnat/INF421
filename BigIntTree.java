import java.math.BigInteger;

/**
 * <b>BigIntTree implements a binary tree of BigIntegers.</b>
 * It's a recursive structure, every instances also represent a node of the tree.
 * Each node of this tree must have 2 sons (internal node) or zero sons (leaf).
 *
 */
public class BigIntTree {
	
	/**
	 * Value stored in this node
	 */
	protected BigInteger val;
	/**
	 * Right son of this node.
	 * Null when there are none.
	 */
	private BigIntTree rSubTree;
	/**
	 * Left son of this node.
	 * Null when there are none.
	 */
	private BigIntTree lSubTree;
	
	/* ----------------------  Constructor ---------------------*/
	
	/**
	 * Construct a leaf (no sons)
	 * @param v
	 * 		The value to store in the leaf.
	 */
	public BigIntTree(BigInteger v) {
		val = v;
		rSubTree = null;
		lSubTree = null;
	}
	
	/**
	 * Construct an internal node (two sons)
	 * @param l
	 * 			The left son of this node.
	 * @param r
	 * 			The right son of this node.
	 * @param v
	 * 			The value stored in this node.
	 */
	public BigIntTree(BigIntTree l, BigIntTree r, BigInteger v) {
		
		//Two non-null sons are expected
		if(r == null || l == null)
			throw new IllegalArgumentException("A node must have two or no sons.");
		
		rSubTree = r;
		lSubTree = l;
		val = v;
	}
	
	/*----------------------  Getter / Setters -----------------------*/
	/**
	 * Getter to the value of the node.
	 * @return The value stored in this node
	 */
	public BigInteger getVal(){
		return val;
	}
	
	/**
	 * Getter to the left son.
	 * @return The left son of this node.
	 */
	public BigIntTree getlSubTree(){
		return lSubTree;
	}
	
	/**
	 * Getter to the right son.
	 * @return The right son of this node.
	 */
	public BigIntTree getrSubTree(){
		return rSubTree;
	}
	
	/**
	 * Represent this tree by a String.
	 * Wrapper of toStringAux
	 * @see BigIntTree#toStringAux
	 * @return A string representing this tree in a human-friendly way.
	 */
	public String toString(){
		StringBuilder s = new StringBuilder();
		
		toStringAux(s, 0);
			
		return s.toString();
	}
		
	
	/**
	 * Recursive function building a representation of the tree by a string.
	 * Wrapped in toString.
	 * @see BigIntTree#toString
	 * @param s
	 * 			Buffer containing the string while it is constructed
	 * @param i
	 * 			Level of indentation of this node in the tree
	 */
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


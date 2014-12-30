import java.math.BigInteger;
import java.util.LinkedList;

/**
 * 
 * Binary tree of BigInteger.
 * Each nodes is expected to have two or zero sons.
 *
 */
public class BigIntTree {
	
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


import java.util.BitSet;
import java.util.LinkedList;
import java.io.IOException;


public abstract class PrefixTree {

    // parent of this tree
    PrefixTree parent;

    // weight of this tree (w)
    int weight;

    // number of this tree (l)
    int number;

    /**
        Constructor with parent.
    */
    public PrefixTree(PrefixTree parent, int weight, int number) {
    
        this.parent = parent;
        this.weight = weight;
        this.number = number;
    }

    /**
        Constructor without parent.
    */
    public PrefixTree(int weight, int number) {
    
        this.parent = null;
        this.weight = weight;
        this.number = number;
    }

    /**
        Check whether this tree has a parent.
    */
    public boolean isRoot() {
    
        return parent == null;
    }

    /**
     * Encodes the character.
     */
    public Tuple<BitSet, Integer> encode(char ch) {

    	return this.encode(ch, new BitSet(), 0);
    }

    /**
     * Adds the next bit to the bitset.
     */
	public abstract Tuple<BitSet, Integer> encode(char ch, BitSet bitSet, int length);

    /**
     * Decodes the next character.
     */
	public abstract char decode(BitInputStream bis) throws IOException;
	
	/**
	 * Update the tree. TODO Probably returns a tree when doing this functional.
	 */
	public abstract void update(char c);
	
	/**
	 * Expand this node or leaf. Thus adding its children and their encoding to a list.
	 * 
	 * @return null if the node does not match the searched one, the encoding otherwise.
	 */
	public abstract Tuple<BitSet, Integer> expandOrFinish(char c, BitSet b, int length, LinkedList<PrefixTree> openList, LinkedList<Tuple<BitSet, Integer>> openEncodingList);
	
	/**
	 * Uses expand to perform a breadth-first-search on the prefix tree, should be faster because the more frequent leafs are more top.
	 * @param c
	 * @return
	 */
	public Tuple<BitSet, Integer> encodeBFS(char c) {
		
		LinkedList<PrefixTree> openList = new LinkedList<PrefixTree>();
		LinkedList<Tuple<BitSet, Integer>> openEncodingList = new LinkedList<Tuple<BitSet,Integer>>();

		// expand first node
		PrefixTree currentNode = this;

		Tuple<BitSet, Integer> result = this.expandOrFinish(c, new BitSet(), 0, openList, openEncodingList);

		if (result != null) {
			return result;
		}
		else {
		
			while (true) {
				
				// this should never happen
				if (openList.isEmpty()) {
					return null;
				}
				
				currentNode = openList.removeLast();
				Tuple<BitSet, Integer> currentEncoding = openEncodingList.removeLast();
				
				result = currentNode.expandOrFinish(c, currentEncoding.first, currentEncoding.second, openList, openEncodingList);
				
				if (result != null) {
					return result;
				}
			}
		}
	}
}


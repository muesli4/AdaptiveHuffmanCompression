import java.util.BitSet;
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
    public BitSet encode(char ch) {

    	return this.encode(ch, new BitSet(), 0);
    }

    /**
     * Adds the next bit to the bitset.
     */
	public abstract BitSet encode(char ch, BitSet bitSet, int length);

    /**
     * Decodes the next character.
     */
	public abstract char decode(BitInputStream bis) throws IOException;
	
	/**
	 * Update the tree. TODO Probably returns a tree.
	 */
	public abstract void update(char c);
}


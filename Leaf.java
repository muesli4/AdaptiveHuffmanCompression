import java.util.BitSet;
import java.util.LinkedList;
import java.io.IOException;

public class Leaf extends PrefixTree {

	private char value;

	public Leaf(PrefixTree parent, int weight, int number, char value) {
		super(parent, weight, number);
		this.value = value;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Tuple<BitSet, Integer> encode(char ch, BitSet bitSet, int length) {
		if (value == ch) {
			// the leaf is the one we're looking for	
			return new Tuple<BitSet, Integer>(bitSet, length);
		}
		else {
			// the leaf is not the one we're looking for
			return null;
		}
			
	}

	@Override
	public char decode(BitInputStream bis) throws IOException {

		return value;
	}

	public void update(char c) {
	    // TODO
	}
	
	public Tuple<BitSet, Integer> expandOrFinish(char c, BitSet b, int length, LinkedList<PrefixTree> openList, LinkedList<Tuple<BitSet, Integer>> openEncodingList) {

		// we found our encoding
		if (c == this.value) {
			return new Tuple<BitSet, Integer>(b, length);
		}
		else {
			return null;
		}
	}
}


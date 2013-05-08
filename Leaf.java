import java.util.BitSet;


public class Leaf extends PrefixTree {

	private char value;

	public Leaf(PrefixTree parent, int weight, int number, char value) {
		super(parent, weight, number);
		this.value = value;
		// TODO Auto-generated constructor stub
	}

	@Override
	public BitSet encode(char ch, BitSet bitSet, int length) {
		if(value == ch){
			// the leaf is the one looking for	
			return bitSet;
		}
		else{
			// the leaf is not the one looking for
			return null;
		}
			
	}

	@Override
	public char decode(BitSet bitSet, int position) {
		return value;
	}

}


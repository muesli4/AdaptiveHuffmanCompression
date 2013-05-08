import java.util.BitSet;


public class Node extends PrefixTree {
	private PrefixTree leftNode;
	private PrefixTree rightNode;
	
	public Node(PrefixTree parent, int weight, int number, PrefixTree rightNode, PrefixTree leftNode) {
		super(parent, weight, number);
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}

	@Override
	public BitSet encode(char ch, BitSet bitSet, int length) {
		
		BitSet bitSetLeft = bitSet;
		BitSet bitSetRight = bitSet;
		bitSetLeft.set(length);
		bitSetRight.set(length);
		bitSetLeft = leftNode.encode(ch, bitSetLeft, length + 1);
		bitSetRight = rightNode.encode(ch, bitSetRight, length + 1);
		
		/*if (bitSetLeft == null && bitSetRight == null){
			return null;
		}
		else */
		// Returns the shorter bitSet - NYT-BitSet is always longer ( + 8 bits)
		if (bitSetLeft == null){
			return bitSetRight;
		}
		else if (bitSetRight == null){
			return bitSetLeft;
		}
		else if(bitSetLeft.length() > bitSetRight.length()){
			return bitSetRight;
		}
		else{
			return bitSetLeft;
		}
	}
	
	public char decode(BitSet bitSet,int position){
		if(bitSet.length() > position){
			if(bitSet.get(position)){
				return rightNode.decode(bitSet, position + 1);
			}
			else{
				return leftNode.decode(bitSet, position + 1);
			}
		}
		else{
			// TODO: Throw not decodeable yet error
			return '@';
		}
	}
}


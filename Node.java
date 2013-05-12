import java.util.BitSet;
import java.io.EOFException;
import java.io.IOException;

public class Node extends PrefixTree {

	private PrefixTree rightNode;
	private PrefixTree leftNode;

	public Node(PrefixTree parent, int weight, int number, PrefixTree leftNode, PrefixTree rightNode) {
		super(parent, weight, number);
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}

	@Override
	public Tuple<BitSet, Integer> encode(char ch, BitSet bitSet, int length) {
		
		// TODO implement with bfs maybe
		
		BitSet rightEncoding = (BitSet) bitSet.clone();
		rightEncoding.set(length);
		Tuple<BitSet, Integer> resultRight  = rightNode.encode(ch, rightEncoding, length + 1);
		
		Tuple<BitSet, Integer> resultLeft  = leftNode.encode(ch, (BitSet) bitSet.clone(), length + 1);


		if (resultLeft == null && resultRight == null){
			return null;
		}
		else
		// Returns the shorter bitSet - NYT-BitSet is always longer ( + 8 bits)
		if (resultLeft == null) {
			return resultRight;
		}
		else if (resultRight == null) {
			return resultLeft;
		}
		// return shorter result (other may be NYT encoding)
		else if (resultRight.second > resultLeft.second) {
			return resultLeft;
		}
		else {
			return resultRight;
		}

	}

	public char decode(BitInputStream bis) throws IOException {

        int ch = bis.read();
        
        if (ch == -1) {
        
            throw new EOFException("incomplete encoding");
        }
        // descend left
        else if (ch == 0) {
        
            return leftNode.decode(bis);
        }
        // right otherwise
        else {
            
            return rightNode.decode(bis);
        }
	}
	
	public void update(char c) {
	    // TODO
	}
}


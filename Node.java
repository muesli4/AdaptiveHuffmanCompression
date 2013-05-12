import java.util.BitSet;
import java.io.EOFException;
import java.io.IOException;

public class Node extends PrefixTree {
	private PrefixTree leftNode;
	private PrefixTree rightNode;
	
	public Node(PrefixTree parent, int weight, int number, PrefixTree rightNode, PrefixTree leftNode) {
		super(parent, weight, number);
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}

	@Override
	public Tuple<BitSet, Integer> encode(char ch, BitSet bitSet, int length) {
		
		// TODO implement with bfs maybe

		Tuple<BitSet, Integer> resultLeft  = leftNode.encode(ch, (BitSet) bitSet.clone(), length + 1);
		Tuple<BitSet, Integer> resultRight  = rightNode.encode(ch, (BitSet) bitSet.clone(), length + 1);

		/*if (bitSetLeft == null && bitSetRight == null){
			return null;
		}
		else */
		// Returns the shorter bitSet - NYT-BitSet is always longer ( + 8 bits)
		if (resultLeft == null) {
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
        
            return rightNode.decode(bis);
        }
        // right otherwise
        else {
            
            return leftNode.decode(bis);
        }
	}
	
	public void update(char c) {
	    // TODO
	}
}


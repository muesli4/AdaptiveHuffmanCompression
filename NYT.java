import java.util.BitSet;
import java.util.LinkedList;
import java.io.EOFException;
import java.io.IOException;


public class NYT extends PrefixTree {

    public NYT(PrefixTree parent, int weight, int number) {
    
        super(parent, weight, number);
    }

    public NYT(int weight, int number) {
    
        super(weight, number);
    }
    
	@Override
	public Tuple<BitSet, Integer> encode(char c, BitSet bitSet, int length) {

		// add the bits of the character to the bitSet
		for (int i = 0; i < 8; i++) {

			bitSet.set(length + i, (c & (1 << i)) != 0);
		}

		return new Tuple<BitSet, Integer>(bitSet, length + 8);
	}

	@Override
	public char decode(BitInputStream bis) throws IOException {
		char c = 0;

		// reads the transmitted character bits
		for (int i = 0; i < 8; i++) {

            int ch = bis.read();
            
            if (ch == -1) {
                throw new EOFException("couldn't receive not yet transmitted character");
            }
            else if (ch != 0) {
            
                c = (char)(c | (char)(1 << i));
            }
		}	

		return  c;
	}
	
	public void update(char c) {
	    // TODO
	}
	
	public Tuple<BitSet, Integer> expandOrFinish(char c, BitSet b, int length, LinkedList<PrefixTree> openList, LinkedList<Tuple<BitSet, Integer>> openEncodingList) {

		// always return a success because it is visited last
		
		BitSet NYTEncoding = (BitSet) b.clone();
		
		// add the bits of the character to the bitSet
		for (int i = 0; i < 8; i++) {

			NYTEncoding.set(length + i, (c & (1 << i)) != 0);
		}
		
		return new Tuple<BitSet, Integer>(NYTEncoding, length);
	}
}


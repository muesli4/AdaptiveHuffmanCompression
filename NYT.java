import java.util.BitSet;


public class NYT extends PrefixTree {

    public NYT(PrefixTree parent, int weight, int number) {
    
        super(parent, weight, number);
    }

    public NYT(int weight, int number) {
    
        super(weight, number);
    }
    
	@Override
	public BitSet encode(char ch, BitSet bitSet, int length) {
		Byte b = (byte)ch;
		
		// Adds the bits of the character to the bitSet
		for (int x = 0; x < 8; x++){
			bitSet.set(length + x, (b & (1 << x)) == (1 << x));
		}
		
		// Sets the last bit so the length can be determined
		bitSet.set(length + 9);
		
		return bitSet;
	}

	@Override
	public char decode(BitSet bitSet, int position) {
		byte ch = 0;
		
		// decodes the transmitted character
		for (int x = 0; x < 8; x++){
			if (bitSet.get(position + x)){
				ch |= (1 << x);
			}
			else{
				ch &= ~ (1 << x);
			}
		}	
		return (char)ch;
	}
}



import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

public class DecoderInputStream extends InputStream {

    // the compressed input (including initial tree represenation) will be received here
    private BitInputStream bitInputStream;

    // the huffman tree used for decoding
    private PrefixTree prefixTree;

    /**
        Initialize the DecoderStream with an InputStream (could be for example System.in)
    */
    public DecoderInputStream(InputStream inputStream) {
    
        this.bitInputStream = new BitInputStream(inputStream);
//        this.prefixTree = new NYT(0, 513);
        this.prefixTree = new Node(null, 1, 511, new NYT(0, 513), new Leaf(null, 1, 512, 'a'));


    }

    public int read() throws IOException {
        
        char c = prefixTree.decode(bitInputStream);
        
        prefixTree.update(c);
        
        return c;
    }
    
    /**
     * Skip n bytes.
     */
    @Override
    public long skip(long n) throws IOException {
		// skip 8 * n bits from the bit stream
    	bitInputStream.skip(n * 8);
    	
    	return n;
    }
    
    public void dropCurrentByte() {
    	this.bitInputStream.dropCurrentByte();
    }

}


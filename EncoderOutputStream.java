
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.BitSet;

public class EncoderOutputStream extends OutputStream {

    // the compressed output (including initial tree represenation) will be sent here
    private BitOutputStream bitOutputStream;

    // the huffman tree used for encoding
    private PrefixTree prefixTree;

    
    /**
        Initialize the EncoderStream with an OutputStream (could be for example System.out).
    */
    public EncoderOutputStream(OutputStream outputStream) {
    
        this.bitOutputStream = new BitOutputStream(outputStream);
//        this.prefixTree = new NYT(0, 513);
        this.prefixTree = new Node(null, 1, 511, new NYT(0, 513), new Leaf(null, 1, 512, 'a'));
    }
    

    public void write(int b) throws IOException {
    
        char c = (char) b;
        Tuple<BitSet, Integer> result = prefixTree.encode(c);
        bitOutputStream.write(result.first, result.second);

        prefixTree.update(c);
    }
    
    @Override
    public void flush() throws IOException {
    	bitOutputStream.flush();
    }
}


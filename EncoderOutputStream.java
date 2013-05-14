import java.io.OutputStream;
import java.io.IOException;

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
        this.prefixTree = new PrefixTree();
    }
    
    @Override
    public void write(int b) throws IOException {
    	this.prefixTree.encode((char) b, bitOutputStream);
    }
    
    @Override
    public void flush() throws IOException {
    	bitOutputStream.flush();
    }
    
    public void printPrefixTree() {
    	this.prefixTree.print();
    }
}


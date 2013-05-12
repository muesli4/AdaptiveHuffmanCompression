
import java.io.OutputStream;
import java.io.InputStream;
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
    }
    

    public void write(int b) throws IOException {
    
        char c = (char) b;

        bitOutputStream.write(prefixTree.encode(c));

        prefixTree.update(c);
    }
}


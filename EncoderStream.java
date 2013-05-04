
import java.io.OutputStream;
import java.io.InputStream;

public class EncoderStream extends OutputStream {

    // the compressed output (including initial tree represenation) will be sent here
    private OutputStream outputStream;

    // the huffman tree used for encoding
    private PrefixTree prefixTree;

    
    /**
        Initialize the EncoderStream with an OutputStream (could be for example System.out).
    */
    public EncoderStream(OutputStream outputStream) {
    
        this.outputStream = outputStream;
        
        // TODO load and send the first tree representation here
    }
}


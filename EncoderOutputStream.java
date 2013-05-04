
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

public class EncoderOutputStream extends OutputStream {

    // the compressed output (including initial tree represenation) will be sent here
    private OutputStream outputStream;

    // the huffman tree used for encoding
    private PrefixTree prefixTree;

    
    /**
        Initialize the EncoderStream with an OutputStream (could be for example System.out).
    */
    public EncoderOutputStream(OutputStream outputStream) {
    
        this.outputStream = outputStream;
        
        // TODO load and send the first tree representation here
    }
    
    

    public void write(int b) throws IOException {
    
        // TODO encode symbol to bit sequence
        
        // TODO send bit sequence with old encoding
        
        // TODO update tree
    }
}



import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

public class DecoderInputStream extends InputStream {

    // the compressed input (including initial tree represenation) will be received here
    private InputStream inputStream;

    // the huffman tree used for decoding
    private PrefixTree prefixTree;

    /**
        Initialize the DecoderStream with an InputStream (could be for example System.in)
    */
    public DecoderInputStream(InputStream inputStream) {
    
        this.inputStream = inputStream;
        
        // TODO receive the initial prefix tree from inputStream
    }

    public int read() throws IOException {
        
        int ch = this.inputStream.read();
        
        if (ch == -1) {
        
            return -1;
        }
        else {

            // TODO read until found a valid encoding and then decode to symbol
        
            // TODO update the tree with the symbol
            
            // TODO return decoded symbol
            return -1;
        }
    }

}


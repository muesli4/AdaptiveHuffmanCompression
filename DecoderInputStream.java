
import java.io.OutputStream;
import java.io.InputStream;

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

}


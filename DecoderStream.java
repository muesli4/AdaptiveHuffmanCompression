
public class DecoderStream extends OutputStream {

    // the compressed input (including initial tree represenation) will be received here
    private InputStream inputStream;

    /**
        Initialize the DecoderStream with an InputStream (could be for example System.in)
    */
    public DecoderStream(InputStream inputStream) {
    
        this.inputStream = inputStream;
    }

}


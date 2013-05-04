
public class EncoderStream extends InputStream {

    // the compressed output (including initial tree represenation) will be sent here
    private OutputStream outputStream;
    
    /**
        Initialize the EncoderStream with an OutputStream (could be for example System.out).
    */
    public EncoderStream(outputStream outputStream) {
    
        this.outputStream = outputStream;    
    }
}


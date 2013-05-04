
public class Main {

    public static void main(String[] args) {
    
        // create a manual pipe
        PipedInputStream is = new PipedInputStream();
        PipedOutputStream os = new PipedOutputStream(is);
    
        // connect both through the pipe
        EncoderOutputStream encoder = new EncoderOutputStream(os);
        DecoderInputStream encode = new DecoderInputStream(is);

        // TODO test
    }
}


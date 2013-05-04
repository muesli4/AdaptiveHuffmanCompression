
import java.io.PipedOutputStream;
import java.io.PipedInputStream;

public class Main {

    public static void main(String[] args) {
    
        try {
            // create a manual pipe
            PipedInputStream is = new PipedInputStream();
            PipedOutputStream os = new PipedOutputStream(is);
        
            // connect both through the pipe
            EncoderOutputStream encoder = new EncoderOutputStream(os);
            DecoderInputStream encode = new DecoderInputStream(is);

            // TODO test
        }
        catch (Exception e) {
        
            System.err.println("error: " + e.getLocalizedMessage());
        }
    }
}


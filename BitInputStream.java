
import java.io.InputStream;
import java.io.IOException;

public class BitInputStream extends InputStream {

    // currently stored bits
    byte byteBuffer;
    
    // points on the first not read bit
    int byteBufferPosition = 8;

    // the input stream to read from
    InputStream inputStream;


    public BitInputStream(InputStream inputStream) {
    
        this.inputStream = inputStream;
    }

    /**
        Reads the next bit, is not equal 0 if set, otherwise equal 0.
    */
    public int read() throws IOException {
    
        // if not filled, then read from stream
        if (byteBufferPosition == 8) {
        
            int ch = inputStream.read();
            
            if (ch == -1) {
            
                return -1;
            }
            else {
                
                byteBuffer = (byte) ch;
                
                byteBufferPosition = 1;
                
                return byteBuffer & 1;
            }
        }
        // read from buffer
        else {
        
            int pos = byteBufferPosition;
            byteBufferPosition = byteBufferPosition + 1;
        
            return byteBuffer & (1 << pos);
        }
    }
}


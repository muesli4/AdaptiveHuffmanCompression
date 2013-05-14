
import java.io.InputStream;
import java.io.IOException;

/**
    Reads bitwise input from the given bytewise InputStream.
*/
public class BitInputStream extends InputStream {

    // currently stored bits
    byte byteBuffer;
    
    // points on the first not read bit
    int byteBufferPosition = 8;

    // the input stream to read from
    InputStream inputStream;

    /**
        @param inputStream The stream to read from.
    */
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
    
    /**
     *  Skip n bits.
     */
    @Override
    public long skip(long n) throws IOException {
        for (int i = 0; i < n; ++i) {
            read();
        }
        
        return n;
    }
    
    public void dropCurrentByte() {
        byteBuffer = 0;
        byteBufferPosition = 8;
    }
}


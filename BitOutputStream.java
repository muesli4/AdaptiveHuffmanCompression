
import java.io.OutputStream;
import java.io.IOException;

import java.util.BitSet;

/**
    Writes bitwise output to the given bytewise OutputStream.
*/
public class BitOutputStream extends OutputStream {

    // currently stored bits
    byte byteBuffer = 0;
    
    // points on the first not used bit
    int byteBufferPosition = 0;

    // the output stream to write from
    OutputStream outputStream;

    /**
        @param outputStream The stream to write to.
    */
    public BitOutputStream(OutputStream outputStream) {
    
        this.outputStream = outputStream;
    }

    /**
        Writes a bit to an internal buffer and sends it bytewise. Values equal to zero are treated as zero otherwise one.
    */
    public void write(int b) throws IOException {
    
        this.write(b != 0);
    }

    /**
        Writes a bit to the internal buffer and sends it bytewise. True is treated as one, false as zero.
    */
    public void write(boolean b) throws IOException {

        if (b) {
        
            byteBuffer = (byte)(byteBuffer | (1 << byteBufferPosition));
        }

        byteBufferPosition = byteBufferPosition + 1;
        
        if (byteBufferPosition == 8) {
        
            outputStream.write(byteBuffer);
            byteBufferPosition = 0;
            byteBuffer = 0;
        }
    }

    /**
        Special method to write a BitSet to the stream.
    */
    public void write(BitSet b, int length) throws IOException {

        for (int i = 0; i < length; ++i) {
            
            this.write(b.get(i));
        }
    } 

    /**
        Flushes not sent bytes from the buffer, pads the byte with zeroes for missing bits.
    */
    public void flush() throws IOException {

        if (byteBufferPosition != 0) {
            for (;byteBufferPosition < 8; ++byteBufferPosition) {
    
                byteBuffer = (byte)(byteBuffer & ~(1 << byteBufferPosition));
            }
    
            outputStream.write(byteBuffer);
            outputStream.flush();
            
            byteBufferPosition = 0;
            byteBuffer = 0;
        }
    }
}


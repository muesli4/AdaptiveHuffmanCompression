
import java.io.OutputStream;
import java.io.IOException;

import java.util.BitSet;

public class BitOutputStream extends OutputStream {

    // currently stored bits
    byte byteBuffer;
    
    // points on the first not used bit
    int byteBufferPosition = 0;

    // the output stream to write from
    OutputStream outputStream;


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
        else {
        
            byteBuffer = (byte)(byteBuffer & ~(1 << byteBufferPosition));
        }

        byteBufferPosition = byteBufferPosition + 1;
        
        if (byteBufferPosition == 8) {
        
            byteBufferPosition = 0;
            
            outputStream.write(byteBuffer);
        }
    }

    /**
        Special method to write a Bitset to the stream.
    */
    public void write(BitSet b) throws IOException {

        for (int i = 0; i < b.size(); ++i) {
            
            this.write(b.get(i));
        }
    } 

    /**
        Flushes the buffer, pads the byte with zeros for missing bits.
    */
    public void flush() throws IOException {

        for (;byteBufferPosition < 8; ++byteBufferPosition) {

            byteBuffer = (byte)(byteBuffer & ~(1 << byteBufferPosition));
        }
        
        byteBufferPosition = 0;
        
        outputStream.write(byteBuffer);
        outputStream.flush();
    }
}


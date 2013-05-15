import java.io.IOException;
import java.io.InputStream;

/**
 * Counts bytes read.
 */
public class CountingInputStream extends InputStream {

    private InputStream source;
    
    private int counter = 0;
    
    
    public CountingInputStream(InputStream source) {
        super();
        this.source = source;
    }

    @Override
    public int read() throws IOException {
        counter = counter + 1;
        return source.read();
    }
    
    public int getCounter() {
        return counter;
    }
    
    public void resetCounter() {
        counter = 0;
    }
    
}

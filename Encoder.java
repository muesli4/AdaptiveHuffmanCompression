import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;

/**
 * Encoder class for the encoder binary.
 *
 */

public class Encoder {

    public static void main(String[] args) {

        try {
            
            OutputStream os = System.out;
            InputStream is;
            
            if (args.length == 2) {
            
                os = new FileOutputStream(args[1]);
                is = new FileInputStream(args[0]);
            }
            else if (args.length == 1) {
                
                is = new FileInputStream(args[0]);
            }
            else {
                
                System.err.println("invalid number of arguments");
                return;             
            }
            
            // write size at the file beginning
            {
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeInt(is.available());
            }
            
            EncoderOutputStream eos = new EncoderOutputStream(os);
        
            byte[] buffer = new byte[1024];
        
            while (true) {
                int bytesRead = is.read(buffer);
            
                if (bytesRead == -1) {
                    break;
                }
            
                eos.write(buffer, 0, bytesRead);
            }
        
            eos.flush();
        }
        catch(FileNotFoundException f) {

            System.err.println("error: " + f.getLocalizedMessage());
        }
        catch (IOException e) {
            
            System.err.println("io error: " + e.getLocalizedMessage());
        }
    }
}

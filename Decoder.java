import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;

/**
 * Decoder class for the decoder binary.
 *
 */
public class Decoder {

    public static void main(String[] args) {
        
        try {

            OutputStream os = System.out;
            InputStream is = System.in;

            if (args.length == 2) {
                
                os = new FileOutputStream(args[1]);
                is = new FileInputStream(args[0]);
            }
            else if (args.length == 1) {

                is = new FileInputStream(args[0]);
            }
            else if (args.length > 2){
                System.err.println("invalid number of arguments");
                return;
            }

            DecoderInputStream dis;
            
            dis = new DecoderInputStream(is);

            int amount;

            // read byte count
            {
                DataInputStream datIs = new DataInputStream(is);
                
                amount = datIs.readInt();
            }

            byte[] buffer = new byte[1024];
        
            while (true) {

                int bytesRead = dis.read(buffer, 0, (amount >= 1024? 1024 : amount));
            
                if (bytesRead == -1 || amount == 0) {
                    break;
                }
                
                amount = amount - bytesRead;
            
                os.write(buffer, 0, bytesRead);
            }
        
            os.flush();


        }
        catch(FileNotFoundException f) {

            System.err.println("error: " + f.getLocalizedMessage());
        }
        catch (IOException e) {
            
            System.err.println("io error: " + e.getLocalizedMessage());
        }
    }
}

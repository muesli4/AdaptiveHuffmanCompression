
import java.io.PipedOutputStream;
import java.io.PipedInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
    
        try {
//            // create a manual pipe
//            PipedInputStream is = new PipedInputStream();
//            PipedOutputStream os = new PipedOutputStream(is);
//        
//            // connect both through the pipe
//            PrintStream encoderStream = new PrintStream(new EncoderOutputStream(os));
//            BufferedReader decoderReader = new BufferedReader(new InputStreamReader(new DecoderInputStream(is)));
//
//            // TODO test
//            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
//            
//            while (true) {
//            
//                String line = inputReader.readLine();
//                
//                if (line.equals("quit")) {
//                    break;
//                }
//            
//                encoderStream.println(line);
//                System.out.println(decoderReader.readLine());
//            }

            PipedInputStream is = new PipedInputStream();
            PipedOutputStream os = new PipedOutputStream();
            is.connect(os);
            
            EncoderOutputStream eos = new EncoderOutputStream(os);
            DecoderInputStream eis = new DecoderInputStream(is);
            
            
            byte[] buff = new byte[]{97, 98, 99, 100};
            eos.write(buff);
            buff = new byte[4];
            eis.read(buff);
            
            System.out.print((char)buff[0]);
            System.out.print((char)buff[1]);
            System.out.print((char)buff[2]);
            System.out.print((char)buff[3]);
            
            
        }
        catch (Exception e) {
        
            System.err.println("error: " + e.getLocalizedMessage());
        }
    }
}


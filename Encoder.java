import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Encoder {

	public static void main(String[] args) {
		
		EncoderOutputStream eos;
		OutputStream os = System.out;
		InputStream is = System.in;
		
		try {
			if (args.length == 2) {
			
				os = new FileOutputStream(args[1]);
				is = new FileInputStream(args[0]);
			}
			else if (args.length == 1) {
				
				is = new FileInputStream(args[0]);				
			}

			eos = new EncoderOutputStream(os);
			
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

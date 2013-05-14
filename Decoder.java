import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Decoder {

	public static void main(String[] args) {
		
		DecoderInputStream dis;
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

			dis = new DecoderInputStream(is);
			
			byte[] buffer = new byte[1024];
			
			while (true) {
				int bytesRead = dis.read(buffer);
				
				if (bytesRead == -1) {
					break;
				}
				
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

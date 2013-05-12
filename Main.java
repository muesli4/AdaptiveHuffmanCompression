import java.io.BufferedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedInputStream;


public class Main {

	public static void main(String[] args) {

		try {
			PipedInputStream is = new PipedInputStream();
			PipedOutputStream os = new PipedOutputStream();
			is.connect(os);
			
			EncoderOutputStream eos = new EncoderOutputStream(os);
			DecoderInputStream eis = new DecoderInputStream(is);

			while (true) {
			
				int ch = System.in.read();
				
				if (ch == -1) {
					
					break;
				}
				
				eos.write(ch);
				// flush to fill up byte
				eos.flush();
				System.out.print((char)eis.read());
				
				// drop current byte
				eis.dropCurrentByte();
			}

		} catch (Exception e) {

			System.err.println("error: " + e.getLocalizedMessage());
		}
	}
}

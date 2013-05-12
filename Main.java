import java.io.BufferedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedInputStream;


public class Main {

	public static void main(String[] args) {

		try {
			PipedInputStream is = new PipedInputStream();
			PipedOutputStream os = new PipedOutputStream();
			is.connect(os);
			
			CountingInputStream cis = new CountingInputStream(is);
			
			EncoderOutputStream eos = new EncoderOutputStream(os);
			DecoderInputStream eis = new DecoderInputStream(cis);

			while (true) {
			
				String input = "";
				while (true) {
					int ch = System.in.read();
				
					if (ch == -1) {
						return;
					}
					else if (ch == '\n') {
						break;
					}
					else {
						input = input + (char) ch;
					}
				}
				byte[] bytes = input.getBytes();
				
				eos.write(bytes);
				// flush to fill up byte
				eos.flush();
				
				for (int i = 0; i < bytes.length; ++i) {
					System.out.print((char)eis.read());
				}
				
				System.out.println("\nUsed " + cis.getCounter() + " bytes to encode the message, which is a compression rate of: "
										     + (((double)cis.getCounter() / (double)bytes.length) * 100.0) + "%");
				
				// drop last byte
				eis.dropCurrentByte();
				
				// reset counter
				cis.resetCounter();
			}

		} catch (Exception e) {

			System.err.println("error: " + e.getLocalizedMessage());
		}
	}
}

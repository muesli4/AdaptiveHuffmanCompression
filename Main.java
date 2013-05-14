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
			DecoderInputStream dis = new DecoderInputStream(cis);

            System.out.println("Interactive mode started\n");

			while (true) {
			
			    System.out.print(">> ");

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
				
				// quit main loop
				if (input.equals("quit") || input.equals("exit")) {
					return;
				}
				// print encoder
				else if (input.equals("show encoder")) {
					eos.printPrefixTree();
					System.out.println();
					
					continue;
				}
				// print decoder
				else if (input.equals("show decoder")) {
					dis.printPrefixTree();
					System.out.println();
					
					continue;
				}
				else {
					
					byte[] bytes = input.getBytes();
					
					eos.write(bytes);
					// flush to fill up byte
					eos.flush();
					
					System.out.print("Source text was: \"");
					for (int i = 0; i < bytes.length; ++i) {
						System.out.print((char)dis.read());
					}
					System.out.print("\"\n");
					
	
					// print byte usage and compression rate
					System.out.println("Used " + cis.getCounter() + " bytes to encode the message, which is a compression rate of: "
											     + (((double)cis.getCounter() / (double)bytes.length) * 100.0) + "%");
					System.out.println();
					
					
					// drop last byte
					dis.dropCurrentByte();
					
					// reset flow-through counter
					cis.resetCounter();
				}
			}

		}
		catch (Exception e) {

			System.err.println("error: " + e.getLocalizedMessage());
		}
	}
}

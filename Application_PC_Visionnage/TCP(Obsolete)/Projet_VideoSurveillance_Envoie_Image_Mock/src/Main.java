import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

	public static final String IP = "127.0.0.1";
	public static final int PORT = 2000;

	public static final String PATH_FILE_IN1 = "C:\\Users\\rben-hmidane\\Desktop\\images.jpg";
	public static final String PATH_FILE_IN2 = "C:\\Users\\rben-hmidane\\Desktop\\BEN-HMIDANE_Rayane.jpg";
	public static final String PATH_FILE_OUT = "C:\\Users\\rben-hmidane\\Desktop\\BEN-HMIDANE_Rayane.jpg";
	public static final int bufferSize = 30000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Envoyeur envoyeur = new Envoyeur(InetAddress.getByName(IP), PORT,
					PATH_FILE_IN1);

			envoyeur.envoyerFile();

			// envoyeur.close(); faits par le receveur
			// receveur.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Deconnexion extends Thread{

	
	private Connexion connexion;
	
	public Deconnexion (Connexion conn)
	{
		this.connexion = conn;
	}
	public void run() {
		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new InputStreamReader(this.connexion.getSocketTransition().getInputStream()));
			System.out.println("ICI");
			buffer.read();
		} catch (IOException e) {
			
		}
		System.out.println("Deconnexion de ID n°"+this.connexion.getID_Connexion()+" ...");
		connexion.closeConnexion();
	}
	
	
}

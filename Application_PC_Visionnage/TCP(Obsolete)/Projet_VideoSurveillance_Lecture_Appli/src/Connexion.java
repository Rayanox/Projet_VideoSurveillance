import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Connexion extends Thread{

	private Socket socket;
	private ServerSocket serveurSocket;
	private ThreadLecture threadDeLecture;
	
	public Connexion(ServerSocket serveurSoc, ThreadLecture t) {
		this.serveurSocket = serveurSoc;
		this.threadDeLecture = t;		
	}
	
	public void start() {
		try {
			this.socket = serveurSocket.accept();
			System.out.println("Connection RÃ©ussie");
			this.threadDeLecture.activerLecture(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("(receveur) : connexion échouée");
			e.printStackTrace();
		}
	}
	
	public Socket getSocketTransition() {
		return this.socket;
	}
	
	
}

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.jws.Oneway;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Connexion extends Thread{

	
	private static int numero = 1; 
	
	private Socket socket;
	private ServerSocket serveurSocket;
	private ThreadLecture threadDeLecture;
	private InetAddress IPDestinataire;
	private Fenetre fenetre;
	private JPanel panel;
	private JLabel labelImage;
	private Deconnexion deconnexionListenner;
	
	public Connexion(ServerSocket serveurSoc, Socket s, ThreadLecture t, Fenetre f) {
		
		this.panel = new JPanel();
		this.serveurSocket = serveurSoc;
		this.fenetre = f;
		this.threadDeLecture = t;		
		this.socket = s;
		this.labelImage = new JLabel();
		this.panel.add(this.labelImage);
		this.fenetre.getPanel().add(this.panel);
		this.IPDestinataire = s.getInetAddress();// par sur que cela retourne l'ip de la machine distante...
		this.updateLayoutFenetre();
		this.threadDeLecture.activerLecture();
		this.deconnexionListenner = new Deconnexion(this);
		this.deconnexionListenner.start();
	}
	
	public void start() {
		
	
		
		
	}
	
	public Socket getSocketTransition() {
		return this.socket;
	}
	
	public InetAddress getIPDestinataire() {
		return this.IPDestinataire;
	}
	
	public Fenetre getFenetre() {
		return this.fenetre;
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	
	public JLabel getLabelImage() {
		return this.labelImage;
	}
	
	//Met à jours le layout de sorte à obtenir un grid layout avec une case en plus pour accueillir le panel de cette connexion
	public void updateLayoutFenetre() {
		if(this.threadDeLecture.getNbConnexionsActives()>0)
		this.fenetre.modifierLayout(this.threadDeLecture.getNbConnexionsActives());
	}
	
	//Met à jours le layout de la fenetre de sorte à obtenir un grid layout avec une case en moins
	public void updateLayoutFenetreRemove() {
		if(this.threadDeLecture.getNbConnexionsActives()>0)
		this.fenetre.modifierLayout(this.threadDeLecture.getNbConnexionsActives()-1);
	}
	
	public void closeConnexion() {
		this.threadDeLecture.removeConnexionFromList(this);
		this.fenetre.getPanel().remove(this.panel);// on enlève le panel (qui contient le JLabel) de sorte à ce qu'il ne soit plus dans la fenetre.
		this.updateLayoutFenetreRemove();
		if(this.socket != null) {
			try {
				this.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}

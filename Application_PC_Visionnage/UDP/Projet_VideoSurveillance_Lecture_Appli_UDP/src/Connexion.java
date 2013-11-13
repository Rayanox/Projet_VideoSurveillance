import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

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
	
	public void updateLayoutFenetre() {
		if(this.threadDeLecture.getNbConnexionsActives()>0)
		this.fenetre.modifierLayout(this.threadDeLecture.getNbConnexionsActives());
	}
	
}

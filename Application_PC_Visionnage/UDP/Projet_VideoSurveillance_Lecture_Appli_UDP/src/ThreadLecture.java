import java.applet.Applet;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class ThreadLecture extends Thread {

	
	private ArrayList<Connexion> listThreadConnexion;
	private boolean lectureActive;
	private BufferedInputStream bufferDeReception;
	private DatagramSocket socket;
	private DatagramPacket paquetRecu;
	private Fenetre fenetre;

	public ThreadLecture(ArrayList<Connexion> a) {
		this.listThreadConnexion = a;
		this.lectureActive = false;
		try {
			this.socket = new DatagramSocket(Main.PORT_UDP);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void run() {
		try {

			byte[] buffer = new byte[Main.bufferSize];
			int poidImage;
			
			this.paquetRecu = new DatagramPacket(buffer, buffer.length);
			
			
			
			while (this.lectureActive) {
				System.out.println("Lecture lancée ...");
			  
				this.socket.receive(this.paquetRecu);
				
				
				for (Connexion c : this.listThreadConnexion) {
					//Teste si le paquet reçu correspond à celui d'une des connections de notre liste (test en fonction de l'IP)
					if(c.getIPDestinataire().equals(this.paquetRecu.getAddress())) {
						poidImage = this.paquetRecu.getLength();
						
						try {				
							ImageInputStream imageInputS = ImageIO.createImageInputStream(new ByteArrayInputStream(buffer, 0, poidImage));
							BufferedImage bu = ImageIO.read(imageInputS);
							Image image = bu;
							c.getLabelImage().setIcon(new ImageIcon(image));
							//this.fenetre.getPanel().repaint();
							c.getPanel().repaint();
							SwingUtilities.windowForComponent(c.getPanel()).pack();
							//this.fenetre.pack(); // TODO A retirer peut etre selon le rendu
						} catch (IOException e) { 
							e.printStackTrace();
						}
						if(!Main.MultiClientSurMemeOrdi) break;
					}
				}
				buffer = new byte[Main.bufferSize];
				this.paquetRecu = new DatagramPacket(buffer, buffer.length);
				
				
				
			}
			System.out.println("Lecture stoppée ...");

			

			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public boolean getLectureActive() {
		return this.lectureActive;
	}
	
	public void setLectureActive(boolean v) {
		this.lectureActive = v;
	}
	
	public void activerLecture() {
		if(this.getLectureActive() == false)  {
			this.setLectureActive(true);
			this.start();
		} 
	}

	public void desactiverLecture() {
		this.lectureActive = false;
	}

	public int getNbConnexionsActives() {
		return this.listThreadConnexion.size();
	}

}

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
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.plugins.bmp.BMPImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class ThreadLecture extends Thread {

	
	
	private HashMap<Byte, Connexion> SetOfConnexions;
	private boolean lectureActive;
	private BufferedInputStream bufferDeReception;
	private DatagramSocket socket;
	private DatagramPacket paquetRecu;
	private Fenetre fenetre;

	public ThreadLecture(HashMap<Byte, Connexion> set) {
		this.SetOfConnexions = set;
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
				//System.out.println("Lecture lanc�e ...");
			  
				this.socket.receive(this.paquetRecu);
				
				//teste de la connexion
				if(this.SetOfConnexions.containsKey(buffer[0])) {
					//Sélection de la bonne connexion
					Connexion c = this.SetOfConnexions.get(buffer[0]);
					
					
					poidImage = this.paquetRecu.getLength();

					try {				

						ImageInputStream imageInputS = ImageIO.createImageInputStream(new ByteArrayInputStream(buffer, 1, poidImage));


						BufferedImage bu = ImageIO.read(imageInputS);
						Image image = bu;
						c.getLabelImage().setIcon(new ImageIcon(image));
						c.getPanel().repaint();
						SwingUtilities.windowForComponent(c.getPanel()).pack();
					} catch (Exception e) { 
						e.printStackTrace();
					}
						
					
				}
				//On réinitialise le buffer
				buffer = new byte[Main.bufferSize];
				
				
				
				this.paquetRecu = new DatagramPacket(buffer, buffer.length);
				
				
				
			}
			System.out.println("Lecture stopp�e ...");

			

			
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
		return this.SetOfConnexions.size();
	}
	
	public void removeConnexionFromList(Connexion c) {		
		
		this.SetOfConnexions.remove(c.getID_Connexion());
	}

}

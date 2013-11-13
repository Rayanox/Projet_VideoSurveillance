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

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ThreadLecture extends Thread {

	
	private Connexion threadConnexion;
	private boolean lectureActive;
	private BufferedInputStream bufferDeReception;
	private Fenetre fenetre;

	public ThreadLecture(Fenetre f) {
		
		this.lectureActive = false;
		this.fenetre = f;

	}

	public void setThreadConnexion(Connexion c) {
		this.threadConnexion = c;
	}
	
	public void run() {
		try {

			byte[] b = new byte[30000];
			int poidImage;
			this.bufferDeReception = new BufferedInputStream(
					this.threadConnexion.getSocketTransition().getInputStream());
			System.out.println("Lecture lancÃ©e ...");
			

			

			
			while (this.lectureActive) {
				System.out.println("Lecture lancée ...");
			  
				poidImage = this.bufferDeReception.read(b);

				try {				
					ImageInputStream imageInputS = ImageIO.createImageInputStream(new ByteArrayInputStream(b, 0, poidImage));
					BufferedImage bu = ImageIO.read(imageInputS);
					Image image = bu;
					this.fenetre.getLabelImage().setIcon(new ImageIcon(image));
					this.fenetre.getPanel().repaint();
					this.fenetre.pack();
					b = new byte[30000];
				} catch (IOException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Lecture stoppée ...");
			}
			 

			

			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void activerLecture(Connexion c) {
		this.setThreadConnexion(c);
		this.lectureActive = true;
		this.start();
	}

	public void desactiverLecture() {
		this.lectureActive = false;
	}

	// Uniquement pour test dÃ©bogage
	public void ecrireDansFichier(byte[] b, int tailleFichier) {
		File file = new File(
				Main.PATH);
		BufferedOutputStream ecrivainFichier;
		try {
			ecrivainFichier = new BufferedOutputStream(new FileOutputStream(
					file));

			try {
				ecrivainFichier.write(b, 0, tailleFichier);
				ecrivainFichier.flush();
				ecrivainFichier.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

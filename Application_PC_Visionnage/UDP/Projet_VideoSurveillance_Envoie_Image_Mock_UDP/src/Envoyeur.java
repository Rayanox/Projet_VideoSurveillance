import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Envoyeur {

	private DatagramSocket socket;
	private Socket socketConnection;
	private InetAddress IP;
	private int port;
	private String pathFile;
	private FileInputStream bufferLectureFichier1;
	private FileInputStream bufferLectureFichier2;
	private File fichier;
	private File fichier2;

	public Envoyeur(InetAddress ip, int port, String pathFile) {
		this.IP = ip;
		this.port = port;
		this.pathFile = pathFile;
		
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.fichier = new File(this.pathFile);
		this.fichier2 = new File(Main.PATH_FILE_IN2);
		
		try {

			this.bufferLectureFichier1 = new FileInputStream(fichier);
			this.bufferLectureFichier2 = new FileInputStream(fichier2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	public void seConnecterAuServeur() throws IOException {
		this.socketConnection = new Socket(this.IP, this.port);
		System.out.println("Connection réussie");
	}

	public void envoyerFile() {
		byte[] b = new byte[Main.bufferSize];
		int i = 0;
		boolean fichier1Tour = true;
		System.out.println("(envoyeur) : envoie de fichier ...");
		int compteur = 0;
		
		while(true) {
			try {

				if(fichier1Tour) {
					i = this.bufferLectureFichier1.read(b);
					DatagramPacket packetEnvoie = new DatagramPacket(b, b.length, InetAddress.getByName(Main.IP), Main.PORT_UDP);
					this.socket.send(packetEnvoie);					
					b = new byte[Main.bufferSize];
					this.bufferLectureFichier1 = new FileInputStream(this.fichier);
					
				} else {
					i = this.bufferLectureFichier2.read(b);
					DatagramPacket packetEnvoie = new DatagramPacket(b, b.length, InetAddress.getByName(Main.IP), Main.PORT_UDP);
					this.socket.send(packetEnvoie);					
					b = new byte[Main.bufferSize];
					this.bufferLectureFichier2 = new FileInputStream(fichier2);
				}
				
				
			

				
				
			
				System.out.println("(envoyeur) : envoie de fichier réussie "+compteur++);
				b = new byte[Main.bufferSize];
				fichier1Tour = !fichier1Tour;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("(envoyeur) : envoie de fichier échoué");
			}
		}
		

		//this.close();
	}

	/*
	 * public void envoyerFile2() { byte t[] = new byte[1024]; int n; try {
	 * while((n = this.bufferLectureFichier.read()) != -1) {
	 * this.bufferEnvoie.println(t); this.bufferEnvoie.flush(); }
	 * this.bufferEnvoie.println("null"); this.bufferEnvoie.flush(); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 * 
	 * }
	 */

	public void close() {
		try {
			this.bufferLectureFichier1.close();
			this.bufferLectureFichier2.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.socket.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}

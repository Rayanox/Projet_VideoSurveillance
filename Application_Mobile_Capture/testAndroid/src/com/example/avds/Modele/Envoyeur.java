package com.example.avds.Modele;

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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

import javax.net.SocketFactory;

import com.example.avds.MainActivity;

public class Envoyeur {

	// La solution de connection utilisée est à la fois du TCP/IP et de l'UDP. 
	// La procédure est la suivante :
	// On effectue tout d'abord une connection en TCP/IP pour être sur que les deux appareils
	// soient connectés. Puis les échanges sont effectués en UDP pour une rapidité optimale
	// et parce qu'en vidéo (ou pseudo-vidéo), la perte d'image est peu importante. 
	
	private DatagramSocket socket;
	private Socket socketConnection;
	private InetAddress IP;
	private int port;
	private boolean envoyeurDispo; //variable utile pour locker la classe (du moins la méthode d'envoie)  pendant l'envoie d'une image. C'est une sécurité.

	public Envoyeur(InetAddress ip, int port) {
		this.IP = ip;
		this.port = port;
		this.envoyeurDispo = true;
		
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		

		
		
	}
	
	public void seConnecterAuServeur() throws IOException {
		try {
			this.socketConnection = new Socket();
			this.socketConnection.connect(new InetSocketAddress(this.IP, this.port), 6000); // 6000 est la valeur du timeout, ça correspond à 6 secondes et c'est raisonnable. Par défaut, on devait attendre plusieurs minutes, ce qui était très embettant
			System.out.println("Connection r�ussie");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Connexion échouée !");
			throw e;
		}
	}

	public boolean getEnvoyeurDispo() {
		return this.envoyeurDispo;
	}
	
	public void envoyerDonnees(byte[] datas) {	
		
			try {
				this.envoyeurDispo = false;
				DatagramPacket packetEnvoie = new DatagramPacket(datas, datas.length, InetAddress.getByName(MainActivity.IP), MainActivity.PORT_UDP);
				this.socket.send(packetEnvoie);			
			
				System.out.println("(envoyeur) : envoie de fichier réussie ");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("(envoyeur) : envoie de fichier échoué");
			}
			
			this.envoyeurDispo =  true;
		
	}

	

	public void closeConnexion() {
		
		try {
			this.socket.close();
			this.socketConnection.close();
			System.out.println("Fermeture socket r�ussie");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}

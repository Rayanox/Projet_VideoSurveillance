import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

//TEEEEST

public class Main {

	/**
	 * @param args
	 */
	
	
	//TODO Mise en place UDP [OK]
	//TODO Ajouter syst�me d'authentification par ip (UDP) lors de la r�c�ption de chaque requete, ou rechercher une autre solution. [OK]
	//TODO R�gler bug lors de 3 clients sur m�me poste... [OK] => fixed with a sleep, threadlecture accedait � la liste alors que l'objet n'�tait pas encore initialis� totalement.
	//TODO Faire syst�me de une fenetre avec un panel par connexion [OK]
	//TODO Mettre en place github [OK]
	//TODO G�rer les d�connections
	//TODO G�rer tous les closes 
	//TODO Rajouter des trucs graphiques (TRES OPTIONNEL)
	

	public static final int WidthFenetreMin = 300;
	public static final int HeightFenetreMin = 300;
	public static final int LocationFenetreX = 2000; // vaut  2000 pour un double �cran, pour unique �cran, changer � 500.
	public static final int LocationFenetreY = 300;
	
	public static final int PORT_TCP = 2000;
	public static final int PORT_UDP = 2001;
	public static final int bufferSize = 30000; // A changer en fonction de la taille de l'image
	public static final int nombreMaxConnection = 127; //correspond à la taille d'un byte d'en l'éventualité que celui-ci mesure 7bits (voir l'usage de la variable dans le programme -> dans le main)
	public static final long nbMilliSecondesLatence = 1500; // temps de pause � chaque ajout d'image.
	
	
	public static void main(String[] args) {
		
		HashMap<Byte, Connexion> SetOfConnexions = new HashMap<Byte, Connexion>();// Ensemble des utilisateurs connect�s
		
		Fenetre fenetre = new Fenetre();
		ThreadLecture threadLecture = new ThreadLecture(SetOfConnexions);
		
		
		try {
			ServerSocket SocketServeur = new ServerSocket(PORT_TCP);
			
			
			while(true) {
				// Attente de connexion ...                				
				try {
					Socket s = SocketServeur.accept();
					
					byte ID = genererEtEnvoyerID(s, SetOfConnexions);
					
					if(ID!= -1) {
						threadLecture.sleep(Main.nbMilliSecondesLatence);
						Connexion c = new Connexion(SocketServeur, s,threadLecture, fenetre, ID);
						SetOfConnexions.put(ID, c);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static byte genererEtEnvoyerID(Socket s, HashMap<Byte, Connexion> SetOfConnexions) {
		// TODO Auto-generated method stub
		try {
			BufferedOutputStream writer = new  BufferedOutputStream(s.getOutputStream());
			byte ID = genererID(SetOfConnexions);
			if(ID != -1) {
				writer.write(new byte [] {ID}, 0 , 1);
				writer.flush();
				System.out.println("ID généré");
			} else writer.close();
			System.out.println("ID = "+ID);
			return ID;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}


	private static byte genererID(HashMap<Byte, Connexion> setOfConnexions) {
		// TODO Auto-generated method stub
		byte newId = 0;
		while(newId < nombreMaxConnection) {// taille max d'un byte de 7bit
			if(!setOfConnexions.containsKey(newId)) {
				return newId;
			}
			newId++;
		}
		return -1;
	}

}

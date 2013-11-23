import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//TEEEEST

public class Main {

	/**
	 * @param args
	 */
	
	
	//TODO Mise en place UDP [OK]
	//TODO Ajouter syst�me d'authentification par ip (UDP) lors de la r�c�ption de chaque requete, ou rechercher une autre solution. [OK]
	//TODO R�gler bug lors de 3 clients sur m�me poste... [OK] => fixed with a sleep, threadlecture accedait � la liste alors que l'objet n'�tait pas encore initialis� totalement.
	//TODO Faire syst�me de une fenetre avec un panel par connexion [OK]
	//TODO Mettre en place github
	//TODO G�rer les d�connections
	//TODO G�rer tous les closes 
	//TODO Rajouter des trucs graphiques (TRES OPTIONNEL)
	
	public static final String PATH = "C:\\Users\\rben-hmidane\\Desktop\\image_recue.jpg";
	public static final int WidthFenetreMin = 300;
	public static final int HeightFenetreMin = 300;
	public static final int PORT_TCP = 2000;
	public static final int PORT_UDP = 2001;
	public static final int bufferSize = 30000; // A changer en fonction de la taille de l'image
	public static final int LocationFenetreX = 2000; // vaut  2000 pour un double �cran, pour unique �cran, changer � 500.
	public static final int LocationFenetreY = 300;
	public static final int NombreMaxConnections = 5;
	public static final long nbMilliSecondesLatence = 1500; // temps de pause � chaque ajout d'image.
	
	public static final boolean MultiClientSurMemeOrdi = true; // mettre � true si plusieurs clients peuvent �tre connect�s sur le m�me
															   // ordinateur, cependant, les performances peuvent �tre tr�s
															   // ralenties car l'algorithme parcourt syst�matiquement 
															   // toute la liste de clients. En prod, la valeur doit �tre � FALSE.
	
	
	public static void main(String[] args) {
		
		
		ArrayList<Connexion> listeDeConnexions = new ArrayList<Connexion>(); // Liste des utilisateurs connect�s
		Fenetre fenetre = new Fenetre();
		ThreadLecture threadLecture = new ThreadLecture(listeDeConnexions);
		
		
		try {
			ServerSocket SocketServeur = new ServerSocket(2000);
			
			//Autorise 5 connexions simultann�es max
			for(int i = 0; i<Main.NombreMaxConnections; i++) {
				// Attente de connexion ...               ( � cause de SocketServeur.accept() ) 				
				try {
					Socket s = SocketServeur.accept();
					threadLecture.sleep(Main.nbMilliSecondesLatence);
					listeDeConnexions.add(new Connexion(SocketServeur, s,threadLecture, fenetre)); 
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

}

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	
	
	//TODO Mise en place UDP [OK]
	//TODO Ajouter système d'authentification par ip (UDP) lors de la récéption de chaque requete, ou rechercher une autre solution. [OK]
	//TODO Régler bug lors de 3 clients sur même poste... [OK] => fixed with a sleep, threadlecture accedait à la liste alors que l'objet n'était pas encore initialisé totalement.
	//TODO Faire système de une fenetre avec un panel par connexion [OK]
	//TODO Mettre en place github
	//TODO Gérer les déconnections
	//TODO Gérer tous les closes 
	//TODO Rajouter des trucs graphiques (TRES OPTIONNEL)
	
	public static final String PATH = "C:\\Users\\rben-hmidane\\Desktop\\image_recue.jpg";
	public static final int WidthFenetreMin = 300;
	public static final int HeightFenetreMin = 300;
	public static final int PORT_TCP = 2000;
	public static final int PORT_UDP = 2001;
	public static final int bufferSize = 30000; // A changer en fonction de la taille de l'image
	public static final int LocationFenetreX = 2000; // vaut  2000 pour un double écran, pour unique écran, changer à 500.
	public static final int LocationFenetreY = 300;
	public static final int NombreMaxConnections = 5;
	public static final long nbMilliSecondesLatence = 1500; // temps de pause à chaque ajout d'image.
	
	public static final boolean MultiClientSurMemeOrdi = true; // mettre à true si plusieurs clients peuvent être connectés sur le même
															   // ordinateur, cependant, les performances peuvent être très
															   // ralenties car l'algorithme parcourt systématiquement 
															   // toute la liste de clients. En prod, la valeur doit être à FALSE.
	
	
	public static void main(String[] args) {
		
		
		ArrayList<Connexion> listeDeConnexions = new ArrayList<Connexion>(); // Liste des utilisateurs connectés
		Fenetre fenetre = new Fenetre();
		ThreadLecture threadLecture = new ThreadLecture(listeDeConnexions);
		
		
		try {
			ServerSocket SocketServeur = new ServerSocket(2000);
			
			//Autorise 5 connexions simultannées max
			for(int i = 0; i<Main.NombreMaxConnections; i++) {
				// Attente de connexion ...               ( à cause de SocketServeur.accept() ) 				
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

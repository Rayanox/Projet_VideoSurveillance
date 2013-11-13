import java.io.IOException;
import java.net.ServerSocket;

public class Main {

	/**
	 * @param args
	 */
	
	public static final String PATH = "C:\\Users\\rben-hmidane\\Desktop\\image_recue.jpg";
	public static final int WidthFenetreMin = 300;
	public static final int HeightFenetreMin = 300;
	public static final int PORT = 2000;
	
	
	public static void main(String[] args) {
		
		
		
		
		// TODO Auto-generated method stub
		/*
		 * Socket socketTransition; ThreadLecture lecteur; Connexion connexion;
		 * ServerSocket socketServeur;
		 */
		Fenetre fenetre = new Fenetre();
		ThreadLecture t = new ThreadLecture(fenetre);
		try {
			Connexion c = new Connexion(new ServerSocket(PORT), t);
			c.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * try { socketServeur = new ServerSocket(PORT); } catch (IOException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		// connexion = new Connexion(socketServeur, socketTransition, lecteur);

	}

}

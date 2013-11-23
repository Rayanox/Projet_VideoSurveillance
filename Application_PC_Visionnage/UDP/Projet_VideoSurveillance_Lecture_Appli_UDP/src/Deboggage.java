import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


//Classe avec dees utilitaires uniquement utiles en déboggages

public class Deboggage {

	public static void ecrireDansFichier(byte[] b, int tailleFichier) {
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

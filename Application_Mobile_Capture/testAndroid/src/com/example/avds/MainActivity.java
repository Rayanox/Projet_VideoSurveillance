package com.example.avds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.example.avds.Modele.Captureur;
import com.example.avds.Modele.Envoyeur;
import com.example.avds.Modele.Module_Arborescence_Fichier;
import com.example.avds.Vue.Capture.CameraRunActivity;
import com.example.avds.Vue.Configuration.CaptureActivity;
import com.example.avds.Vue.Configuration.ConfigurationActivity;
import com.example.avds.Vue.Configuration.GeneralActivity;
import com.example.testandroid.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	// A faire :
	
	// - Vérifier le codage du Lecteur par rapport à plusieurs lancements de l'applis. (en lien avec le point suivant)
	// - Améliorer la reconnaissance de chaque client (actuellement par adresse IP)
	// - Coder de sorte à ce que les déconnexions à tout moment n'aient pas d'impact étrange sur les programmes distants. [~OK] : mais pas géré pour une déconnexion inopinée du serveur, l'appli risque de planter
	// - Faire les liaisons entre les récupérations de la config et les parametres globaux
	// - Dans les configurations des ports, modifier le programme pour que l'on puisse rajouter manuellement le TCP/IP et  l'UDP
	// - [OK] Ajouter un fichier de config ou seront stoquées les configurations de l'appli (avec un méthode exists() qui vérifiera si le fichier existe bien, sinon, on ne peut pas run la capture)
	// - Rajouter champs de port UDP dans la vue de config du serveur
	// - Adapter les tailles des composants à tous les téléphones.	
	// - Ajouter une capture par vraie vidéo.
	// - Vérifier l'utilisation de toutes les Tasks du code (todo )
	// - Application Lecture : Peut etre passer sur un modele multi-thread  de threadLecture pour un petit plus optimiser sachant qu'il y aura toujours un controller qui recevra les requetes, mais il délèguera le travail du traitement aux threads(pourrait se faire par le run des connexions).
	// - Modifier la connection en ajoutant une socketStreaming. 
	
	//Faits :
	
	// - Désallouer tous les éléments à la fin [~OK]	
	// - Coder la capture et l'envoie [OK]
	// - 
	
	
	
	
	public static String IP = "10.0.2.2"; //Mettre l'IP du serveur (ne surtout pas mettre 127.0.0.1 car c'est le localhost de la machine virtuel qui est diff�rent de celui du pc :-> dans ce cas la, on peut utiliser 10.0.2.2 qui signifie l'ip de la machine mere)
	public static int PORT = -1;// -1 signifie que le fichier de configuration n'a pas encore été chargé. Note :(2000 : Port par défaut)
	public static int PORT_UDP = -1; // Note : (2001 : Port par défaut)
	public static int bufferSize = 30000; //Taille maximale d'un fichier image (� r�duire au plus possible en fonction de la taille des images que l'on va envoyer)

	
	public static String PATH_FICHIER_CONFIG ; // Défini à la création du programme car on ne pouvait pas faire appel à la méthode qui permet de trouver le fichier père interne du télépone car la méthode n'était pas static.
	
	public static boolean lectureActive = false;
	public static boolean plannificationActive = false; // Lock le programme pour ne pas qu'on puisse le détruire si une plannification a été définie.
	public static boolean modeSecret = false;	
	
	private Context context = this;
	private Button StopCaptureButton;
	
	@Override
	public void onBackPressed() {
	
		if(MainActivity.lectureActive || MainActivity.plannificationActive) {
			moveTaskToBack(true);
		} else super.onBackPressed();
		
		
	}
	
	// Méthode overridée qui est automatquement appelée en fin d'activité.
	public void onDestroy() {
		
		
		System.out.println("On quitte le programme");	
		super.onDestroy();
		
		
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//On set le chemin du fichier de config
		PATH_FICHIER_CONFIG = getFilesDir().getPath()+"/AVDS/Serveur.config";
		
		System.out.println("OHEEEEEE");
		
		final Button configButton = (Button) findViewById(R.id.btn_config);
		configButton.setOnClickListener(new OnClickListener() {
					
		  @Override
		  public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, ConfigurationActivity.class);
			startActivity(intent);
		  }
		});
		  
		this.StopCaptureButton = (Button) findViewById(R.id.StopperCapture);
		this.StopCaptureButton.setOnClickListener(new OnClickListener() {
					
		  @Override
		  public void onClick(View v) {
			  MainActivity.lectureActive = false;
			  StopCaptureButton.setVisibility(View.GONE);
		  }
		});
		
		StopCaptureButton.setVisibility(View.GONE); //invisible car inutile pour au départ
		
		
		  final Button QuitButton = (Button) findViewById(R.id.btn_quitter);
		  QuitButton.setOnClickListener(new OnClickListener() {
					
		  @Override
		  public void onClick(View v) {			  
			onBackPressed();  
		  }
		});
		  
		  final Button CaptureButton = (Button) findViewById(R.id.btn_demarrer);
		  CaptureButton.setOnClickListener(new OnClickListener() {					
			  @Override
			  public void onClick(View v) {  			  
				  
				  if(!MainActivity.lectureActive){
				  	if(!MainActivity.plannificationActive) {
				  	// Si le fichier de configuration a été chargé, on lance l'activité de capture et d'envoie
				  		if(chargerFichierConfig(context)) {
				  			
				  			//TODO Je ne sais plus à quoi ça sert, à retrouver sur internet, mais ça sera probablement à supprimé par rapport aux changements qui ont été fait ici.	
							StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();	
							StrictMode.setThreadPolicy(policy);
							
							// Lancement de la vue de capture
							if(MainActivity.modeSecret) {
								Captureur captureur = new Captureur(null,getThis() ,null); 
								captureur.start();
								StopCaptureButton.setVisibility(View.VISIBLE);
							} else {
								Intent intent = new Intent(getThis(), CameraRunActivity.class);
								startActivity(intent);
							}
							AfficherToast(getThis(), "Lancement de la capture");
							
				  			
				  		}else { // Sinon  on affiche un message d'erreur et on ne fait rien.
				  		
				  			AfficherToast(context, "Les configurations n'ont pas été parametrées : merci de les remplir dans la rubrique Configuration du serveur");
				  							  			
				  		}	
				  	} else {
				  		AfficherToast(context, "Une plannification est déjà activée : vous ne pouvez pas lancer de capture");
				  	}
				  } else {
					  AfficherToast(context, "Une lecture est déjà en cours");
				  }
			  }
		});  
	}
	
	public static void AfficherToast(Context context, final String texte) {
		Toast.makeText(context, texte, Toast.LENGTH_LONG).show();		
	}
	
	public static boolean chargerFichierConfig(Context context) {
		File fichierConfig = new File(MainActivity.PATH_FICHIER_CONFIG);
		
		if(!fichierConfig.exists() || fichierConfig.isDirectory()) {			
			fichierConfig.mkdirs();			
			try {
				if(fichierConfig.exists()) fichierConfig.delete();
				fichierConfig.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			/*if(fichierConfig.isDirectory()) System.out.println("Le fichier créé est un repertoire");
			else System.out.println("Le fichier créé est un fichier");
			Module_Arborescence_Fichier m = new Module_Arborescence_Fichier();
			System.out.println(m.LancerTravailString(getFilesDir().getAbsolutePath()));*/
			
			return false;			
		}
		
		//--- Cas ou le fichier de config est déjà créé
		String ip ="", port_TCP = "", port_UDP = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fichierConfig));
			for(int i = 0; i<3; i++) {
				String var;
				try {
					var = reader.readLine();
				} catch (IOException e) {
					if(context!=null)
					AfficherToast(context, "Erreur lors de la lecture du buffer du fichier de config");
					return false;
				}
				if(var == null) { // Si une des lignes est manquantes, c'est que le fichier n'est pas correct
					return false;
				}
				
				switch(i) {
				case 0 :
					ip = var;
					if(!VerifieIPValid(ip)) return false;
					break;
				case 1 :
					port_TCP = var;
					if(!VerifiePortValid(port_TCP)) return false;
					break;
				case 2:
					port_UDP = var;
					if(!VerifiePortValid(port_UDP)) return false;
					break;
				default :
					System.out.println("Probleme de code");
					return false;
				}
				
			}
			
			//fermeture du buffer
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//--- Si il sort de la boucle et arrive ici, c'est que le fichier de conf est valide
			//--- on peut donc ajouter mettre à jours les variables statics et ouvrir l'accès à la vue suivante
			
			MainActivity.IP = ip;
			MainActivity.PORT = Integer.parseInt(port_TCP);
			MainActivity.PORT_UDP = Integer.parseInt(port_UDP);
			
			return true;
			
		} catch (FileNotFoundException e) {
			if(context!=null)
			AfficherToast(context, "Erreur lors de la lecture du buffer du fichier de config");
			return false;
		}		
	}
	
	private static boolean VerifiePortValid(String port) {
		int nb;
		try {
			nb = Integer.parseInt(port);
		} catch (Exception e) {
			return false;
		}
		return nb > 0;
	}
	
	private static boolean VerifieIPValid(String host) {
		try {
			Inet4Address.getByName(host);
		} catch (UnknownHostException e) {
			return false;
		}
		return true;
	}
	
	
	public MainActivity getThis() {
		return this;
		
	}
	
	
}

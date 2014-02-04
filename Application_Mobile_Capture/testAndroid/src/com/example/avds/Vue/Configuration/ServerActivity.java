package com.example.avds.Vue.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avds.MainActivity;
import com.example.avds.Modele.Module_Arborescence_Fichier;
import com.example.testandroid.R;

public class ServerActivity extends Activity {
	private Button servRetourButton;
	private Button servEnregistrerButton;
	private EditText ChampsIP;
	private EditText ChampsPortTCP;
	private EditText ChampsPortUDP;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serveur);
		
		this.ChampsIP = (EditText) findViewById(R.id.edt_adrIP);		
		this.ChampsPortTCP = (EditText) findViewById(R.id.edt_portServeurTCP);
		this.ChampsPortUDP = (EditText) findViewById(R.id.edt_portServeurUDP);
		
		// Remplissage des valeurs par défaut si elles existent déjà (A executé obligatoirement après l'initialisation des variables de champs)
		this.MettreTexteParDefautDansChamps();
		
		this.servRetourButton = (Button) findViewById(R.id.btn_servRetour);
		this.servRetourButton.setOnClickListener(new OnClickListener() {
						
			@Override
			public void onClick(View v) {
				finish();
			  }
			});
		
		//Récupération de l'adresse ip et port du serveur
		this.servEnregistrerButton = (Button) findViewById(R.id.btn_servEnregistrer);
		this.servEnregistrerButton.setOnClickListener(new OnClickListener() {
						
			@Override
			public void onClick(View v) {
				//Récupération des valeurs
				String adrIP = ChampsIP.getText().toString();
				String portServeurTCP = ChampsPortTCP.getText().toString();
				String portServeurUDP = ChampsPortUDP.getText().toString();
				//String port_UDP = "2001"; 
				
				//Lancement du remplissage du fichier de config
				configurerFichierConfig(adrIP, portServeurTCP, portServeurUDP);
			}
			});	
	
	}

	//Dans cette méthode a été introduit un pattern de sortie de méthode à tout instant d'échec. La fin de la méthode est donc obligatoirement une réussite
	private void configurerFichierConfig(String ip, String port_tcp, String port_udp) {
		File fichierConfig = new File(MainActivity.PATH_FICHIER_CONFIG);
		
		if(!fichierConfig.exists() || fichierConfig.isDirectory()) {			
			fichierConfig.mkdirs();			
			try {
				if(fichierConfig.exists()) fichierConfig.delete();
				fichierConfig.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				this.AfficherToast("Probleme inconnu survenu");
				return;
			}
					
		} else {
			//--- Cas ou le fichier de config est déjà créé
			
			// on vérifie la validité des informations
			int p_tcp, p_udp;
			try {
				p_tcp = Integer.parseInt(port_tcp);
				p_udp = Integer.parseInt(port_udp);
				Inet4Address.getByName(ip);
				if(p_tcp < 1 || p_tcp > 65000) throw new Exception("Port inférieur à 0 ou supérieur à 65000");
				if(p_udp < 1 || p_udp >65000) throw new Exception("Port inférieur à 0 ou supérieur à 65000");				
			} catch(Exception e) {
				this.AfficherToast("Paramètres de configuration invalides : modifier les paramètres incorrects");
				return;
			}
				
			
			
			
			//--- Si on arrive ici, c'est que les parametres sont bien valides et que le fichier de conf est créé, on peut alors commencer l'insertion dans le fichier
			
			FileWriter writer;
			try {
				writer = new FileWriter(fichierConfig);
				writer.write(ip+"\n");
				writer.write(p_tcp+"\n");
				writer.write(p_udp+"\n");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				this.AfficherToast("Probleme lors de l'écriture dans le fichier");
				e.printStackTrace();
				return;
			}
			
			this.AfficherToast("Enregistrement réussi !");
			this.finish();
			
			//test lecture fichier
			/*BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(fichierConfig));
				String affichage ="";
				while((affichage = reader.readLine()) != null) System.out.println("ligne = "+affichage);
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			
		}		
		
	}
	
	// Met un texte par défaut dans les champs. Le texte correspond à la valeur dans le fichier de config. Si celui-ci n'est pas valide, alors on laisse les valeurs par défaut comme elles étaient déjà.
	private void MettreTexteParDefautDansChamps() {
		File fichierConfig = new File(MainActivity.PATH_FICHIER_CONFIG);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fichierConfig));
			String ip ="", port_TCP = "", port_UDP = "";
			
				for(int i = 0; i<3; i++) {
					String var;
					try {
						var = reader.readLine();
						
						if(var == null) { // Si une des lignes est manquantes, c'est que le fichier n'est pas correct
							return;
						}
						
						switch(i) {
						case 0 :
							ip = var;
							break;
						case 1 :
							port_TCP = var;
							break;
						case 2:
							port_UDP = var;						
							break;
						default :
							System.out.println("Probleme de code");
							return;
						}
					} catch (IOException e) {
						this.AfficherToast("Erreur lors de la lecture du buffer du fichier de config");
						return;
					}
					
					
				}
				
				//fermeture du buffer
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// --- Verification des valeurs
				if(this.verificationValidite(ip, port_TCP, port_UDP)) {
					this.ChampsIP.setText(ip);
					this.ChampsPortTCP.setText(port_TCP);
					this.ChampsPortUDP.setText(port_UDP);
				}
				
				
		} catch (FileNotFoundException e) {
			this.AfficherToast("Erreur lors de la lecture du fichier config pour valeurs par défaut");
			e.printStackTrace();
		}
		
	}
	
	private boolean verificationValidite(String ip, String port_tcp, String port_udp) {
		int p_tcp, p_udp;
		try {
			p_tcp = Integer.parseInt(port_tcp);
			p_udp = Integer.parseInt(port_udp);
			Inet4Address.getByName(ip);
			if(p_tcp < 1 || p_tcp > 65000) throw new Exception("Port inférieur à 0 ou supérieur à 65000");
			if(p_udp < 1 || p_udp >65000) throw new Exception("Port inférieur à 0 ou supérieur à 65000");				
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	private void AfficherToast(final String texte) {
		Toast.makeText(this, texte, Toast.LENGTH_LONG).show();		
	}
}

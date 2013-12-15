package com.example.avds;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.example.avds.Modele.Envoyeur;
import com.example.avds.Vue.Capture.CameraRunActivity;
import com.example.avds.Vue.Configuration.CaptureActivity;
import com.example.avds.Vue.Configuration.ConfigurationActivity;
import com.example.avds.Vue.Configuration.GeneralActivity;
import com.example.testandroid.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	// A faire :
	// - Adapter les tailles des composants à tous les téléphones.
	// - Faire les liaisons entre les récupérations de la config et les parametres globaux
	// - Ajouter un fichier de config ou seront stoquées les configurations de l'appli (avec un méthode exists() qui vérifiera si le fichier existe bien, sinon, on ne peut pas run la capture)
	// - Ajouter une capture par vraie vidéo.
	// - Désallouer tous les éléments à la fin
	
	public static final String IP = "192.168.3.84"; //Mettre l'IP du serveur (ne surtout pas mettre 127.0.0.1 car c'est le localhost de la machine virtuel qui est diff�rent de celui du pc)
	public static final int PORT = 2000;
	public static final int PORT_UDP = 2001;
	public static final int bufferSize = 30000; //Taille maximale d'un fichier image (� r�duire au plus possible en fonction de la taille des images que l'on va envoyer)

	public static final String PATH_FILE_IN1 = Environment.getExternalStorageDirectory().getPath()+"/Download/TestMe.jpg";
	public static final String PATH_FILE_IN2 = Environment.getExternalStorageDirectory().getPath()+"/Download/TestMe.jpg";
	
	private Envoyeur envoyeur;
	
	public void onDestroy() {
		System.out.println("On quitte le programme");
		if(this.envoyeur != null) this.envoyeur.close();
		super.onDestroy();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		System.out.println("OHEEEEEE");
		
		final Button configButton = (Button) findViewById(R.id.btn_config);
		configButton.setOnClickListener(new OnClickListener() {
					
		  @Override
		  public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, ConfigurationActivity.class);
			startActivity(intent);
		  }
		});
		  
		  final Button QuitButton = (Button) findViewById(R.id.btn_quitter);
		  QuitButton.setOnClickListener(new OnClickListener() {
					
		  @Override
		  public void onClick(View v) {
			finish();
		  }
		});
		  
		  final Button CaptureButton = (Button) findViewById(R.id.btn_demarrer);
		  CaptureButton.setOnClickListener(new OnClickListener() {					
			  @Override
			  public void onClick(View v) {  			  
				  
						//envoyeur = new Envoyeur(InetAddress.getByName(IP), PORT);
	
						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	
						StrictMode.setThreadPolicy(policy);
						
						Intent intent = new Intent(getThis(), CameraRunActivity.class);
							startActivity(intent);
							
						try {
							//envoyeur.seConnecterAuServeur();
							//envoyeur.envoyerFile();
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							
							e.printStackTrace();
							System.out.println("Connection �chou�e");
						}
						
						// envoyeur.close(); faits par le receveur
						// receveur.close();
	
					
			  }
		});  
	}
	public MainActivity getThis() {
		return this;
	}
}

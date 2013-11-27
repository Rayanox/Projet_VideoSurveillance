package com.example.testandroid;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
	
	
	public static final String IP = "192.168.3.84"; //Mettre l'IP du serveur (ne surtout pas mettre 127.0.0.1 car c'est le localhost de la machine virtuel qui est différent de celui du pc)
	public static final int PORT = 2000;
	public static final int PORT_UDP = 2001;
	public static final int bufferSize = 30000; //Taille maximale d'un fichier image (à réduire au plus possible en fonction de la taille des images que l'on va envoyer)

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
		
		final Button loginButton = (Button) findViewById(R.id.btn_config);
		  loginButton.setOnClickListener(new OnClickListener() {
					
		  @Override
		  public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, ConfigurationActivity.class);
			startActivity(intent);
		  }
		});
		  
		  final Button CaptureButton = (Button) findViewById(R.id.btn_demarrer);
		  CaptureButton.setOnClickListener(new OnClickListener() {
					
		  @Override
		  public void onClick(View v) {
			  
			  			  
			  try {
					envoyeur = new Envoyeur(InetAddress.getByName(IP), PORT);

					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

					StrictMode.setThreadPolicy(policy);

					try {
						envoyeur.seConnecterAuServeur();
						envoyeur.envoyerFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
						System.out.println("Connection échouée");
					}
					
					
					// envoyeur.close(); faits par le receveur
					// receveur.close();

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  
			  
			  
			  
		  }
		});  
	}
}

package com.example.avds.Vue.Capture;

import com.example.avds.Modele.Captureur;
import com.example.testandroid.R;
import com.example.testandroid.R.layout;
import com.example.testandroid.R.menu;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

// C'est ici que se trouve le code de la capture des photos et de leur traitement. On effectue en fait une boucle de capture lors du lancement du thread
// Dès que le thread est starté, il entamme un début de boucle de capture et renouvelle l'opération à chaque fin de prise de capture de manière à optimiser la boucle$
// et ne pas poser de problème de caméra déjà utilisée.

public class CameraRunActivity extends Activity {

	
	private ImageView image;
	private Button boutonFinCapture;
	private Captureur captureur;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_run);
		this.image = (ImageView) findViewById(R.id.imageAffiche);
		this.boutonFinCapture = (Button) findViewById(R.id.BoutonPrisePhoto);
		
		this.captureur = new Captureur(this.image, this, this);				
		this.initialiserBoutons();
		
		
		//lancement du travail de capture et d'envoie
		this.captureur.start();
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera_test, menu);
		return true;
	}
	
	public void initialiserBoutons() {
		this.boutonFinCapture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Coder la prise de photo.
				onBackPressed();
				
				
			}
		});
	}
	
	public void onBackPressed() {
		this.captureur.StopperCapture();
		super.onBackPressed();
	}
	
	
	
	public void AfficherToast(final String texte) {
		
		runOnUiThread(new Runnable() {

	        @Override
	        public void run() {
	            Toast.makeText(getApplicationContext(), texte, Toast.LENGTH_LONG).show();
	        }
	    });
	}
	
		

}

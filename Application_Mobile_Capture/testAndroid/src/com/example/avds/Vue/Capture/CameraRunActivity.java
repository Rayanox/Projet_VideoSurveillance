package com.example.avds.Vue.Capture;

import com.example.avds.Modele.Captureur;
import com.example.testandroid.R;
import com.example.testandroid.R.layout;
import com.example.testandroid.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CameraRunActivity extends Activity {

	//Il faut aussi que j'ajoute le code dans le MainActivity pour ouvrir cette activité lors du cclique sur le boutton de capture.
	
	private ImageView image;
	private Button boutonFinCapture;
	private Captureur captureur;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_run);
		this.image = (ImageView) findViewById(R.id.imageAffiche);
		this.boutonFinCapture = (Button) findViewById(R.id.BoutonPrisePhoto);
		
		this.captureur = new Captureur(this.image, this);				
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
				captureur.fermer();
				retour();
				
				
			}
		});
	}
	
	public void retour() {
		super.onBackPressed();
	}

}

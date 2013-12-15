package com.example.avds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testandroid.R;

public class ServerActivity extends Activity {
	TextView textView; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		textView = new TextView(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serveur);
		
		final Button servRetourButton = (Button) findViewById(R.id.btn_servRetour);
		servRetourButton.setOnClickListener(new OnClickListener() {
						
			@Override
			public void onClick(View v) {
				finish();
			  }
			});
		
		//Récupération de l'adresse ip et port du serveur
		final Button servEnregistrerButton = (Button) findViewById(R.id.btn_servEnregistrer);
		servEnregistrerButton.setOnClickListener(new OnClickListener() {
						
			@Override
			public void onClick(View v) {
				EditText  edt_AdrIP = (EditText) findViewById(R.id.edt_adrIP);
				String adrIP = edt_AdrIP.getText().toString();
				textView.setText(adrIP);
				setContentView(textView);
			  }
			});
		servEnregistrerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText  edt_portServeur = (EditText) findViewById(R.id.edt_portServeur);
				String portServeur = edt_portServeur.getText().toString();
			  }
			});
		
		
;
	}

}

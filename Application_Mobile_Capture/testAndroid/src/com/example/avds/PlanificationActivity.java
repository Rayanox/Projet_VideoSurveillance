package com.example.avds;

import com.example.testandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import android.widget.Toast;

public class PlanificationActivity extends Activity {
	private Spinner spnr_planifHeureDebut;
	private Spinner spnr_planifMinDebut;
	private Spinner spnr_planifHeureFin;
	private Spinner spnr_planifMinFin;
	private String[] listeHeureDeb = new String[24];
	private String[] listeMinDeb = new String[60];
	private String[] listeHeureFin = new String[60];
	private String[] listeMinFin = new String[60];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planification);
		
		spnr_planifHeureDebut = (Spinner)findViewById(R.id.spnr_planifHeureDebut);
		spnr_planifMinDebut = (Spinner)findViewById(R.id.spnr_planifMinDebut);
		spnr_planifHeureFin = (Spinner)findViewById(R.id.spnr_planifHeureFin);
		spnr_planifMinFin = (Spinner)findViewById(R.id.spnr_planifMinFin);
		
		for (int i=0;i<24;i++) {
			if (i<10) {
				listeHeureDeb[i] = "0" +String.valueOf(i);
				listeHeureFin[i] = "0" +String.valueOf(i);
			}
			else {
				listeHeureDeb[i] = String.valueOf(i);
				listeHeureFin[i] = String.valueOf(i);
			}
		}
		
		for (int i=0;i<60;i++) {
			if (i<10) {
				listeMinDeb[i] = "0" +String.valueOf(i);
				listeMinFin[i] = "0" +String.valueOf(i);
			}
			else {
				listeMinDeb[i] = String.valueOf(i);
				listeMinFin[i] = "0" +String.valueOf(i);
			}
		}
		
		spnr_planifHeureDebut.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeHeureDeb));
		spnr_planifMinDebut.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeMinDeb));
		spnr_planifHeureFin.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeHeureFin));
		spnr_planifMinFin.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeMinFin));
		
		final Button planifEnregistrerButton = (Button) findViewById(R.id.btn_planifEnregistrer);
		planifEnregistrerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String heureDebut = spnr_planifHeureDebut.getSelectedItem().toString();
				String minDebut = spnr_planifMinDebut.getSelectedItem().toString();
				String heureFin = spnr_planifHeureFin.getSelectedItem().toString();
				String minFin = spnr_planifMinFin.getSelectedItem().toString();
				
				Toast.makeText(getApplicationContext(), heureDebut, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), minDebut, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), heureFin, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), minFin, Toast.LENGTH_SHORT).show();
			}
		});
		
		final Button plannifRetourButton = (Button) findViewById(R.id.btn_plannifRetour);
		plannifRetourButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			  }
			});	 
	}
}
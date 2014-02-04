package com.example.avds.Vue.Configuration;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.example.avds.MainActivity;
import com.example.avds.Modele.Captureur;
import com.example.avds.Modele.ServiceDeCapture;
import com.example.avds.Modele.ServiceTest;
import com.example.testandroid.R;

import android.R.integer;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
				int heureDebut = Integer.parseInt(spnr_planifHeureDebut.getSelectedItem().toString());
				int minDebut = Integer.parseInt(spnr_planifMinDebut.getSelectedItem().toString());
				int heureFin = Integer.parseInt(spnr_planifHeureFin.getSelectedItem().toString());
				int minFin = Integer.parseInt(spnr_planifMinFin.getSelectedItem().toString());
				
				plannifier(heureDebut, minDebut, heureFin, minFin);
								
				
				onBackPressed();
			}
		});
		
		final Button plannifRetourButton = (Button) findViewById(R.id.btn_plannifRetour);
		plannifRetourButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			  }
			});	 
	}
	
	private void plannifier(int heureDebut, int minutesDebut, int heureFin, int minutesFin) {
		if(MainActivity.plannificationActive) {
			String messageToast = "Il y a deja une pannification active";
			Toast.makeText(getApplicationContext(), messageToast, Toast.LENGTH_LONG).show();
		} else {
			MainActivity.plannificationActive = true;
			  
			  AlarmManager alarme = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			  Calendar calendrier = new GregorianCalendar(), calendrier2 = new GregorianCalendar();
			  
			  int jour = calendrier.get(Calendar.DAY_OF_MONTH);
			  int mois = calendrier.get(Calendar.MONTH);
			  int annee = calendrier.get(Calendar.YEAR);
			  int heure = calendrier.get(Calendar.HOUR_OF_DAY);
			  int minutes = calendrier.get(Calendar.MINUTE);
			  int secondes = calendrier.get(Calendar.SECOND);
			  
			  		  
			  
			  
			  
			  
			  
			  
			  //Plannificationde l'alarme.
			  			  
			  Intent in = new Intent(PlanificationActivity.this, ServiceDeCapture.class);			  
			  PendingIntent pending = PendingIntent.getService(PlanificationActivity.this, 0, in, 0);
			  
			  
			  calendrier.set(annee, mois, jour, heureDebut, minutesDebut, 0);
			  
			  
			  if(calendrier.before(new GregorianCalendar())) {//comparaison par rapport à l'horaire actuel
				   calendrier.add(Calendar.DAY_OF_MONTH, 1);// on ajoute un jour car c'est prévu pour une heure du lendemain.
				  System.out.println("ajout d'un jour");
			  }
			  
			  if(MainActivity.chargerFichierConfig(null)) {
				  alarme.set(AlarmManager.RTC_WAKEUP, calendrier.getTimeInMillis(), pending);
				  
				  
				  //Ajout de l'alarme de fin
				  in = new Intent(PlanificationActivity.this, ServiceDeCapture.class);				  
				  pending = PendingIntent.getService(PlanificationActivity.this, 1, in, 0); // Bien mettre un requestCode différent pour ne pas que l'alarme pense que c'est la même alarme que la précédente
				  
				  
				  calendrier2.set(annee, mois, jour, heureFin, minutesFin, 0);
				  
				  while(calendrier2.before(calendrier)) {//comparaison par rapport à l'horaire actuel, on fait une boucle pour le cas ou le calendrier2 est inférieur à la date actuelle en plus d'etre inférieur à la date du calendrier 1
					   calendrier2.add(Calendar.DAY_OF_MONTH, 1);// on ajoute un jour car c'est prévu pour une heure du lendemain.
					  System.out.println("ajout d'un jour");
				  }
				  
				  alarme.set(AlarmManager.RTC_WAKEUP, calendrier2.getTimeInMillis(), pending);
				  
				  String messageToast = "Capture planifiée du "+this.getJour(calendrier.get(Calendar.DAY_OF_WEEK))+" "+calendrier.get(Calendar.DAY_OF_MONTH)+" à "+calendrier.get(Calendar.HOUR_OF_DAY)+"h"+calendrier.get(Calendar.MINUTE)+" jusqu'à "
						  +this.getJour(calendrier2.get(Calendar.DAY_OF_WEEK))+" "+calendrier2.get(Calendar.DAY_OF_MONTH)+" à "+calendrier2.get(Calendar.HOUR_OF_DAY)+"h"+calendrier2.get(Calendar.MINUTE);
					Toast.makeText(getApplicationContext(), messageToast, Toast.LENGTH_LONG).show();
				  
			  } else {
				  String messageToast = "Configurations mauvaises, veuillez configurer le serveur.";
				  Toast.makeText(getApplicationContext(), messageToast, Toast.LENGTH_LONG).show();
			  }
			  
			  
			  
			  
			  
			  
			  /*Intent in = new Intent(PlanificationActivity.this, ServiceDeCapturePlannifiee.class);
			  
			  PendingIntent pending = PendingIntent.getService(PlanificationActivity.this, 0, in, 0);
			  
			  calendrier.set(annee, mois, jour, heure, minutes, secondes+10);
			  alarme.set(AlarmManager.RTC_WAKEUP, calendrier.getTimeInMillis(), pending);
			  
			  
			  //Fin de plannif en utilisant le meme intent		  
			  calendrier.set(annee, mois, jour, heure, minutes+5, secondes);
			  alarme.set(AlarmManager.RTC_WAKEUP, calendrier.getTimeInMillis(), pending);
			  */
		}
		
	}

	private String getJour(int i) {
		// TODO Auto-generated method stub
		HashMap<Integer, String> mappingJours = new HashMap<Integer, String>();
		mappingJours.put(2, "Lundi");
		mappingJours.put(3, "Mardi");
		mappingJours.put(4, "Mercredi");
		mappingJours.put(5, "Jeudi");
		mappingJours.put(6, "Vendredi");
		mappingJours.put(7, "Samedi");
		mappingJours.put(1, "Dimanche");
		return mappingJours.get(i);
	}
	
	
}

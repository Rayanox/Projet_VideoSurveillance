package com.example.avds.Modele;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.avds.MainActivity;
import com.example.avds.Vue.Capture.CameraRunActivity;
import com.example.avds.Vue.Configuration.PlanificationActivity;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.AlarmClock;
import android.provider.CalendarContract.Calendars;
import android.text.format.Time;
import android.util.Log;


public class ServiceDeCapture extends IntentService {
	
	private Thread threadTest;
	
  public ServiceDeCapture() {
    // Il faut passer une chaîne de caractères au superconstructeur
		
    super("ServiceCapture");
  }

  @TargetApi(Build.VERSION_CODES.GINGERBREAD)
@Override
  protected void onHandleIntent(Intent intent) {
	  
	  System.out.println("PASSAGE SERVICE CAPTURE");
	  
	  if(!MainActivity.plannificationActive && intent.getFlags() ==1) return; // cas ou il y a eu un probleme à la première alarme
		  
		  if(!MainActivity.lectureActive){
			  try {
				  if(MainActivity.chargerFichierConfig(null)) {
					  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();	
					  StrictMode.setThreadPolicy(policy);
					  
					  if(MainActivity.modeSecret) {						  
						  Captureur captureur = new Captureur(null,this ,null); 
						  captureur.start();
					  } else {
						  Intent in = new Intent(this, CameraRunActivity.class);						  					  
						  in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						  getApplication().startActivity(in);
					  }
					  
					  //le cas où l'on affiche l'activité
					  System.out.println("Debut capture service plannif");
				  }
				  
			  }catch (Exception e) {
				  System.out.println("Problème lors du lancement de la plannif : "+e.getMessage());
				  MainActivity.plannificationActive = false;
				  MainActivity.lectureActive = false;
				  //MainActivity.modeSecret = false; //TODO A modifier
			  }
			  
			  
		  } else {
			  MainActivity.lectureActive = false;
			  //MainActivity.modeSecret = false; //TODO A modifier
			  System.out.println("Fin capture service plannif");
		  }
	  
	  
		
	  
	  
	  
    	
    	
  }
  
  protected void alarmeTest() {
	  System.out.println("Dans l'alarme !!!");
	  this.threadTest = new Thread() {
  		public void run() {
  			int compteurTest = 0;
  			while(compteurTest < 20){
  				System.out.println("Courage man n°"+compteurTest++);
  				try {
						this.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
  			}
  			MainActivity.plannificationActive = false;
  		}
  	};
  	this.threadTest.start();
  }
}
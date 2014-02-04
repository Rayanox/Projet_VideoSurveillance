package com.example.avds.Modele;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.avds.MainActivity;

public class ServiceTest extends IntentService{
	private Thread threadTest;
	
	  public ServiceTest() {
	    // Il faut passer une chaîne de caractères au superconstructeur
			
	    super("ServiceTest");
	    System.out.println("Dans le service test");
	  }

	  
	  
	  protected void alarmeTest() {
		  System.out.println("Dans l'alarme !!!");
		  this.threadTest = new Thread() {
	  		public void run() {
	  			int compteurTest = 0;
	  			while(compteurTest < 100){
	  				System.out.println("Courage man n°"+compteurTest++);
	  				try {
							this.sleep(1500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	  			}
	  			System.out.println("fin de la boucle de test");
	  			MainActivity.plannificationActive = false;
	  		}
	  	};
	  	this.threadTest.start();
	  }

	

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		this.alarmeTest();
	}
}

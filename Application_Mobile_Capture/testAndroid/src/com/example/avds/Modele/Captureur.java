package com.example.avds.Modele;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.example.avds.MainActivity;
import com.example.avds.Vue.Capture.CameraRunActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;

public class Captureur extends Thread{

	private Camera camera;
	private PictureCallback FonctionCallBackJPeg;
	private ImageView imageAffiche;
	private Context contexteActivityMere;
	private boolean close; // sert à terminer le while du run
	private int heightCapture;
	private int widthCapture;
	private boolean cameraDispo;
	private Envoyeur envoyeur;
	private CameraRunActivity activiteMere;
	
	public Captureur(ImageView image, Context context, CameraRunActivity activiteMere) {
		this.imageAffiche = image;
		this.contexteActivityMere = context;
		this.close = false;
		this.cameraDispo = true;
		this.activiteMere = activiteMere;
		
		
		this.camera = Camera.open();
		ArrayList<Size> listSizes = (ArrayList<Camera.Size>) this.camera.getParameters().getSupportedPictureSizes();
		for (Size mode : listSizes) {
			System.out.println("Affichage : height = "+mode.height+" width = "+mode.width);
		}
		this.widthCapture = listSizes.get(0).width;
		this.heightCapture = listSizes.get(0).height;
		this.camera.release();
		
		//on initialise les fonctions utilisées lors de la prise de la photo			
		this.initCallBack();
		
		//initialisation de l'envoyeur
		try {
			this.envoyeur = new Envoyeur(Inet4Address.getByName(MainActivity.IP), MainActivity.PORT);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.activiteMere.AfficherToast("Problème de configuration d'IP : Revoir les configurations");
			//activiteMere.onBackPressed();
		}
		
	}
	
	public void StopperCapture() {
		this.close = true;
		
	}
	
	public void run() {
		
		this.camera = Camera.open();
		if(this.camera == null) {
			this.activiteMere.AfficherToast("Vous n'avez pas de caméra...");			
		}else {
			try {
				//Connexion de l'envoyeur
				this.envoyeur.seConnecterAuServeur();
				
				//Récupération des parametres de la caméra.
				Camera.Parameters params = this.camera.getParameters();
				System.out.println(params);
				this.camera.getParameters().setFlashMode(camera.getParameters().FLASH_MODE_OFF);
							
				
				//On prend les deuxiemes dimensions possibles
				this.camera.getParameters().setPictureSize(this.widthCapture, this.heightCapture);
				
				//premiere prise (début de la boucle de prise de photos)
				this.nouvelleCapture();
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//this.activiteMere.onBackPressed(); // ne marche pas car lance une exception inrésolvable
				this.activiteMere.AfficherToast("Problème de connexion : Revoir les configurations");
				this.camera.release();
			}			
			
		}
		
	}
	
	//C'est ici que sont initialisés les fonctions utilisées par l'appareil photo : c'est le coeur du traitement de l'image prise en photo
	private void initCallBack() {
		
		this.FonctionCallBackJPeg = new PictureCallback() {
			
			@SuppressLint("NewApi")
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				
				//Tranformation de jpeg en bitMap
				Bitmap imageBitMap = BitmapFactory.decodeByteArray(data, 0, data.length);
				
				//Compression de BitMap en JPEG avec reglage de la qualité et donc du poids
				ByteArrayOutputStream imageOut = new ByteArrayOutputStream();
				//La qualité de l'image est raisonnable à partir de 10 bien qu'elle reste un petit peu floue, mais elle est tout de même acceptable pour une caméra de surveillance
				// l'image obtenue à 10 pèse environ 7500octets (7Mo), ce qui est tout à fait raisonnable par rapport à un début dans les 300 000 octets
				// De plus, il faut savoir que le buffer de la socket UDP (Datagram) ne peut pas prendre plus de 64ko 
				imageBitMap.compress(CompressFormat.JPEG, 10, imageOut);
								
				imageAffiche.setImageBitmap(BitmapFactory.decodeByteArray(imageOut.toByteArray(), 0, imageOut.size()));//TODO A retirer
				
				// On attend tant que l'envoyeur n'est pas dispo (peu probable mais peut arriver selon les performances matérielles)
				while(!envoyeur.getEnvoyeurDispo()); 
				//envoyeur.envoyerDonnees(data);
				envoyeur.envoyerDonnees(imageOut.toByteArray());
				
				camera.stopPreview();
				nouvelleCapture();
				
			}
		};
	}
	
	public void nouvelleCapture() {
		if(this.close) {
			this.close = false;
			this.camera.release();
			this.envoyeur.closeConnexion();
		}
		else {/*
			SurfaceHolder holder = new SurfaceView(this.contexteActivityMere).getHolder();
			try {
				this.camera.setPreviewDisplay(holder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			this.camera.takePicture(null, null, this.FonctionCallBackJPeg);
		}
	}	
}

package com.example.avds.Modele;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.widget.ImageView;
import android.widget.Toast;

public class Captureur extends Thread{

	private Camera camera;
	private PictureCallback FonctionCallBack;
	private ImageView imageAffiche;
	private Context contexteActivityMere;
	private boolean close; // sert à terminer le while du run
	
	public Captureur(ImageView image, Context context) {
		this.imageAffiche = image;
		this.contexteActivityMere = context;
		this.close = false;
		this.camera = Camera.open();
		if(this.camera == null) {
			Toast.makeText(this.contexteActivityMere, "Vous n'avez pas de caméra...", Toast.LENGTH_LONG).show();
		}else {
			Camera.Parameters params = this.camera.getParameters();
			System.out.println(params);
			this.camera.getParameters().setFlashMode(camera.getParameters().FLASH_MODE_OFF);
			int i = 0;
			Size [] listSizes = (Size []) this.camera.getParameters().getSupportedPictureSizes().toArray();
			for (Size mode : listSizes) {
				System.out.println("Affichage : height = "+mode.height+" width = "+mode.width);
			}
			
			//On prend les deuxiemes dimensions possibles
			this.camera.getParameters().setPictureSize(listSizes[1].width, listSizes[1].height);
			
			//on initialise les fonctions utilisées lors de la prise de la photo			
			this.initCallBack();
			
			//On prend les photos et les callbacks effectuent leurs travaux
			this.camera.takePicture(null, null, this.FonctionCallBack);
		}
		
		//this.camera.getParameters().setPictureSize(width, height);
	}
	
	public void run() {
		
	}
	
	private void initCallBack() {
		this.FonctionCallBack = new PictureCallback() {
			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				imageAffiche.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
			}
		};
	}
	
	public void fermer() {
		this.close = true;
		if(this.camera != null)	this.camera.release();
	}
	
}

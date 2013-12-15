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
	private PictureCallback FonctionCallBackRaw;
	private PictureCallback FonctionCallBackJPeg;
	private ImageView imageAffiche;
	private Context contexteActivityMere;
	private boolean close; // sert à terminer le while du run
	private int heightCapture;
	private int widthCapture;
	private boolean cameraDispo;
	
	public Captureur(ImageView image, Context context) {
		this.imageAffiche = image;
		this.contexteActivityMere = context;
		this.close = false;
		this.cameraDispo = true;
		
		this.camera = Camera.open();
		ArrayList<Size> listSizes = (ArrayList<Camera.Size>) this.camera.getParameters().getSupportedPictureSizes();
		for (Size mode : listSizes) {
			System.out.println("Affichage : height = "+mode.height+" width = "+mode.width);
		}
		this.widthCapture = listSizes.get(1).width;
		this.heightCapture = listSizes.get(1).height;
		this.camera.release();
		
		//on initialise les fonctions utilisées lors de la prise de la photo			
		this.initCallBack();
		
	}
	/*
	public void run() {
		
		this.camera = Camera.open();
		if(this.camera == null) {
			Toast.makeText(this.contexteActivityMere, "Vous n'avez pas de caméra...", Toast.LENGTH_LONG).show();
		}else {
			Camera.Parameters params = this.camera.getParameters();
			System.out.println(params);
			this.camera.getParameters().setFlashMode(camera.getParameters().FLASH_MODE_OFF);
						
			
			//On prend les deuxiemes dimensions possibles
			this.camera.getParameters().setPictureSize(this.widthCapture, this.heightCapture);
			
			
			
			this.cameraDispo = false;
			
			//On prend les photos et les callbacks effectuent leurs travaux
			while(!this.close) {
				if(this.cameraDispo) this.camera.takePicture(null, this.FonctionCallBackRaw, this.FonctionCallBackJPeg);
				
				try {
					this.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.camera.release();
			this.close = false;
			
		}
		
		
	}
	
	private void initCallBack() {
		this.FonctionCallBackRaw = new PictureCallback() {
			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				imageAffiche.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
			}
		};
		
		this.FonctionCallBackJPeg = new PictureCallback() {
			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				cameraDispo = true;
			}
		};
	}*/
	
	public void StopperCapture() {
		this.close = true;
	}
	
public void run() {
		
		this.camera = Camera.open();
		if(this.camera == null) {
			Toast.makeText(this.contexteActivityMere, "Vous n'avez pas de caméra...", Toast.LENGTH_LONG).show();
		}else {
			Camera.Parameters params = this.camera.getParameters();
			System.out.println(params);
			this.camera.getParameters().setFlashMode(camera.getParameters().FLASH_MODE_OFF);
						
			
			//On prend les deuxiemes dimensions possibles
			this.camera.getParameters().setPictureSize(this.widthCapture, this.heightCapture);
			
			//premiere prise
			this.camera.takePicture(null, this.FonctionCallBackRaw, this.FonctionCallBackJPeg);
			
			
			
		}
		
		
	}
	
	private void initCallBack() {
		this.FonctionCallBackRaw = new PictureCallback() {
			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				
			}
		};
		
		this.FonctionCallBackJPeg = new PictureCallback() {
			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				imageAffiche.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
				nouvelleCapture();
			}
		};
	}
	
	public void nouvelleCapture() {
		if(this.close) {
			this.close = false;
			this.camera.release();
		}
		else {
			this.camera.takePicture(null, this.FonctionCallBackRaw, this.FonctionCallBackJPeg);
		}
	}
	
}

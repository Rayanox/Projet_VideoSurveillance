package com.example.avds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CameraActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();

		if (intent != null) {
			System.out.println("Je suis dans le contexte CAM !");
			// Initialisation appareil photo
		}
	}
}

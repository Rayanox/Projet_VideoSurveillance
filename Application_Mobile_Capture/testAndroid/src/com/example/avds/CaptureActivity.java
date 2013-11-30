package com.example.avds;

import com.example.testandroid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CaptureActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        
        final Button captureGeneral = (Button) findViewById(R.id.btn_captureGeneral);
        captureGeneral.setOnClickListener(new OnClickListener() {
						
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(CaptureActivity.this, GeneralActivity.class);
			startActivity(intent);
			}
		});	
        
        final Button captureMode = (Button) findViewById(R.id.btn_captureMode);
        captureMode.setOnClickListener(new OnClickListener() {
						
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(CaptureActivity.this, ModeActivity.class);
			startActivity(intent);
			}
		});
        
        final Button capturePlanif = (Button) findViewById(R.id.btn_capturePlanif);
        capturePlanif.setOnClickListener(new OnClickListener() {
						
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(CaptureActivity.this, PlanificationActivity.class);
			startActivity(intent);
			}
		});
        
        final Button captureRetourButton = (Button) findViewById(R.id.btn_captureRetour);
        captureRetourButton.setOnClickListener(new OnClickListener() {
						
			@Override
			public void onClick(View v) {
				finish();
			  }
			});	 
	}
}

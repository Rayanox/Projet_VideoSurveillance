package com.example.avds.Vue.Configuration;

import com.example.testandroid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ConfigurationActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        
        final Button serverButton = (Button) findViewById(R.id.btn_configServeur);
        serverButton.setOnClickListener(new OnClickListener() {
					
		  @Override
		  public void onClick(View v) {
			Intent intent = new Intent(ConfigurationActivity.this, ServerActivity.class);
			startActivity(intent);
		  }
		});
        
		final Button configCaptureButton = (Button) findViewById(R.id.btn_configCapture);
		configCaptureButton.setOnClickListener(new OnClickListener() {
						
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ConfigurationActivity.this, CaptureActivity.class);
				startActivity(intent);
			  }
			});
		
		final Button configRetourButton = (Button) findViewById(R.id.btn_configRetour);
		configRetourButton.setOnClickListener(new OnClickListener() {
						
			@Override
			public void onClick(View v) {
				finish();
			  }
			});	 
		  
    } 
}

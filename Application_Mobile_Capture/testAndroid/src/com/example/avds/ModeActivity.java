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

public class ModeActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		final ListView lstviewTypeCap;
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        
        lstviewTypeCap = (ListView)findViewById(R.id.lstview_modeTypeMode);
		String[] listeStrings = {"Discret","Visible"};
		lstviewTypeCap.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeStrings));
    
		
		
		final Button modeEnregistrerButton = (Button) findViewById(R.id.btn_modeEnregistrer);
		modeEnregistrerButton.setOnClickListener(new OnClickListener() {
			
		//Récupération du type de mode sélectionné	
		@Override
		public void onClick(View v) {
			lstviewTypeCap.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> lstviewTypeCap, View v, int position,
						long id) {
					Object obj_typeMode = lstviewTypeCap.getItemAtPosition(position);
					String typeMode = obj_typeMode.toString();
				}
			});
		  }
		});
		
		
		final Button modeRetourButton = (Button) findViewById(R.id.btn_modeRetour);
		modeRetourButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			  }
			});	 
	}
}

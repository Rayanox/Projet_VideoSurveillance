package com.example.avds.Vue.Configuration;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testandroid.R;

public class GeneralActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		final ListView lstviewTypeCap;
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        
        //Construction de la liste des types de capture
        lstviewTypeCap = (ListView)findViewById(R.id.lstview_generalTypCap);
		String[] listeStrings = {"Photo","Vidéo"};
		lstviewTypeCap.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeStrings));
		
		final Button generalEnregistrerButton = (Button) findViewById(R.id.btn_generalEnregistrer);
		generalEnregistrerButton.setOnClickListener(new OnClickListener() {
			
		//Récupération du type de capture sélectionné	
		@Override
		public void onClick(View v) {
			lstviewTypeCap.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> lstviewTypeCap, View v, int position,
						long id) {
					Object obj_typeCapture = lstviewTypeCap.getItemAtPosition(position);
					String typeCapture = obj_typeCapture.toString();
				}
			});
		  }
		});
		
	
	final Button generalRetourButton = (Button) findViewById(R.id.btn_generalRetour);
	generalRetourButton.setOnClickListener(new OnClickListener() {
					
		@Override
		public void onClick(View v) {
			finish();
		  }
		});	
	}
}

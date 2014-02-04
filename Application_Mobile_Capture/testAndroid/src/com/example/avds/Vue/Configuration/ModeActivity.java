package com.example.avds.Vue.Configuration;

import java.util.HashMap;

import com.example.avds.MainActivity;
import com.example.testandroid.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ModeActivity extends Activity {
	
	private Context context = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		final ListView lstviewTypeCap;
		
		
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        
        lstviewTypeCap = (ListView)findViewById(R.id.lstview_modeTypeMode);
        
        final HashMap<Integer, String> mappingTextesBouttons = new HashMap<Integer, String>();
        mappingTextesBouttons.put(0, "Discret");
        mappingTextesBouttons.put(1, "Visible");
        
		String[] listeStrings = {mappingTextesBouttons.get(0),mappingTextesBouttons.get(1)};
		lstviewTypeCap.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeStrings));
		lstviewTypeCap.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//System.out.println("Click = "+arg0.getItemIdAtPosition(position));
				System.out.println(mappingTextesBouttons.get(arg2));
				if(mappingTextesBouttons.get(arg2).toLowerCase().trim().equals("discret")) {
					if(!MainActivity.modeSecret) {
						MainActivity.modeSecret = true;
						AfficherToast(context, "Mode discret activé");
					} else AfficherToast(context, "Le mode discret est déjà activé");
					
				}else if(mappingTextesBouttons.get(arg2).toLowerCase().trim().equals("visible")) {
					if(MainActivity.modeSecret) {
						MainActivity.modeSecret = false;
						AfficherToast(context, "Mode visible activé");
					} else AfficherToast(context, "Le mode visible est déjà activé");
					
					
				} else AfficherToast(context, "Bouton non reconnu");
				
				onBackPressed();
			}
		});
		
		
		
		
		
		final Button modeRetourButton = (Button) findViewById(R.id.btn_modeRetour);
		modeRetourButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			  }
			});	 
	}
	
	public static void AfficherToast(Context context, final String texte) {
		Toast.makeText(context, texte, Toast.LENGTH_LONG).show();		
	}
}

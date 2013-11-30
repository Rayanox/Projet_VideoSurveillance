package com.example.avds;

import com.example.testandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ModeActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		ListView lstviewTypeCap;
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        
        lstviewTypeCap = (ListView)findViewById(R.id.lstview_modeTypeMode);
		String[] listeStrings = {"Discret","Visible"};
		lstviewTypeCap.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeStrings));
    
		final Button modeRetourButton = (Button) findViewById(R.id.btn_modeRetour);
		modeRetourButton.setOnClickListener(new OnClickListener() {
						
			@Override
			public void onClick(View v) {
				finish();
			  }
			});	 
	}
}

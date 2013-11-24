package com.example.testandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GeneralActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		ListView lstviewTypeCap;
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        
        lstviewTypeCap = (ListView)findViewById(R.id.lstview_generalTypCap);
		String[] listeStrings = {"Photo","Vidéo"};
		lstviewTypeCap.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeStrings));
    } 
}

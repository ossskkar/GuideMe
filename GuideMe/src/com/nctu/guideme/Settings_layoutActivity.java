package com.nctu.guideme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Settings_layoutActivity extends Activity {

	/* Declare views in current layout */
	EditText contactName_editText;
	EditText contactPhone_editText;
	EditText contactEmail_editText;
	Button ok_button;
	Button cancel_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		/* Find id of views */
		contactName_editText = (EditText) findViewById(R.id.contactName_editText);
		contactPhone_editText = (EditText) findViewById(R.id.contactPhone_editText);
		contactEmail_editText = (EditText) findViewById(R.id.contactEmail_editText);
		ok_button         = (Button)findViewById(R.id.ok_button);
		cancel_button         = (Button)findViewById(R.id.cancel_button);
		
		/* Save settings and executes the initial layout */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				/* Save data to preferences */
				//PENDING 
				
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});
		
		/* Discard settings and return to initial layout */
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});
	}
	
	
}


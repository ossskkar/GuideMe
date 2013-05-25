package com.nctu.guideme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Settings_layoutActivity extends Activity {

	/* Declare views in current layout */
	EditText contactName_editText;
	EditText contactPhone_editText;
	EditText contactEmail_editText;
	Button ok_button;
	Button cancel_button;
	SharedPreferences settings;
	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergency_contact);
		
		/* Find id of views */
		contactName_editText = (EditText) findViewById(R.id.contactName_editText);
		contactPhone_editText = (EditText) findViewById(R.id.contactPhone_editText);
		contactEmail_editText = (EditText) findViewById(R.id.contactEmail_editText);
		ok_button         = (Button)findViewById(R.id.ok_button);
		cancel_button         = (Button)findViewById(R.id.cancel_button);
		
		/* Obtain preferences */
		settings = getSharedPreferences("SettingsFile",0);
		
		/* Load preferences if they exits */
		String preferencesString = settings.getString("contactName", null);
		if (preferencesString != null) 
			contactName_editText.setText(preferencesString);
		
		preferencesString = settings.getString("contactPhone", null);
		if (preferencesString != null) 
			contactPhone_editText.setText(preferencesString);
		
		preferencesString = settings.getString("contactEmail", null);
		if (preferencesString != null) 
			contactEmail_editText.setText(preferencesString);
		
		/* Save settings and executes the initial layout */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				/* Save data to preferences */
				SharedPreferences.Editor preferencesEditor = settings.edit();
				preferencesEditor.putString("contactName", contactName_editText.getText().toString());
				preferencesEditor.putString("contactPhone", contactPhone_editText.getText().toString());
				preferencesEditor.putString("contactEmail", contactEmail_editText.getText().toString());
				preferencesEditor.commit();
				
				/* Return to initial layout */
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		ok_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.save);
				mp.start();
				return true;
			}
		});
		
		/* Discard settings and return to initial layout */
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		cancel_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.cancel);
				mp.start();
				return true;
			}
		});
	}
	
	
}


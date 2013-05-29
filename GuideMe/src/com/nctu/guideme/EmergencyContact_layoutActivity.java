package com.nctu.guideme;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EmergencyContact_layoutActivity extends BaseActivity {

	/* Declare views in current layout */
	EditText contactName_editText;
	EditText contactPhone_editText;
	EditText contactEmail_editText;
	Button ok_button;
	Button cancel_button;
	
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
		
		/* Create vibrator for haptic feedback */
		vibrator=(Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		
		/* Initial message */
		audioInterface=new AudioInterface(getApplicationContext(),"emergency_contact_information");
		
		/* PreferencesManager class*/
		preferences = new PreferenceManager(this, "SettingsFile");
		
		/* Load preferences */
		contactName_editText.setText(preferences.GetPreference("contactName", null));
		contactPhone_editText.setText(preferences.GetPreference("contactPhone", null));
		contactEmail_editText.setText(preferences.GetPreference("contactEmail", null));
		
		/* Save settings and executes the initial layout */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				/* Save data to preferences */
				preferences.SetPreference("contactName", contactName_editText.getText().toString());
				preferences.SetPreference("contactPhone", contactPhone_editText.getText().toString());
				preferences.SetPreference("contactEmail", contactEmail_editText.getText().toString());
				
				/* Return to initial layout */
				startActivity(new Intent(getApplicationContext(), Settings_layoutActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		ok_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				audioInterface=new AudioInterface(getApplicationContext(),"save");
				return true;
			}
		});
		
		/* Discard settings and return to initial layout */
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				startActivity(new Intent(getApplicationContext(), Settings_layoutActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		cancel_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				audioInterface=new AudioInterface(getApplicationContext(),"cancel");
				return true;
			}
		});
	}
}


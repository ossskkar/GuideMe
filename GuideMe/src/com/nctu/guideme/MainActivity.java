package com.nctu.guideme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity {

	/* Declare views in current layout */
	Button recordAPath_button;
	Button getDirections_button;
	Button settings_button;
	Button exit_button;
	Button panic_button;
	MediaPlayer mp;
	SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//setTheme(android.R.style.Theme_Black);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initial_screen);
		
		/* Find id of views */
		recordAPath_button   = (Button)findViewById(R.id.recordAPath_button);
		getDirections_button = (Button)findViewById(R.id.getDirections_button);
		settings_button      = (Button)findViewById(R.id.settings_button);
		exit_button          = (Button)findViewById(R.id.exit_button);
		panic_button         = (Button)findViewById(R.id.panic_button);
		
		/* Obtain preferences */
		settings = getSharedPreferences("SettingsFile",0);
		
		/* Load preferences for stepValue if it exits */
		GlobalVariables.fStepValue = settings.getFloat("stepValue", GlobalVariables.fDefaultStepValue);
		
		/* Welcome message */
		mp = MediaPlayer.create(this, R.raw.welcome_message);
		//mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }
		});
		
		/* Execute create a path layout */
		recordAPath_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), CreateAPath_layoutActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		recordAPath_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.record_a_path);
				mp.start();
				return true;
			}
		});
		
		/* Execute get directions layout */
		getDirections_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), SelectAPath_layoutActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		getDirections_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.get_directions);
				mp.start();
				return true;
			}
		});
		
		/* Execute settings layout */
		settings_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), Settings_layoutActivity.class));
				finish();				
			}
		});
		
		/* Play the sound help */
		settings_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.settings);
				mp.start();
				return true;
			}
		});
		
		/* Exit */
		exit_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		/* Play the sound help */
		exit_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.exit_application);
				mp.start();
				return true;
			}
		});
		
		/* Execute panic button function */
		panic_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.panic_button);
				mp.start();
				
				//NOT DEFINED YET
			}
		});
		
		/* Play the sound help */
		panic_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.panic_message3);
				mp.start();
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

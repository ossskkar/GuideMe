package com.nctu.guideme;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class Settings_layoutActivity extends BaseActivity {

	/* Declare views in current layout */
	Button emergencyContact_button;
	Button calibration_button;
	Button cancel_button;
	Button panic_button;
	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		/* Find id of views */
		emergencyContact_button=(Button)findViewById(R.id.emergencyContact_button);
		calibration_button=(Button)findViewById(R.id.calibration_button);
		cancel_button=(Button)findViewById(R.id.cancel_button);
		panic_button=(Button)findViewById(R.id.panic_button);

		/* Initial message */
		mp = MediaPlayer.create(this, R.raw.finish_save_path); // CORRECT THE RIGHT MP3 LATER 
		//mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }
		});
		
		emergencyContact_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				/* Return to initial layout */
				startActivity(new Intent(getApplicationContext(), EmergencyContact_layoutActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		emergencyContact_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.emergency_contact_information);
				mp.start();
				return true;
			}
		});
		
		/* Discard settings and return to initial layout */
		calibration_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), Calibration_layoutActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		calibration_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.calibration);
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
}

package com.nctu.guideme;

import android.content.Intent;
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
	//MediaPlayer getDirections_mp;
	//MediaPlayer settings_mp;
	//MediaPlayer exit_mp;
	//MediaPlayer panic_mp;
	//MediaPlayer panicMessage_mp;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initial_screen);
		
		/* Find id of views */
		recordAPath_button   = (Button)findViewById(R.id.recordAPath_button);
		getDirections_button = (Button)findViewById(R.id.getDirections_button);
		settings_button      = (Button)findViewById(R.id.settings_button);
		exit_button          = (Button)findViewById(R.id.exit_button);
		panic_button         = (Button)findViewById(R.id.panic_button);
		
		/* Welcome message */
		mp = MediaPlayer.create(this, R.raw.welcome_message);
		mp.start();
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
		mp = MediaPlayer.create(this, R.raw.record_a_path);
		recordAPath_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
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
		//getDirections_mp = MediaPlayer.create(this, R.raw.get_directions);
		mp = MediaPlayer.create(this, R.raw.get_directions);
		getDirections_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				//getDirections_mp.start();
				mp.start();
				return true;
			}
		});
		
		/* Execute settings layout */
		settings_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), Settings_layoutActivity.class));
			}
		});
		
		/* Play the sound help */
		//settings_mp = MediaPlayer.create(this, R.raw.settings);
		mp = MediaPlayer.create(this, R.raw.settings);
		settings_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				//settings_mp.start();
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
		//exit_mp = MediaPlayer.create(this, R.raw.exit_application);
		mp = MediaPlayer.create(this, R.raw.exit_application);
		exit_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				//exit_mp.start();
				mp.start();
				return true;
			}
		});
		
		/* Execute panic button function */
		//panicMessage_mp = MediaPlayer.create(this, R.raw.panic_button);
		panic_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//panicMessage_mp.start();
				
				//NOT DEFINED YET
			}
		});
		
		/* Play the sound help */
		//mp = MediaPlayer.create(this, R.raw.panic_message3);
		panic_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				//panicMessage_mp.start();
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

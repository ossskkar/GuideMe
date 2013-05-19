package com.nctu.guideme;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FinishRecordAPath_layoutActivity extends Activity {
	/* Declare views in current layout */
	TextView status_textView;
	Button   ok_button;
	Button   cancel_button;
	Button   panic_button;
	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_record_a_path);
		
		/* Find id of views */
		status_textView = (TextView) findViewById(R.id.status_textView);
		ok_button       = (Button)   findViewById(R.id.ok_button);
		cancel_button   = (Button)   findViewById(R.id.cancel_button);
		panic_button    = (Button)   findViewById(R.id.panic_button);
		
		/* Initial message */
		mp = MediaPlayer.create(this, R.raw.finish_save_path);
		mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }
		});
		
		/* Confirm recording of a path and return to initial layout */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* finish recording of the path */
				//PENDING 
				
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
				mp = MediaPlayer.create(getApplicationContext(), R.raw.yes);
				mp.start();
				return true;
			}
		});
		
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Cancel the recording of the path, delete data */
				//PENDING 
				
				/* Return to initial layout */
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
				mp = MediaPlayer.create(getApplicationContext(), R.raw.no);
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

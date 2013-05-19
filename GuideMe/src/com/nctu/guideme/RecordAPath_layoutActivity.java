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

public class RecordAPath_layoutActivity extends Activity {

	/* Declare views in current layout */
	TextView status_textView;
	Button ok_button;
	Button finish_button;
	Button panic_button;
	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_a_path);
		
		/* Find id of views */
		status_textView = (TextView) findViewById(R.id.status_textView);
		ok_button       = (Button)   findViewById(R.id.ok_button);
		finish_button   = (Button)   findViewById(R.id.cancel_button);
		panic_button    = (Button)   findViewById(R.id.panic_button);
		
		/* Initial message */
		mp = MediaPlayer.create(this, R.raw.prest_start_to_record_the_path);
		mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }
		});
		
		/* Start/pause the recording of a path */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			
				/*Change the label of the button accordingly */
				if (ok_button.getText().toString().equals("Start")) {
					ok_button.setText("Pause");
				}
				else {
					ok_button.setText("Start");
				}
				
				//PENDING 
			}
		});
		
		/* Play the sound help */
		ok_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				if (ok_button.getText().toString().equals("Start"))
					mp = MediaPlayer.create(getApplicationContext(), R.raw.start);
				else 
					mp = MediaPlayer.create(getApplicationContext(), R.raw.pause);
				mp.start();
				return true;
			}
		});
		
		/* Finish the recording of a path  */
		finish_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				//PENDING
				
				/* Return to initial layout */
				startActivity(new Intent(getApplicationContext(), FinishRecordAPath_layoutActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		finish_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.finish);
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

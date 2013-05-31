package com.nctu.guideme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateAPath_layoutActivity extends BaseActivity {

	/* Declare buttons in current layout */
	TextView pathName_textView;
	EditText pathName_editText;
	Button   record_button;
	Button   play_button;
	Button   ok_button;
	Button   cancel_button;
	Button   panic_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_a_path);

		/* Find id of views */
		pathName_textView = (TextView)findViewById(R.id.pathName_textView);
		pathName_editText = (EditText)findViewById(R.id.pathName_editText);
		record_button     = (Button)findViewById(R.id.record_button);
		play_button       = (Button)findViewById(R.id.play_button);
		ok_button         = (Button)findViewById(R.id.ok_button);
		cancel_button     = (Button)findViewById(R.id.cancel_button);
		panic_button      = (Button)findViewById(R.id.panic_button);

		/* Create vibrator for haptic feedback */
		vibrator=(Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		
		/* Initial message */
		audioInterface=new AudioInterface(this,"record_name_for_new_path");
		
		/* Object for recording audio */
		recorder=new RecordAudio(this);
		
		/* Object for playing audio */
		playAudio=new PlayAudio();
		
		/* record button, records a name for the new path*/
		record_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				/* Start recording */
				if (record_button.getText().equals("Record")) {
					record_button.setText("Stop");
					recorder.StartRecording();
				}
				/* Stop recording */
				else {
					record_button.setText("Record");
					recorder.StopRecording();
				}
			}
		});
		
		/* Play the sound help */
		record_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (record_button.getText().equals("Record"))
					audioInterface=new AudioInterface(getApplicationContext(),"record");
				else 
					audioInterface=new AudioInterface(getApplicationContext(),"stop");
				return true;
			}
		});
		
		play_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				/* Start playing */
				if (play_button.getText().equals("Play")) {
					/* Change label */
					play_button.setText("Stop");
					
					playAudio.StartPlaying(recorder.GetFileName());
				}
				/* Stop playing */
				else {
					/* Change label */
					play_button.setText("Play");

					playAudio.StopPlaying();
				}
			}
		});
		
		/* Play the sound help */
		play_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (play_button.getText().equals("Play"))
					audioInterface=new AudioInterface(getApplicationContext(),"play");
				else 
					audioInterface=new AudioInterface(getApplicationContext(),"stop");
				return true;
			}
		});
		
		/* Verify path name and execute next layout */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				/*Verify parameters */
				if (!recorder.GetFileStatus()) 
					audioInterface=new AudioInterface(getApplicationContext(),"record_name_for_new_path");
				else {
					currentFileName=recorder.GetFileName();
					//currentFileName="fromhere.3ggp";
					/* Execute next layout and exit current layout */
					startActivity(new Intent(getApplicationContext(), RecordAPath_layoutActivity.class));
					finish();
				}
			}
		});
		
		/* Play the sound help */
		ok_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				audioInterface=new AudioInterface(getApplicationContext(),"accept");
				return true;
			}
		});
		
		/* Exit */
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
		
		/* Execute panic button function */
		panic_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Haptic feedback */
				vibrator.vibrate(50);
				audioInterface=new AudioInterface(getApplicationContext(),"panic_button");
				//NOT DEFINED YET
			}
		});
		
		/* Play the sound help */
		panic_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				audioInterface=new AudioInterface(getApplicationContext(),"panic_message3");
				return true;
			}
		});
	}
}

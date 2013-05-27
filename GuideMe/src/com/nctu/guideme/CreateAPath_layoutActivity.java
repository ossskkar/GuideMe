package com.nctu.guideme;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
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
	MediaPlayer mp;
	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private String OUTPUT_FILE;
	
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
		
		/* Welcome message */
		mp = MediaPlayer.create(this, R.raw.enter_a_name_for_the_new_path);
		mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }
		});

		/* set path and name for output file */
		OUTPUT_FILE=Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.3gpp";
		
		/* record button, records a name for the new path*/
		record_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				/* Start recording */
				if (record_button.getText().equals("Record")) {
					
					/* Change label */
					record_button.setText("Stop");
					
					/* stop recording if any */
					if (recorder != null) {
						try {
							recorder.release();
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
					
					/* Output file variable */
					File pathNameFile = new File(OUTPUT_FILE);
					
					/* Check if file already exist*/
					if (pathNameFile.exists())
						pathNameFile.delete();
					
					/* Catch exceptions */
					try {
						/* Configure recorder */
						recorder = new MediaRecorder();
						recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
						recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
						recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
						recorder.setOutputFile(OUTPUT_FILE);
						recorder.prepare();
						recorder.start();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				/* Stop recording */
				else {
					
					/* Change label */
					record_button.setText("Record");
					
					/* Stop recording */
					if (recorder != null) {
						recorder.stop();
						recorder.release();
						recorder=null;
					}
				}
			}
		});
		
		play_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				/* Start playing */
				if (play_button.getText().equals("Play")) {
					
					/* Change label */
					play_button.setText("Stop");
					
					/* Stop playing if any */
					try {
					if (mediaPlayer!=null) 
						mediaPlayer.release();
					}catch(Exception e) {
						e.printStackTrace();
					}
					
					/* Prepare to play */
					try {
						mediaPlayer = new MediaPlayer();
						mediaPlayer.setDataSource(OUTPUT_FILE);
						mediaPlayer.prepare();
						mediaPlayer.start();
						
						/* When audio finish playing */
						mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				            public void onCompletion(MediaPlayer mp) {
				            	mediaPlayer.release();
				            	
				            	/* Change label */
								play_button.setText("Play");
				            }
						});
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				/* Stop playing */
				else {
					
					/* Change label */
					play_button.setText("Play");
					
					/* Stop playing */
					if (mediaPlayer!=null)
						mediaPlayer.stop();
				}
			}
		});
		
		/* Verify path name and execute next layout */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*Verify parameters */
				GlobalVariables.cCurrentPath = pathName_editText.getText().toString();
				//PENDING
				
				/* Execute next layout and exit current layout */
				startActivity(new Intent(getApplicationContext(), RecordAPath_layoutActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		ok_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				mp.reset();
				mp = MediaPlayer.create(getApplicationContext(), R.raw.accept);
				mp.start();
				return true;
			}
		});
		
		/* Exit */
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

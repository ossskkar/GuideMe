package com.nctu.guideme;

import java.io.IOException;

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

public class SelectAPath_layoutActivity extends BaseActivity {
	
	/* Declare views in current layout */
	TextView status_textView;
	Button ok_button;
	Button previous_button;
	Button next_button;
	Button cancel_button;
	Button panic_button;
	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_a_path);
		
		/* Find id of views */
		status_textView       = (TextView) findViewById(R.id.status_textView);
		previous_button       = (Button)   findViewById(R.id.previous_button);
		next_button           = (Button)   findViewById(R.id.next_button);
		ok_button             = (Button)   findViewById(R.id.ok_button);
		cancel_button         = (Button)   findViewById(R.id.cancel_button);
		panic_button          = (Button)   findViewById(R.id.panic_button);
		
		/* Initial message */
		AI=new AudioInterface(this,"select_path");
		
		/* Select previous/first path */
		previous_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//PENDING 
			}
		});
		
		/* Play the sound help */
		previous_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				AI=new AudioInterface(getApplicationContext(),"previous");
				return true;
			}
		});
		
		/* Select next/last path */
		next_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//PENDING 
			}
		});
		
		/* Play the sound help */
		next_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				AI=new AudioInterface(getApplicationContext(),"next");
				return true;
			}
		});
		
		/* Confirm the path selected and executes the next layout */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), GetDirections_layoutActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		ok_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				AI=new AudioInterface(getApplicationContext(),"select");
				return true;
			}
		});
		
		/* Cancel the path selection and return to initial layout */
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		cancel_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				AI=new AudioInterface(getApplicationContext(),"cancel");
				return true;
			}
		});
		
		/* Execute panic button function */
		panic_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AI=new AudioInterface(getApplicationContext(),"panic_button");
				
				//NOT DEFINED YET
			}
		});
		
		/* Play the sound help */
		panic_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				AI=new AudioInterface(getApplicationContext(),"panic_message3");
				return true;
			}
		});
	}
}

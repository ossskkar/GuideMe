package com.nctu.guideme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SelectAPath_layoutActivity extends Activity {
	
	/* Declare views in current layout */
	TextView status_textView;
	Button ok_button;
	Button previous_button;
	Button next_button;
	Button cancel_button;
	Button panic_button;
	
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
		
		/* Select previous/first path */
		previous_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//PENDING 
			}
		});
		
		/* Select next/last path */
		next_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//PENDING 
			}
		});
		
		/* Confirm the path selected and executes the next layout */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), GetDirections_layoutActivity.class));
				finish();
			}
		});
		
		/* Cancel the path selection and return to initial layout */
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});
		
		/* Execute panic button function */
		panic_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//NOT DEFINED YET
			}
		});
	}
}

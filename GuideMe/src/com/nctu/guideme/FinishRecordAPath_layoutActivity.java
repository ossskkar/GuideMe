package com.nctu.guideme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FinishRecordAPath_layoutActivity extends Activity {
	/* Declare views in current layout */
	TextView status_textView;
	Button   ok_button;
	Button   cancel_button;
	Button   panic_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_record_a_path);
		
		/* Find id of views */
		status_textView = (TextView) findViewById(R.id.status_textView);
		ok_button       = (Button)   findViewById(R.id.ok_button);
		cancel_button   = (Button)   findViewById(R.id.cancel_button);
		panic_button    = (Button)   findViewById(R.id.panic_button);
		
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
		
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Cancel the recording of the path, delete data */
				//PENDING 
				
				/* Return to initial layout */
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

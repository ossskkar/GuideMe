package com.nctu.guideme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateAPath_layoutActivity extends Activity {

	/* Declare buttons in current layout */
	TextView pathName_textView;
	EditText pathName_editText;
	Button   ok_button;
	Button   cancel_button;
	Button   panic_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_a_path);
		
		/* Find id of views */
		pathName_textView = (TextView)findViewById(R.id.pathName_textView );
		pathName_editText = (EditText)findViewById(R.id.pathName_editText );
		ok_button         = (Button)  findViewById(R.id.ok_button         );
		cancel_button     = (Button)  findViewById(R.id.cancel_button     );
		panic_button      = (Button)  findViewById(R.id.panic_button);
		
		/* Verify path name and execute next layout */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*Verify parameters */
				//PENDING
				
				/* Execute next layout and exit current layout */
				startActivity(new Intent(getApplicationContext(), RecordAPath_layoutActivity.class));
				finish();
			}
		});
		
		/* Exit */
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});
		
		/* Execute panic function */
		panic_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//PENDING 
			}
		});
	}
	
	
}

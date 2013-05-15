package com.nctu.guideme;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity {

	/* Declare views in current layout */
	Button recordAPath_button;
	Button getDirections_button;
	Button settings_button;
	Button exit_button;
	Button panic_button;
	
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
		
		/* Execute create a path layout */
		recordAPath_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), CreateAPath_layoutActivity.class));
				finish();
			}
		});
		
		/* Execute get directions layout */
		getDirections_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), SelectAPath_layoutActivity.class));
				finish();
			}
		});
		
		/* Execute settings layout */
		settings_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), Settings_layoutActivity.class));
			}
		});
		
		/* Exit */
		exit_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

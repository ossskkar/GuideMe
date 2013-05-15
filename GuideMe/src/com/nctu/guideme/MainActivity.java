package com.nctu.guideme;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity {

	Button recordAPath_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initial_screen);
		
		/* Create buttons */
		//Button recordAPath_button   = (Button)findViewById(R.id.recordAPath_button);
		recordAPath_button   = (Button)findViewById(R.id.recordAPath_button);
		Button getDirections_button = (Button)findViewById(R.id.getDirections_button);
		Button settings_button      = (Button)findViewById(R.id.settings_button);
		Button exit_button          = (Button)findViewById(R.id.exit_button);
		Button panic_button         = (Button)findViewById(R.id.panicButton_button);
		
		
		/* Implement listeners */
		recordAPath_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), RecordAPath_layoutActivity.class));
				recordAPath_button.setText("Clicked");
			}
		});
		
		exit_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
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

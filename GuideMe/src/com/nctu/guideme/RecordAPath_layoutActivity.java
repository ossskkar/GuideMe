package com.nctu.guideme;

import java.io.IOException; 
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RecordAPath_layoutActivity extends BaseActivity {

	/* Declare views in current layout */
	TextView status_textView;
	TextView magneticField_textView;
	Button ok_button;
	Button finish_button;
	Button panic_button;
	MediaPlayer mp;
	SensorManager sm = null;
	List<Sensor> list_g;

	SensorEventListener sel = 
	new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			
			/* Check only data for accelerometer */
			if (event.sensor == list_g.get(0) && list_g.get(0).getType() == Sensor.TYPE_ACCELEROMETER) {
				
				/* read data only when the button start has been pressed */
				if (ok_button.getText().toString().equals("Pause")) {
					
					/* set data to global variable */
					GlobalVariables.fAcceleration = event.values;
					
					/* Obtain system time */
					Date dCurrentTime = new Date();
					CharSequence sCurrentTime = DateFormat.format("hh:mm:ss", dCurrentTime.getTime());
					
					/* Create the URL */
					String url = "http://www.0160811.bugs3.com/sensorReadings/insert_accelerometer.php?time="
					   + sCurrentTime
					   + "&x=" + GlobalVariables.fAcceleration[0]
					   + "&y=" + GlobalVariables.fAcceleration[1]
					   + "&z=" + GlobalVariables.fAcceleration[2]
					   + "&comment=";
									
					HttpConnection con = new HttpConnection(url);
					   (new Thread(con)).start();
					
					/* Update status_textView */
					status_textView.setText("X: "+GlobalVariables.fAcceleration[0]
							+"\nY: "+GlobalVariables.fAcceleration[1]
							+"\nZ: "+GlobalVariables.fAcceleration[2]);
				}
			}
		}
	};
	
	SensorEventListener sel2 = 
	new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			/* Make use of data only when start button is pressed */
			if (ok_button.getText().toString().equals("Pause")) {
					float[] values = event.values;
					magneticField_textView.setText("X: "+values[0]
							+" Y: "+values[1]
							+" Z: "+values[2]);
			}
		}
	};




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_a_path);
		
		/* Find id of views */
		status_textView        = (TextView) findViewById(R.id.status_textView);
		magneticField_textView = (TextView) findViewById(R.id.magneticField_textView);
		ok_button              = (Button)   findViewById(R.id.ok_button);
		finish_button          = (Button)   findViewById(R.id.cancel_button);
		panic_button           = (Button)   findViewById(R.id.panic_button);
		
		/* Configure accelerometer sensor*/
		sm = (SensorManager)getSystemService(SENSOR_SERVICE);
		list_g = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (list_g.size()>0) {
		sm.registerListener(sel,  list_g.get(0), SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
		}
		
		/* Configure magnetic field sensor */
		sm.registerListener(sel2, sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
		
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


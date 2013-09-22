package com.nctu.guideme;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GetDirections_layoutActivity extends BaseActivity {

	/* Declare views in current layout */
	TextView status_textView;
	Button ok_button;
	Button cancel_button;
	Button panic_button;	
	SensorManager sm = null;
	List<Sensor> list_g;
	int currentIndex;

	String playIcon;
	
	SensorEventListener sel = 
	new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSensorChanged(SensorEvent event) { //if (bListenSensors) {
				
			/* Check only data for accelerometer */
			if (event.sensor == list_g.get(0) && list_g.get(0).getType() == Sensor.TYPE_ACCELEROMETER) {
						
				/* read data only when the button start has been pressed */
				//if (ok_button.getText().toString().equals("Pause") && (bDirectionReady)) {
				if (playIcon.equals("pause"))  {
					
					/* set data to global variable */
					fAcceleration = event.values;

					/*---------------------------------------------- CUMULATIVE ACCELERATION METHOD USING X Y Z--------------------------------------------------*/
					/* Update current and previous accelerations */
					//if (bDirectionReady) {
						fPreviousYAcceleration = fCurrentYAcceleration;
						fCurrentYAcceleration = fAcceleration[0]+fAcceleration[1]+fAcceleration[2];
						//fCurrentYAcceleration = fAcceleration[1];
						
						/* We accumulate only decreasing acceleration, that is only a pick */
						if (fCurrentYAcceleration < fPreviousYAcceleration){
							fCumulativeYAcceleration=fPreviousYAcceleration-fCurrentYAcceleration; 
						}
					//}
					
					/* If acceleration is rising the we reset the cumulative acceleration */
					if (fCurrentYAcceleration > fPreviousYAcceleration){

						/* If the cumulative acceleration so far is > fStepValue that means 1 step */
						if (fCumulativeYAcceleration>fStepValue){
							/*If the direction is correct then count the step */
								iStepsCounter++;

							/* If you walk all the steps in path_d then it moves to the next path_d and initialize steps counter */
							if (iStepsCounter>=paths_d.get(currentIndex).getSteps()) {
								iStepsCounter=0;

								if (currentIndex>=(paths_d.size()-1)) {
									bFinishPath=true; 
									status_textView.setText("You have arrived to your destination.");
									status_textView.setBackgroundColor(Color.GREEN);
									cancel_button.setText("Finish");
									audioInterface=new AudioInterface(getApplicationContext(),"finish");
									ok_button.setText("Start");
								}
								else {
									currentIndex++;
									bDirectionReady=false;
								}
							}
						}
						/* Reset cumulative acceleration */
						fCumulativeYAcceleration=0;
					}
							
					/*--------------------------------------------------------------------------------------------------------------------------------*/
							   
					/* Update status_textView */
					//status_textView.setText("Steps: "+iStepsCounter
					//		+" \nDirection Step= "+currentIndex);
					
					
				}
			}
		}//}
	};
			
	SensorEventListener sel2 = 
	new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) { //if (bListenSensors){
					
			/* Flag to indicate data is ready */
			iDirectionDataReady = 1;
			fDirection = event.values;

			/* Make use of data only when start button is pressed */
			if (playIcon.equals("pause")) {
					float[] values = event.values;

					status_textView.setText("Walk "+(paths_d.get(currentIndex).getSteps()-iStepsCounter)+ " steps" 
							+"\nCurrent : "+values[0]
							+"\nDirection: "+paths_d.get(currentIndex).getDirectionX());
					//if (!bDirectionReady){
						if ((values[0]<(paths_d.get(currentIndex).getDirectionX()+8) && values[0]>(paths_d.get(currentIndex).getDirectionX()-8))) {
							status_textView.setBackgroundColor(Color.BLACK);
							bDirectionReady=true;
						}
						else {
							bDirectionReady=false;
							//audioInterface=new AudioInterface(getApplicationContext(),"beep7");
							vibrator.vibrate(20);
							status_textView.setBackgroundColor(Color.RED);
						}
					//}
			}
		}//}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_directions);
		
		/* Find id of views */
		status_textView = (TextView) findViewById(R.id.status_textView);
		ok_button       = (Button)   findViewById(R.id.ok_button);
		cancel_button   = (Button)   findViewById(R.id.cancel_button);
		panic_button    = (Button)   findViewById(R.id.panic_button);
		
		/* Create vibrator for haptic feedback */
		vibrator=(Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		
		/* Database object */
		dataSource_d=null;
		dataSource_d=new Path_d_dataSource(this);
		dataSource_d.open();
		paths_d=dataSource_d.getAllPath_d(lPath_h);
		dataSource_d.close();
		
		bFinishPath=false;
		bDirectionReady=false;

		/* Variable used for change of icon */
		playIcon="arrow_right";
		
		/* Initialize variables */
		InitializeVariables();
		
		status_textView.setText("Walk "+(paths_d.get(currentIndex).getSteps()-iStepsCounter)+ " steps" 
				+"\nCurrent : 0"
				+"\nDirection: "+paths_d.get(currentIndex).getDirectionX());
		
		/* Initial message */
		audioInterface=new AudioInterface(getApplicationContext(),"");
		
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
		
		/* Start/pause the directions of a path */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				/* Press Start */
				if (playIcon.equals("arrow_right")) {
					/* Change icon */
					playIcon="pause";
					ok_button.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.pause), null, null);

					/* Activate Sensor Listeners */
					bListenSensors=true;
				}
				/* Press Pause */
				else {
					/* Change icon */
					playIcon="arrow_right";
					ok_button.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.arrow_right), null, null);
					
					/* Deactivate Sensor Listeners */
					bListenSensors=false;
				}
			}
		});
		
		/* Play the sound help */
		ok_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (ok_button.getText().toString().equals("Start"))
					audioInterface=new AudioInterface(getApplicationContext(),"start");
				else
					audioInterface=new AudioInterface(getApplicationContext(),"pause");
				return true;
			}
		});
		
		/* cancel the directions of a path  */
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Haptic feedback */
				vibrator.vibrate(50);

				sm.unregisterListener(sel);
				sm.unregisterListener(sel2);
				
				/* Return to initial layout */
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
				
				/* Audio interface */
				audioInterface=new AudioInterface(getApplicationContext(),"panic_message3");
		
				/* Make a call to emergency contact */
				switch(v.getId())
				{
					case R.id.panic_button:
						panic.phoneCall(preferences.GetPreference("contactPhone", null));
						break;
					default:
						break;
				}
			}
		});
		
		/* Play the sound help */
		panic_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				
				/* Audio interface */
				audioInterface=new AudioInterface(getApplicationContext(),"panic_button");
				
				return true;
			}
		});
	}
}

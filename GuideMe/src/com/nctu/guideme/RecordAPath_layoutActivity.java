package com.nctu.guideme;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class RecordAPath_layoutActivity extends BaseActivity {

	/* Declare views in current layout */
	TextView status_textView;
	TextView magneticField_textView;
	CheckBox http_checkBox;
	Button ok_button;
	Button finish_button;
	Button panic_button;
	SeekBar stepValue_seekBar;
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
					fAcceleration = event.values;

					/*---------------------------------------------- CUMULATIVE ACCELERATION METHOD USING X Y Z--------------------------------------------------*/
					
					/* Update current and previous accelerations */
					fPreviousYAcceleration = fCurrentYAcceleration;
					fCurrentYAcceleration = fAcceleration[0]+fAcceleration[1]+fAcceleration[2];
					
					/* We accumulate only decreasing acceleration, that is only a pick */
					if (fCurrentYAcceleration < fPreviousYAcceleration){
						fCumulativeYAcceleration=fPreviousYAcceleration-fCurrentYAcceleration; 
					}
					
					/* If acceleration is rising the we reset the cumulative acceleration */
					if (fCurrentYAcceleration < fPreviousYAcceleration){

						/* If the cumulative acceleration so far is > fStepValue that means 1 step */
						if (fCumulativeYAcceleration>fStepValue) 
							iStepsCounter++;
						
						/* Reset cumulative acceleration */
						fCumulativeYAcceleration=0;
					}
					
					/*--------------------------------------------------------------------------------------------------------------------------------*/
					
					/*---------------------------------------------- CUMULATIVE ACCELERATION METHOD USING ONLY Y--------------------------------------------------*/
					
					/* Update current and previous accelerations */
					//fPreviousYAcceleration = fCurrentYAcceleration;
					//fCurrentYAcceleration = fAcceleration[1];
					
					/* We accumulate only decreasing acceleration, that is only a pick */
					//if (fCurrentYAcceleration < fPreviousYAcceleration){
					//	fCumulativeYAcceleration=fPreviousYAcceleration-fCurrentYAcceleration; 
					//}
					
					/* If acceleration is rising the we reset the cumulative acceleration */
					//if (fCurrentYAcceleration < fPreviousYAcceleration){

						/* If the cumulative acceleration so far is > fStepValue that means 1 step */
					//	if (fCumulativeYAcceleration>fStepValue) 
					//		iStepsCounter++;
						
						/* Reset cumulative acceleration */
					//	fCumulativeYAcceleration=0;
					//}
					
					/*--------------------------------------------------------------------------------------------------------------------------------*/
					
					
					/*---------------------------------------------- BASE LINE METHOD ----------------------------------------------------------------*/
					/* Obtain Y base line only one time */
					//if (fBaseLineY == -999)
					//	fBaseLineY = fAcceleration[1];
					//
					///* We identify a step if the difference between the initial and current acceleration is greater than 1*/
					//if ((fBaseLineY-fAcceleration[1])>1) {
					//	/* Identify a step */
					//	if (iStepStatus == 0) iStepStatus = 1;
					//}
					//
					//if ((fBaseLineY-fAcceleration[1])<1) {
					//	if (iStepStatus == 1) {
					//		iStepStatus = 0;
					//		iStepsCounter++;
					//	}
					//}
					/*--------------------------------------------------------------------------------------------------------------------------------*/
					
					/* Obtain system time */
					Date dCurrentTime = new Date();
					CharSequence sCurrentTime = DateFormat.format("hh:mm:ss", dCurrentTime.getTime());
					
					if (iDirectionDataReady==1) {
						/* Create the URL */
						String url = "http://www.0160811.bugs3.com/sensorReadings/insert_accelerometer.php?time="
								+ sCurrentTime
								+ "&x=" + fAcceleration[0]
								+ "&y=" + fAcceleration[1]
								+ "&z=" + fAcceleration[2]
								+ "&angleX=" + fDirection[0]
								+ "&angleY=" + fDirection[1]
								+ "&angleZ=" + fDirection[2]
								+ "&steps=" + iStepsCounter
								+ "&stepValue=" + fStepValue
								+ "&comment="+cCurrentPath;
						
						if (http_checkBox.isChecked()) {
							HttpConnection con = new HttpConnection(url);
								(new Thread(con)).start();
						}
					}
					   
					/* Update status_textView */
					status_textView.setText("Steps: "+iStepsCounter
							//+"\nBaseline: "+fBaseLineY
							//+"\nY Acce: "+fAcceleration[1]);
							//+"\nX: "+fAcceleration[0]
							//"X: "+fAcceleration[0]
							//+"\nY: "+fAcceleration[1]
							//+"\nZ: "+fAcceleration[2]
							+" StepValue= "+fStepValue
							);
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
			
			/* Flag to indicate data is ready */
			iDirectionDataReady = 1;
			fDirection = event.values;
			/* Make use of data only when start button is pressed */
			if (ok_button.getText().toString().equals("Pause")) {
					float[] values = event.values;
					//magneticField_textView.setText("X: "+values[0]
					//		+" Y: "+values[1]
					//		+" Z: "+values[2]);
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
		http_checkBox          = (CheckBox) findViewById(R.id.http_checkBox);
		ok_button              = (Button)   findViewById(R.id.ok_button);
		finish_button          = (Button)   findViewById(R.id.cancel_button);
		panic_button           = (Button)   findViewById(R.id.panic_button);
		stepValue_seekBar      = (SeekBar)  findViewById(R.id.stepValue_seekBar);
		
		/* Create vibrator for haptic feedback */
		vibrator=(Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		
		/* Initialize seekBar*/
		stepValue_seekBar.setProgress((int) (fStepValue*100));
		status_textView.setText("Steps: "+iStepsCounter
				+" StepValue= "+fStepValue);
		
		/*-----------------Base Line Method ---------------------------------*/
		///* Initialize Y base line and step variables */
		//fBaseLineY = -999;
		//iStepStatus=0;
		//iStepsCounter=0;
		//iDirectionDataReady=0;
		/*--------------------------------------------------------------------*/
		
		/*--------------- Cumulative Acceleration Method----------------------*/
		iStepsCounter=0;
		fCurrentYAcceleration=0;
		fPreviousYAcceleration=0;
		fCumulativeYAcceleration=0;
		/*--------------------------------------------------------------------*/
		
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
		audioInterface=new AudioInterface(getApplicationContext(),"prest_start_to_record_the_path");
	
		/* Seek Bar */
		stepValue_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				// TODO Auto-generated method stub
				//seekBar.setProgress((int) (fStepValue*100));
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				fStepValue = (float) progress/100;
				 
				status_textView.setText("Steps: "+iStepsCounter
						+" StepValue= "+fStepValue);
			}
	
		});
		
		/* Start/pause the recording of a path */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Haptic feedback */
				vibrator.vibrate(50);
				
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
				
				if (ok_button.getText().toString().equals("Start"))
					audioInterface=new AudioInterface(getApplicationContext(),"start");
				else 
					audioInterface=new AudioInterface(getApplicationContext(),"pause");
				
				/*--------------- Cumulative Acceleration Method----------------------*/
				iStepsCounter=0;
				fCurrentYAcceleration=0;
				fPreviousYAcceleration=0;
				fCumulativeYAcceleration=0;
				/*--------------------------------------------------------------------*/
				
				status_textView.setText("Steps: "+iStepsCounter
						+" StepValue= "+fStepValue);
				
				return true;
			}
		});
		
		/* Finish the recording of a path  */
		finish_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				//PENDING
				
				/* Return to initial layout */
				startActivity(new Intent(getApplicationContext(), FinishRecordAPath_layoutActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		finish_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				audioInterface=new AudioInterface(getApplicationContext(),"finish");
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


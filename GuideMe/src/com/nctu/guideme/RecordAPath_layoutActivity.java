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
					GlobalVariables.fAcceleration = event.values;

					/*---------------------------------------------- CUMULATIVE ACCELERATION METHOD USING X Y Z--------------------------------------------------*/
					
					/* Update current and previous accelerations */
					GlobalVariables.fPreviousYAcceleration = GlobalVariables.fCurrentYAcceleration;
					GlobalVariables.fCurrentYAcceleration = GlobalVariables.fAcceleration[0]+GlobalVariables.fAcceleration[1]+GlobalVariables.fAcceleration[2];
					
					/* We accumulate only decreasing acceleration, that is only a pick */
					if (GlobalVariables.fCurrentYAcceleration < GlobalVariables.fPreviousYAcceleration){
						GlobalVariables.fCumulativeYAcceleration=GlobalVariables.fPreviousYAcceleration-GlobalVariables.fCurrentYAcceleration; 
					}
					
					/* If acceleration is rising the we reset the cumulative acceleration */
					if (GlobalVariables.fCurrentYAcceleration < GlobalVariables.fPreviousYAcceleration){

						/* If the cumulative acceleration so far is > GlobalVariables.fStepValue that means 1 step */
						if (GlobalVariables.fCumulativeYAcceleration>GlobalVariables.fStepValue) 
							GlobalVariables.iStepsCounter++;
						
						/* Reset cumulative acceleration */
						GlobalVariables.fCumulativeYAcceleration=0;
					}
					
					/*--------------------------------------------------------------------------------------------------------------------------------*/
					
					/*---------------------------------------------- CUMULATIVE ACCELERATION METHOD USING ONLY Y--------------------------------------------------*/
					
					/* Update current and previous accelerations */
					//GlobalVariables.fPreviousYAcceleration = GlobalVariables.fCurrentYAcceleration;
					//GlobalVariables.fCurrentYAcceleration = GlobalVariables.fAcceleration[1];
					
					/* We accumulate only decreasing acceleration, that is only a pick */
					//if (GlobalVariables.fCurrentYAcceleration < GlobalVariables.fPreviousYAcceleration){
					//	GlobalVariables.fCumulativeYAcceleration=GlobalVariables.fPreviousYAcceleration-GlobalVariables.fCurrentYAcceleration; 
					//}
					
					/* If acceleration is rising the we reset the cumulative acceleration */
					//if (GlobalVariables.fCurrentYAcceleration < GlobalVariables.fPreviousYAcceleration){

						/* If the cumulative acceleration so far is > GlobalVariables.fStepValue that means 1 step */
					//	if (GlobalVariables.fCumulativeYAcceleration>GlobalVariables.fStepValue) 
					//		GlobalVariables.iStepsCounter++;
						
						/* Reset cumulative acceleration */
					//	GlobalVariables.fCumulativeYAcceleration=0;
					//}
					
					/*--------------------------------------------------------------------------------------------------------------------------------*/
					
					
					/*---------------------------------------------- BASE LINE METHOD ----------------------------------------------------------------*/
					/* Obtain Y base line only one time */
					//if (GlobalVariables.fBaseLineY == -999)
					//	GlobalVariables.fBaseLineY = GlobalVariables.fAcceleration[1];
					//
					///* We identify a step if the difference between the initial and current acceleration is greater than 1*/
					//if ((GlobalVariables.fBaseLineY-GlobalVariables.fAcceleration[1])>1) {
					//	/* Identify a step */
					//	if (GlobalVariables.iStepStatus == 0) GlobalVariables.iStepStatus = 1;
					//}
					//
					//if ((GlobalVariables.fBaseLineY-GlobalVariables.fAcceleration[1])<1) {
					//	if (GlobalVariables.iStepStatus == 1) {
					//		GlobalVariables.iStepStatus = 0;
					//		GlobalVariables.iStepsCounter++;
					//	}
					//}
					/*--------------------------------------------------------------------------------------------------------------------------------*/
					
					/* Obtain system time */
					Date dCurrentTime = new Date();
					CharSequence sCurrentTime = DateFormat.format("hh:mm:ss", dCurrentTime.getTime());
					
					if (GlobalVariables.iDirectionDataReady==1) {
						/* Create the URL */
						String url = "http://www.0160811.bugs3.com/sensorReadings/insert_accelerometer.php?time="
								+ sCurrentTime
								+ "&x=" + GlobalVariables.fAcceleration[0]
								+ "&y=" + GlobalVariables.fAcceleration[1]
								+ "&z=" + GlobalVariables.fAcceleration[2]
								+ "&angleX=" + GlobalVariables.fDirection[0]
								+ "&angleY=" + GlobalVariables.fDirection[1]
								+ "&angleZ=" + GlobalVariables.fDirection[2]
								+ "&steps=" + GlobalVariables.iStepsCounter
								+ "&stepValue=" + GlobalVariables.fStepValue
								+ "&comment="+GlobalVariables.cCurrentPath;
						
						if (http_checkBox.isChecked()) {
							HttpConnection con = new HttpConnection(url);
								(new Thread(con)).start();
						}
					}
					   
					/* Update status_textView */
					status_textView.setText("Steps: "+GlobalVariables.iStepsCounter
							//+"\nBaseline: "+GlobalVariables.fBaseLineY
							//+"\nY Acce: "+GlobalVariables.fAcceleration[1]);
							//+"\nX: "+GlobalVariables.fAcceleration[0]
							//"X: "+GlobalVariables.fAcceleration[0]
							//+"\nY: "+GlobalVariables.fAcceleration[1]
							//+"\nZ: "+GlobalVariables.fAcceleration[2]
							+" StepValue= "+GlobalVariables.fStepValue
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
			GlobalVariables.iDirectionDataReady = 1;
			GlobalVariables.fDirection = event.values;
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
		
		/* Initialize seekBar*/
		stepValue_seekBar.setProgress((int) (GlobalVariables.fStepValue*100));
		status_textView.setText("Steps: "+GlobalVariables.iStepsCounter
				+" StepValue= "+GlobalVariables.fStepValue);
		
		/*-----------------Base Line Method ---------------------------------*/
		///* Initialize Y base line and step variables */
		//GlobalVariables.fBaseLineY = -999;
		//GlobalVariables.iStepStatus=0;
		//GlobalVariables.iStepsCounter=0;
		//GlobalVariables.iDirectionDataReady=0;
		/*--------------------------------------------------------------------*/
		
		/*--------------- Cumulative Acceleration Method----------------------*/
		GlobalVariables.iStepsCounter=0;
		GlobalVariables.fCurrentYAcceleration=0;
		GlobalVariables.fPreviousYAcceleration=0;
		GlobalVariables.fCumulativeYAcceleration=0;
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
		AI=new AudioInterface(getApplicationContext(),"prest_start_to_record_the_path");
	
		/* Seek Bar */
		stepValue_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//seekBar.setProgress((int) (GlobalVariables.fStepValue*100));
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				GlobalVariables.fStepValue = (float) progress/100;
				 
				status_textView.setText("Steps: "+GlobalVariables.iStepsCounter
						+" StepValue= "+GlobalVariables.fStepValue);
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
				
				if (ok_button.getText().toString().equals("Start"))
					AI=new AudioInterface(getApplicationContext(),"start");
				else 
					AI=new AudioInterface(getApplicationContext(),"pause");
				
				/*--------------- Cumulative Acceleration Method----------------------*/
				GlobalVariables.iStepsCounter=0;
				GlobalVariables.fCurrentYAcceleration=0;
				GlobalVariables.fPreviousYAcceleration=0;
				GlobalVariables.fCumulativeYAcceleration=0;
				/*--------------------------------------------------------------------*/
				
				status_textView.setText("Steps: "+GlobalVariables.iStepsCounter
						+" StepValue= "+GlobalVariables.fStepValue);
				
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
				AI=new AudioInterface(getApplicationContext(),"finish");
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


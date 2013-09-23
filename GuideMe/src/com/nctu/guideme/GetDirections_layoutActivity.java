package com.nctu.guideme;

import java.util.Date;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GetDirections_layoutActivity extends BaseActivity implements SensorEventListener{

	/* Declare views in current layout */
	Button orientation_button;
	Button steps_button;
	Button ok_button;
	Button cancel_button;
	Button panic_button;	

	/* Sensor Variables*/
	SensorManager sensorManager;
	Sensor sensorAccelerometer;
	Sensor sensorOrientation;
	float[] valuesAccelerometer;
	float[] previousAcceleration;
	float[] currentAcceleration;
	float[] cumulativeAcceleration;
	float[] valuesOrientation;
	
	int currentIndex;

	String playIcon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_directions);
		
		/* Find id of views */
		orientation_button = (Button)   findViewById(R.id.orientation_button);
		steps_button       = (Button)   findViewById(R.id.steps_button);
		ok_button          = (Button)   findViewById(R.id.ok_button);
		cancel_button      = (Button)   findViewById(R.id.cancel_button);
		panic_button       = (Button)   findViewById(R.id.panic_button);
		
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

		/* Initialize variables */
		InitializeVariables();
		
		valuesAccelerometer    = new float[3];
		previousAcceleration   = new float[3];
		currentAcceleration    = new float[3];
		cumulativeAcceleration = new float[3];
		valuesOrientation      = new float[3];

		/* Variable to control change of icon */
		playIcon = "play";
		
		/* PreferencesManager class*/
		preferences = new PreferenceManager(this,"SettingsFile");
		
		/* Load fStepValue */
		preferences.SetPreference("stepValue", fStepValue);
		
		/* Sensor Manager */
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
	    sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
	    sensorOrientation   = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
		/* Initial message */
		audioInterface=new AudioInterface(getApplicationContext(),"");
		
		/* Start/pause the directions of a path */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				/* Haptic feedback */
				vibrator.vibrate(50);
				
				/* Press Start */
				if (playIcon.equals("play")) {
					/* Change icon */
					playIcon="pause";
					ok_button.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.pause), null, null);
				}
				/* Press Pause */
				else {
					/* Change icon */
					playIcon="play";
					ok_button.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.play), null, null);
				}
			}
		});
		
		/* Play the sound help */
		ok_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if (playIcon.equals("play"))
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
				audioInterface=new AudioInterface(getApplicationContext(),"panic_button");
		
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
				audioInterface=new AudioInterface(getApplicationContext(),"panic_message3");
				
				return true;
			}
		});
	}
	
	@Override
	public void onResume() {

		sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	    sensorManager.registerListener(this, sensorOrientation, SensorManager.SENSOR_DELAY_NORMAL);
	    
	    super.onResume();
	}

	@Override
	 protected void onPause() {
		
		sensorManager.unregisterListener(this, sensorAccelerometer);
	    sensorManager.unregisterListener(this, sensorOrientation);
   
	    super.onPause();
	 }
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		switch(event.sensor.getType()){

		/* Accelerometer Sensor */
		case Sensor.TYPE_LINEAR_ACCELERATION:
			
			/* read data only when the button start has been pressed */
			if (playIcon.equals("pause") && !bFinishPath && bDirectionReady) {

				//sampleCounter++;
				
				/* Read data from sensor */
				for(int i =0; i < 3; i++){
					valuesAccelerometer[i] = event.values[i];
				}
				
				/*---------------------------------------------- CUMULATIVE ACCELERATION METHOD USING Y --------------------------------------------------*/
				
				/* Update current and previous accelerations */
				previousAcceleration[1]=currentAcceleration[1];
				previousAcceleration[2]=currentAcceleration[2];

				currentAcceleration[1]=valuesAccelerometer[1];
				currentAcceleration[2]=valuesAccelerometer[2];
				
				/* We accumulate only decreasing acceleration of Y and Z */
				if (currentAcceleration[1]<=previousAcceleration[1]){
					cumulativeAcceleration[1]=cumulativeAcceleration[1]+previousAcceleration[1]-currentAcceleration[1];
				}
				
				if (currentAcceleration[2]<=previousAcceleration[2]){
					cumulativeAcceleration[2]=cumulativeAcceleration[2]+previousAcceleration[2]-currentAcceleration[2];
				}
				
				/* If acceleration is rising then we reset the cumulative acceleration */
				if (Math.abs((cumulativeAcceleration[1]+cumulativeAcceleration[2]))>3){
						
					/* A step detected */
					iStepsCounter++;
						
					/* Resete comulative acceleration */
					cumulativeAcceleration[1]=0;
					cumulativeAcceleration[2]=0;
				
					/* If you walk all the steps in path_d then it moves to the next path_d and initialize steps counter */
					if (iStepsCounter>=paths_d.get(currentIndex).getSteps()) {
						iStepsCounter=0;

						/* finish the path */
						if (currentIndex>=(paths_d.size()-1)) {
							bFinishPath=true; 
							//status_textView.setText("You have arrived to your destination.");
							//status_textView.setBackgroundColor(Color.GREEN);
							
							/* PLay audio interface */
							audioInterface=new AudioInterface(getApplicationContext(),"finish");
							
							/* Change icon */
							playIcon="play";
							ok_button.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.play), null, null);
						}
						
						/* Change of path_d */
						else {
							
							/* Check for orientation */
							bDirectionReady=false;
							
							/* Increment index of path_d */
							currentIndex++;
							
							/* ask to go left or right */
							//PENDING 
							
						}
					}
				}
					
				if (currentAcceleration[1]>previousAcceleration[1]) {
					cumulativeAcceleration[1]=0;
				}
				
				if (currentAcceleration[2]>previousAcceleration[2]) {
					cumulativeAcceleration[2]=0;
				}
				/*--------------------------------------------------------------------------------------------------------------------------------*/

				/* Update data in steps */
				steps_button.setText("Walk:\n"+String.valueOf(paths_d.get(currentIndex).getSteps()-iStepsCounter));
				
			}
			if (!bDirectionReady) {
				
				/* Initial check of orientation */
				if (currentIndex==0) {
					//DO NOTHING 
				}

				/* Following checks of orientation */
				else {
				
					/* PLay audio interface */
					audioInterface=new AudioInterface(getApplicationContext(),"stop");
				}
				
				/* ---------------------------- Check orientation ---------------------------- */
				if ((paths_d.get(currentIndex).getDirectionX()<(valuesOrientation[0]+10))&&(paths_d.get(currentIndex).getDirectionX()>(valuesOrientation[0]-10))) {
					//CORRECT ORIENTATION
					orientation_button.setTextAppearance(this, R.style.GreenText);
					bDirectionReady=true;
				}
				else if (paths_d.get(currentIndex).getDirectionX()<(valuesOrientation[0]-10)){
					//TURN LEFT
					orientation_button.setTextAppearance(this, R.style.RedText);
				}
				else if (paths_d.get(currentIndex).getDirectionX()>(valuesOrientation[0]+10)){
					//TURN RIGHT
					orientation_button.setTextAppearance(this, R.style.RedText);
				}
				
				//orientation_button.setText(String.valueOf(Math.round(paths_d.get(currentIndex).getDirectionX())+"°"+sOrientation2+"\n")
				//		+String.valueOf(Math.round(valuesOrientation[0]))+"°"+sOrientation);
				/* Ask to turn left or right */
				
				
			}
			break;
		
		/* Orientation Sensor */   
		case Sensor.TYPE_ORIENTATION:
			/* Flag to indicate data is ready */
			iDirectionDataReady = 1;

			/* Make use of data only when start button is pressed */
			if (playIcon.equals("pause") && !bFinishPath) {

				/* Read data from sensor */
				for(int i =0; i < 3; i++){
					valuesOrientation[i] = event.values[i];
				}    

				/* Check for correct orientation */
				if ((paths_d.get(currentIndex).getDirectionX()<(valuesOrientation[0]+10))&&(paths_d.get(currentIndex).getDirectionX()>(valuesOrientation[0]-10))) {
					//CORRECT ORIENTATION
					orientation_button.setTextAppearance(this, R.style.GreenText);
					/* Haptic feedback */
					
				}
				else if (paths_d.get(currentIndex).getDirectionX()<(valuesOrientation[0]-10)){
					//TURN LEFT
					orientation_button.setTextAppearance(this, R.style.RedText);
					/* Haptic feedback */
					vibrator.vibrate(50);
				}
				else if (paths_d.get(currentIndex).getDirectionX()>(valuesOrientation[0]+10)){
					//TURN RIGHT
					orientation_button.setTextAppearance(this, R.style.RedText);
					/* Haptic feedback */
					vibrator.vibrate(50);
				}
				
				String sOrientation="";
				if ((valuesOrientation[0]>350) || (valuesOrientation[0]<10))
					sOrientation="N";
				else if ((valuesOrientation[0]>=10) && (valuesOrientation[0]<80))
					sOrientation="NE";
				else if ((valuesOrientation[0]>=80) && (valuesOrientation[0]<100))
					sOrientation="E";
				else if ((valuesOrientation[0]>=100) && (valuesOrientation[0]<170))
					sOrientation="SE";
				else if ((valuesOrientation[0]>=170) && (valuesOrientation[0]<190))
					sOrientation="S";
				else if ((valuesOrientation[0]>=190) && (valuesOrientation[0]<260))
					sOrientation="SW";
				else if ((valuesOrientation[0]>=260) && (valuesOrientation[0]<280))
					sOrientation="W";
				else if ((valuesOrientation[0]>=280) && (valuesOrientation[0]<350))
					sOrientation="NW";
				
				String sOrientation2="";
				if ((paths_d.get(currentIndex).getDirectionX()>350) || (paths_d.get(currentIndex).getDirectionX()<10))
					sOrientation2="N";
				else if ((paths_d.get(currentIndex).getDirectionX()>=10) && (paths_d.get(currentIndex).getDirectionX()<80))
					sOrientation2="NE";
				else if ((paths_d.get(currentIndex).getDirectionX()>=80) && (paths_d.get(currentIndex).getDirectionX()<100))
					sOrientation2="E";
				else if ((paths_d.get(currentIndex).getDirectionX()>=100) && (paths_d.get(currentIndex).getDirectionX()<170))
					sOrientation2="SE";
				else if ((paths_d.get(currentIndex).getDirectionX()>=170) && (paths_d.get(currentIndex).getDirectionX()<190))
					sOrientation2="S";
				else if ((paths_d.get(currentIndex).getDirectionX()>=190) && (paths_d.get(currentIndex).getDirectionX()<260))
					sOrientation2="SW";
				else if ((paths_d.get(currentIndex).getDirectionX()>=260) && (paths_d.get(currentIndex).getDirectionX()<280))
					sOrientation2="W";
				else if ((paths_d.get(currentIndex).getDirectionX()>=280) && (paths_d.get(currentIndex).getDirectionX()<350))
					sOrientation2="NW";
				
				/* Update data in orientation_button */
				orientation_button.setText(String.valueOf(Math.round(paths_d.get(currentIndex).getDirectionX())+"°"+sOrientation2+"\n")
						+String.valueOf(Math.round(valuesOrientation[0]))+"°"+sOrientation);
			}
			break;
		}
	}
}

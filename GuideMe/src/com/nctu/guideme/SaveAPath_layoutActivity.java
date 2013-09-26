package com.nctu.guideme;

import java.util.List;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SaveAPath_layoutActivity extends BaseActivity {
	/* Declare views in current layout */
	TextView status_textView;
	Button   ok_button;
	Button   cancel_button;
	Button   panic_button;
	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_record_a_path);
		
		/* Find id of views */
		status_textView = (TextView) findViewById(R.id.status_textView);
		ok_button       = (Button)   findViewById(R.id.ok_button);
		cancel_button   = (Button)   findViewById(R.id.cancel_button);
		panic_button    = (Button)   findViewById(R.id.panic_button);
		
		/* Initialize panic button */
		panic=new PanicButton(this);
		
		/* Create vibrator for haptic feedback */
		vibrator=(Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		
		/* Initial message */
		audioInterface=new AudioInterface(getApplicationContext(),"finish_save_path");
		
		/* Database objects */
		dataSource_h=new Path_h_dataSource(this);
		dataSource_h.open();
		
		dataSource_d=new Path_d_dataSource(this);
		dataSource_d.open();
		
		/* Confirm recording of a path and return to initial layout */
		ok_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				/* insert path_h to database */
				Path_h path_h=null;
				path_h=dataSource_h.createPath_h(currentFileName);
				dataSource_h.close();
				
				/* Update column path_h in paths_d*/
				int currentIndex=0;
				while (currentIndex<paths_d.size()){
					paths_d.get(currentIndex).setPath_h(path_h.getId());
					currentIndex++;
				}
				
				/* Process path_d */
				currentIndex=0;
				int   tmp_steps=0;
				float tmp_totalDirX=0;
				float tmp_maxDirX=0;
				float tmp_minDirX=0;

				float InitialAngle;
				float tmpNorth, tmpSouth, tmpEast,tmpWest;
				InitialAngle=paths_d.get(0).getDirectionX();
				
				
				
				/* Group and insert path_d into database */
				while (currentIndex<paths_d.size()){
					
					/* Max of X*/
					if (tmp_maxDirX==0 || paths_d.get(currentIndex).getDirectionX()>tmp_maxDirX)
						tmp_maxDirX=paths_d.get(currentIndex).getDirectionX();
					/* Min of X*/
					if (tmp_minDirX==0 || paths_d.get(currentIndex).getDirectionX()<tmp_minDirX)
						tmp_minDirX=paths_d.get(currentIndex).getDirectionX();
					
					/* check to see if the step is in the boundaries*/
					if (((tmp_maxDirX!=0) && (tmp_minDirX!=0) &&  (tmp_maxDirX-tmp_minDirX)>11)||(currentIndex==(paths_d.size()-1))) {
						dataSource_d.createPath_d(paths_d.get(currentIndex).getPath_h(), tmp_steps, tmp_totalDirX/tmp_steps, 0, 0);
						
						tmp_steps=0;
						tmp_totalDirX=0;
						
						// we still need to initialize the max min of X
						tmp_maxDirX=tmp_minDirX=paths_d.get(currentIndex).getDirectionX();
					}
					
					/*total steps and direction */
					tmp_steps++;
					tmp_totalDirX+=paths_d.get(currentIndex).getDirectionX();
					
					currentIndex++;
				}
				
				/* insert path_d to database */
				//currentIndex=0;
				//while (currentIndex<paths_d.size()){
				//	dataSource_d.createPath_d(paths_d.get(currentIndex).getPath_h(),
				//			paths_d.get(currentIndex).getSteps(),
				//			paths_d.get(currentIndex).getDirectionX(),
				//			paths_d.get(currentIndex).getDirectionY(),
				//			paths_d.get(currentIndex).getDirectionZ());
				//	currentIndex++;
				//}
				//dataSource_d.close();
				
				/* Update pathFileNameCounter preference*/
				preferences=new PreferenceManager(getApplicationContext(),"pathFileNameCounter");
				preferences.IncrementPreference("pathFileNameCounter", 0);
				
				/* Return to initial layout */
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		ok_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				audioInterface=new AudioInterface(getApplicationContext(),"yes");
				return true;
			}
		});
		
		cancel_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/* Haptic feedback */
				vibrator.vibrate(50);
				
				/* Cancel the recording of the path, delete data */
				//PENDING 
				
				/* Return to initial layout */
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});
		
		/* Play the sound help */
		cancel_button.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				audioInterface=new AudioInterface(getApplicationContext(),"no");
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
				
				/* Audio interface*/
				audioInterface=new AudioInterface(getApplicationContext(),"panic_button");
				return true;
			}
		});
	}
}

package com.nctu.guideme;

import java.util.List;

import android.app.Activity;
import android.os.Vibrator;

public abstract class BaseActivity extends Activity {
	public AudioInterface audioInterface;
	public Vibrator vibrator;
	public PreferenceManager preferences;
	public RecordAudio recorder;
	public PlayAudio playAudio;
	public static String currentFileName;
	
	public Path_h_dataSource dataSource_h;
	public Path_d_dataSource dataSource_d;
	public static List<Path_h> paths_h;
	public static List<Path_d> paths_d;
	public static long lPath_h;
	
	/* Global variables */
	public static CharSequence cCurrentPath;
	public static String sHttpStatus;
	public static float[] fDirection;
	public static float[] fAcceleration;
	public static float   fBaseLineY;
	public static int     iStepsCounter;
	public static int     iStepStatus; /* 0 = initial, 1 => start step */
	public static int     iDirectionDataReady;
	
	public static float   fCurrentYAcceleration;
	public static float   fPreviousYAcceleration;
	public static float   fCumulativeYAcceleration;
	public static float  fStepValue;
	public static float  fDefaultStepValue=1;
	public static boolean bDirectionReady=false;
	public static boolean bFinishPath=false;
	public static boolean bListenSensors=false;
	public void InitializeVariables(){
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
	}
}

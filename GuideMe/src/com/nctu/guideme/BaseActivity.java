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
	public Path_h_dataSource dataSource;
	public List<Path_h> paths_h;
	
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
}

package com.nctu.guideme;

import android.app.Activity;
import android.os.Vibrator;

public abstract class BaseActivity extends Activity {
	
	/* Global declarations */
	public AudioInterface audioInterface;
	public Vibrator vibrator;
	public PreferenceManager preferences;
}

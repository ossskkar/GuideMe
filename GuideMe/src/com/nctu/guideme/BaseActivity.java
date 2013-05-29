package com.nctu.guideme;

import android.app.Activity;
import android.os.Vibrator;

public abstract class BaseActivity extends Activity {
	public AudioInterface audioInterface;
	public Vibrator vibrator;
	public PreferenceManager preferences;
	public RecordAudio recorder;
}

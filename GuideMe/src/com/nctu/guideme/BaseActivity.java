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
}

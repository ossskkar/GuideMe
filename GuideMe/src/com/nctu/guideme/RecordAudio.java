package com.nctu.guideme;

import java.io.File;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;

public class RecordAudio extends BaseActivity {

	private MediaRecorder recorder;
	private String OUTPUT_FILE; 
	private File pathNameFile;
	private Context currentContext;
	
	public RecordAudio(Context context){
		currentContext=context;
	}

	public void StartRecording(){
		try {
			InitializeRecording();
			CreateFileName();
			SetupRecording();
			recorder.prepare();
			recorder.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void StopRecording(){
		try {
			if (recorder != null) {
				recorder.stop();
				recorder.release();
				recorder=null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void InitializeRecording() throws Exception{
		if (recorder != null) {
			recorder.release();
		}
	}

	public void SetupRecording() throws Exception{
		recorder=new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(OUTPUT_FILE);
	}
	
	public void CreateFileName() throws Exception{
		preferences=new PreferenceManager(currentContext,"pathFileNameCounter");
		OUTPUT_FILE=Environment.getExternalStorageDirectory().getAbsolutePath()
				+"/Path"
				+preferences.GetPreference("pathFileNameCounter", 0)
				+".3gpp";
		preferences.IncrementPreference("pathFileNameCounter", 0);
		pathNameFile = new File(OUTPUT_FILE);
		CheckFileName();
	}
	
	public void CheckFileName() throws Exception{
		if (pathNameFile.exists())
			pathNameFile.delete();
	}

	public String GetFileName(){
		return OUTPUT_FILE;
	}
}

package com.nctu.guideme;

import android.app.Activity;
import android.media.MediaPlayer;

public class PlayAudio extends Activity {

	private MediaPlayer mp;
	private String fileName;
	
	public PlayAudio(){
	}

	public void StartPlaying(String aFileName){
		fileName=aFileName;
		try {
			InitializePlayer();
			mp.prepare();
			mp.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void InitializePlayer()throws Exception{
		if (mp!=null) 
			mp.release();
		SetupPlayer();
	}
	
	public void SetupPlayer()throws Exception{
		mp=new MediaPlayer();
		mp.setDataSource(fileName);
	}
	
	public void StopPlaying(){
		if (mp!=null)
			mp.stop();
	}
}

package com.nctu.guideme;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

public class AudioInterface extends Activity {

	private MediaPlayer mp2=null;
	private Context currentContext=null;
	private String currentAudio=null;
	
	public AudioInterface(Context thisContext, String audioName) {
		currentContext=thisContext;
		currentAudio=audioName;
		try {
			PlayAudio();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void PlayAudio() throws Exception {
		ClearMp();
		SetUpMp();
		StartAudio();
	}

	private void StartAudio() throws Exception{
		if (mp2.isPlaying())
			mp2.stop();
		mp2.start();
		mp2.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp2) {
				mp2.release();
			}
		});
	}

	private void SetUpMp() throws Exception{
		mp2 = new MediaPlayer();
		SelectAudioFile();
	}


	private void ClearMp() throws Exception{
		if (mp2!=null)	{
			mp2.stop();
			//mp2.release();
		}
	}
	
	private void SelectAudioFile() throws Exception {
		if (currentAudio.equals("accept"))
			mp2=MediaPlayer.create(currentContext, R.raw.accept);

		if (currentAudio.equals("calibration"))
			mp2=MediaPlayer.create(currentContext, R.raw.calibration);
		
		if (currentAudio.equals("cancel"))
			mp2=MediaPlayer.create(currentContext, R.raw.cancel);
		
		if (currentAudio.equals("emergency_contact_information"))
			mp2=MediaPlayer.create(currentContext, R.raw.emergency_contact_information);
		
		if (currentAudio.equals("enter_a_name_for_the_new_path"))
			mp2=MediaPlayer.create(currentContext, R.raw.enter_a_name_for_the_new_path);
		
		if (currentAudio.equals("exit_application"))
			mp2=MediaPlayer.create(currentContext, R.raw.exit_application);
		
		if (currentAudio.equals("exit"))
			mp2=MediaPlayer.create(currentContext, R.raw.exit);
		
		if (currentAudio.equals("finish_save_path"))
			mp2=MediaPlayer.create(currentContext, R.raw.finish_save_path);
		
		if (currentAudio.equals("finish"))
			mp2=MediaPlayer.create(currentContext, R.raw.finish);
		
		if (currentAudio.equals("get_directions"))
			mp2=MediaPlayer.create(currentContext, R.raw.get_directions);
		
		if (currentAudio.equals("next"))
			mp2=MediaPlayer.create(currentContext, R.raw.next);
		
		if (currentAudio.equals("no"))
			mp2=MediaPlayer.create(currentContext, R.raw.no);
		
		if (currentAudio.equals("panic_button"))
			mp2=MediaPlayer.create(currentContext, R.raw.panic_button);
		
		if (currentAudio.equals("panic_message3"))
			mp2=MediaPlayer.create(currentContext, R.raw.panic_message3);
		
		if (currentAudio.equals("pause"))
			mp2=MediaPlayer.create(currentContext, R.raw.pause);
		
		if (currentAudio.equals("play"))
			mp2=MediaPlayer.create(currentContext, R.raw.play);
			
		if (currentAudio.equals("prest_start_to_record_the_path"))
			mp2=MediaPlayer.create(currentContext, R.raw.prest_start_to_record_the_path);
		
		if (currentAudio.equals("previous"))
			mp2=MediaPlayer.create(currentContext, R.raw.previous);
		
		if (currentAudio.equals("record"))
			mp2=MediaPlayer.create(currentContext, R.raw.record);
		
		if (currentAudio.equals("record_a_path"))
			mp2=MediaPlayer.create(currentContext, R.raw.record_a_path);
		
		if (currentAudio.equals("save"))
			mp2=MediaPlayer.create(currentContext, R.raw.save);
		
		if (currentAudio.equals("select_path"))
			mp2=MediaPlayer.create(currentContext, R.raw.select_path);
		
		if (currentAudio.equals("select"))
			mp2=MediaPlayer.create(currentContext, R.raw.select);
		
		if (currentAudio.equals("settings"))
			mp2=MediaPlayer.create(currentContext, R.raw.settings);
		
		if (currentAudio.equals("start"))
			mp2=MediaPlayer.create(currentContext, R.raw.start);
		
		if (currentAudio.equals("welcome_message"))
			mp2=MediaPlayer.create(currentContext, R.raw.welcome_message);
		
		if (currentAudio.equals("welcome_message2"))
			mp2=MediaPlayer.create(currentContext, R.raw.welcome_message2);
		
		if (currentAudio.equals("yes"))
			mp2=MediaPlayer.create(currentContext, R.raw.yes);
		
		//if (currentAudio.equals(""))
		//	mp2=MediaPlayer.create(currentContext, R.raw.);
	}
	
}

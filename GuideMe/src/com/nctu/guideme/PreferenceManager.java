package com.nctu.guideme;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager extends Application {

	SharedPreferences settings;
	
	public PreferenceManager(Context currentContext, String preferenceFileName){
		try {
		settings = currentContext.getSharedPreferences("settingsFile",0);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void SetPreference(String preferenceName, String preferenceValue){
		SharedPreferences.Editor preferencesEditor = settings.edit();
		preferencesEditor.putString(preferenceName, preferenceValue);
		preferencesEditor.commit();
	}
	
	public void SetPreference(String preferenceName, Float preferenceValue){
		SharedPreferences.Editor preferencesEditor = settings.edit();
		preferencesEditor.putFloat(preferenceName, preferenceValue);
		preferencesEditor.commit();
	}
	
	public String GetPreference(String preferenceName, String defaultValue){
		String preferenceValue = settings.getString(preferenceName, defaultValue);
		return preferenceValue;
	}
	
	public float GetPreference(String preferenceName, float defaultValue){
		float preferenceValue = settings.getFloat(preferenceName, defaultValue);
		return preferenceValue;
	}
	
	public void IncrementPreference(String preferenceName, float defaultValue){
		GetPreference(preferenceName, settings.getFloat(preferenceName, defaultValue)+1);
	}
}

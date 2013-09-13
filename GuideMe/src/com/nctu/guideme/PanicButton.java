package com.nctu.guideme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PanicButton{
	
	private Context currentContext;
	private Boolean fileStatus;
	
	
	
	public PanicButton(Context context){
		currentContext=context;
		SetFileStatus(false);
	}
	
	public void phoneCall(String number)
	{
	//	try{
			String phoneCallUri="tel:"+number;  //call eric
			Intent phoneCallIntent=new Intent(Intent.ACTION_CALL);
			phoneCallIntent.setData(Uri.parse(phoneCallUri));
			currentContext.startActivity(phoneCallIntent);
		//	startActivity(phoneCallIntent);
	//	}catch(Exception e){
	//		String phoneCallUri="tel:0918036812";  //call eric
	//		Intent phoneCallIntent=new Intent(Intent.ACTION_CALL);
	//		phoneCallIntent.setData(Uri.parse(phoneCallUri));
	//		startActivity(phoneCallIntent);
		//	e.printStackTrace();
		//	SetFileStatus(false);
	//	}
	}
	public void SetFileStatus(Boolean status){
		fileStatus=status;
	}
	
}

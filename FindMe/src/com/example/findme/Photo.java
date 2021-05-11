package com.example.findme;

import android.app.Activity;
import android.content.Intent;

public class Photo extends Activity{
	
	public void fromfile(){
		Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select file to upload "),1);		
	}
	public void fromcamera(){
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
		
		
	}

}

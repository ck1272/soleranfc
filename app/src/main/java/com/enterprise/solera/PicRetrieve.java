package com.enterprise.solera;

import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;


public class PicRetrieve extends AsyncTask<String, Void, Drawable> {

	@Override
	protected Drawable doInBackground(String... urls) {
		String url = urls[0];
		try
	      {
	       InputStream is = (InputStream) new URL(url).getContent();
	       Drawable d = Drawable.createFromStream(is, "src name");
	       return d;
	      }catch (Exception e) {
	       System.out.println("Exc="+e);
	       return null;
	      }
	}

}

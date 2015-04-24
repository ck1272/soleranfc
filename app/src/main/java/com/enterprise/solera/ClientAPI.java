package com.enterprise.solera;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.os.AsyncTask;


//import android.R.string;

public class ClientAPI extends AsyncTask<String, Void, String>
{
	private final String URL = "http://solera.masonegger.com/api/tagInfo?serial=";

	@Override
	protected String doInBackground (String... serials)
	{
		String serial = serials[0];
		DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
		String query = URL + serial;
		System.out.println(query);
		HttpGet httpget = new HttpGet(query);
		// Depends on your web service

		InputStream inputStream = null;
		String result = null;
		try {
		    HttpResponse response = httpclient.execute(httpget);           
		    HttpEntity entity = response.getEntity();

		    inputStream = entity.getContent();
		    // json is UTF-8 by default
		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
		    StringBuilder sb = new StringBuilder();

		    String line = null;
		    while ((line = reader.readLine()) != null)
		    {
		        sb.append(line + "\n");
		    }
		    result = sb.toString();
		} catch (Exception e) { 
		    return e.toString();
		}
		finally {
		    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
		}
		return result;
	}

}

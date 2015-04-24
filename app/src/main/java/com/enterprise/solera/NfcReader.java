package com.enterprise.solera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class NfcReader extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc_reader);
		//getActionBar().setTitle("Solera");
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nfc_reader, menu);
		return true;
	}
	
	
	public void onResume()
	{
		super.onResume();
		Intent intent = getIntent();
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		byte[] bytes = tag.getId();
		String serial = "";
		for (int i = 0; i < bytes.length; i++) {
			String hex = (Integer.toHexString(0xFF & bytes[i]));
			if (hex.length() == 1)
				hex = "0" + hex;
			serial += hex;
		}
		ClientAPI cont = new ClientAPI();
		cont.execute(serial);
		String jsonRaw = "";
		try {
			jsonRaw = cont.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (jsonRaw.length() > 0) {
			try {
				JSONObject reader = new JSONObject(jsonRaw);
				TextView txtView = (TextView) findViewById(R.id.name);
				txtView.setText(reader.getString("name"));
				txtView = (TextView) findViewById(R.id.description);
				txtView.setText(reader.getString("description"));
				txtView = (TextView) findViewById(R.id.url);
				txtView.setText(reader.getString("url"));
				ImageView imgView = (ImageView) findViewById(R.id.picture);
				String url = "http://solera.masonegger.com" + reader.getString("picture");
				PicRetrieve picRet = new PicRetrieve();
				picRet.execute(url);
				Drawable pic = picRet.get();
				imgView.setImageDrawable(pic);
			} catch (Exception e) {
				// TODO Auto-generated catch block
                System.out.println("FUCK THIS SHIT");
				e.printStackTrace();
			}
		}
	}
	
}



package com.nateemma.wtp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Context  mContext;

	private Button btnBoiler;
	private Button btnCooling;
	private Button btnAbout;
	private Button btnContact;
	private Button btnHelp;

	private static final String TAG = "MainActivity";
	private static Boolean splashDisplayed = false;
	private static Boolean mSetupDone = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try{

			if (!mSetupDone) {
				mSetupDone = true;
				setContentView(R.layout.activity_main);

				mContext = this;

				//getActionBar().setDisplayHomeAsUpEnabled(true);

				// Set up handlers for the buttons
				btnBoiler = (Button) findViewById(R.id.btnBoiler);
				btnCooling = (Button) findViewById(R.id.btnCooling);
				btnAbout = (Button) findViewById(R.id.btnAbout);
				btnContact = (Button) findViewById(R.id.btnContact);
				btnHelp = (Button) findViewById(R.id.btnHelp);

				btnBoiler.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.BOILER_INPUT);};});
				btnCooling.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.COOLING_INPUT);};});
				btnAbout.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.ABOUT);};});
				btnContact.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.CONTACT);};});
				btnHelp.setOnClickListener (new OnClickListener(){public void onClick(View v) {launchActivity(LocalIntents.HELP);};});
			}
		} catch (Exception e){
			Log.e(TAG, "Error: "+e.toString());
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	} //onDestroy





	// Utility to launch activity given the Intent name
	void launchActivity(String intentName) {
		Intent intent = new Intent();
		intent.setAction(intentName);
		try {
			startActivity(intent);
		} catch (Throwable t){
			Log.e(TAG, "startActivity exception: " + t.toString());
			Toast.makeText(mContext, "Error starting " + intentName, Toast.LENGTH_SHORT).show();
		}

	}

}

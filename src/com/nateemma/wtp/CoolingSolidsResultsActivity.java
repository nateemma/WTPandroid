package com.nateemma.wtp;
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

// 11/2/13 - took out cost/kg and cost/annum columns. Just commented out for now

// Class to implement display of Steam Cooling results
public class CoolingSolidsResultsActivity extends Activity {


	private static final String TAG = "CoolingResultsActivity";

	private static CoolingModel mCoolingModel = null;

	private Context mContext=null;

	// Product-based values
	private static TextView vOutHS2097Dosage;
	private static TextView vOutHS2097Usage;
	//private static TextView vOutHS2097CostKg;
	//private static TextView vOutHS2097CostAnnum;

	private static TextView vOutHS4390Dosage;
	private static TextView vOutHS4390Usage;
	//private static TextView vOutHS4390CostKg;
	//private static TextView vOutHS4390CostAnnum;

	private static TextView vOutHS3990Dosage;
	private static TextView vOutHS3990Usage;
	//private static TextView vOutHS3990CostKg;
	//private static TextView vOutHS3990CostAnnum;

	// Biocide values

	private static TextView vOutCS4400Dosage;
	private static TextView vOutCS4400Usage;
	//private static TextView vOutCS4400CostKg;
	//private static TextView vOutCS4400CostAnnum;

	private static TextView vOutCS4802Dosage;
	private static TextView vOutCS4802Usage;
	//private static TextView vOutCS4802CostKg;
	//private static TextView vOutCS4802CostAnnum;

	private static TextView vOutCS4490Dosage;
	private static TextView vOutCS4490Usage;
	//private static TextView vOutCS4490CostKg;
	//private static TextView vOutCS4490CostAnnum;

	private static TextView vOutSCG1Dosage;
	private static TextView vOutSCG1Usage;
	//private static TextView vOutSCG1CostKg;
	//private static TextView vOutSCG1CostAnnum;

	private static TextView vOutCSChlorDosage;
	private static TextView vOutCSChlorUsage;
	//private static TextView vOutCSChlorCostKg;
	//private static TextView vOutCSChlorCostAnnum;

	private static TextView vOutC42TDosage;
	private static TextView vOutC42TUsage;
	//private static TextView vOutC42TCostKg;
	//private static TextView vOutC42TCostAnnum;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			mContext = this;
		    getActionBar().setDisplayHomeAsUpEnabled(true);

			setContentView(R.layout.cooling_solids_results);

			// connect the views with the output fields
			vOutHS2097Dosage = (TextView)findViewById(R.id.outHS2097Dosage);
			vOutHS2097Usage = (TextView)findViewById(R.id.outHS2097Usage);
			//vOutHS2097CostKg = (TextView)findViewById(R.id.outHS2097CostKg);
			//vOutHS2097CostAnnum = (TextView)findViewById(R.id.outHS2097CostAnnum);
			vOutHS4390Dosage = (TextView)findViewById(R.id.outHS4390Dosage);
			vOutHS4390Usage = (TextView)findViewById(R.id.outHS4390Usage);
			//vOutHS4390CostKg = (TextView)findViewById(R.id.outHS4390CostKg);
			//vOutHS4390CostAnnum = (TextView)findViewById(R.id.outHS4390CostAnnum);
			vOutHS3990Dosage = (TextView)findViewById(R.id.outHS3990Dosage);
			vOutHS3990Usage = (TextView)findViewById(R.id.outHS3990Usage);
			//vOutHS3990CostKg = (TextView)findViewById(R.id.outHS3990CostKg);
			//vOutHS3990CostAnnum = (TextView)findViewById(R.id.outHS3990CostAnnum);
			vOutCS4400Dosage = (TextView)findViewById(R.id.outCS4400Dosage);
			vOutCS4400Usage = (TextView)findViewById(R.id.outCS4400Usage);
			//vOutCS4400CostKg = (TextView)findViewById(R.id.outCS4400CostKg);
			//vOutCS4400CostAnnum = (TextView)findViewById(R.id.outCS4400CostAnnum);
			vOutCS4802Dosage = (TextView)findViewById(R.id.outCS4802Dosage);
			vOutCS4802Usage = (TextView)findViewById(R.id.outCS4802Usage);
			//vOutCS4802CostKg = (TextView)findViewById(R.id.outCS4802CostKg);
			//vOutCS4802CostAnnum = (TextView)findViewById(R.id.outCS4802CostAnnum);
			vOutCS4490Dosage = (TextView)findViewById(R.id.outCS4490Dosage);
			vOutCS4490Usage = (TextView)findViewById(R.id.outCS4490Usage);
			//vOutCS4490CostKg = (TextView)findViewById(R.id.outCS4490CostKg);
			//vOutCS4490CostAnnum = (TextView)findViewById(R.id.outCS4490CostAnnum);
			vOutSCG1Dosage = (TextView)findViewById(R.id.outSCG1Dosage);
			vOutSCG1Usage = (TextView)findViewById(R.id.outSCG1Usage);
			//vOutSCG1CostKg = (TextView)findViewById(R.id.outSCG1CostKg);
			//vOutSCG1CostAnnum = (TextView)findViewById(R.id.outSCG1CostAnnum);
			vOutCSChlorDosage = (TextView)findViewById(R.id.outCSChlorDosage);
			vOutCSChlorUsage = (TextView)findViewById(R.id.outCSChlorUsage);
			//vOutCSChlorCostKg = (TextView)findViewById(R.id.outCSChlorCostKg);
			//vOutCSChlorCostAnnum = (TextView)findViewById(R.id.outCSChlorCostAnnum);
			vOutC42TDosage = (TextView)findViewById(R.id.outC42TDosage);
			vOutC42TUsage = (TextView)findViewById(R.id.outC42TUsage);
			//vOutC42TCostKg = (TextView)findViewById(R.id.outC42TCostKg);
			//vOutC42TCostAnnum = (TextView)findViewById(R.id.outC42TCostAnnum);


			// load the  data
			loadData();

		} catch (Exception e){
			Log.e(TAG, "onCreate() Error: "+e.toString());
			e.printStackTrace();
		}

	} // onCreate


	// MENU HANDLING

	// Register the menu actions for display on the action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.results_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}


	// Handler for the menu action(s)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_email:
			sendEmail();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}


	// APP LOGIC

	// round to 1 decimal place
	private String round1(Double value) {
		Double tmp = 0.0;
		if (value==null) value = 0.0;
		tmp = ((int)((value+0.05) * 10)) / 10.0;	    
		return tmp.toString();
	}

	// load the initial set of data
	private void loadData(){
		try{
			mCoolingModel = CoolingModel.sharedInstance();
			mCoolingModel.setContext(mContext);		

			vOutHS2097Dosage.setText(round1(mCoolingModel.hs2097Dosage));
			vOutHS2097Usage.setText(round1(mCoolingModel.hs2097Usage));
			//vOutHS2097CostKg.setText(round1(mCoolingModel.hs2097CostKg));
			//vOutHS2097CostAnnum.setText(round1(mCoolingModel.hs2097CostAnnum));
			vOutHS4390Dosage.setText(round1(mCoolingModel.hs4390Dosage));
			vOutHS4390Usage.setText(round1(mCoolingModel.hs4390Usage));
			//vOutHS4390CostKg.setText(round1(mCoolingModel.hs4390CostKg));
			//vOutHS4390CostAnnum.setText(round1(mCoolingModel.hs4390CostAnnum));
			vOutHS3990Dosage.setText(round1(mCoolingModel.hs3990Dosage));
			vOutHS3990Usage.setText(round1(mCoolingModel.hs3990Usage));
			//vOutHS3990CostKg.setText(round1(mCoolingModel.hs3990CostKg));
			//vOutHS3990CostAnnum.setText(round1(mCoolingModel.hs3990CostAnnum));
			vOutCS4400Dosage.setText(round1(mCoolingModel.cs4400Dosage));
			vOutCS4400Usage.setText(round1(mCoolingModel.cs4400Usage));
			//vOutCS4400CostKg.setText(round1(mCoolingModel.cs4400CostKg));
			//vOutCS4400CostAnnum.setText(round1(mCoolingModel.cs4400CostAnnum));
			vOutCS4802Dosage.setText(round1(mCoolingModel.cs4802Dosage));
			vOutCS4802Usage.setText(round1(mCoolingModel.cs4802Usage));
			//vOutCS4802CostKg.setText(round1(mCoolingModel.cs4802CostKg));
			//vOutCS4802CostAnnum.setText(round1(mCoolingModel.cs4802CostAnnum));
			vOutCS4490Dosage.setText(round1(mCoolingModel.cs4490Dosage));
			vOutCS4490Usage.setText(round1(mCoolingModel.cs4490Usage));
			//vOutCS4490CostKg.setText(round1(mCoolingModel.cs4490CostKg));
			//vOutCS4490CostAnnum.setText(round1(mCoolingModel.cs4490CostAnnum));
			vOutSCG1Dosage.setText(round1(mCoolingModel.scg1Dosage));
			vOutSCG1Usage.setText(round1(mCoolingModel.scg1Usage));
			//vOutSCG1CostKg.setText(round1(mCoolingModel.scg1CostKg));
			//vOutSCG1CostAnnum.setText(round1(mCoolingModel.scg1CostAnnum));
			vOutCSChlorDosage.setText(round1(mCoolingModel.cschlorDosage));
			vOutCSChlorUsage.setText(round1(mCoolingModel.cschlorUsage));
			//vOutCSChlorCostKg.setText(round1(mCoolingModel.cschlorCostKg));
			//vOutCSChlorCostAnnum.setText(round1(mCoolingModel.cschlorCostAnnum));
			vOutC42TDosage.setText(round1(mCoolingModel.c42tDosage));
			vOutC42TUsage.setText(round1(mCoolingModel.c42tUsage));
			//vOutC42TCostKg.setText(round1(mCoolingModel.c42tCostKg));
			//vOutC42TCostAnnum.setText(round1(mCoolingModel.c42tCostAnnum));

		} catch (Exception e){
			Log.e(TAG, "loadData() Error: "+e.toString());
			e.printStackTrace();
		}
	}


	// EMAIL FORMATION AND HANDLING

	private void sendEmail(){

		// get HTML-formatted summary of results
		String emailBody = buildHTMLsummary();

		//Log.v(TAG, "Email text: \n"+emailBody);

		/**
		 * NOTE: GMAIL cannot handle tables in HTML, so we can't just insert the text, as with iPhone
		 *       So, write the data to an HTML file and send it as an attachment instead
		 */
		// create the attachment
		Uri attachUri = createAttachment(emailBody);

		if (attachUri != null){
			// start email activity
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			//i.setType("text/html");
			//i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
			i.putExtra(Intent.EXTRA_SUBJECT, "WTP Product Estimates for Cooling Water");
			String emailIntro = "<html><br>Please find attached a summary of the WTP Product estimates<br></html>";
			i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(emailIntro));
			i.putExtra(Intent.EXTRA_STREAM, attachUri);
			try {
				//startActivity(Intent.createChooser(i, "Send mail..."));
				startActivity(i);
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			} catch (Exception e){
				Log.e(TAG, "sendEmail() exception: "+e.toString());
			}
		} else {
			Log.e(TAG, "sendEmail() Error creating attachment");
		}
	}



	// routine to build HTML Summary of data
	private String buildHTMLsummary(){
		String htmlString; // string to hold the HTML Data
		String row;        // String to hold data for a row
		String disclaimer = "<p><i>Disclaimer</i>: the values shown are estimates only.<p>";

		htmlString = "<p>Below are the input parameters used for the calculations, and the resulting product estimates:</p>";

	    // Input Parameters
	    htmlString += "<br>";
	    htmlString += "<b>Cooling Water Input Parameters</b>";
	    htmlString += "<TABLE border=\"1\" style=\"border-collapse:collapse; border: 1px solid black;\">";
	    
	    // Site Section
	    htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Site</TR>";
	    row = "<TD colspan=\"3\">" + mCoolingModel.inSite + "</TD>" ;
	    htmlString += row;
	    
	    // Criteria Section
	    htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Criteria";
	    htmlString += "<TR style=\"background-color:#FFE0B2\"><TH>Parameter<TH>Value<TH>Units</TR>";
	    // rowFormat = "<TR><TD>" +  + "<TD>" +  + "<TD>" +  + "</TR>";
	    row = "<TR><TD>" +  "Circulation" + "<TD>" + mCoolingModel.inCirculation + "<TD>" + "m<sup>3</sup>/hr" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "ÆT" + "<TD>" + mCoolingModel.inDeltaT + "<TD>" + "¡C" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Sump Volume" + "<TD>" + mCoolingModel.inSumpVolume + "<TD>" + "m3" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "System Volume" + "<TD>" + mCoolingModel.inSysVolume + "<TD>" + "m3" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Concentration Factor" + "<TD>" + mCoolingModel.inConcFactor + "<TD>" + "" + "</TR>";
	    htmlString += row;
	    
	    // Make Up Water Quality Section
	    htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Make Up Water Quality";
	    htmlString += "<TR style=\"background-color:#FFE0B2\"><TH>Parameter<TH>Value<TH>Units</TR>";
	    //rowFormat = "<TR><TD>" +  + "<TD>" +  + "<TD>" +  + "</TR>";
	    row = "<TR><TD>" +  "TDS" + "<TD>" + mCoolingModel.inTDS + "<TD>" + "ppm" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Total Hardness" + "<TD>" + mCoolingModel.inTotalHardness + "<TD>" + "ppm" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "M Alk" + "<TD>" + mCoolingModel.inMAlk + "<TD>" + "ppm" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "pH" + "<TD>" + mCoolingModel.inPH + "<TD>" + "" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Chlorides" + "<TD>" + mCoolingModel.inChlorides + "<TD>" + "ppm" + "</TR>";
	    htmlString += row;
	    
	    // Duty/Operation Section
	    htmlString += "<TR><TH colspan=\"3\" style=\"background-color:#F0F8FF\">Duty/Operation";
	    htmlString += "<TR style=\"background-color:#FFE0B2\"><TH>Parameter<TH>Value<TH>Units</TR>";
	    //rowFormat = "<TR><TD>" +  + "<TD>" +  + "<TD>" +  + "</TR>";
	    row = "<TR><TD>" +  "Hours/Day" + "<TD>" + mCoolingModel.inHours + "<TD>" + "" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Days/Week" + "<TD>" + mCoolingModel.inDays + "<TD>" + "" + "</TR>";
	    htmlString += row;
	    row = "<TR><TD>" +  "Weeks/Year" + "<TD>" + mCoolingModel.inWeeks + "<TD>" + "" + "</TR>";
	    htmlString += row;
	    
	    htmlString += "</TABLE>";
	    
	    // Estimated Product amounts
	    htmlString += "<br>";
	    htmlString += "<b>Estimated Cooling Water Products Needed</b>";
	    htmlString += "<TABLE border=\"1\" style=\"border-collapse:collapse; border: 1px solid black;\">";
	    
	    // Inhibitor Products
	    htmlString += "<TR><TH colspan=\"6\" style=\"background-color:#F0F8FF\">Inhibitors";
	    htmlString += "<TR style=\"background-color:#FFE0B2\">";
	    //htmlString += "<TH>Product<TH>Dosage<BR>(g/m<sup>3</sup>)<TH>Usage<BR>(kg/yr)<TH>Cost<BR>/kg<TH>Cost<BR>/Annum<TH>Description</TR>";
	    htmlString += "<TH>Product<TH>Dosage<BR>(g/m<sup>3</sup>)<TH>Usage<BR>(kg/yr)<TH>Description</TR>";
	    
	    //rowFormat = "<TR><TD>" +  + "<TD>" +  + "<TD>" +  + "<TD>" +  + "<TD>" +  + "<TD>" +  + "</TR>";
	    
	    row = "<TR><TD>" + 
	           "WTPHS2097" + "<TD>" + 
	           round1(mCoolingModel.hs2097Dosage) + "<TD>" + 
	           round1(mCoolingModel.hs2097Usage) + "<TD>" + 
	           //round1(mCoolingModel.hs2097CostKg) + "<TD>" + 
	           //round1(mCoolingModel.hs2097CostAnnum) + "<TD>" + 
	           "All organic" + "</TR>";
	    htmlString += row;
	    
	    row = "<TR><TD>" + 
	           "WTPHS4390" + "<TD>" + 
	           round1(mCoolingModel.hs4390Dosage) + "<TD>" + 
	           round1(mCoolingModel.hs4390Usage) + "<TD>" + 
	           //round1(mCoolingModel.hs4390CostKg) + "<TD>" + 
	           //round1(mCoolingModel.hs4390CostAnnum) + "<TD>" + 
	           "Molybdate/Phosphonate" + "</TR>";
	    htmlString += row;
	    
	    row = "<TR><TD>" + 
	           "WTPHS3990" + "<TD>" + 
	           round1(mCoolingModel.hs3990Dosage) + "<TD>" + 
	           round1(mCoolingModel.hs3990Usage) + "<TD>" + 
	           //round1(mCoolingModel.hs3990CostKg) + "<TD>" + 
	           //round1(mCoolingModel.hs3990CostAnnum) + "<TD>" + 
	           "Silicate/Phosphonate" + "</TR>";
	    htmlString += row;
	    
	    // Biocide Products
	    htmlString += "<TR><TH colspan=\"6\" style=\"background-color:#F0F8FF\">Biocides";
	    htmlString += "<TR style=\"background-color:#FFE0B2\">";
	    //htmlString += "<TH>Product<TH>Dosage<BR>(g/m<sup>3</sup>)<TH>Usage<BR>(kg/yr)<TH>Cost<BR>/kg<TH>Cost<BR>/Annum<TH>Description</TR>";
	    htmlString += "<TH>Product<TH>Dosage<BR>(g/m<sup>3</sup>)<TH>Usage<BR>(kg/yr)<TH>Description</TR>";
	    
	    //rowFormat = "<TR><TD>" +  + "<TD>" +  + "<TD>" +  + "<TD>" +  + "<TD>" +  + "<TD>" +  + "</TR>";
	    
	    row = "<TR><TD>" + 
	           "WTPCS4400" + "<TD>" + 
	           round1(mCoolingModel.cs4400Dosage) + "<TD>" + 
	           round1(mCoolingModel.cs4400Usage) + "<TD>" + 
	           //round1(mCoolingModel.cs4400CostKg) + "<TD>" + 
	           //round1(mCoolingModel.cs4400CostAnnum) + "<TD>" + 
	           "Biocide + Dispersant" + "</TR>";
	    htmlString += row;
	    
	    row = "<TR><TD>" + 
	           "WTPCS4802" + "<TD>" + 
	           round1(mCoolingModel.cs4802Dosage) + "<TD>" + 
	           round1(mCoolingModel.cs4802Usage) + "<TD>" + 
	           //round1(mCoolingModel.cs4802CostKg) + "<TD>" + 
	           //round1(mCoolingModel.cs4802CostAnnum) + "<TD>" + 
	           "Bronopol" + "</TR>";
	    htmlString += row;
	    
	    row = "<TR><TD>" + 
	           "WTPCS4490" + "<TD>" + 
	           round1(mCoolingModel.cs4490Dosage) + "<TD>" + 
	           round1(mCoolingModel.cs4490Usage) + "<TD>" + 
	           //round1(mCoolingModel.cs4490CostKg) + "<TD>" + 
	           //round1(mCoolingModel.cs4490CostAnnum) + "<TD>" + 
	           "DBNPA" + "</TR>";
	    htmlString += row;
	    
	    row = "<TR><TD>" + 
	           "WTPSCG1" + "<TD>" + 
	           round1(mCoolingModel.scg1Dosage) + "<TD>" + 
	           round1(mCoolingModel.scg1Usage) + "<TD>" + 
	           //round1(mCoolingModel.scg1CostKg) + "<TD>" + 
	           //round1(mCoolingModel.scg1CostAnnum) + "<TD>" + 
	           "SDIC" + "</TR>";
	    htmlString += row;
	    
	    row = "<TR><TD>" + 
	           "WTPCSChlor" + "<TD>" + 
	           round1(mCoolingModel.cschlorDosage) + "<TD>" + 
	           round1(mCoolingModel.cschlorUsage) + "<TD>" + 
	           //round1(mCoolingModel.cschlorCostKg) + "<TD>" + 
	           //round1(mCoolingModel.cschlorCostAnnum) + "<TD>" + 
	           "Cal Hypo Tablets" + "</TR>";
	    htmlString += row;
	    
	    row = "<TR><TD>" + 
	           "WTPC42T" + "<TD>" + 
	           round1(mCoolingModel.c42tDosage) + "<TD>" + 
	           round1(mCoolingModel.c42tUsage) + "<TD>" + 
	           //round1(mCoolingModel.c42tCostKg) + "<TD>" + 
	           //round1(mCoolingModel.c42tCostAnnum) + "<TD>" + 
	           "Bromine tablets (brominator)" + "</TR>";
	    htmlString += row;
	    
	    //end table
	    htmlString += "</TABLE>";

		// Disclaimer
		htmlString += disclaimer ;
		htmlString += "<br>" ;
		htmlString += "</html>" ;
		return htmlString;
	}

	private Uri createAttachment(String contents) {
		Uri attachUri=null;

		String ATTACH_FILE = "coolingsolids";
		FileCache.init();
		String attachPath = FileCache.getFilePath(ATTACH_FILE);
		FileCache.writeTextFile(attachPath, contents);
		attachUri = Uri.fromFile(new File(attachPath));
		
		return attachUri;
	}
	
	// FILE MANAGEMENT UTILITIES
	
	
}

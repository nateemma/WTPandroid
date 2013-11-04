package com.nateemma.wtp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

// Class that implements the 'model' for boiler calculations
public class BoilerModel {

	private static final String TAG = "BoilerModel";

	// the actual (shared) instance
	private static BoilerModel _sharedInstance;

	// Application context
	private static Context mContext = null;

	// Site Name
	public String inSite; // this is the key for the data

	// String values provided by user
	public String inSumSteam;
	public String inSumHours;
	public String inSumDays;
	public String inSumWeeks;
	public String inWinSteam;
	public String inWinHours;
	public String inWinDays;
	public String inWinWeeks;
	public String inTDS;
	public String inMAlk;
	public String inPH;
	public String inCaHardness;
	public String inTemp;
	public String inMaxTDS;
	public String inMinSulphite;
	public String inMinCausticAlk;


	// Boiler Duty parameters
	public Double sumSteam;
	public Double sumHours;
	public Double sumDays;
	public Double sumWeeks;
	public Double winSteam;
	public Double winHours;
	public Double winDays;
	public Double winWeeks;

	// Feedwater params
	public Double TDS;
	public Double MAlk;
	public Double pH;
	public Double CaHardness;
	public Double temp;


	// Boiler Details params
	public Double maxTDS;
	public Double minSulphite;
	public Double minCausticAlk;

	public Double annualFeed;
	public Double dissolvedO2;

	// Calculated values
	public Double ss1295Dosage;
	public Double ss1295Usage;

	public Double ss1350Dosage;
	public Double ss1350Usage;

	public Double ss1095Dosage;
	public Double ss1095Usage;

	public Double ss1995Dosage;
	public Double ss1995Usage;

	public Double ss2295Dosage;
	public Double ss2295Usage;

	public Double ss8985Dosage;
	public Double ss8985Usage;




	// FRAMEWORK METHODS


	// default constructor
	private BoilerModel() { 
		// no implementation, just private to control usage, i.e. must call sharedInstance
	} 


	// prevent instantiation via an explicit clone() (or "=" operator) call
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	// gets the copy of the boiler model
	public static BoilerModel sharedInstance(){
		if (_sharedInstance == null) {
			_sharedInstance = new BoilerModel();
		}
		return _sharedInstance;
	}

	// need context to use system functions, so provide setter function
	public void setContext (Context context){
		mContext = context;
		/***
		if ((_sharedInstance.inSite==null) || (_sharedInstance.inSite.length()==0)) { // first time?
			_sharedInstance.restore(); // can't do this until Context has been set
		}
		***/
	}


	// check that all inputs have been defined and are valid
	public Boolean inputsValid(){
		return true; //TODO: add field checks
	}


	// SAVE/RESTORE METHODS

	// name of storage file for saved data
	private static final String PREFS_NAME = "BoilerModel";

	// save values to persistent storage
	public void save(){
		try{
			if (mContext != null){
				SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("inSite", _sharedInstance.inSite);
				editor.putString("inSumSteam", _sharedInstance.inSumSteam);
				editor.putString("inSumHours", _sharedInstance.inSumHours);
				editor.putString("inSumDays", _sharedInstance.inSumDays);
				editor.putString("inSumWeeks", _sharedInstance.inSumWeeks);
				editor.putString("inWinSteam", _sharedInstance.inWinSteam);
				editor.putString("inWinHours", _sharedInstance.inWinHours);
				editor.putString("inWinDays", _sharedInstance.inWinDays);
				editor.putString("inWinWeeks", _sharedInstance.inWinWeeks);
				editor.putString("inTDS", _sharedInstance.inTDS);
				editor.putString("inMAlk", _sharedInstance.inMAlk);
				editor.putString("inPH", _sharedInstance.inPH);
				editor.putString("inCaHardness", _sharedInstance.inCaHardness);
				editor.putString("inTemp", _sharedInstance.inTemp);
				editor.putString("inMaxTDS", _sharedInstance.inMaxTDS);
				editor.putString("inMinSulphite", _sharedInstance.inMinSulphite);
				editor.putString("inMinCausticAlk", _sharedInstance.inMinCausticAlk);

				// Commit the edits!
				editor.commit();
				Log.v(TAG, "save() inSite:"+_sharedInstance.inSite);
			} else {
				Log.e(TAG, "save() Context not defined. Preferences NOT saved");
			}
		} catch (Exception e){
			Log.e(TAG, "save() exception: "+e.toString());
		}
	} // save()


	// restore saved values
	public void restore(){
		try{
			if (mContext != null){
				SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);

				if (settings != null){
					_sharedInstance.inSite = settings.getString("inSite", "");
					if (_sharedInstance.inSite.length()>0){
						_sharedInstance.inSumSteam = settings.getString("inSumSteam", "");
						_sharedInstance.inSumHours = settings.getString("inSumHours", "");
						_sharedInstance.inSumDays = settings.getString("inSumDays", "");
						_sharedInstance.inSumWeeks = settings.getString("inSumWeeks", "");
						_sharedInstance.inWinSteam = settings.getString("inWinSteam", "");
						_sharedInstance.inWinHours = settings.getString("inWinHours", "");
						_sharedInstance.inWinDays = settings.getString("inWinDays", "");
						_sharedInstance.inWinWeeks = settings.getString("inWinWeeks", "");
						_sharedInstance.inTDS = settings.getString("inTDS", "");
						_sharedInstance.inMAlk = settings.getString("inMAlk", "");
						_sharedInstance.inPH = settings.getString("inPH", "");
						_sharedInstance.inCaHardness = settings.getString("inCaHardness", "");
						_sharedInstance.inTemp = settings.getString("inTemp", "");
						_sharedInstance.inMaxTDS = settings.getString("inMaxTDS", "");
						_sharedInstance.inMinSulphite = settings.getString("inMinSulphite", "");
						_sharedInstance.inMinCausticAlk = settings.getString("inMinCausticAlk", "");

						// convert strings and update binary versions of parameters
						_sharedInstance.sumSteam = Double.valueOf(_sharedInstance.inSumSteam);
						_sharedInstance.sumHours = Double.valueOf(_sharedInstance.inSumHours);
						_sharedInstance.sumDays = Double.valueOf(_sharedInstance.inSumDays);
						_sharedInstance.sumWeeks = Double.valueOf(_sharedInstance.inSumWeeks);
						_sharedInstance.winSteam = Double.valueOf(_sharedInstance.inWinSteam);
						_sharedInstance.winHours = Double.valueOf(_sharedInstance.inWinHours);
						_sharedInstance.winDays = Double.valueOf(_sharedInstance.inWinDays);
						_sharedInstance.winWeeks = Double.valueOf(_sharedInstance.inWinWeeks);
						_sharedInstance.TDS = Double.valueOf(_sharedInstance.inTDS);
						_sharedInstance.MAlk = Double.valueOf(_sharedInstance.inMAlk);
						_sharedInstance.pH = Double.valueOf(_sharedInstance.inPH);
						_sharedInstance.CaHardness = Double.valueOf(_sharedInstance.inCaHardness);
						_sharedInstance.temp = Double.valueOf(_sharedInstance.inTemp);
						_sharedInstance.maxTDS = Double.valueOf(_sharedInstance.inMaxTDS);
						_sharedInstance.minSulphite = Double.valueOf(_sharedInstance.inMinSulphite);
						_sharedInstance.minCausticAlk = Double.valueOf(_sharedInstance.inMinCausticAlk);
					} else {
						Log.d(TAG, "restore(), no data found");
					}

				} else {
					Log.e(TAG, "restore() Settings file not found!");
				}

			} else {
				Log.e(TAG, "restore() Context not defined. Preferences NOT fully restored");
			}
		} catch (Exception e){
			Log.e(TAG, "restore() exception: "+e.toString());
		}

	}//restore()


	// check whether any saved data is available
	public Boolean savedDataPresent(){
		Boolean result = false;
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
		if (settings != null){
			String inSite = settings.getString("inSite", "");
			if ((inSite!=null) && inSite.length()>0){
				result = true;
			}
		}
		return result;
	}


	// FUNCTIONAL LOGIC

	// calculate the product amounts, based on input values
	// Note: expects inputs to have already been verified


	// lookup data for dissolved O2
	static Double lookupData [] = { 6.8, 6.2, 5.7, 5.2, 4.7, 4.25, 3.8, 3.4, 2.8, 2.2, 1.6, 0.7 };

	public void calculateAmounts(){
		Double tmp;

		try {
			// Annual Feed
			tmp = ((_sharedInstance.sumSteam / 2200.00) * _sharedInstance.sumHours * _sharedInstance.sumDays * _sharedInstance.sumWeeks) +
					((_sharedInstance.winSteam / 2200.00) * _sharedInstance.winHours * _sharedInstance.winDays * _sharedInstance.winWeeks) ;

			_sharedInstance.annualFeed = tmp;

			// Dissolved Oxygen
			int idx = (int) (_sharedInstance.temp - 40.0) / 5;
			int maxIdx = lookupData.length - 1;
			if ((idx<0) || (idx>maxIdx)){
				Log.d(TAG, " Oops, temp index out of range!!! temp:" + _sharedInstance.temp + " index:" + idx);
				if (idx > maxIdx){
					tmp = lookupData[maxIdx];
					_sharedInstance.dissolvedO2 = tmp;
				} else {
					_sharedInstance.dissolvedO2 = 0.0;
				}
			} else {
				tmp = lookupData[idx];
				_sharedInstance.dissolvedO2 = tmp;
			}
			Log.d(TAG, " temp:" + _sharedInstance.temp + " idx:" + idx + " O2:" + _sharedInstance.dissolvedO2 + " (maxIdx:" + maxIdx + ")");

			// Product Amounts

			// SS1295
			tmp = (_sharedInstance.dissolvedO2 * 10.0) + (35.0 / (_sharedInstance.maxTDS / _sharedInstance.TDS));
			_sharedInstance.ss1295Dosage = tmp;

			tmp = _sharedInstance.ss1295Dosage * _sharedInstance.annualFeed / 1000.0 ;
			_sharedInstance.ss1295Usage = tmp;

			// SS1350
			tmp = (_sharedInstance.dissolvedO2 * 10.0) + (35.0 / (_sharedInstance.maxTDS / _sharedInstance.TDS));
			_sharedInstance.ss1350Dosage = tmp;

			tmp = _sharedInstance.ss1350Dosage * _sharedInstance.annualFeed / 1000.0 ;
			_sharedInstance.ss1350Usage = tmp;

			// SS1095
			tmp = (_sharedInstance.dissolvedO2 * 9.0) + (28.0 / (_sharedInstance.maxTDS / _sharedInstance.TDS));
			_sharedInstance.ss1095Dosage = tmp;

			tmp = _sharedInstance.ss1095Dosage * _sharedInstance.annualFeed / 1000.0 ;
			_sharedInstance.ss1095Usage = tmp;

			// SS1995
			tmp = (_sharedInstance.CaHardness - _sharedInstance.MAlk) + ((1000.0 - _sharedInstance.minCausticAlk) / (_sharedInstance.maxTDS / _sharedInstance.TDS));

			// -ve number means no need for product, so reset to 0.0
			if (tmp < 0.0){
				_sharedInstance.ss1995Dosage = 0.0;
				_sharedInstance.ss1995Usage = 0.0;
			} else {
				_sharedInstance.ss1995Dosage = tmp;

				tmp = _sharedInstance.ss1995Dosage * _sharedInstance.annualFeed / 1000.0 ;
				_sharedInstance.ss1995Usage = tmp;
			}

			// SS2295
			tmp = (45.0 / (_sharedInstance.maxTDS / _sharedInstance.TDS)) + (1.11 * _sharedInstance.CaHardness);
			_sharedInstance.ss2295Dosage = tmp;

			tmp = _sharedInstance.ss2295Dosage * _sharedInstance.annualFeed / 1000.0 ;
			_sharedInstance.ss2295Usage = tmp;

			// SS8985
			tmp = (_sharedInstance.MAlk / 3.0);
			_sharedInstance.ss8985Dosage = tmp;

			tmp = _sharedInstance.ss8985Dosage * _sharedInstance.annualFeed / 1000.0 ;
			_sharedInstance.ss8985Usage = tmp;
		} catch (Exception e) {
			Log.e(TAG, "calculateAmounts() - exception: " + e.toString());
		}

	}

}

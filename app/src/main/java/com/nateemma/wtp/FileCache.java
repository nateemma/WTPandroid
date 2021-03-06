package com.nateemma.wtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

// Helper class for managing attachments
public class FileCache {


	private static final String TAG         = "AttachmentBuilder";
	private static final String ATTACH_DIR  = "/sdcard/WTP/";
	private static final String ATTACH_EXT  = ".html";
	private static boolean      mCacheReady = false;


	/////////////////////////////////////////////////////////////////////////
	// General purpose methods for use by all derived classes
	/////////////////////////////////////////////////////////////////////////

	// These variables are intended to be overwritten by subclasses
	protected static String mCacheDir = ATTACH_DIR;
	protected static String mTag = TAG;

	protected static void setTag(String tag){
		mTag = tag;
	}

	protected static void setCacheDir(String dir){
		mCacheDir = dir;
	}


	// initialise cache.
	// Derived classes should check directories and create if necessary
	protected static void init(){

		mCacheReady = false;

		try {
			// check that storage is accessible
			if (checkStorage()){
				// make sure directory exists
				File lclpath = new File(ATTACH_DIR);

				// Make sure the  directory exists.
				lclpath.mkdirs();
				if (lclpath.exists()){
					mCacheReady = true;
					Log.d(TAG, "Profile cache directory set up: "+ATTACH_DIR);
				} else {
					Log.e(TAG, "Unkown error, cache not set up");
				}

			} else {
				Log.e(TAG, "*** External Storage not available, cannot save profiles!!! ***");
			}
		} catch (Exception e) {
			Log.e(TAG, "Error setting up profile cache ("+ATTACH_DIR+"): "+e.toString());
		}
	}


	// update this to change the log tag
	protected  static String getLogTag() {
		return mTag;
	}

	// Get the directory location
	protected static String getPath(){
		return mCacheDir;
	}

	// get the actual path of the file (it does not have to exist, so you can use it in creating the files)
	public static String getFilePath (String name){
		return 	ATTACH_DIR + getFilename(name) + ATTACH_EXT ;
	}


	// override this with result of whether cache is ready or not. 
	// Or, just set mCacheReady

	// Check to see if cache is ready
	protected static boolean cacheReady(){
		// If not ready, try and initialise
		if (!mCacheReady) init();

		return mCacheReady;
	}


	/////////////////////////////////////////////////////////////////////////
	// Generic file management stuff that can be used directly
	/////////////////////////////////////////////////////////////////////////


	// Check to see if cache is ready
	public static boolean isReady(){
		// If not ready, try and initialise
		if (!cacheReady()) init();
		return cacheReady();
	}


	// return the filename from the full path name
	public static String getFilename (String path){
		String fname = path;
		fname=path.replaceAll("\\\\", "/");
		if (path.contains("/")){
			fname = fname.substring(fname.lastIndexOf("/")+1);
		} 
		return fname;
	}


	// check to see if named file is present
	public static boolean isFilePresent(String path){
		boolean present = false;
		if (cacheReady()){
			File f = new File (path);
			if (f.exists()){
				present = true;
			}
		} else {
			Log.e(getLogTag(), "isFilePresent() - Cache not set up");
		}
		return present;		
	}



	// remove file
	public static void removeFile (String path){
		// Remove the file
		File f = new File (path);
		if (f.exists()){
			f.delete();
		} else {
			Log.e(getLogTag(), "removeFile() file does not exist: "+path);
		}
	}



	// move file
	public static void moveFile (String oldpath, String newpath){
		// Remove the file
		File f = new File (oldpath);
		if (f.exists()){
			f.renameTo(new File(newpath));
		} else {
			Log.e(getLogTag(), "moveFile() file does not exist: "+oldpath);
		}
	}



	// copy file
	public static void copyFile (String srcpath, String destpath){
		try {
			InputStream  in  = new FileInputStream(srcpath);
			OutputStream out = new FileOutputStream(destpath);  

			byte[] buf = new byte[1024];  
			int len;  
			while ((len = in.read(buf)) > 0) {  
				out.write(buf, 0, len);  
			}  
			in.close();  
			out.close(); 

		} catch (Exception e){
			Log.e(TAG, "copyFile() error: "+e.toString());
		}
	}



	// read a single string from the named file
	public static String readTextFile(String path){
		String text = "";
		if (cacheReady()){
			File f = new File (path);
			if (f.exists()){
				try {
					FileReader file = new FileReader (f);
					BufferedReader buf = new BufferedReader(file);
					text = buf.readLine();
					buf.close();
					//Log.d(getLogTag(), "readTextFile: "+path+"("+text+")");
				} catch (Exception e) {
					Log.e(getLogTag(), "readTextFile() Error reading file: "+f.getPath());
				}
			} else {
				Log.e(getLogTag(), "readTextFile() file does not exist: "+path);
			}
		} else {
			Log.e(getLogTag(), "readTextFile() - Cache not set up");
		}
		return text;
	}


	// add a single string to the named file
	public static void appendTextFile (String path, String text){
		if (cacheReady()){
			if ((text!=null) && (text.length()>0)){
				try {
					//Log.d(getLogTag(), "writeTextFile: "+path+"("+text+")");
					FileWriter pfile = new FileWriter (path);
					pfile.write(text);
					pfile.close();
				} catch (Exception e) {
					Log.e(getLogTag(), "writeTextFile() Error writing to file: "+path);
					Log.e(getLogTag(), e.toString());
				}
			} else {
				Log.e(getLogTag(), "writeTextFile() invalid text supplied: "+text);
			}
		} else {
			Log.e(getLogTag(), "writeTextFile() - Cache not set up");
		}	
	}


	// write a single string to the named file
	// If the file exists, it is overwritten
	public static void writeTextFile (String path, String text){
		if (cacheReady()){
			if ((text!=null) && (text.length()>0)){
				try {

					// If file exists, delete it
					File f = new File (path);
					if (f.exists()){
						f.delete();
					}

					// create file (FileWriter apparently doesn't do this)
					f.createNewFile();

					//Log.d(getLogTag(), "writeTextFile: "+path+"("+text+")");
					FileWriter pfile = new FileWriter (path);
					pfile.write(text);
					pfile.close();
				} catch (Exception e) {
					Log.e(getLogTag(), "writeTextFile() Error writing to file: "+path);
					Log.e(getLogTag(), e.toString());
				}
			} else {
				Log.e(getLogTag(), "writeTextFile() invalid text supplied: "+text);
			}
		} else {
			Log.e(getLogTag(), "writeTextFile() - Cache not set up");
		}	
	}


	// write byte array to file
	public static void writeBinaryFile (String path, byte[] bdata){
		if (cacheReady()){
			if ((path!=null) && (path.length()>0)){
				try {
					// If file exists, delete it
					File f = new File (path);
					if (f.exists()){
						f.delete();
					}

					FileOutputStream pfile = new FileOutputStream (path);
					pfile.write(bdata);
					pfile.close();
				} catch (Exception e) {
					Log.e(getLogTag(), "writeBinaryFile() Error saving to file");
				}
			} else {
				Log.e(getLogTag(), "writeBinaryFile() invalid name supplied: "+path);
			}
		} else {
			Log.e(getLogTag(), "writeBinaryFile() - Cache not set up");
		}
	}

	// read byte array from file
	public static byte[] readBinaryFile (String path){
		byte[] bdata=new byte[0];
		if (cacheReady()){
			File f = new File (path);
			if (f.exists()){
				try {
					FileInputStream fis = new FileInputStream(f.getPath());
					int count = fis.read(bdata);
					fis.close();
				} catch (Exception e) {
					Log.e(getLogTag(), "readBinaryFile() Error reading file: "+f.getPath());
				}
			} else {
				Log.e(getLogTag(), "readBinaryFile() Thumbnail does not exist ("+path+")");
			}
		} else {
			Log.e(getLogTag(), "readBinaryFile() - Cache not set up");
		}
		return bdata;
	}


	// check that external storage is mounted and writeable
	public static boolean checkStorage(){
		boolean ExternalStorageAvailable = false;
		boolean ExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		// check that external storage is accessible
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			ExternalStorageAvailable = ExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			ExternalStorageAvailable = true;
			ExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but all we need
			//  to know is we can neither read nor write
			ExternalStorageAvailable = ExternalStorageWriteable = false;
		}

		return (ExternalStorageAvailable && ExternalStorageWriteable);
	}


	// Get the timestamp of the file
	public static long getFileTimestamp (String path){
		long ts=0;
		File f = new File(path);
		if (f.exists()){
			try {
				ts = f.lastModified();
			} catch(Exception e){
				Log.e(getLogTag(), "Error getting timestamp for: "+path+". "+e.toString());
			}
		}
		return ts;
	}


	// Check to see if file is older than 'minutes' minutes
	// If file does not exist, or there a problem, then this will return true
	// (The assumption is that returning true causes an action, so default to that)
	public static boolean isFileOlderThan(String path, int minutes){
		boolean older=true;
		long ts = getFileTimestamp(path);
		if (ts>0){
			// Get current time, figure out difference and check
			long interval = ((new Date()).getTime() - ts) / (60 * 1000);
			if (interval < minutes)
				older = false;
		}
		return older;
	}
}

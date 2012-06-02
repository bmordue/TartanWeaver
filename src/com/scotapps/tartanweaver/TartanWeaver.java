package com.scotapps.tartanweaver;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

//TODO: implement equals() and hashcode() methods
//TODO: implement toString() method
//TODO: Fill Javadoc comments correctly
//TODO: Validation of sett string (using regex?), graceful fail if not a valid sett.

//Contains a full description of the tartan, and methods for manipulating and drawing it.
public class TartanWeaver extends java.lang.Object {

	private static final String TAG = "TartanWeaver";

	// TODO: expand palette to include light and dark variations (LR, DR, LG, DG
	// and so on)
	// TODO: move palette into a resource file (eg XML)
	//TODO : move dictionary loader into a separate private class

	// The palette of colours we can recognize
	// Replaced by createColourDictionary()
	/*
	 * private static final String[] colour_names = { "Red", "Green", "Blue",
	 * "Black", "Brown", "Grey" }; private static final String[] colour_codes =
	 * { "R", "G", "B", "K", "T", "N" }; private static final int[]
	 * colour_palette = { Color.RED, Color.GREEN, Color.BLUE, Color.BLACK,
	 * 0xffffb5b5, 0xffb5b5b5 };
	 */
	// map codes ("R", "G" etc) against RGB integer representations (ff0000,
	// 00ff00 etc)
	private HashMap<String, Integer> mColourValues;
	// map codes ("R", "G" etc) against colour names ("Red", "Green")
	private HashMap<String, String> mColourCodes;

	// The sett, in String form, ie a sequence of colours and thread counts
	private String mSettString;
	private ArrayList<Integer> mSettColours;

	// width of a notional thread, in dip (device independent pixels)
	// default to 2 dip
	private short mThreadWidth = 1;

	private Drawable mTartanDrawable;
	

	public TartanWeaver() {
		super();
//		createColourDictionary();
	}

	/**
	 * 
	 */
	public TartanWeaver(String theSett) {
		super();
		mSettString = theSett;
//		createColourDictionary();
	}

	//TESTING ONLY
/*	private void createColourDictionary() {

		mColourValues = new HashMap<String, Integer>();

		mColourValues.put("R", Color.RED);
		mColourValues.put("G", Color.GREEN);
		mColourValues.put("B", Color.BLUE);
		mColourValues.put("K", Color.BLACK);
		mColourValues.put("T", new Integer(0xffffb5b5));
		mColourValues.put("N", new Integer(0xffb5b5b5));
		mColourValues.put("DG", new Integer(0xff005500));
		

		mColourCodes = new HashMap<String, String>();

		mColourCodes.put("R", "Red");
		mColourCodes.put("G", "Green");
		mColourCodes.put("B", "Blue");
		mColourCodes.put("K", "Black");
		mColourCodes.put("T", "Tan");
		mColourCodes.put("N", "Grey");
		mColourCodes.put("DG", "Dark Green");

	}
*/
	public void setSett(String theSett) {
		mSettString = theSett;
	}

	public void setThreadWidth(short threadWidth) {
		mThreadWidth = threadWidth;
	}

	public void setColourDictionary(HashMap<String, Integer> colourValues, 
			HashMap<String,String> colourCodes) {
		mColourValues = colourValues;
		mColourCodes = colourCodes;
	}
	
	
	// Parse the sett in its String form to create a list of colours.
	// TODO: should throw an exception if string couldn't be correctly parsed
	// TODO: move parsing into a separate utility class?
	public void parseSettString() {
		mSettColours = new ArrayList<Integer>();

		// Once
		TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(
				' ');

		// Once per string to split
		splitter.setString(mSettString);
		for (String s : splitter) {
			parseSettToken(s);
		}

	}

	// expect a token like "R24" or "DG/6"
	// add to mSettColours
	// TODO: add exception handling, more protection, etc etc
	private void parseSettToken(String token) {
		boolean isPivot = false;

		// there is a problem -- so just don't add anything to mSettColours
		if (token == null) {
			return;
		}

		String[] atoms = token.split("[0-9]");
		String letters = atoms[0];

		String number = token.substring(letters.length());

		if (letters.endsWith("/")) {
			// pivot!
	//		Log.d(TAG, "parseSettToken: colour " + letters + " -- pivot!");
			isPivot=true;

			//chop off '/'
			letters = letters.substring(0,letters.length()-1);
		}


	//	Log.d(TAG, "parseSettToken, number: " + number + ", letters: " + letters);

		// look up the colour code in the dictionary
		Integer theColour = mColourValues.get(letters);
		
		//TODO: Implement better error handling if this call returns null
		//ie, if letters does not refer to a code in the mColourValues dictionary
		//should throw some kind of ParseErrorException...
		if (theColour == null) {
			theColour = new Integer (Color.BLACK);
		}
		
		// convert the parsed string representing the number of threads to an
		// int
		int threads = 0;
		try {
			threads = Integer.valueOf(number);
		} catch (Exception e) {
			// TODO: deal with this problem
			Log.e(TAG, e.toString());
		}

	//	Log.d(TAG,"Code: " + letters + ", threads: " + threads + ", colour: " + theColour);

		//If the thread count indicates that this is a pivot point in the sett,
		//get the mirror image of the existing sett by calling pivotSett
		ArrayList<Integer> mirroredSett = new ArrayList<Integer>();
		if (isPivot) {
			mirroredSett = pivotSett();
		}
		
		//Add the coloured threads indicated by the thread count to the list of colours
		for (int i = 0; i < threads; i++) {
			mSettColours.add(theColour);
		}
		
		//If the thread count indicates that this is a pivot point in the sett,
		//add the mirrored image to the existing sett to complete the sequence
		if (isPivot) {
			mSettColours.addAll(mirroredSett);
		}

	}
	
	
	//A 'pivot' in the sett indicates that the pattern is mirrored
	//Copy what is already in the sett, reverse it, add to existing sett
	private ArrayList<Integer> pivotSett() {
		ArrayList<Integer> tempSett = new ArrayList<Integer>();
		
		int settLength = mSettColours.size();
		for (int i=0; i<settLength; i++) {
			tempSett.add(mSettColours.get(settLength - i - 1)); 
		}
		
		return tempSett;
			
	}

	// TESTING ONLY
	public void testing_setupDummyTartan() {
		// colours of each thread in sequence
		// this hardcoded sequence represents "R/12 DG6 B/18"
		// mSettColours = {};
		// Colour the red threads

		mSettColours = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {
			mSettColours.add(0xff770000);
		}

		for (int i = 0; i < 6; i++) {
			mSettColours.add(0xff003500);
		}

		for (int i = 0; i < 18; i++) {
			mSettColours.add(0xff000077);
		}

		for (int i = 0; i < 6; i++) {
			mSettColours.add(0xff003500);
		}

		for (int i = 0; i < 12; i++) {
			mSettColours.add(0xff770000);
		}

	}

	// TODO: make this asynchronous or threaded or something -- can take a while.
	// Do the actual work here
	public void renderTartan() {
		// TODO: replace with parsed colours sequence, remove hardcoded sequence
		// TODO: replace hardcoded pattern with proper logic!

		//TODO: replace "if x==null" error checking with exception throwing
		if (mSettColours == null) {
			Log.e(TAG, "renderTartan(): mSettColours is null.");
			return;
		}
		
		//TODO: replace "if x==null" error checking with exception throwing
		if (mColourValues == null) {
			Log.e(TAG, "renderTartan(): mColourValues is null.");
			return;
		}

		//TODO: replace "if x==null" error checking with exception throwing
		if (mColourCodes == null) {
			Log.e(TAG, "renderTartan(): mColourCodes is null.");
			return;
		}
		
		
		int dimension = mSettColours.size();
	
		//Null string in mSettString will lead to empty mSettColours, ie size 0
		// calling return here prevent runtime NPE
		//TODO: better handling of this error case
		if (dimension == 0) {
			return;
		}
		

		// draw in the weave (horizontal threads) and then the warp (vertical
		// threads)
		// basic approach in each direction: draw two, skip two.

		// are we drawing a warp thread?
		// Effectively a boolean, but must use int for arithmetic
		int warp = 0;
		int pixels[] = new int[dimension * dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				// weave
				warp = ((i + j) / 2) % 2;

				if (warp != 0) {
					pixels[i + j * dimension] = mSettColours.get(i);
				} else {
					pixels[i + j * dimension] = mSettColours.get(j);
				}
			}
		}

		
		Bitmap bm = Bitmap.createBitmap(pixels, dimension, dimension,
				Bitmap.Config.ARGB_8888);

		
		// scale it up x2 in height and width
		// false: no filtering
		bm = Bitmap.createScaledBitmap(bm, dimension * mThreadWidth, dimension
				* mThreadWidth, false);
		
		// deprecated since API 5 -- see docs for BitmapDrawable constructors
		mTartanDrawable = new BitmapDrawable(bm);
	}

	/**
	 * @return the mTartanDrawable
	 */
	public Drawable getTartanDrawable() {

		return mTartanDrawable;
	}

	public String getSett() {
		// TODO Auto-generated method stub
		return mSettString;
	}
	

}

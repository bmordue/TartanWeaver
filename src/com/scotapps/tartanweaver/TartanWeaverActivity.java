package com.scotapps.tartanweaver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adwhirl.AdWhirlLayout.AdWhirlInterface;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

//TODO: include extra AdWhirl networks (MobFox, others?)
//TODO: include Google Analytics  

public class TartanWeaverActivity extends Activity implements AdWhirlInterface {

	private EditText mEditText;
	private TartanWeaver mWeaver;
	private ImageView mTartanImage;
	
	private static final String GoogleAnalyticsAppId = "UA-20816495-3";


	private final static String TAG = "TartanWeaverActiviy";

	private GoogleAnalyticsTracker tracker;

	private final static int ABOUT_DIALOG_ID = 0;

	static final String[] COUNTRIES = new String[] {
	    "Afghanistan", "Albania", "Algeria", "American Samoa"};
	
	private RelativeLayout mAnchorLayout;
	protected Context mContext;
	
	private int bottomWidgetId;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mEditText = (EditText) findViewById(R.id.threadcount_edit);

		mWeaver = new TartanWeaver();

		mTartanImage = (ImageView) findViewById(R.id.tartan_image);
		
		mContext = getBaseContext();

		tracker = GoogleAnalyticsTracker.getInstance();
		// Google Analytics tracker can be started with a dispatch interval (in
		// seconds).
		tracker.start(GoogleAnalyticsAppId, 20, this);

		// Get colour palette information from the res/raw/colours.xml file
		// then pass it to the TartanWeaver
		// Must be called before updateTartan()
		loadColourDictionary();
		updateTartan();

		bottomWidgetId = mEditText.getId();
		mAnchorLayout = (RelativeLayout) findViewById(R.id.scrollcontainer);
		buildSlidingDrawer();
		

		registerForContextMenu(mTartanImage);
		
		// set up ads using AdWhirl
//		AdWhirlManager.setConfigExpireTimeout(1000 * 60 * 5);

		// TODO: use AdWhirl targeting
		/*
		 * AdWhirlTargeting.setAge(23);
		 * AdWhirlTargeting.setGender(AdWhirlTargeting.Gender.MALE);
		 * AdWhirlTargeting.setKeywords("online games gaming");
		 * AdWhirlTargeting.setPostalCode("94123");
		 */
		// TODO: set test mode to false
	//	AdWhirlTargeting.setTestMode(true);

	//	AdWhirlLayout adWhirlLayout = (AdWhirlLayout) findViewById(R.id.adwhirl_layout);
	//	adWhirlLayout.setAdWhirlInterface(this);

	}

	private void buildSlidingDrawer() {
	
		Button button = (Button) findViewById(R.id.draw_button);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				updateTartan();

				// GA click event
				tracker.trackEvent("Clicks", // Category
						"Button", // Action
						"Draw button", // Label
						1); // Value
				
			}
		});
		
/*
		mListView = (ListView) findViewById(R.id.controlslist);
		 
		ArrayList<String> lst = new ArrayList<String>();
		lst.addAll(Arrays.asList(COUNTRIES));
		mListAdapter = new ArrayAdapter <String>
						(this, R.layout.threadwidget, R.id.dummytext, lst);
		mListView.setAdapter(mListAdapter);

		  mListView.setTextFilterEnabled(true);
*/
	/*	  list.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		      // When clicked, show a toast with the TextView text
		      Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
		          Toast.LENGTH_SHORT).show();
		    }
		  });
	*/

			Button addButton = (Button) findViewById(R.id.expand_sett);
			addButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
		//			mListAdapter.add("dummy");

					if (mAnchorLayout == null) {
						Log.d(TAG, "anchorView is null.");
						return;
					}

					TartanThreadWidget widget = new TartanThreadWidget(mContext, null);
					
					try {
			//		RelativeLayout.LayoutParams layoutParams = (LayoutParams) widget.getLayoutParams();
						RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(320, 48);
						
					if (layoutParams == null) {
						Log.d(TAG, "layoutParams is null.");
					} else {
						layoutParams.addRule(RelativeLayout.BELOW, bottomWidgetId);
					}
					//bit of a fudge to prevent Id collisions -- increase by 100
					bottomWidgetId = bottomWidgetId + 100;
					widget.setId(bottomWidgetId);
					mAnchorLayout.addView(widget, layoutParams);
					
					
					} catch (NullPointerException e) {
						Log.e(TAG, e.toString());
					}
					
			/*		TextView tv = new TextView (mContext);
					tv.setText("Testing add button");
					RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(50, 20);
					lay.addRule(RelativeLayout.BELOW, R.id.threadcount_edit);
					mAnchorLayout.addView(tv, lay);*/
				}
			});

/*		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// inflate our view from the corresponding XML file
		View layout = inflater.inflate(R.layout.coloursgrid,
				(ViewGroup) findViewById(R.id.coloursgridview));
		// create a 100px width and 200px height popup window
		PopupWindow pw = new PopupWindow(layout, 100, 200, true);
		// finally show the popup in the center of the window
		pw.showAsDropDown(mTartanImage, -100, -100);
*/
		  
		//replaced by compound TartanThreadWidget

		//set up colour spinner
/*	    Spinner spinner = (Spinner) findViewById(R.id.spinner);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.colours_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	*/



		
	}

	// get colour palette information from the res/raw/colours.xml file
	// then pass it to the TartanWeaver
	private void loadColourDictionary() {
		HashMap<String, Integer> colourValues = new HashMap<String, Integer>();
		HashMap<String, String> colourCodes = new HashMap<String, String>();

		ColourDictionaryLoader loader = new ColourDictionaryLoader();
		loader.loadColourDictionary(this, R.raw.colours);

		colourValues = loader.getColourValues();
		colourCodes = loader.getColourCodes();

		// um, is there any need to check isFinished()?
		// in any case, there's a problem if this isn't true
		if (loader.isFinished()) {
			mWeaver.setColourDictionary(colourValues, colourCodes);
		}
	}

	public void updateTartan() {
		mWeaver.setSett(mEditText.getText().toString());
		mWeaver.parseSettString();

		// TESTING ONLY -- remove as soon as parseSettString() functions
		// correctly
		// mWeaver.testing_setupDummyTartan();

		mWeaver.renderTartan();

		try {
			BitmapDrawable bm = (BitmapDrawable) mWeaver.getTartanDrawable();

			bm.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);

			// mTartanImage.setImageDrawable(bm);
			mTartanImage.setBackgroundDrawable(bm);
			mTartanImage.setScaleType(ImageView.ScaleType.FIT_XY);

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adwhirl.AdWhirlLayout.AdWhirlInterface#adWhirlGeneric()
	 */
	@Override
	public void adWhirlGeneric() {
		// TODO Auto-generated method stub
		// No documentation on what this method should do!!
//		Log.d(TAG, "adWhirlGeneric()");

	}

	// Open a file for writing
	// Save to file
	// TODO: avoid over-writing existing saved image
	//
	// Note: should not use a "Save file" dialog -- user should not see filesystem
	// cf http://code.google.com/p/android/issues/detail?id=2406
	void saveBitmapToFile() {

		Toast.makeText(this, R.string.saving, Toast.LENGTH_SHORT).show();


		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		//Storage is not available, so return.
		if (!mExternalStorageAvailable || !mExternalStorageWriteable) {
			Toast.makeText(this, R.string.image_not_saved, Toast.LENGTH_SHORT).show();
			return;
		}

		FileOutputStream outStream = null;

		File path = Environment.getExternalStorageDirectory();
		String dir = this.getResources().getString(R.string.save_directory);
		String filename = this.getResources().getString(R.string.save_filename);
		
		//Add the tartan's sett to the filename to make it unique
		//Strip out spaces and backslash first for neatness
		String sett = mWeaver.getSett();
		sett = sett.replaceAll(" ", "");
		sett = sett.replaceAll("/", "");

		filename = filename.concat("-" + sett);
		
//		Log.d(TAG, "Filename: " + filename);
		
		File savePath = new File(path, dir + "/");
		if (!savePath.exists()) {
			savePath.mkdir();
		}
	
		// Open file in which to save tartan image
		try {
			outStream = new FileOutputStream(savePath.getPath() + "/" + filename + ".png");
		} catch (FileNotFoundException e) {
			Log.e(TAG, e.toString());
		}

		BufferedOutputStream bos = new BufferedOutputStream(outStream);

		Bitmap largeBitmap = getTiledBitmap();
		
		// quality=0, ignored for lossless PNG
		largeBitmap.compress(CompressFormat.PNG, 0, bos);

		try {
			bos.flush();
			bos.close();
			Toast.makeText(this, R.string.image_saved,
					Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Log.e(TAG, e.toString());
			Toast.makeText(this, R.string.image_not_saved, Toast.LENGTH_SHORT).show();

		}
		
	}

	Bitmap getTiledBitmap () {

		Context context = this.getBaseContext();

		BitmapDrawable bmDrawable = (BitmapDrawable) mWeaver.getTartanDrawable();

		int height = context.getWallpaperDesiredMinimumHeight();
		int width = context.getWallpaperDesiredMinimumHeight();
		
//		Log.d(TAG, "Wallpaper size:" + width + " x " + height);
		
		Bitmap smallBitmap = bmDrawable.getBitmap();
		int patternSize = smallBitmap.getHeight();
		
		Bitmap largeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		
		Canvas myCanvas = new Canvas(largeBitmap);
		
		Paint dummyPaint = new Paint();
		Matrix identity=new Matrix(); 
		for (int i=0; i<width-patternSize; i++) {
		  for (int j=0; j<height-patternSize; j++) {
			  identity.setTranslate(patternSize * i, patternSize * j);
			  myCanvas.drawBitmap (smallBitmap, identity, dummyPaint);
		  }
		}

		return largeBitmap;
	}
	
	// Set the wallpaper directly
	// Requires the SET_WALLPAPER permission in Android manifest
	// doesn't tile the bitmap, so not much good.
	public void setAsWallpaper() {
		Context context = this.getBaseContext();

		Bitmap wallpaper = getTiledBitmap();
		try {
			context.setWallpaper(wallpaper);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.options_menu, menu);

		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.about:
			showDialog(ABOUT_DIALOG_ID);
		}

		//event has been 'consumed'
		return true;

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		//TODO: build a nicer "about" dialog
		case ABOUT_DIALOG_ID:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.dialog_about);
			builder.setMessage(R.string.about_message);
			builder.setCancelable(true);
			
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.dismiss();
		           }
		       });

			return builder.create();
		}
	return null;
	}

	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Stop the tracker when it is no longer needed.
		tracker.stop();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		  switch (item.getItemId()) {
		  case R.id.save_file:
		    saveBitmapToFile();
		    return true;
		  case R.id.set_wallpaper:
		    setAsWallpaper();
		    return true;
		  case R.id.share_tartan:
//			  shareTartan();
			  return true;
		  default:
		    return super.onContextItemSelected(item);
		  }
	}
	
	//use an intent to share the tartan bitmap
	void shareTartan() {
		Bitmap theBitmap = getTiledBitmap();
		
		Intent intent = new Intent();
	    intent.setAction(Intent.ACTION_SEND);
	    intent.setType("image/bmp");
	    Parcel[] parcels = new Parcel[1];
	    theBitmap.writeToParcel(parcels[0], 0);
	    intent.putExtra(Intent.EXTRA_STREAM, parcels);
	    try {
	        startActivity(Intent.createChooser(intent, getText(R.string.share_chooser_label) ));
	    } catch (android.content.ActivityNotFoundException ex) {
	    	Log.e(TAG, ex.toString());
	    }

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
		  MenuInflater inflater = getMenuInflater();
		  inflater.inflate(R.layout.context_menu, menu);

	}

}
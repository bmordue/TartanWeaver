/**
 * 
 */
package com.scotapps.tartanweaver;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

// TODO NEEDS SORTING OUT

/**
 * @author ben
 *
 */
public class TartanThreadWidget extends RelativeLayout  {

	private Context mContext;
	private Button mColourPickerButton;
	private ColourPickerPopup mColourPopup;

	private static final String TAG = "TartanThreadWidget";
	
	public TartanThreadWidget(Context context, AttributeSet set) {
		super(context, set);
		mContext = context;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.threadwidget, this);

/*
        //get custom attribute "gridview" from xml layout definition
        TypedArray a=getContext().obtainStyledAttributes(set, R.styleable.TartanThreadWidget);
        //Use a
      //  Log.d("TAG","XML test:" + String.valueOf(a.getResourceId(R.styleable.TartanThreadWidget_gridview, -1) ));
     //   Log.d("TAG","XML test:"+a.getString(R.styleable.TartanThreadWidget_teststring));
     //   Log.i("test",a.getString(R.styleable.MyCustomView_android_extraInformation));
        mGridId = a.getResourceId(R.styleable.TartanThreadWidget_gridview, NO_ID);

        //Don't forget this
        a.recycle();
*/
        
        mColourPopup = new ColourPickerPopup(mContext);
  /*      mColourPopup.setTouchInterceptor( new OnTouchListener () {
        	@Override
        	public boolean onTouch(View v, MotionEvent event) {
        		Log.d(TAG, "onTouch()");
        				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
        					mColourPopup.dismiss();
        					return true;
        				}			
        				return false;
        	}
        });
        */
        
        mColourPickerButton = (Button) findViewById(R.id.colourpicker);
        mColourPickerButton.setOnClickListener(	new OnClickListener() {
			@Override
			public void onClick(View v) {

				Log.d(TAG, "onClick : R.id.colourpicker: "
						+ String.valueOf(R.id.colourpicker));

				if (mColourPopup.isShowing()) {
					Log.d(TAG, "Dismissing mColourPopup");
					mColourPopup.dismiss();

				} else {
					Log.d(TAG, "Showing mColourPopup");

					// finally show the popup in the center of the window
					// TODO: protect from NPE
					mColourPopup.showAsDropDown(v);
				}
			}
    	});
        
        
        
        SeekBar sb = (SeekBar) findViewById(R.id.slider);
        sb.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				Log.d(TAG, "SeekBar progress: " + String.valueOf(progress) );
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) { }
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        
        
	
	
	}

/*
	private void setupGridView() {
		
		//must call setGridView() before this method
	       if (mGrid == null) {
	        	//TODO: better error handling -- throw an exception?
	        	Log.d(TAG, "GridView is null.");
	        	return;
	        }
	*/	
	  /*     RelativeLayout parent = (RelativeLayout) this.getParent();
        
        if (parent == null) {
        	//TODO: better error handling
        	Log.d(TAG, "Parent is null.");
        	return;
        }
    */    
  /*      View rootView = this.getRootView();
        Log.d(TAG, "rootView.getTag() gives " + rootView.getTag());
        Log.d(TAG, "rootView.getRootView().getTag() gives " + rootView.getRootView().getTag());
        
        mGrid = (GridView) findViewById(gridId);
        if (mGrid == null) {
        	Log.d(TAG, "mGrid: Darn!");
        } else {
        	Log.d(TAG, "mGrid: Yay!");
        }
        */
        
  /*
   
        	mGrid = (GridView) parent.findViewById(gridId);
        } else //failed to get gridview from XML -- should probably throw an exception
        {	//TODO: throw an exception if xml did not provide gridview id.
        	mGrid = null;
        	Log.d(TAG, "Didn't find GridView.");
        	return;
        }
    */
        
   /*      
        mGrid.setVisibility(VISIBLE);
 		//set image adapter for GridView
        //can't use R.id.gridview because gridview is not in scope for this view (or something...)
/*	    mGrid = (GridView) findViewById(R.id.gridview);
	    try {
	    	mGrid.setAdapter(new ColoursAdapter(this.getContext() ));
	    } catch (NullPointerException e) {
	    	Log.d(TAG, e.toString());
	    }
	    */
/*	    Log.d(TAG, " milestone 1.");
	    
	    mColourPickerButton = (Button) findViewById(R.id.colourpicker);
	    mColourPickerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int visibility = mGrid.getVisibility();
				if (visibility == View.VISIBLE){
					mGrid.setVisibility(View.GONE);
				} else {
			           //position the GridView appropriately, above this widget's colourPickerButton
					int buttonId = mColourPickerButton.getId();
					RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) mGrid
							.getLayoutParams();
					layout.addRule(RelativeLayout.ABOVE, buttonId);
					mGrid.setLayoutParams(layout);
					mGrid.setVisibility(View.VISIBLE);
				}
			}
		});

	    Log.d(TAG, " milestone 2.");

	    
	    mGrid.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	           //TODO: replace this dummy action with correct action (ie pick colour)
	        	mColourPickerButton.setText(new Integer(position).toString() );
	           
	        	mGrid.setVisibility(View.GONE);
	        }
	    });

	    Log.d(TAG, " milestone 3.");
	
	}
*/	
	
/*	
	//This does not seem to be the right way to do things... :-(
	public void setGridView (GridView grid) {
		mGrid = grid;
		
		//set click listeners and other bits n pieces
	//	setupGridView();
	}
*/	
	
	public void addWidget () {
		//TODO Stub
		Log.d(TAG, "addWidget()");
	}

	public void removeWidget () {
		//TODO Stub
		Log.d(TAG, "removeWidget()");
	}
	
/*
	public void setPopup(PopupWindow popup) {
		mColourPopup = popup;
	}
*/
/*
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Clicked View id is " + v.getId());

		switch (v.getId()) {
		case R.id.colourpicker:
			Log.d(TAG, "onClick : R.id.colourpicker: "
					+ String.valueOf(R.id.colourpicker));

			if (mColourPopup.isShowing()) {
				Log.d(TAG, "Dismissing mColourPopup");
				mColourPopup.dismiss();
				
			} else {
				Log.d(TAG, "Showing mColourPopup");

				// finally show the popup in the center of the window
				// TODO: protect from NPE
				mColourPopup.showAsDropDown(v);
			}
			break;

		default:
			break;
		}

	}
	*/

}

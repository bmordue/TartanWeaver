package com.scotapps.tartanweaver;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ColourPickerPopup extends PopupWindow  {

	private Context mContext;
	private final static String TAG = "ColourPickerPopup";
	private GridView mGridView; 
	
	/**
	 * @param context
	 */
	public ColourPickerPopup(Context context) {
		super(context);
		mContext = context;
		View rootView;
		
		LayoutInflater inflater = (LayoutInflater) 
			mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//null: parent viewgroup; false: attach to parent	
		rootView = inflater.inflate(R.layout.coloursgrid, null, false);
				
		//TODO: TESTING REMOVE if test
		if (rootView != null) {
			setContentView(rootView);
		} else { Log.e(TAG, "ColourPickerPopup(): parent View is null."); }
		
		this.setWidth(LayoutParams.WRAP_CONTENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);

	    mGridView = (GridView) rootView.findViewById(R.id.gridview);
	    mGridView.setAdapter(new ColoursAdapter(mContext));

	    mGridView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Log.d(TAG, "GridView clicked, item " + String.valueOf(position));
	            Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	    
/*	    mGridView.setOnTouchListener( new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					ColourPickerPopup.this.dismiss();
					return true;
				}
				
				return false;
			}
		});      */ 


	}




}

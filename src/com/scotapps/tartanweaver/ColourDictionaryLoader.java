/**
 * 
 */
package com.scotapps.tartanweaver;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

/**
 * @author ben Heavily based on
 *         http://www.helloandroid.com/tutorials/newsdroid-rss-reader
 */
public class ColourDictionaryLoader extends DefaultHandler {

	private static final String TAG = "ColourDictionaryLoader";

	private HashMap<String, Integer> mColourValues;
	private HashMap<String, String> mColourCodes;
	
	private boolean finished = false;
	
	ColourDictionaryLoader () {
		super();

		mColourValues = new HashMap<String, Integer>();
		mColourCodes = new HashMap<String, String>();
	}
	
	public void startElement(String uri, String name, String qName,
			Attributes atts) {
		
		String colourName;
		String colourCode;
		String colourValue;
		Integer colourRGB = null;
		
		//if it's not a 'colour' element, we're not interested
		if (name.trim().equals("colour")) {

			colourName = atts.getValue(uri, "name");
			colourCode = atts.getValue(uri, "code");
			colourValue = atts.getValue(uri, "rgb");			
			
			try {
				colourRGB = Color.parseColor(colourValue);
			} catch (Exception e) {
				Log.e(TAG,e.toString());
			}
			
//			Log.d(TAG,"name" + colourName + ", code " + colourCode + ", rgb " + colourValue);
			
			mColourValues.put(colourCode, colourRGB);
			mColourCodes.put(colourCode, colourName);
		
		}
	}

	// Attempt to read a list of pubs in KML format from a Google Maps query
	public void loadColourDictionary(Context ctx, int resourceId) {
		try {

			InputStream coloursInputStream = ctx.getResources()
					.openRawResource(resourceId);

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(this);
			xr.parse(new InputSource(coloursInputStream));

		} catch (IOException e) {
			Log.e(TAG, e.toString());
		} catch (SAXException e) {
			Log.e(TAG, e.toString());
		} catch (ParserConfigurationException e) {
			Log.e(TAG, e.toString());
		}
	}

	public void endDocument() {
		finished = true;
	}
	
	public boolean isFinished() {
		return finished;
	}

	public HashMap <String, String> getColourCodes () {
		return mColourCodes;
	}
	
	public HashMap <String, Integer> getColourValues() {
		return mColourValues;
	}
	
}

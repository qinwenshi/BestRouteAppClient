package ie.ul.myproject.parsing;

import ie.ul.myproject.models.RoadStage;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ParseRoadStage {

	List<RoadStage> roadStages;
	RoadStage currentRoadStage;
	boolean inDistance = false;
	boolean inDuration = false;
	boolean inValue = false;
	
	public List<RoadStage> doParseString(String s) {
		
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser parser = factory.newPullParser();
			//System.out.println("Parser implementation class is " + parser.getClass());
			parser.setInput(new StringReader(s));
			processDocument(parser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roadStages;
	}

	/**
	 * @param parser
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public void processDocument(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		int eventType = parser.getEventType();
		do {
			if (eventType == XmlPullParser.START_DOCUMENT) {
				//System.out.println("Start document");
				roadStages = new ArrayList<RoadStage>();
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
				//System.out.println("End document");
			} else if (eventType == XmlPullParser.START_TAG) {
				processStartElement(parser);
			} else if (eventType == XmlPullParser.END_TAG) {
				processEndElement(parser);
			} else if (eventType == XmlPullParser.TEXT) {
				processText(parser);
			}
			eventType = parser.next();
		} while (eventType != XmlPullParser.END_DOCUMENT);
	}

	public void processStartElement(XmlPullParser parser) {
		String name = parser.getName();
		if(name.equals("row")){
			;
		} else if (name.equals("element")) {
			currentRoadStage = new RoadStage();
			roadStages.add(currentRoadStage);
		} else if (name.equals("duration")) {
			inDuration = true;
		} else if (name.equals("distance")) {
			inDistance = true;
		} else if (name.equals("value")) {
			inValue = true;
		}
	}

	public void processEndElement(XmlPullParser parser) {
		String name = parser.getName();
		
		if (name.equals("duration")) {
			inDuration = false;
		} else if (name.equals("distance")) {
			inDistance = false;
		} else if (name.equals("value")) {
			inValue = false;
		}
	}

	public void processText(XmlPullParser parser) throws XmlPullParserException {
		if (inDuration && inValue) {
			String text = parser.getText();
			int seconds = Integer.parseInt(text);
			System.out.println("Seconds: " + seconds);
			int minutes = seconds/60;
			System.out.println("Minutes: " + minutes);
			currentRoadStage.setElapsedTimeMinutes(minutes);
					
		} else if (inDistance && inValue) {
			String text = parser.getText();
			//System.out.println(text);
			currentRoadStage.setDistance(Double.parseDouble(text)/1000);
		} 
	}
}

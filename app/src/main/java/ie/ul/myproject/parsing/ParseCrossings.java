package ie.ul.myproject.parsing;

import ie.ul.myproject.models.Crossing;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ParseCrossings {

	List<Crossing> crossings;

	Crossing currentCrossing;
	boolean inId = false;
	boolean inFromPortId = false;
	boolean inToPortId = false;
	boolean inFerryCompanyId = false;
	boolean inDepartureTime = false;
	boolean inArrivalTime = false;
	boolean inMonday = false;
	boolean inTuesday = false;
	boolean inWednesday = false;
	boolean inThursday = false;
	boolean inFriday = false;
	boolean inSaturday = false;
	boolean inSunday = false;

	public List<Crossing> doParseString(String s) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser parser = factory.newPullParser();
			// System.out.println("Parser implementation class is " +
			// parser.getClass());
			parser.setInput(new StringReader(s));
			processDocument(parser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return crossings;
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
				// System.out.println("Start document");
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
				// System.out.println("End document");
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
		if (name.equals("crossings")) {
			crossings = new ArrayList<Crossing>();
		} else if (name.equals("crossing")) {
			currentCrossing = new Crossing();
			crossings.add(currentCrossing);
		} else if (name.equals("id")) {
			inId = true;
		} else if (name.equals("fromPortId")) {
			inFromPortId = true;
		} else if (name.equals("toPortId")) {
			inToPortId = true;
		} else if (name.equals("ferryCompanyId")) {
			inFerryCompanyId = true;
		} else if (name.equals("departureTime")) {
			inDepartureTime = true;
		} else if (name.equals("arrivalTime")) {
			inArrivalTime = true;
		} else if (name.equals("mon")) {
			inMonday = true;
		} else if (name.equals("tue")) {
			inTuesday = true;
		} else if (name.equals("wed")) {
			inWednesday = true;
		} else if (name.equals("thu")) {
			inThursday = true;
		} else if (name.equals("fri")) {
			inFriday = true;
		} else if (name.equals("sat")) {
			inSaturday = true;
		} else if (name.equals("sun")) {
			inSunday = true;
		}
	}

	public void processEndElement(XmlPullParser parser) {
		String name = parser.getName();

		if (name.equals("id")) {
			inId = false;
		} else if (name.equals("fromPortId")) {
			inFromPortId = false;
		} else if (name.equals("toPortId")) {
			inToPortId = false;
		} else if (name.equals("ferryCompanyId")) {
			inFerryCompanyId = false;
		} else if (name.equals("departureTime")) {
			inDepartureTime = false;
		} else if (name.equals("arrivalTime")) {
			inArrivalTime = false;
		} else if (name.equals("mon")) {
			inMonday = false;
		} else if (name.equals("tue")) {
			inTuesday = false;
		} else if (name.equals("wed")) {
			inWednesday = false;
		} else if (name.equals("thu")) {
			inThursday = false;
		} else if (name.equals("fri")) {
			inFriday = false;
		} else if (name.equals("sat")) {
			inSaturday = false;
		} else if (name.equals("sun")) {
			inSunday = false;
		}
	}

	public void processText(XmlPullParser parser) throws XmlPullParserException {
		if (inId) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setId(Integer.parseInt(text));

		} else if (inFromPortId) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setFromPortId(Integer.parseInt(text));

		} else if (inToPortId) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setToPortId(Integer.parseInt(text));

		} else if (inFerryCompanyId) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setFerryCompanyId(Integer.parseInt(text));

		} else if (inDepartureTime) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setDepartureTime(text);

		} else if (inArrivalTime) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setArrivalTime(text);

		} else if (inMonday) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setMon(Boolean.parseBoolean(text));

		} else if (inTuesday) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setTue(Boolean.parseBoolean(text));

		} else if (inWednesday) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setWed(Boolean.parseBoolean(text));

		} else if (inThursday) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setThu(Boolean.parseBoolean(text));

		} else if (inFriday) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setFri(Boolean.parseBoolean(text));

		} else if (inSaturday) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setSat(Boolean.parseBoolean(text));

		} else if (inSunday) {
			String text = parser.getText();
			// System.out.println(text);
			currentCrossing.setSun(Boolean.parseBoolean(text));

		}
	}
}

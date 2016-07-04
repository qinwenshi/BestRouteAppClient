package ie.ul.myproject.parsing;

import ie.ul.myproject.models.Port;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ParsePorts {

	List<Port> ports;
	Port currentPort;
	boolean inId = false;
	boolean inName = false;
	boolean inGoogleName = false;
	boolean inCountryCode = false;
	boolean inLatitude = false;
	boolean inLongitude = false;

	public List<Port> doParseString(String s) {

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
		return ports;
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
				; // System.out.println("Start document");
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
				; // System.out.println("End document");
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
		if (name.equals("ports")) {
			ports = new ArrayList<Port>();
		} else if (name.equals("port")) {
			currentPort = new Port();
			ports.add(currentPort);
		} else if (name.equals("id")) {
			inId = true;
		} else if (name.equals("name")) {
			inName = true;
		} else if (name.equals("googleName")) {
			inGoogleName = true;
		} else if (name.equals("countryCode")) {
			inCountryCode = true;
		} else if (name.equals("latitude")) {
			inLatitude = true;
		} else if (name.equals("longitude")) {
			inLongitude = true;
		}
	}

	public void processEndElement(XmlPullParser parser) {
		String name = parser.getName();

		if (name.equals("id")) {
			inId = false;
		} else if (name.equals("name")) {
			inName = false;
		} else if (name.equals("googleName")) {
			inGoogleName = false;
		} else if (name.equals("countryCode")) {
			inCountryCode = false;
		} else if (name.equals("latitude")) {
			inLatitude = false;
		} else if (name.equals("longitude")) {
			inLongitude = false;
		}
	}

	public void processText(XmlPullParser parser) throws XmlPullParserException {
		if (inId) {
			String text = parser.getText();
			// System.out.println(text);
			currentPort.setId(Integer.parseInt(text));

		} else if (inName) {
			String text = parser.getText();
			// System.out.println(text);
			currentPort.setName(text);
		} else if (inGoogleName) {
			String text = parser.getText();
			// System.out.println(text);
			currentPort.setGoogleName(text);
		} else if (inCountryCode) {
			String text = parser.getText();
			// System.out.println(text);
			currentPort.setCountryCode(text);
		} else if (inLatitude) {
			String text = parser.getText();
			// System.out.println(text);
			currentPort.setLatitude(Double.parseDouble(text));
		} else if (inLongitude) {
			String text = parser.getText();
			// System.out.println(text);
			currentPort.setLongitude(Double.parseDouble(text)) ;
		}
	}
}

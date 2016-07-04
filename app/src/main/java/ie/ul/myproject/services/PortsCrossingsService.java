package ie.ul.myproject.services;

import ie.ul.myproject.models.Crossing;
import ie.ul.myproject.models.Port;
import ie.ul.myproject.parsing.ParseCrossings;
import ie.ul.myproject.parsing.ParsePorts;
import ie.ul.myproject.services.http.HttpUtility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;

public class PortsCrossingsService {
	
	static final long MINUTES_IN_DAY = 1440;

	public static List<Port> getPortsByCountry(String countryCode){
		
		//new PCGetPortsByCountryAsync(countryCode).execute();

		List<Port> ports = new ArrayList<Port>();

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		
		String request = "https://a2restport.herokuapp.com/services/port/" + countryCode;
		System.out.println(request);
	
		HttpGet httpGet = new HttpGet(request);

		String xmlText = null;
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			xmlText = HttpUtility.getASCIIContentFromEntity(entity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println(xmlText);
		
		ParsePorts pp = new ParsePorts();
		
		ports = pp.doParseString(xmlText);
		return ports;
	}
	
	
	public static List<Crossing> getCrossingsByPort(int portId){
		List<Crossing> crossings = new ArrayList<Crossing>();
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		
		String request = "https://a2restport.herokuapp.com/services/crossing/" + portId;
		System.out.println(request);
	
		HttpGet httpGet = new HttpGet(request);

		String xmlText = null;
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			xmlText = HttpUtility.getASCIIContentFromEntity(entity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println(xmlText);
		
		ParseCrossings cc = new ParseCrossings();
		crossings = cc.doParseString(xmlText);
		return crossings;
	}
		
	public static Set<Integer> getDestinations(List<Crossing> crossings){
		Set<Integer> portIds = new HashSet<Integer>();
		
		for (Crossing c : crossings){
			portIds.add(c.getToPortId());
		}
		
		return portIds;
	}
	
	public static Crossing getNextCrossing(List<Crossing> crossings, int destinationPortId, LocalTime currentTime){
		Crossing next = null;
		long minWaitMinutes = Long.MAX_VALUE;
		
		for (Crossing crossing : crossings) {
			if(crossing.getToPortId() == destinationPortId){
				LocalTime departureTime = LocalTime.parse(crossing
						.getDepartureTime());
				//System.out.println(departureTime);
				int minutes = Minutes.minutesBetween(currentTime, departureTime).getMinutes();
				// deal with wait past midnight
				if(minutes < 0){
					minutes += MINUTES_IN_DAY ;
				}
				
				if(minutes < minWaitMinutes){
					minWaitMinutes = minutes;
					next = crossing ;
				}
			}
		}		
		return next;
	}
}

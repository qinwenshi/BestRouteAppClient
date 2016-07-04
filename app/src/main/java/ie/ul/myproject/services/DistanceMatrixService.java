package ie.ul.myproject.services;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;
import java.util.List;

import ie.ul.myproject.models.LandLocation;
import ie.ul.myproject.models.Port;
import ie.ul.myproject.models.RoadStage;
import ie.ul.myproject.parsing.ParseRoadStage;
import ie.ul.myproject.services.http.HttpUtility;

// import org.apache.http.HttpResponse;


public class DistanceMatrixService {

	public static List<RoadStage> getStages(LandLocation l1, List<Port> ports) {
		List<RoadStage> stages = new ArrayList<RoadStage>();

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		String request = "http://maps.googleapis.com/maps/api/distancematrix/xml?origins=";
		request += l1.getGoogleName();
		request += "&destinations=";
		request += ports.get(0).getGoogleName();

		for (int i = 1; i < ports.size(); i++) {
			request += "%7C" + ports.get(i).getGoogleName();
		}
		request += "&sensor=false&avoid=tolls";

		System.out.println(request);

		HttpGet httpGet = new HttpGet(request);
		String text = null;
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			text = HttpUtility.getASCIIContentFromEntity(entity);
			// System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ParseRoadStage parseRoadStage = new ParseRoadStage();
		stages = parseRoadStage.doParseString(text);

		for (int i = 0; i < stages.size(); i++) {
			stages.get(i).setFrom(l1);
			stages.get(i).setTo(ports.get(i));
		}

		return stages;
	}

	public static List<RoadStage> getStages(List<Port> ports, LandLocation l1) {
		List<RoadStage> stages = new ArrayList<RoadStage>();

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		String request = "http://maps.googleapis.com/maps/api/distancematrix/xml?origins=";

		request += ports.get(0).getGoogleName();
		for (int i = 1; i < ports.size(); i++) {
			request += "%7C" + ports.get(i).getGoogleName();
		}

		request += "&destinations=";
		request += l1.getGoogleName();

		request += "&sensor=false&avoid=tolls";

		System.out.println(request);

		HttpGet httpGet = new HttpGet(request);
		String text = null;
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			text = HttpUtility.getASCIIContentFromEntity(entity);
			// System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ParseRoadStage parseRoadStage = new ParseRoadStage();
		stages = parseRoadStage.doParseString(text);

		for (int i = 0; i < stages.size(); i++) {
			stages.get(i).setFrom(ports.get(i));
			stages.get(i).setTo(l1);
		}

		return stages;
	}

	
	// when a roadstage from english port to destination is found, 
	// copy it. otherwise get a problem when there are more than 1 route to the english port
	public static RoadStage find(List<RoadStage> stages, int portId) {
		
		for(RoadStage r: stages){
			Port p = (Port) r.getFrom();
			if(p.getId() == portId) {
				return r.copyRoadStage();
			}
		}

		return null;
	}

}

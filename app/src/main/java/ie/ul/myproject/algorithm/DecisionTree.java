package ie.ul.myproject.algorithm;

import ie.ul.myproject.models.Crossing;
import ie.ul.myproject.models.LandLocation;
import ie.ul.myproject.models.Port;
import ie.ul.myproject.models.RoadStage;
import ie.ul.myproject.models.Route;
import ie.ul.myproject.models.Stage;
import ie.ul.myproject.models.WaitStage;
import ie.ul.myproject.services.DistanceMatrixService;
import ie.ul.myproject.services.PortsCrossingsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.LocalTime;

import android.annotation.SuppressLint;
import android.location.Location;

public class DecisionTree {
	LandLocation startLocation;
	LandLocation endLocation;

	public static Location myLastLocation;
	public static LandLocation destination;
	public static Route selectedRoute;
	public static Stage selectedStage;
	
	// visitor pattern
	public static CostMetric metric;


	public void accept(){
		for(Route r: allRoutes){
			r.accept(metric);
		}
	}


	public List<Route> allRoutes = new ArrayList<Route>();

	public List<Route> getAllRoutes() {
		return allRoutes;
	}

	@SuppressLint("UseSparseArrays")
	public static Map<Integer, Port> portMap = new HashMap<Integer, Port>();
	public final int MIN_WAIT_TIME = 60;

	public DecisionTree(LandLocation startLocation, LandLocation endLocation) {
		super();
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}

	public Route buildTree() {

		List<Port> irishPorts = PortsCrossingsService.getPortsByCountry("IE");
		List<Port> englishPorts = PortsCrossingsService.getPortsByCountry("UK");

		for (Port p : irishPorts) {
			portMap.put(p.getId(), p);
		}

		for (Port p : englishPorts) {
			portMap.put(p.getId(), p);
		}

		LocalTime startTime = LocalTime.now();

		List<RoadStage> startStages = DistanceMatrixService.getStages(
				startLocation, irishPorts);
		List<RoadStage> endStages = DistanceMatrixService.getStages(
				englishPorts, endLocation);

		for (RoadStage roadStage : startStages) {

			roadStage.setStartTime(startTime);
			roadStage.setEndTime(startTime.plusMinutes(roadStage
					.getElapsedTimeMinutes()));

			Route route = new Route();
			route.addStage(roadStage);

			int roadStageDuration = roadStage.getElapsedTimeMinutes();

			// leave 1 hour (MIN_WAIT_TIME) to check in etc
			LocalTime readyLeavePortTime = startTime.plusMinutes(
					roadStageDuration).plusMinutes(MIN_WAIT_TIME);

			Port port = (Port) roadStage.getTo();

			// get all crossings from irish port
			List<Crossing> crossings = PortsCrossingsService
					.getCrossingsByPort(port.getId());

			// get all destination ports from irish port
			Set<Integer> destinationIds = PortsCrossingsService
					.getDestinations(crossings);

			// get first crossing for each destination
			List<Crossing> nextCrossings = new ArrayList<Crossing>();
			for (Integer i : destinationIds) {
				Crossing crossing = PortsCrossingsService.getNextCrossing(
						crossings, i, readyLeavePortTime);
				nextCrossings.add(crossing);
			}

			for (Crossing crossing : nextCrossings) {
				Stage waitStage = new WaitStage(roadStage.getEndTime(),
						crossing.getStartTime());
				Route newRoute = route.copyRoute();
				newRoute.addStage(waitStage);
				newRoute.addStage(crossing);

				Port destinationPort = portMap.get(crossing.getToPortId());

				RoadStage endR = DistanceMatrixService.find(endStages,
						destinationPort.getId());

				// endR.setStartTime(LocalTime.parse(crossing.getArrivalTime()));
				endR.setStartTime(crossing.getEndTime());
				endR.setEndTime(endR.getStartTime().plusMinutes(
						endR.getElapsedTimeMinutes()));

				newRoute.addStage(endR);
				allRoutes.add(newRoute);
			}
		}
		return null;
	}

	public void printRoutes() {

		for (Route route : allRoutes) {
			System.out.println(route.toString());
		}

	}

	public void sortRoutes() {
		Collections.sort(allRoutes);
	}
}

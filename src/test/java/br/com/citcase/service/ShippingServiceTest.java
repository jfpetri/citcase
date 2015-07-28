package br.com.citcase.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.citcase.model.DeliveryMap;
import br.com.citcase.model.Edge;
import br.com.citcase.model.Shipping;


public class ShippingServiceTest {

	public List<Edge> deliveryRoutes = new ArrayList<Edge>();
	double fuelPrice = 2.5;
	double performance = 10;
	DeliveryMap dMap = new DeliveryMap("Mapa1", deliveryRoutes);
	
	
	@Before
	public void setUp() {
		deliveryRoutes.add(new Edge("A", "B", 10));
		deliveryRoutes.add(new Edge("B", "D", 15));
		deliveryRoutes.add(new Edge("A", "C", 20));
		deliveryRoutes.add(new Edge("C", "D", 30));
		deliveryRoutes.add(new Edge("B", "E", 50));
		deliveryRoutes.add(new Edge("D", "E", 30));
	}

	@Test
	public void testRouteAtoDSucess() {

		
		Shipping shipping = new Shipping("a", "d", fuelPrice, performance);

		DeliveryNetworkService g = new DeliveryNetworkService(dMap);
		g.dijkstra(shipping.getOrigin());
		shipping = g.printPath(shipping);

		assertEquals(shipping.getRoute(), "A > B > D");

	}
	@Test
	public void testDistanceAtoDSucess() {

		
		Shipping shipping = new Shipping("a", "d", fuelPrice, performance);

		DeliveryNetworkService g = new DeliveryNetworkService(dMap);
		g.dijkstra(shipping.getOrigin());
		shipping = g.printPath(shipping);

		assertEquals(shipping.getDistance(), 25.0, 0.001);

	}
	@Test
	public void testTotalPriceAtoDSucess() {

		
		Shipping shipping = new Shipping("a", "d", fuelPrice, performance);

		DeliveryNetworkService g = new DeliveryNetworkService(dMap);
		g.dijkstra(shipping.getOrigin());
		shipping = g.printPath(shipping);

		assertEquals(shipping.getTotalCost(), 6.25, 0.001);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRoutNotFoundEndPoint() throws Exception {
		
		Shipping shipping = new Shipping("A", "Z", fuelPrice, performance);

		DeliveryNetworkService g = new DeliveryNetworkService(dMap);
		g.dijkstra(shipping.getOrigin());
		shipping = g.printPath(shipping);
		
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testRoutNotFoundStartPoint() throws Exception {
		
		Shipping shipping = new Shipping("Z", "B", fuelPrice, performance);

		DeliveryNetworkService g = new DeliveryNetworkService(dMap);
		g.dijkstra(shipping.getOrigin());
		shipping = g.printPath(shipping);
		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testParamPerformanceAndPriceNotFound() throws Exception {
		
		Shipping shipping = new Shipping("A", "B", 0, 0);

		DeliveryNetworkService g = new DeliveryNetworkService(dMap);
		g.dijkstra(shipping.getOrigin());
		shipping = g.printPath(shipping);
		
		
	}
	
}
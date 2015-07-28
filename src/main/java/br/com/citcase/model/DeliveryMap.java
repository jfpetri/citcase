package br.com.citcase.model;

import java.io.Serializable;
import java.util.List;

public class DeliveryMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7474786300565087991L;
	private String name;
	private List<Edge> routes;


	/**
	 * Create new DeliveryMap
	 * 
	 * @param name
	 * 			the map name
	 * @param deliveryRoutes
	 * 			the list routes
	 */
	public DeliveryMap(String name, List<Edge> deliveryRoutes) {
		this.name = name;
		this.routes = deliveryRoutes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Edge> getDeliveryRoutes() {
		return routes;
	}

	public void setDeliveryRoutes(List<Edge> deliveryRoutes) {
		this.routes = deliveryRoutes;
	}
}
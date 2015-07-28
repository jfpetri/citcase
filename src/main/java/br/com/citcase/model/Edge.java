package br.com.citcase.model;


public final class Edge {
	
	public final String origin, destination;
	public final double distance;
	 
	/**
	 * Create new edge
	 * 
	 * @param origin
	 * 			the origin point
	 * @param destination
	 * 			the destination point
	 * @param distance
	 * 			the distance between both points
	 */
	public Edge(String origin, String destination, double distance) {
		this.origin = origin.toUpperCase();
		this.destination = destination.toUpperCase();
		this.distance = distance;
	}
	
}
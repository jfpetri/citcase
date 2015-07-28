package br.com.citcase.model;

import java.util.HashMap;
import java.util.Map;

public final class Vertex implements Comparable<Vertex> {

	public final String name;
	public double dist = Double.MAX_VALUE;
	public Vertex previous = null;
	public final Map<Vertex, Double> neighbours = new HashMap<>();

	/**
	 * Create new Vertex
	 * 
	 * @param name
	 *            the Vertex name
	 */
	public Vertex(String name) {
		this.name = name;
	}

	/**
	 * Return the shortest path found
	 * 
	 * @param shipping
	 *            the shipping
	 * @return Shipping the format of path is: pn1 > pn2 > pn3 ....
	 */
	public Shipping getResultPath(Shipping shipping) {
		if (this == this.previous || this.previous == null) {
			shipping.setRoute(this.name);
		} else {
			this.previous.getResultPath(shipping);
			shipping.setRoute(" > " + this.name);
			shipping.setDistance(this.dist);
		}
		return shipping;
	}

	public int compareTo(Vertex other) {
		return Double.compare(dist, other.dist);
	}
}
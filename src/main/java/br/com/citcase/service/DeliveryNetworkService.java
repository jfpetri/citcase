package br.com.citcase.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import br.com.citcase.model.DeliveryMap;
import br.com.citcase.model.Edge;
import br.com.citcase.model.Shipping;
import br.com.citcase.model.Vertex;

public class DeliveryNetworkService {

	private final Map<String, Vertex> graph; 

	
	/**
	 * Builds a graph from a set of edges
	 * @param map
	 * 			the map object
	 */
	public DeliveryNetworkService(DeliveryMap map) {
		List<Edge> edges = map.getDeliveryRoutes();
		graph = new HashMap<>(edges.size());

		// one pass to find all vertices
		for (Edge e : edges) {
			if (!graph.containsKey(e.origin))
				graph.put(e.origin, new Vertex(e.origin));
			if (!graph.containsKey(e.destination))
				graph.put(e.destination, new Vertex(e.destination));
		}

		// another pass to set neighbouring vertices
		for (Edge e : edges) {
			graph.get(e.origin).neighbours.put(graph.get(e.destination),
					e.distance);
			// graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also
			// do this for an undirected graph
		}
	}

	
	/**
	 * Runs dijkstra using a specified source vertex
	 * @param startName
	 * 			the fisrt point position	
	 **/
	public void dijkstra(String startName) {
		try {
			graph.containsKey(startName);

			final Vertex source = graph.get(startName);
			NavigableSet<Vertex> q = new TreeSet<>();

			// set-up vertices
			for (Vertex v : graph.values()) {
				v.previous = v == source ? source : null;
				v.dist = v == source ? 0 : Double.MAX_VALUE;
				q.add(v);
			}

			dijkstra(q);
		} catch (NullPointerException ex) {
			throw new IllegalArgumentException("O Ponto Inicial não existe"
					+ startName);
		}
	}
	
	/**
	 * Implementation of dijkstra's algorithm using a binary heap.
	 * @param q
	 */
	private void dijkstra(final NavigableSet<Vertex> q) {
		Vertex u, v;
		while (!q.isEmpty()) {

			u = q.pollFirst(); // vertex with shortest distance (first iteration
								// will return source)
			if (u.dist == Double.MAX_VALUE)
				break; // we can ignore u (and any other remaining vertices)
						// since they are unreachable

			// look at distances to each neighbour
			for (Map.Entry<Vertex, Double> a : u.neighbours.entrySet()) {
				v = a.getKey(); // the neighbour in this iteration

				final double alternateDist = u.dist + a.getValue();
				if (alternateDist < v.dist) { // shorter path to neighbour found
					q.remove(v);
					v.dist = alternateDist;
					v.previous = u;
					q.add(v);
				}
			}
		}
	}

	
	/**
	 * Prints a path from the source to the specified vertex 
	 * @param shipping
	 * @return Shipping
	 * 			the Shipping with pricecost already calculated
	 * 	
	 */
	public Shipping printPath(Shipping shipping) {

		try {
			graph.containsKey(shipping.getDestination());
			return graph.get(shipping.getDestination()).getResultPath(shipping)
					.calculateCost();
		} catch (Exception ex) {
			throw new IllegalArgumentException("O Ponto Final não existe."
					+ shipping.getDestination());
		}

	}

}
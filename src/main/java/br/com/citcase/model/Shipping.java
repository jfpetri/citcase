package br.com.citcase.model;

public class Shipping {

	private String origin;
	private String destination;
	private double fuelPrice;
	private double performance;
	private String route;
	private double distance;
	private double totalCost;

	/**
	 * Create new Shipping
	 * 
	 * @param origin
	 * 			the origin point
	 * @param destination
	 * 			the destination point
	 * @param fuelPrice
	 * 			the current fuel price
	 * @param performance
	 * 			the performance of vehicle
	 */
	public Shipping(String origin, String destination, double fuelPrice,
			double performance) {
		this.origin = origin.toUpperCase();
		this.destination = destination.toUpperCase();
		this.fuelPrice = fuelPrice;
		this.performance = performance;
		this.route = "";
		
	}

	/**
	 * Return Total Cost from delivery
	 * 
	 * @return Shipping
	 * 				Total Cost Delivery
	 * @throws IllegalArgumentException
	 *              if distance is null.
	 *              if fuelPrice is null.
	 *              if performance is null.
	 */
	public Shipping calculateCost() {
		if (this.distance <= 0){
			throw new IllegalArgumentException("Distance can't be less than zero");
		}
		if (this.fuelPrice <= 0){
			throw new IllegalArgumentException("FuelPrice can't be less than zero");
		}
		if (this.performance <= 0){
			throw new IllegalArgumentException("Performance can't be less than zero");
		}
		this.totalCost = (this.distance * this.fuelPrice) / this.performance;
		return this;
	}

	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public double getFuelPrice() {
		return fuelPrice;
	}

	public double getPerformance() {
		return performance;
	}

	public String getRoute() {
		return route;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setFuelPrice(double fuelPrice) {
		this.fuelPrice = fuelPrice;
	}

	public void setRoute(String route) {
		this.route += route;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}

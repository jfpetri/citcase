package br.com.citcase.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.citcase.common.MapConverter;
import br.com.citcase.exception.RepositoryException;
import br.com.citcase.model.DeliveryMap;
import br.com.citcase.model.Shipping;
import br.com.citcase.service.DeliveryMapService;
import br.com.citcase.service.DeliveryNetworkService;

/**
 * @author Jean
 *
 */
@Controller
public class DeliveryNetworkController {

	@Autowired
	DeliveryMapService mapService;

	@Autowired
	private DeliveryMapService deliveryMapRepository;

	/**
	 * Create Map, POST method
	 * 
	 * @param name
	 * @param legs
	 * @throws Exception
	 */
	@RequestMapping(value = "/map/{name}", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> createMap(@PathVariable String name, @RequestBody String legs)
			throws IllegalArgumentException, RepositoryException {

		HttpHeaders headers = new HttpHeaders();
		DeliveryMap deliveryMap = new DeliveryMap(name, MapConverter.parseEdge(legs));

		Boolean map = deliveryMapRepository.register(deliveryMap);
		return new ResponseEntity<>(headers, HttpStatus.CREATED);

	}

	/**
	 * Getting Route
	 * 
	 * @param origin
	 * @param destination
	 * @param performance
	 * @param fuelPrice
	 * @param mapName
	 * @return
	 * @throws Exception
	 *             if is illegal argument
	 */
	@RequestMapping(value = "/route/{origin}/{destination}", method = RequestMethod.GET, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Shipping> getRoute(@PathVariable String origin, @PathVariable String destination,
			@RequestParam double performance, @RequestParam double fuelPrice, @RequestParam String mapName)
					throws Exception {
		HttpHeaders headers = new HttpHeaders();

		DeliveryMap dMap = deliveryMapRepository.show(mapName);

		Shipping delivery = new Shipping(origin, destination, fuelPrice, performance);

		DeliveryNetworkService g = new DeliveryNetworkService(dMap);
		g.dijkstra(delivery.getOrigin());
		delivery = g.printPath(delivery);

		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(delivery, headers, HttpStatus.OK);

	}

	/**
	 * Custom exception handler.
	 * 
	 * @param e
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
	}

	/**
	 * Custom exception handler.
	 * 
	 * @param e
	 */
	@ExceptionHandler(RepositoryException.class)
	public void handleRepositoryException(RepositoryException e, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
	}

	/**
	 * Process error 500
	 * 
	 * @param e
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Ocorreu um erro calculando Rota.")
	public void handleServiceException(Exception e) {

	}

}

package br.com.citcase.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.util.JSON;

import br.com.citcase.exception.RepositoryException;
import br.com.citcase.model.DeliveryMap;
import br.com.citcase.model.Edge;
import br.com.citcase.repository.MongoDBFactory;

@Repository
public class DeliveryMapService {

	private static final Logger LOGGER = Logger
			.getLogger(DeliveryMapService.class);

	@Autowired
	private MongoDBFactory mongoDBFactory;

	/**
	 * Create map in MomgoDB
	 * 
	 * @param deliveryMap
	 * @return boolean true is sucess
	 * @throws RepositoryException
	 */
	public Boolean register(DeliveryMap deliveryMap)
			throws RepositoryException {
		DB db = null;

		try {
			db = mongoDBFactory.getClient();
			DBCollection mapCollection = db.getCollection("routes");

			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", deliveryMap.getName());
			// mantem somente 1 com o mesmo nome
			mapCollection.remove(searchQuery);

			ObjectMapper mapper = new ObjectMapper();
			String value = mapper.writeValueAsString(deliveryMap);

			BasicDBObject doc = (BasicDBObject) JSON.parse(value);
			mapCollection.insert(doc);

			return true;

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
		}

	}

	/**
	 * Get the map stored in mongodb
	 * 
	 * @param mapName
	 *            the map name
	 * @return DeliveryMap the DeliveryMap object
	 * @throws RepositoryException
	 */
	public DeliveryMap show(String mapName) throws RepositoryException {
		DB db = null;

		try {
			db = mongoDBFactory.getClient();

			DBCollection collection = db.getCollection("routes");

			BasicDBObject query = new BasicDBObject();
			query.append("name", mapName);
			BasicDBObject map = (BasicDBObject) collection.findOne(query);

			if (map == null)
				throw new RepositoryException("Mapa informado não existe");

			List<Edge> routes = new ArrayList<>();
			List<BasicDBObject> docRoutes = (List<BasicDBObject>) map
					.get("deliveryRoutes");

			for (BasicDBObject d : docRoutes) {
				Edge e = new Edge(d.getString("origin"),
						d.getString("destination"), d.getInt("distance"));
				routes.add(e);
			}
			DeliveryMap deliveryMap = new DeliveryMap(
					map.get("name").toString(), routes);

			return deliveryMap;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}
}

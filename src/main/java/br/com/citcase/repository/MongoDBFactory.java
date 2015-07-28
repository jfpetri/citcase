package br.com.citcase.repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MongoDBFactory {
	
	
	private String uri = "mongodb://user-cit:12345@ds061651.mongolab.com:61651/case-cit";

	/**
	 * Return the DB connection
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	public DB getClient() throws UnknownHostException {
		MongoClientURI clientURI = new MongoClientURI(uri);
		MongoClient client = new MongoClient(clientURI);
		return client.getDB("case-cit");
	}
}
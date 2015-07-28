package br.com.citcase.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.citcase.common.FileIO;
import br.com.citcase.common.MapConverter;
import br.com.citcase.controller.DeliveryNetworkController;
import br.com.citcase.exception.RepositoryException;
import br.com.citcase.model.DeliveryMap;
import br.com.citcase.model.Shipping;
import br.com.citcase.service.DeliveryMapService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class MapServiceTest {

	@Autowired
	private DeliveryMapService deliveryMapRepository;

	@Autowired
	DeliveryNetworkController deliveryMapRest;

	String mapName = "MapaTest";

	@Test
	public void testCreateMapSucess() throws RepositoryException, IOException {
		String legs = FileIO.get("dtest/setupInicialMap.json");

		DeliveryMap deliveryMap = new DeliveryMap(mapName,
				MapConverter.parseEdge(legs));

		Boolean map = deliveryMapRepository.register(deliveryMap);
		assertThat(map, is(true));
	}
	
	@Test
	public void testGetMapSucess() throws RepositoryException, IOException {

		DeliveryMap mapGot = deliveryMapRepository.show(mapName);

		assertThat(mapGot, is(notNullValue()));
		assertThat(mapGot.getDeliveryRoutes().size(), is(equalTo(6)));
		assertThat(mapGot.getName(), is(equalTo(mapName)));
		assertThat(mapGot.getDeliveryRoutes(), is(notNullValue()));

	}
	@Test(expected = RepositoryException.class)
	public void testGetMapError() throws RepositoryException, IOException {

		DeliveryMap mapGot = deliveryMapRepository.show("XPTO");
	
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMapError() throws RepositoryException, IOException {
		String legs = FileIO.get("dtest/setupInicialMap.json");

		DeliveryMap deliveryMap = new DeliveryMap(mapName,
				MapConverter.parseEdge(null));

	}
}
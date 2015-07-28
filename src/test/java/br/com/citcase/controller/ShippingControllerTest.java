package br.com.citcase.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.citcase.common.FileIO;
import br.com.citcase.controller.DeliveryNetworkController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class ShippingControllerTest {

	@Autowired
	DeliveryNetworkController deliveryMapRest;

	private MockMvc mockMvc;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(deliveryMapRest).build();

	}

	/**
	 * Test  successful route
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRouteAtoDSucess() throws Exception {

		ResultActions response = mockMvc.perform(get(
				"/route/A/D?performance=10&fuelPrice=2.5&mapName=MapaTest")
				.contentType(MediaType.APPLICATION_JSON));

		String result = FileIO.get("dtest/testRouteAtoDSucess.json");

		response.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(result));
	}
	/**
	 * Test  Bad Request, not found map name
	 * 
	 * @throws IllegalArgumentException
	 *             , Start Point not Found
	 */
	@Test
	public void testRouteNotFoundMapName() throws Exception {

		ResultActions response = mockMvc.perform(get(
				"/route/Z/D?performance=10&fuelPrice=2.5&mapName=XPTO")
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isBadRequest());
	}
	/**
	 * Test  Bad Request, not found start point
	 * 
	 * @throws IllegalArgumentException
	 *             , Start Point not Found
	 */
	@Test
	public void testRouteNotFoundStartPoint() throws Exception {

		ResultActions response = mockMvc.perform(get(
				"/route/Z/D?performance=10&fuelPrice=2.5&mapName=MapaTest")
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isBadRequest());
	}
	/**
	 * Test  Bad Request, not found end point
	 * 
	 * @throws IllegalArgumentException
	 *             , End Point not Found
	 */
	@Test
	public void testRouteNotFoundEndPoint() throws Exception {

		ResultActions response = mockMvc.perform(get(
				"/route/A/Z?performance=10&fuelPrice=2.5&mapName=MapaTest")
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isBadRequest());
	}
	
	

}
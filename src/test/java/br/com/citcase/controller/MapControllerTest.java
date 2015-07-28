package br.com.citcase.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class MapControllerTest {

	
	@Autowired
	DeliveryNetworkController deliveryMapRest;

	private MockMvc mockMvc;

	String mapName = "MapaTest2";

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(deliveryMapRest).build();

	}

	/**
	 * Test successful 
	 * @throws Exception
	 */
	@Test
	public void testCreateMapSucess() throws Exception {
		String legs = FileIO.get("dtest/setupInicialMap.json");

		ResultActions response = mockMvc.perform(post("/map/" + mapName)
						.contentType(MediaType.APPLICATION_JSON).content(legs));

		response.andExpect(status().isCreated());
		 
	}
	
	/**
	 * Test failed 
	 * @throws Exception
	 */
	@Test
	public void testCreateMapError() throws Exception {
		
		String legs = FileIO.get("dtest/setupInicialMapErro.json");
		
		ResultActions response = mockMvc.perform(post("/map/" + mapName)
						.contentType(MediaType.APPLICATION_JSON).content(legs));

		response.andExpect(status().isBadRequest())
		.andExpect(content().string(""));
	}

}
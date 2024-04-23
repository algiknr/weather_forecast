package com.algi.ing_weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IngWeatherApplicationTests {

	@Autowired
	private OpenWeatherService openWeatherService;
	
	@Test
	void weatherTestCoords() {
		List<String> coords=openWeatherService.getCoordsCity("Istanbul");
		Assertions.assertNotNull(coords);
		Assertions.assertEquals(coords.size(), 2);
		
	}
	@Test
	public void testCoordinatesThrowException() {
	    Throwable exception = Assertions.assertThrows(Exception.class, () -> openWeatherService.getCoordsCity("Ist"));
	    Assertions.assertEquals("404 Not Found: \"{\"cod\":\"404\",\"message\":\"city not found\"}\"", exception.getMessage());
	}
	
	@Test
	void weatherTestInfo() {
		List<String> info_list=new ArrayList<>();
		info_list.add("28.9833");
		info_list.add("41.0351");
		Map<String,Double> info=openWeatherService.getCompleteWeatherInfo(info_list);
		List<String> keyList = new ArrayList<>(info.keySet());
		Assertions.assertNotNull(info);
		Assertions.assertEquals(info.size(), 2);
		Assertions.assertEquals(Lists.newArrayList("maximum_humidity", "maximum_feels_like"),keyList);		
	}
	
	@Test
	public void testInfoThrowException() {
		List<String> info_list=new ArrayList<>();
		info_list.add("aaaaa");
		info_list.add("bbbbb");
	    Throwable exception = Assertions.assertThrows(Exception.class, () -> openWeatherService.getCompleteWeatherInfo(info_list));
	    Assertions.assertEquals("400 Bad Request: \"{\"cod\":\"400\",\"message\":\"wrong latitude\"}\"", exception.getMessage());
	}

}

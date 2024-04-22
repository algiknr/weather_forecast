package com.algi.ing_weather;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class OpenWeatherService {
	private final String API_KEY = "121b6143c38644f64ae4b04d913f0b38";
	
	
	public  List<String> getCoordsCity(String city) {
		String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+API_KEY;
		RestTemplate restTemplate = new RestTemplate();
		List<String> coordinates = new ArrayList<>();
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, null, JsonNode.class);
		JsonNode map = response.getBody();
		JsonNode coord = map.get("coord");
        coordinates.add(coord.get("lon").toString());
        coordinates.add(coord.get("lat").toString());
	    return coordinates;
	}
	

	public Map<String, Double> getCompleteWeatherInfo(List<String> coords) {
		String url="https://api.openweathermap.org/data/2.5/forecast?lat="+coords.get(1)+"&lon="+coords.get(0)+"&appid="+API_KEY;
		Map<String, Double> map = new HashMap<>();
		map.put("maximum_feels_like", Double.MIN_VALUE);
		map.put("maximum_humidity", Double.MIN_VALUE);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, null, JsonNode.class);
		JsonNode map_info = response.getBody().get("list");
		long unixTimestamp = Instant.now().getEpochSecond();
		map_info.forEach(jsonObject -> {
		   if(jsonObject.get("main").get("dt")!=null&&jsonObject.get("main").get("dt").asLong()>unixTimestamp+(60*60*48)){
			   return;
		   }
		   if(jsonObject.get("main").get("feels_like")!=null&&jsonObject.get("main").get("feels_like").asDouble()>map.get("maximum_feels_like")) {
			   map.put("maximum_feels_like", jsonObject.get("main").get("feels_like").asDouble());
			  
		   }
		   if(jsonObject.get("main").get("humidity")!=null&&jsonObject.get("main").get("humidity").asDouble()>map.get("maximum_humidity")) {
			   map.put("maximum_humidity", jsonObject.get("main").get("humidity").asDouble());
			  
		   }
		});
		
	    return map;
	}

	 
	
	
			
}

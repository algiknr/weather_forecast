package com.algi.ing_weather;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

@Service
public class OpenWeatherService {
	private final String API_KEY ;
	private final String API_URL ;
	
	public OpenWeatherService(Environment environment) {
        this.API_KEY = environment.getProperty("api_key");
        this.API_URL = environment.getProperty("api_url");
    }
	
	public  List<String> getCoordsCity(String city) {
		String url=API_URL+"weather?q="+city+"&appid="+API_KEY;
		RestTemplate restTemplate = new RestTemplate();
		List<String> coordinates = new ArrayList<>();
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, null, JsonNode.class);
		JsonNode map = response.getBody();
		JsonNode coord = JsonNodeFactory.instance.objectNode();
        if(map!=null&&map.get("coord") != null) {
        	coord = map.get("coord");
        }else {
        	throw new NullPointerException("coords are null :(");
        }
        coordinates.add(coord.get("lon").toString());
        coordinates.add(coord.get("lat").toString());
	    return coordinates;
	}
	

	public Map<String, Double> getCompleteWeatherInfo(List<String> coords) {
		String url=API_URL+"forecast?lat="+coords.get(1)+"&lon="+coords.get(0)+"&appid="+API_KEY;
		Map<String, Double> map = new HashMap<>();
		map.put("maximum_feels_like", Double.MIN_VALUE);
		map.put("maximum_humidity", Double.MIN_VALUE);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, null, JsonNode.class);
		JsonNode map_info = JsonNodeFactory.instance.objectNode();
		if(response.hasBody()) {
		map_info = response.getBody();
		}
		if(map_info!=null) {
		 map_info = map_info.get("list");
		}
		long unixTimestamp = Instant.now().getEpochSecond();
		if(map_info!=null) {
		map_info.forEach(jsonObject -> {
			//break the loop when time exceeds 48 hours
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
		}else {
			throw new NullPointerException("map is null :(");
		}
		
	    return map;
	}

	 
	
	
			
}

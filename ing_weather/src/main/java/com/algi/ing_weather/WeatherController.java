package com.algi.ing_weather;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class WeatherController {
	@Autowired
	private OpenWeatherService openWeatherService;
	
	/*@GetMapping("/**")
	public  ResponseEntity<?> handle() {
		return new ResponseEntity<String>("WRONG URL INFO!",HttpStatus.NOT_FOUND);
	}*/
	
	@GetMapping(path="weather/{city}")
	public ResponseEntity<?> getWeatherInfo(@PathVariable("city") String city) {
		
		List<String> coordinates=new ArrayList<>();
		try {
		coordinates = openWeatherService.getCoordsCity(city);
		}catch(Exception e){
			return new ResponseEntity<String>("CITY NOT FOUND.",HttpStatus.NOT_FOUND);
		}
		try {
		Map<String, Double> res = openWeatherService.getCompleteWeatherInfo(coordinates);
		if(res!=null) {
		return new ResponseEntity<Map<String, Double>>(res, HttpStatus.OK);
		}
		}catch(Exception e){
			return new ResponseEntity<String>("Info not Found.",HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	}


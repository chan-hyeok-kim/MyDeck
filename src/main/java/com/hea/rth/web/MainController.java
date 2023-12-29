package com.hea.rth.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MainController {

	@GetMapping("/")
	public ResponseEntity<?> findAll(){
		
		WebClient webClient=WebClient.builder()
				                     .baseUrl("https://us.api.blizzard.com/hearthstone/cards/678?locale=ko_KR")
				                     .build();
		
		
		
		
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
	
}

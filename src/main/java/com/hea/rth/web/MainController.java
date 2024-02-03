package com.hea.rth.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.hea.rth.domain.Card;
import com.hea.rth.service.CardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MainController {

	private final CardService cardService;
	
	@CrossOrigin
	@GetMapping("/card")
	public ResponseEntity<?> findAll(){
		
		WebClient webClient=WebClient.builder()
				                     .baseUrl("https://us.api.blizzard.com/hearthstone/cards/678?locale=ko_KR")
				                     .build();
		
		
		
		
		return new ResponseEntity<>(cardService.모두가져오기(),HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping("/card")
	public ResponseEntity<?> save(@RequestBody Card card){
		return new ResponseEntity<>(cardService.저장하기(card),HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/card/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		return new ResponseEntity<>(cardService.한건가져오기(id),HttpStatus.OK);
	}
	
	@CrossOrigin
	@PutMapping("/card/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Card card){
		return new ResponseEntity<>(cardService.수정하기(id,card),HttpStatus.OK);
	}
	
	@CrossOrigin
	@DeleteMapping("/card/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		return new ResponseEntity<>(cardService.삭제하기(id),HttpStatus.OK);
	}
}
//https://us.api.blizzard.com/hearthstone/cards/678?locale=ko_KR
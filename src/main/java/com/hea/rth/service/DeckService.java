package com.hea.rth.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hea.rth.domain.Card;
import com.hea.rth.domain.CardRepository;
import com.hea.rth.domain.Deck;
import com.hea.rth.domain.DeckRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeckService {

	private final DeckRepository deckRepository;
	
	@Transactional
	public Deck 저장하기(Deck deck) {
		return deckRepository.save(deck);
	}
	
	@Transactional(readOnly=true)//JPA변경감지 라는 내부기능 활성화 X, 
	//update시의 정합성을 유지해줌. insert의 유령데이터현상(팬텀현상)못막음
	public Deck 한건가져오기(Long id) {
		return deckRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!"));
						
	}
	
	@Transactional(readOnly=true)
	public List<Deck> 모두가져오기() {
		return deckRepository.findAll();
	}
	
	@Transactional
	public Deck 수정하기(Long id,Deck deck) {
		Deck deckEntity = deckRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!")); 
		//영속화(card 오브젝트)->영속화된 것은 영속성 컨텍스트에 보관됨
		deckEntity.setDeckName(deck.getDeckName());
		
		return deckEntity;
	}// 함수 종료=>트랜잭션 종료=>영속화 되어있는 데이터를 DB로 갱신(flush라고 함)=>commit =====>더티체킹
	
	public String 삭제하기(Long id) {
		deckRepository.deleteById(id);//오류가 터지면 익셉션을 탐
		
		return "ok";
	}
}

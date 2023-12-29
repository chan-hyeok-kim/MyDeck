package com.hea.rth.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hea.rth.domain.Card;
import com.hea.rth.domain.CardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CardService {

	private final CardRepository cardRepository;
	
	@Transactional
	public Card 저장하기(Card card) {
		return cardRepository.save(card);
	}
	
	@Transactional(readOnly=true)//JPA변경감지 라는 내부기능 활성화 X, 
	//update시의 정합성을 유지해줌. insert의 유령데이터현상(팬텀현상)못막음
	public Card 한건가져오기(Long id) {
		return cardRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!"));
						
	}
	
	@Transactional(readOnly=true)
	public List<Card> 모두가져오기(Long id,Card card) {
		return cardRepository.findAll();
	}
	
	@Transactional
	public Card 수정하기(Long id,Card card) {
		Card cardEntity = cardRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!")); 
		//영속화(card 오브젝트)->영속화된 것은 영속성 컨텍스트에 보관됨
		cardEntity.setCardNo(card.getCardNo());
		cardEntity.setCardName(card.getCardName());
		
		return cardEntity;
	}// 함수 종료=>트랜잭션 종료=>영속화 되어있는 데이터를 DB로 갱신(flush라고 함)
	
	public String 삭제하기(Long id) {
		return cardRepository;
	}
}
//https://us.api.blizzard.com/hearthstone/cards/678?locale=ko_KR
package com.hea.rth.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * 단위테스트(DB 관련된 Bean들이 IOC에 등록되면 됨)
 * Replace.ANY => 가짜 DB로 테스트
 * Replace.NONE => 실제 DB로 테스트 
 * @DataJpaTest => Repository들을 다 IOC 등록해둠
 * 
 */

@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class CardRepositoryUnitTest {

	@Autowired
	private CardRepository cardRepository;
	
	@Test
	public void save_테스트() {
		//given
		Card card=new Card(null,"카드 넣었다");
		
		//when
		Card cardEntity=cardRepository.save(card);
		
		//then
		assertEquals("카드 넣었다", cardEntity.getCardName());
	}
}

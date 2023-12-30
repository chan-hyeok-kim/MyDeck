package com.hea.rth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hea.rth.domain.Card;
import com.hea.rth.domain.CardRepository;

/**
 * 단위테스트(Service와 관련된 애들만 메모리에 띄움)
 * CardRepository=> 가짜 객체로 만들 수 있음
 * @Mock=>가짜 객체 생성
 * @InjectMocks CardRepository객체가 만들어질 때 CardServiceUnitTest 파일에 
 * @Mock로 등록된 모든 애들을 주입받는다
 * - 이렇게 해주는 이유
 * CardRepository를 호출하면 그안의 CardService는 null로 생성된다
 * 그러므로, @InjectMocks를 사용해서 가짜 객체를 주입받는 것
 * 
 * 이때, 해당 가짜 객체들은 Spring Beans가 아니라 가상의 Mockito환경에 만들어지기 때문에
 * 일반적인 Spring환경에서의 DI는 불가능하다
 */

@ExtendWith(MockitoExtension.class)
public class CardServiceUnitTest {

	@InjectMocks
	private CardService cardService;
	
	@Mock
	private CardRepository cardRepository;
	
	@Test
	public void save_테스트() {
		//given
		Card card=new Card(1L,"카드 넣었다");
		
		//stub
		when(cardRepository.save(card)).thenReturn(card);
		
		//when
		Card cardEntity=cardService.저장하기(card);
		
		//then
		assertEquals(cardEntity,card);
	}
	//실제 서비스에 따라 엄청 다르다
}

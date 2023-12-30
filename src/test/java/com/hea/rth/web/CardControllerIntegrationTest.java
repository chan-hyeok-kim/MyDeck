package com.hea.rth.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hea.rth.domain.Card;
import com.hea.rth.domain.CardRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 통합 테스트
 * 모든 bean들을 똑같이 IOC에 올리고 테스트하는 것
 * WebEnvironment.MOCK=실제 톰캣을 올리는 게 아니라,다른 톰캣으로 테스트
 * WebEnvironment.RANDOM_PORT=실제 톰캣으로 테스트
 * @AutoConfigureMockMvc MockMvc
 * @Transactional 각각의 테스트 함수가 종료될 때마다 트랜잭션을 Rollback한다
 * - 원래는 정상적으로 실행되면 Commit, 예외가 나면 Rollback해주는 어노테이션
 * - 하지만 Test메서드나 Test클래스에서는 무조건 롤백.
 * 
 */
@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CardControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private EntityManager entityManager;
	//JPA가 이걸 구현함
	
	@BeforeEach //JUnit4에서는 @Before. 각각 메서드 실행전에 이걸 먼저 실행해라
	public void init() {
//		List<Card> cards=new ArrayList<>();
//		cards.add(new Card(null,"스프링 부트"));
//		cards.add(new Card(null,"리액트"));
//		cards.add(new Card(null,"Junit"));
//		cardRepository.saveAll(cards);
		//위처럼 테스트케이스가 자동으로 만들어지게 해도 되고
		//각각 테스트케이스를 만들어서 해도 된다. 개인 취향
		
		entityManager.createNativeQuery("ALTER SEQUENCE SEQ_CARD RESTART WITH 1").executeUpdate();
	}
	
//	@AfterEach
//	public void end() {
//		cardRepository.deleteAll();
//	}
	
	@Test
	public void save_테스트() throws Exception {
		//given(테스트를 위한 준비)
		Card card=new Card(null,"스프링 따라하기");
		String content=new ObjectMapper().writeValueAsString(card);
		
		//when(테스트 실행)
		ResultActions resultAction=mockMvc.perform(post("/card")
		    .contentType(MediaType.APPLICATION_JSON_UTF8)
		    .content(content)
		    .accept(MediaType.APPLICATION_JSON_UTF8));
		
		//then(검증)
		resultAction
		  .andExpect(status().isCreated()) //201응답을 기대한다
		  .andExpect(jsonPath("$.cardName").value("스프링 따라하기"))
		  .andDo(MockMvcResultHandlers.print()); //json데이터 응답으로 오기 때문에
		
	}
	
	@Test
	public void findAll_테스트() throws Exception{
		//given
		List<Card> cards=new ArrayList<>();
		cards.add(new Card(null,"스프링 부트"));
		cards.add(new Card(null,"리액트"));
		cards.add(new Card(null,"Junit"));
		cardRepository.saveAll(cards);
		
		//when
		ResultActions resultActions=mockMvc.perform(get("/card")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		//then
		resultActions
		  .andExpect(status().isOk())
		  .andExpect(jsonPath("$.[2].cardNo").value(3L))
		  .andExpect(jsonPath("$", Matchers.hasSize(3)))
		  .andExpect(jsonPath("$.[0].cardName").value("스프링 부트"))
		  .andDo(MockMvcResultHandlers.print());
		  
	}
	
	@Test
	public void findById_테스트() throws Exception{
		//given
		List<Card> cards=new ArrayList<>();
		cards.add(new Card(null,"스프링 부트"));
		cards.add(new Card(null,"리액트"));
		cards.add(new Card(null,"Junit"));
		cardRepository.saveAll(cards);
		
		Long id=2L;
		
		//when
		ResultActions resultActions=mockMvc.perform(get("/card/{id}",id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		//then
		resultActions
		  .andExpect(status().isOk())
		  .andExpect(jsonPath("$.cardName").value("리액트"))
		  .andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void update_테스트() throws Exception{
		//given
		List<Card> cards=new ArrayList<>();
		cards.add(new Card(null,"스프링 부트"));
		cards.add(new Card(null,"리액트"));
		cards.add(new Card(null,"Junit"));
		cardRepository.saveAll(cards);
		
		Long id=3L;
		Card card=new Card(null,"자바 따라하기");
		String content=new ObjectMapper().writeValueAsString(card);
		
		
		//when
		ResultActions resultActions=mockMvc.perform(put("/card/{id}",id)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		//then
		resultActions
		  .andExpect(status().isOk())
		  .andExpect(jsonPath("$.cardNo").value(3L))
		  .andExpect(jsonPath("$.cardName").value("자바 따라하기"))
		  .andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void delete_테스트() throws Exception{
		//given
		List<Card> cards=new ArrayList<>();
		cards.add(new Card(null,"스프링 부트"));
		cards.add(new Card(null,"리액트"));
		cards.add(new Card(null,"Junit"));
		cardRepository.saveAll(cards);
		Long id=1L;
		
		
		//when
		ResultActions resultAction=mockMvc.perform(delete("/card/{id}",id)
				.accept(MediaType.TEXT_PLAIN));
		
		//then
		resultAction
		  .andExpect(status().isOk())
		  .andDo(MockMvcResultHandlers.print());
		
		MvcResult reqMvcResult= resultAction.andReturn();
		String result=reqMvcResult.getResponse().getContentAsString();
		
		assertEquals("ok", result);
		
	}
}

package com.hea.rth.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hea.rth.domain.Card;

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
	
}

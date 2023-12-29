package com.hea.rth.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hea.rth.domain.Card;
import com.hea.rth.service.CardService;

import lombok.extern.slf4j.Slf4j;

// 단위테스트(Controller관련 로직만 띄우기)
// 컨트롤러 관련 로직.(Controller,Filter, ControllerAdvice 등등을 메모리에 올림)

// 빠른 대신에 웹 전체를 실행했을 때도 제대로 돌아간다고 보긴 어려움

@Slf4j
@WebMvcTest
//컨트롤러 관련 테스트 어노테이션
public class CardControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean//IOC환경에 bean등록됨
	private CardService cardService;
	
	/**
	 * BDDMockito 패턴
	 * given,when,then으로 행동을 지정함
	 * 그걸 '스텁'이라 한다
	 * - 요청에 대한 응답을 단순하게 미리 정의해둔 것
	 * 여기선 서비스가 mock(가짜 객체)이기 때문에 서비스 실행결과를 미리 스텁으로 만들어준 것
	 */
	
	@Test
	public void save_테스트() throws Exception {
		//given(테스트를 위한 준비)
		Card card=new Card(null,"스프링 따라하기");
		String content=new ObjectMapper().writeValueAsString(card);
		when(cardService.저장하기(card)).thenReturn(new Card(1L,"스프링 따라하기"));
		
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

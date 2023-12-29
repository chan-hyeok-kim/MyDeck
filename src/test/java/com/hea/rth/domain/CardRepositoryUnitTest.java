package com.hea.rth.domain;

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
@AutoConfigureTestDatabase(replace = Replace.ANY)
@DataJpaTest
public class CardRepositoryUnitTest {

	@Autowired
	private CardRepository cardRepository;
}

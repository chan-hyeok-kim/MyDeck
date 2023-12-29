package com.hea.rth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Card {

	@Id//PK를 해당 변수로 하겠다는 뜻
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_CARD")
	@SequenceGenerator(name = "SEQ_CARD",sequenceName = "SEQ_CARD",allocationSize = 1)
	private Long cardNo;
    private String cardName;
}

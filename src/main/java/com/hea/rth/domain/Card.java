package com.hea.rth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Card {

	@Id//PK를 해당 변수로 하겠다는 뜻
	private Long cardNo;
    private String cardName;
}

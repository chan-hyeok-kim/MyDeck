package com.hea.rth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Card {

	@Id//PK를 해당 변수로 하겠다는 뜻
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cardNo;
    private String cardName;
    private String cardContents;
}

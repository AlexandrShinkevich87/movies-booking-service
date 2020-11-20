package com.booking.movies.entity;

import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "user_account")
public class UserAccount  {
	@Id
	@SequenceGenerator(name = "USER_ACCOUNT_SEQ", sequenceName = "USER_ACCOUNT_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ACCOUNT_SEQ")
	private Long id;
	@Column
	private double balance;
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
}

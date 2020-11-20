package com.booking.movies.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = "name")
@Getter
@Entity
@Table(name = "event")
public class Event {
	@Id
	@SequenceGenerator(name = "EVENT_SEQ", sequenceName = "EVENT_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_ID_SEQ")
	private Long id;
	@Column
	private String name;
	@JsonBackReference
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<MovieSession> movieSessions = new HashSet<>();
	@Column
	private double basePrice;
	@Column
	@Enumerated(EnumType.STRING)
	private EventRating rating;
	@Column
	private double ticketPrice;
}

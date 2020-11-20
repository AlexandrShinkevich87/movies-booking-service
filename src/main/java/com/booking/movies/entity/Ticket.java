package com.booking.movies.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"dateTime", "event", "seat"})
@Entity
@Table(name = "ticket")
public class Ticket implements Comparable<Ticket> {
	@Id
	@SequenceGenerator(name = "TICKET_SEQ", sequenceName = "TICKET_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TICKET_SEQ")
	private Long id;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@JsonManagedReference
	@ManyToOne
	private Event event;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column
	private LocalDateTime dateTime;
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Seat seat;

	public Ticket(Event event, LocalDateTime ticketBookedDateTime, Seat seat, User user) {
		this.event = event;
		this.dateTime = ticketBookedDateTime;
		this.seat = seat;
		this.user = user;
	}

	@Override
	public int compareTo(Ticket other) {
		if (other == null) {
			return 1;
		}
		int result = dateTime.compareTo(other.getDateTime());

		if (result == 0) {
			result = event.getName().compareTo(other.getEvent().getName());
		}
		if (result == 0) {
			result = Integer.compare(seat.getSeatNumber(), other.getSeat().getSeatNumber());
		}
		return result;
	}

}

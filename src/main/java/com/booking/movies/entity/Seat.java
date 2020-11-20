package com.booking.movies.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@EqualsAndHashCode(of = {"seatNumber", "seatType", "isAvailable"})
@ToString(of = {"seatNumber", "seatType", "isAvailable"})
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @SequenceGenerator(name = "SEAT_SEQ", sequenceName = "SEAT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEAT_SEQ")
    private Long id;
    @Column
    private int seatNumber;
    @Getter
    @Column
    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    @Column
    private boolean isAvailable;
    @JsonManagedReference
    @ManyToOne
    private Auditorium auditorium;
}

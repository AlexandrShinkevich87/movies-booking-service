package com.booking.movies.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(of = {"name"})
@Entity
@Table(name = "auditorium")
public class Auditorium {
    @Id
    @SequenceGenerator(name = "AUDITORIUM_SEQ", sequenceName = "AUDITORIUM_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDITORIUM_SEQ")
    private Long id;

    @Column
    private String name;
    @Column
    private long numberOfSeats;
    @JsonBackReference
    @OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seat> seats = new HashSet<>();
}

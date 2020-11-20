package com.booking.movies.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = {"airDate", "auditorium"})
@ToString(of = {"airDate", "auditorium"})
@Entity
@Table(name = "movie_session")
public class MovieSession {
    @Id
    @SequenceGenerator(name = "MOVIE_SESSION_SEQ", sequenceName = "MOVIE_SESSION_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVIE_SESSION_SEQ")
    private Long id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column
    private LocalDateTime airDate;
    @JsonIgnore
    @ManyToOne
    private Auditorium auditorium;
    @JsonIgnore
    @ManyToOne
    private Event event;
}

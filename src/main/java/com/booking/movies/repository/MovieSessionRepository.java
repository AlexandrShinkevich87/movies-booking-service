package com.booking.movies.repository;

import com.booking.movies.entity.Event;
import com.booking.movies.entity.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {
    MovieSession getByEvent(Event event);
}

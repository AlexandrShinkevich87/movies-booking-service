package com.booking.movies.repository;

import com.booking.movies.entity.Event;
import com.booking.movies.entity.Ticket;
import com.booking.movies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEventAndDateTime(Event event, LocalDateTime dateTime);

    List<Ticket> getByUser(User user);

    List<Ticket> getByEvent(Event event);
}

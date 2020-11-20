package com.booking.movies.repository;

import com.booking.movies.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

//    @Query("select e from Event e JOIN MovieSession ms on e.id = ms.event.id where ms.airDate >= now() and ms.airDate <= :airDate")
//    Page<Event> findAllWithAirDateBefore(@Param("airDate") LocalDateTime airDate, Pageable pageable);

    @Query("select e from Event e JOIN MovieSession ms on e.id = ms.event.id where ms.airDate >= :from and ms.airDate < :to")
    Page<Event> getForDateRange(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, Pageable pageable);

    Event getByName(String name);
}
